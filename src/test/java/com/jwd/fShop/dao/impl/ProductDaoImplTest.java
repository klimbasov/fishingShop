package com.jwd.fShop.dao.impl;

import com.jwd.fShop.dao.ProductDao;
import com.jwd.fShop.dao.config.DataBaseConfig;
import com.jwd.fShop.dao.constant.ProductSqlNames;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductFilter;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoImplTest {

    private static final String PROPERTIES_PATH = "db_test.properties";
    private static final String INIT_PROPERTIES_PATH = "dbTestInit.properties";
    private static final String INIT_SCRIPT_PATH = "src/test/resources/scripts/TestDatabaseScript.sql";


    private ProductDao productDao;
    private DataBaseConfig dataBaseConfig;
    private ScriptRunner scriptRunner;
    private boolean isNonInit = true;

    @BeforeEach
    void setUp() throws FatalDaoException, SQLException, FileNotFoundException {
        if (isNonInit) {
            dataBaseConfig = new DataBaseConfig(INIT_PROPERTIES_PATH);
            Connection connection = dataBaseConfig.getConnection();
            scriptRunner = new ScriptRunner(connection);
        }
        createTestDatabase();
        if (isNonInit) {
            productDao = new ProductDaoImpl(PROPERTIES_PATH);
        }
        isNonInit = false;
    }

    private void createTestDatabase() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(INIT_SCRIPT_PATH));
        scriptRunner.runScript(reader);
    }

    @Test
    void save() throws SQLException {
        String name = "somePro";
        float price = 12.3f;
        int quantity = 101;
        int type = 3;
        boolean visible = true;

        Product product = new Product.Builder()
                .setName(name)
                .setPrice(price)
                .setQuantity(quantity)
                .setProductType(type)
                .setVisible(visible)
                .build();

        Product invalidProduct = new Product.Builder()
                .setName(name)
                .setPrice(price)
                .setQuantity(quantity)
                .setProductType(type)
                .build();

        List<Product> expected = Collections.singletonList(product);
        List<Product> actual = new LinkedList<>();


        assertDoesNotThrow(() -> productDao.save(product));
        assertThrows(IllegalArgumentException.class, () -> productDao.save(invalidProduct));

        ResultSet resultSet = dataBaseConfig.getConnection().createStatement().executeQuery("SELECT name, price, quantity, typeId, visible FROM fshop_test.products WHERE name LIKE '%somePro%'");

        while (resultSet.next()) {
            Product newProduct = new Product.Builder()
                    .setName(resultSet.getString("name"))
                    .setPrice(resultSet.getFloat("price"))
                    .setQuantity(resultSet.getInt("quantity"))
                    .setProductType(resultSet.getInt("typeId"))
                    .setVisible(resultSet.getBoolean("visible"))
                    .build();
            actual.add(newProduct);
        }

        assertIterableEquals(expected, actual);

    }

    @Test
    void saveMany() throws SQLException, DaoException {
        List<Product> products;
        Product product1 = new Product.Builder()
                .setName("product1")
                .setPrice(12.4f)
                .setQuantity(31)
                .setProductType(3)
                .setVisible(false)
                .build();

        Product product2Invalid = new Product.Builder()
                .setName("product2")
                .setPrice(1f)
                .setQuantity(1057)
                .setProductType(1)
                .build();

        Product product3 = new Product.Builder()
                .setName("product3")
                .setPrice(3.33f)
                .setQuantity(33)
                .setProductType(3)
                .setVisible(true)
                .build();

        Product product4Invalid = new Product.Builder()
                .setName("product4")
                .setPrice(1f)
                .setQuantity(1057)
                .setProductType(1)
                .build();

        products = Arrays.asList(product1, product2Invalid, product3, product4Invalid);

        assertEquals(2, productDao.saveMany(products));

        List<Product> expected = Arrays.asList(product1, product3);
        List<Product> actual = new LinkedList<>();

        ResultSet resultSet = dataBaseConfig.getConnection()
                .createStatement()
                .executeQuery("SELECT name, price, quantity, typeId, visible FROM fshop_test.products;");

        while (resultSet.next()) {
            Product newProduct = new Product.Builder()
                    .setName(resultSet.getString("name"))
                    .setPrice(resultSet.getFloat("price"))
                    .setQuantity(resultSet.getInt("quantity"))
                    .setProductType(resultSet.getInt("typeId"))
                    .setVisible(resultSet.getBoolean("visible"))
                    .build();
            actual.add(newProduct);
        }

        assertIterableEquals(expected, actual);
    }

    @Test
    void get() throws SQLException, DaoException {

        Product product1 = new Product.Builder()
                .setName("product1")
                .setPrice(12.4f)
                .setQuantity(31)
                .setProductType(3)
                .setVisible(false)
                .build();

        Product product2 = new Product.Builder()
                .setName("product2")
                .setPrice(1f)
                .setQuantity(1057)
                .setProductType(1)
                .setVisible(true)
                .build();

        Product product3 = new Product.Builder()
                .setName("product3")
                .setPrice(3.33f)
                .setQuantity(33)
                .setProductType(3)
                .setVisible(true)
                .build();

        Product product4 = new Product.Builder()
                .setName("product4")
                .setPrice(1f)
                .setQuantity(1057)
                .setProductType(1)
                .setVisible(false)
                .build();

        ProductFilter productFilterByName = new ProductFilter.Builder().setName("product").build();

        List<Product> expectedFilteringByName = Arrays.asList(product1, product2, product3, product4);
        List<Product> actualFilteringByName;

        dataBaseConfig.getConnection()
                .createStatement()
                .execute(getInsertMultipleStatement(product1, product2, product3, product4));

        actualFilteringByName = productDao.get(productFilterByName);

        assertIterableEquals(expectedFilteringByName, actualFilteringByName);

    }

    @Test
    void delete() throws SQLException, DaoException {
        boolean expected = false;
        boolean actual = !expected;
        int id = 0;
        Product product1 = new Product.Builder()
                .setName("product1")
                .setPrice(12.4f)
                .setQuantity(31)
                .setProductType(3)
                .setVisible(false)
                .build();

        dataBaseConfig.getConnection()
                .createStatement()
                .execute(getInsertMultipleStatement(product1));

        try (ResultSet resultSet = dataBaseConfig.getConnection()
                .createStatement()
                .executeQuery("SELECT id FROM fshop_test.products WHERE name = 'product1';")) {

            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        }

        productDao.delete(id);

        try (ResultSet resultSet = dataBaseConfig.getConnection()
                .createStatement()
                .executeQuery("SELECT visible FROM fshop_test.products WHERE name = 'product1';")) {
            if (resultSet.next()) {
                actual = resultSet.getBoolean("visible");
            }
        }


        assertEquals(expected, actual);

    }

    @Test
    void update() throws DaoException, SQLException {
        Product product1 = new Product.Builder()
                .setName("product1")
                .setPrice(12.4f)
                .setQuantity(31)
                .setProductType(3)
                .setVisible(false)
                .build();

        Product product2 = new Product.Builder()
                .setName("product2")
                .setPrice(1f)
                .setQuantity(1057)
                .setProductType(1)
                .setVisible(true)
                .build();

        Product product3 = new Product.Builder()
                .setName("product3")
                .setPrice(3.33f)
                .setQuantity(33)
                .setProductType(3)
                .setVisible(true)
                .build();

        Product product4 = new Product.Builder()
                .setName("product4")
                .setPrice(1f)
                .setQuantity(1057)
                .setProductType(1)
                .setVisible(false)
                .build();

        Product newProduct2 = new Product.Builder(product2).
                setQuantity(4).
                build();

        List<Product> expectedFilteringByName = Arrays.asList(product1, newProduct2, product3, product4);
        List<Product> actualFilteringByName = new LinkedList<>();

        dataBaseConfig.getConnection()
                .createStatement()
                .execute(getInsertMultipleStatement(product1, product2, product3, product4));

        productDao.update(newProduct2, 2);

        ResultSet resultSet = dataBaseConfig.getConnection()
                .createStatement()
                .executeQuery("SELECT * FROM fshop_test.products;");

        while (resultSet.next()) {
            actualFilteringByName.add(
                    new Product.Builder().
                            setName(resultSet.getString(ProductSqlNames.NAME_COLUMN_NAME)).
                            setPrice(resultSet.getFloat(ProductSqlNames.PRICE_COLUMN_NAME)).
                            setQuantity(resultSet.getInt(ProductSqlNames.QUANTITY_COLUMN_NAME)).
                            setProductType(resultSet.getInt(ProductSqlNames.TYPE_ID_COLUMN_NAME)).
                            setVisible(resultSet.getBoolean(ProductSqlNames.VISIBLE_COLUMN_NAME)).
                            build()
            );
        }

        assertIterableEquals(expectedFilteringByName, actualFilteringByName);
    }

    @Test
    void getSet() throws SQLException, DaoException {
        Product product1 = new Product.Builder()
                .setName("product1")
                .setPrice(12.4f)
                .setQuantity(31)
                .setProductType(3)
                .setVisible(false)
                .build();

        Product product2 = new Product.Builder()
                .setName("product2")
                .setPrice(1f)
                .setQuantity(1057)
                .setProductType(1)
                .setVisible(true)
                .build();

        Product product3 = new Product.Builder()
                .setName("product3")
                .setPrice(3.33f)
                .setQuantity(33)
                .setProductType(3)
                .setVisible(true)
                .build();

        Product product4 = new Product.Builder()
                .setName("product4")
                .setPrice(1f)
                .setQuantity(1057)
                .setProductType(1)
                .setVisible(false)
                .build();

        dataBaseConfig.getConnection()
                .createStatement()
                .execute(getInsertMultipleStatement(product1, product2, product3, product4));

        ProductFilter productFilterByName = new ProductFilter.Builder().setName("product").build();

        List<Product> expectedUsersSetWithOneElement = Collections.singletonList(product2);
        List<Product> expectedUsersSetWithLackOfElements = Collections.singletonList(product4);
        List<Product> expectedUsersSetWithSomeElements = Arrays.asList(product3, product4);
        List<Product> expectedUsersSetOutOfBound = new LinkedList<>();

        List<Product> actualUsersSetWithOneElement = productDao.getSet(productFilterByName, 1, 1);
        List<Product> actualUsersSetWithLackOfElements = productDao.getSet(productFilterByName, 3, 2);
        List<Product> actualUsersSetWithSomeElements = productDao.getSet(productFilterByName, 2, 2);
        List<Product> actualUsersSetOutOfBound = productDao.getSet(productFilterByName, 5, 3);

        assertIterableEquals(expectedUsersSetWithOneElement, actualUsersSetWithOneElement);
        assertIterableEquals(expectedUsersSetWithLackOfElements, actualUsersSetWithLackOfElements);
        assertIterableEquals(expectedUsersSetWithSomeElements, actualUsersSetWithSomeElements);
        assertIterableEquals(expectedUsersSetOutOfBound, actualUsersSetOutOfBound);
    }

    @Test
    void getSetQuantity() throws SQLException, DaoException {
        Product product1 = new Product.Builder()
                .setName("product1")
                .setPrice(1f)
                .setQuantity(31)
                .setProductType(3)
                .setVisible(false)
                .build();

        Product product2 = new Product.Builder()
                .setName("product2")
                .setPrice(2f)
                .setQuantity(1057)
                .setProductType(1)
                .setVisible(true)
                .build();

        Product product3 = new Product.Builder()
                .setName("product3")
                .setPrice(3f)
                .setQuantity(33)
                .setProductType(3)
                .setVisible(true)
                .build();

        Product product4 = new Product.Builder()
                .setName("product4")
                .setPrice(4f)
                .setQuantity(1057)
                .setProductType(1)
                .setVisible(false)
                .build();

        ProductFilter filterForAll = new ProductFilter.Builder().setName("product").build();
        ProductFilter filterForSingle = new ProductFilter.Builder().setPriceRange(1.5f, 2.5f).build();
        ProductFilter filterForSeveral = new ProductFilter.Builder().setPriceRange(1.5f, 3.5f).build();
        ProductFilter filterForNo = new ProductFilter.Builder().setPriceRange(4.5f, 9.5f).build();

        int[] expected = {4, 1, 2, 0};
        int[] actual = new int[4];


        dataBaseConfig.getConnection()
                .createStatement()
                .execute(getInsertMultipleStatement(product1, product2, product3, product4));

        actual[0] = productDao.getQuantity(filterForAll);
        actual[1] = productDao.getQuantity(filterForSingle);
        actual[2] = productDao.getQuantity(filterForSeveral);
        actual[3] = productDao.getQuantity(filterForNo);

        assertThrows(IllegalArgumentException.class, () -> productDao.getQuantity(null));

        for (int counter = 0; counter < actual.length; counter++) {
            assertEquals(expected[counter], actual[counter]);
        }

    }

    private String getInsertMultipleStatement(Product... products) {
        String qslQueryString = null;
        if (products.length >= 1) {

            StringBuilder sql = new StringBuilder().append("INSERT INTO fshop_test.products (name, price, quantity, typeId, visible) VALUES ");
            for (int counter = 0; counter < products.length; counter++) {
                if (counter > 0) {
                    sql.append(',');
                }
                sql.append(" (").append("'").append(products[counter].getName()).append("'").append(", ").
                        append(products[counter].getPrice()).append(", ").
                        append(products[counter].getQuantity()).append(", ").
                        append(products[counter].getProductType()).append(", ").
                        append(products[counter].getVisible()).append(")");
            }
            sql.append(';');
            qslQueryString = sql.toString();
        }
        return qslQueryString;
    }
}