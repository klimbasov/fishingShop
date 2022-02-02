package com.jwd.fShop.dao.impl;

import com.jwd.fShop.dao.OrderDao;
import com.jwd.fShop.dao.config.DataBaseConfig;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.domain.Order;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductBunch;
import com.jwd.fShop.domain.User;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class OrderDaoImplTest {

    private static final String PROPERTIES_PATH = "db_test.properties";
    private static final String INIT_PROPERTIES_PATH = "dbTestInit.properties";
    private static final String INIT_SCRIPT_PATH = "src/test/resources/scripts/TestDatabaseScript.sql";


    private OrderDao orderDao;
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
        prepareTables();
        if (isNonInit) {
            orderDao = new OrderDaoImpl(PROPERTIES_PATH);
        }
        isNonInit = false;
    }

    private void createTestDatabase() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(INIT_SCRIPT_PATH));
        scriptRunner.runScript(reader);
    }


    @Test
    void save() throws SQLException, DaoException {
        Order actual;
        Order expected = new Order.Builder()
                .setUserId(1)
                .setProductBunchList(Arrays.asList(new ProductBunch(1, 1),
                        new ProductBunch(2, 2)))
                .setOrderingDate(new Date(Date.from(Instant.now()).getTime()))
                .setOrderingTime(new Time(Time.from(Instant.now()).getTime()))
                .build();

        orderDao.save(expected);

        ResultSet resultSet = dataBaseConfig.getConnection()
                .createStatement()
                .executeQuery("SELECT u.userId, o.quantity, o.productId from fshop_test.usersorders u join fshop_test.ordersproducts o on u.orderId = o.orderId WHERE o.orderId = 1;");


        List<ProductBunch> productBunches = new LinkedList<>();
        int userId = 0;
        while (resultSet.next()) {
            userId = resultSet.getInt("userId");
            productBunches.add(new ProductBunch(
                    resultSet.getInt("productId"),
                    resultSet.getInt("quantity")));
        }
        actual = new Order.Builder().setUserId(userId).setProductBunchList(productBunches).build();

        assertEquals(expected.getUserId(), actual.getUserId());
        assertIterableEquals(expected.getProductBunchList(), actual.getProductBunchList());
    }

    @Test
    void getById() throws SQLException, DaoException {
        Order actual;
        Order expected = new Order.Builder()
                .setOrderingDate(new Date(Date.from(Instant.now()).getTime()))
                .setOrderingTime(new Time(Time.from(Instant.now()).getTime()))
                .setUserId(1)
                .setProductBunchList(Arrays.asList(
                        new ProductBunch(2, 5),
                        new ProductBunch(1, 10)
                )).build();


        dataBaseConfig.getConnection()
                .createStatement()
                .execute("insert into fshop_test.usersOrders (userId, orderingDate) values (1, CURDATE())");

        dataBaseConfig.getConnection()
                .createStatement()
                .execute("insert into fshop_test.ordersproducts (orderId, productId, quantity) values (1, 2, 5), (1, 1, 10);");

        actual = orderDao.getById(1);

        assertEquals(expected.getOrderingDate(), actual.getOrderingDate());
        assertEquals(expected.getUserId(), actual.getUserId());
        assertIterableEquals(expected.getProductBunchList(), actual.getProductBunchList());
    }

    @Test
    void get() throws SQLException, DaoException {
        Order order1 = new Order.Builder().
                setId(1).
                setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                setUserId(1).
                build();

        Order order2 = new Order.Builder().
                setId(2).
                setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                setUserId(1).
                build();
        Order order3 = new Order.Builder().
                setId(3).
                setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                setUserId(2).
                build();

        List<Order> expectedMultipleBunches = Arrays.asList(order1, order2);
        List<Order> actualMultipleBunches;
        List<Order> expectedSingleBunches = Collections.singletonList(order3);
        List<Order> actualSingleBunches;
        List<Order> expectedNoBunches = new LinkedList<>();
        List<Order> actualNoBunches;


        dataBaseConfig.getConnection()
                .createStatement()
                .execute("insert into fshop_test.usersOrders (userId, orderingDate) values (1, CURDATE()), (1, curdate()), (2, curdate())");

        dataBaseConfig.getConnection()
                .createStatement()
                .execute("insert into fshop_test.ordersproducts (orderId, productId, quantity) values (1, 2, 5), (1, 1, 10), (2, 2, 10), (2, 1, 20), (3, 1, 3);");

        actualMultipleBunches = orderDao.get(1);
        actualSingleBunches = orderDao.get(2);
        actualNoBunches = orderDao.get(3);

        assertIterableEquals(expectedMultipleBunches, actualMultipleBunches);
        assertIterableEquals(expectedSingleBunches, actualSingleBunches);
        assertIterableEquals(expectedNoBunches, actualNoBunches);
    }

    @Test
    void getSet() throws SQLException, DaoException {
        Order order1 = new Order.Builder().
                setId(1).
                setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                setUserId(1).
                build();

        Order order2 = new Order.Builder().
                setId(2).
                setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                setUserId(1).
                build();
        Order order3 = new Order.Builder().
                setId(3).
                setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                setUserId(1).
                build();

        List<Order> expectedMultipleBunches = Arrays.asList(order2, order3);
        List<Order> actualMultipleBunches;
        List<Order> expectedSingleBunches = Collections.singletonList(order3);
        List<Order> actualSingleBunches;
        List<Order> expectedOutOfRange = new LinkedList<>();
        List<Order> actualOutOfRange;


        dataBaseConfig.getConnection()
                .createStatement()
                .execute("insert into fshop_test.usersOrders (userId, orderingDate) values (1, CURDATE()), (1, curdate()), (1, curdate())");

        dataBaseConfig.getConnection()
                .createStatement()
                .execute("insert into fshop_test.ordersproducts (orderId, productId, quantity) values (1, 2, 5), (1, 1, 10), (2, 2, 10), (2, 1, 20), (3, 1, 3);");

        actualMultipleBunches = orderDao.getSet(1, 1, 2);
        actualSingleBunches = orderDao.getSet(1, 2, 1);
        actualOutOfRange = orderDao.getSet(1, 5, 2);

        assertIterableEquals(expectedMultipleBunches, actualMultipleBunches);
        assertIterableEquals(expectedSingleBunches, actualSingleBunches);
        assertIterableEquals(expectedOutOfRange, actualOutOfRange);
    }

    @Test
    void getQuantity() throws SQLException, DaoException {
        Order order1 = new Order.Builder().
                setId(1).
                setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                setUserId(1).
                build();

        Order order2 = new Order.Builder().
                setId(2).
                setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                setUserId(1).
                build();
        Order order3 = new Order.Builder().
                setId(3).
                setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                setUserId(2).
                build();

        int[] expected = {2, 1, 0};
        int[] actual = new int[3];

        dataBaseConfig.getConnection()
                .createStatement()
                .execute("insert into fshop_test.usersOrders (userId, orderingDate) values (1, CURDATE()), (1, curdate()), (2, curdate())");

        dataBaseConfig.getConnection()
                .createStatement()
                .execute("insert into fshop_test.ordersproducts (orderId, productId, quantity) values (1, 2, 5), (1, 1, 10), (2, 2, 10), (2, 1, 20), (3, 1, 3);");

        actual[0] = orderDao.getQuantity(1);
        actual[1] = orderDao.getQuantity(2);
        actual[2] = orderDao.getQuantity(3);

        for (int counter = 0; counter < actual.length; counter++) {
            assertEquals(expected[counter], actual[counter]);
        }

    }

    private String getInsertMultipleStatement(Product... products) {
        String qslQueryString = null;
        if (products.length >= 1) {

            StringBuilder sql = new StringBuilder().append("INSERT INTO fShop_test.products (name, price, quantity, typeId, visible) VALUES ");
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

    private String getInsertMultipleStatement(User... users) {
        String qslQueryString = null;
        if (users.length >= 1) {

            StringBuilder sql = new StringBuilder().append("INSERT INTO fShop_test.users (name, hashedPass, roleId, registrationDate, registrationTime) VALUES ");
            for (int counter = 0; counter < users.length; counter++) {
                if (counter > 0) {
                    sql.append(',');
                }
                sql.append(" (").append("'").append(users[counter].getName()).append("'").append(", ").
                        append("'").append(users[counter].getName()).append("'").append(", ").
                        append(users[counter].getRole()).append(", ").
                        append("'").append(users[counter].getRegistrationDate()).append("'").append(", ").
                        append("'").append(users[counter].getRegistrationTime()).append("'").append(")");
            }
            sql.append(';');
            qslQueryString = sql.toString();
        }
        return qslQueryString;
    }

    private void prepareTables() throws SQLException {
        Product product1 = new Product.Builder()
                .setName("product1")
                .setPrice(12.4f)
                .setQuantity(31)
                .setProductType(3)
                .setVisible(true)
                .build();

        Product product2 = new Product.Builder()
                .setName("product2")
                .setPrice(12.4f)
                .setQuantity(31)
                .setProductType(3)
                .setVisible(true)
                .build();

        User user1 = new User.Builder()
                .setName("user1")
                .setHashedPassword("12345")
                .setRole(1)
                .setRegistrationDate(new Date(Date.from(Instant.now()).getTime()))
                .setRegistrationTime(new Time(Time.from(Instant.now()).getTime()))
                .build();
        User user2 = new User.Builder()
                .setName("user2")
                .setHashedPassword("12345")
                .setRole(1)
                .setRegistrationDate(new Date(Date.from(Instant.now()).getTime()))
                .setRegistrationTime(new Time(Time.from(Instant.now()).getTime()))
                .build();

        dataBaseConfig.getConnection()
                .createStatement()
                .execute(getInsertMultipleStatement(product1, product2));
        dataBaseConfig.getConnection()
                .createStatement()
                .execute(getInsertMultipleStatement(user1, user2));
    }
}