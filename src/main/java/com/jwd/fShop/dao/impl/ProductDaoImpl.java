package com.jwd.fShop.dao.impl;

import com.jwd.fShop.dao.ProductDao;
import com.jwd.fShop.dao.connectionPool.ConnectionPool;
import com.jwd.fShop.dao.connectionPool.ConnectionWrapper;
import com.jwd.fShop.dao.constant.ProductSqlNames;
import com.jwd.fShop.dao.exception.ConnectionPoolException;
import com.jwd.fShop.dao.exception.ConnectionWrapperException;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.dao.util.QueryFactory;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductFilter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static com.jwd.fShop.dao.util.ArgumentValidator.validate;
import static com.jwd.fShop.dao.util.ArgumentValidator.validateId;
import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

public class ProductDaoImpl implements ProductDao {
    private final ConnectionPool connectionPool;

    public ProductDaoImpl(final String path) throws FatalDaoException {
        try {
            connectionPool = ConnectionPool.getInstance(path);
        } catch (ConnectionPoolException e) {
            throw new FatalDaoException(createExceptionMessage(), e);
        }
    }

    private static List<Product> cleanupProductList(List<Product> products) {
        List<Product> cleanProducts = new LinkedList<>();
        ListIterator<Product> listIterator = products.listIterator();
        while (listIterator.hasNext()) {
            Product product = listIterator.next();
            try {
                validate(product);
                cleanProducts.add(product);
            } catch (IllegalArgumentException exception) {
            }
        }
        return cleanProducts;
    }

    private static void setSingleObjectQueryParams(final PreparedStatement preparedStatement, final Product product) throws SQLException {
        preparedStatement.setString(1, product.getName());
        preparedStatement.setFloat(2, product.getPrice());
        preparedStatement.setInt(3, product.getQuantity());
        preparedStatement.setInt(4, product.getProductType());
        preparedStatement.setBoolean(5, product.getVisible());
    }

    private static void setLimitedSelectParams(PreparedStatement preparedStatement, ProductFilter productFilter, int offset, int size) throws SQLException {
        int counter = setSelectFilterParams(preparedStatement, productFilter);
        preparedStatement.setInt(counter, offset);
        preparedStatement.setInt(counter + 1, size);
    }

    private static void setSelectParams(final PreparedStatement preparedStatement, final ProductFilter productFilter) throws SQLException {
        setSelectFilterParams(preparedStatement, productFilter);
    }

    private static int setSelectFilterParams(final PreparedStatement preparedStatement, final ProductFilter productFilter) throws SQLException {
        int counter = 1;
        if (productFilter.isName()) {
            preparedStatement.setString(counter++, "%" + productFilter.getName() + "%");
        }
        if (productFilter.isId()) {
            preparedStatement.setInt(counter++, productFilter.getId());
        }
        if (productFilter.isProductType()) {
            preparedStatement.setInt(counter++, productFilter.getProductType());
        }
        if (productFilter.isHighPrice()) {
            preparedStatement.setFloat(counter++, productFilter.getHighPrice());
        }
        if (productFilter.isLowPrice()) {
            preparedStatement.setFloat(counter++, productFilter.getLowPrice());
        }
        if (productFilter.isVisibility()) {
            preparedStatement.setBoolean(counter++, productFilter.getVisibility());
        }
        return counter;
    }

    private static List<IdentifiedDTO<Product>> getSelectedProducts(final PreparedStatement preparedStatement) throws SQLException {
        List<IdentifiedDTO<Product>> products = new LinkedList<>();
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                products.add(new IdentifiedDTO<>(
                        resultSet.getInt(ProductSqlNames.ID_COLUMN_NAME),
                        new Product.Builder()
                                .setName(resultSet.getString(ProductSqlNames.NAME_COLUMN_NAME))
                                .setPrice(resultSet.getFloat(ProductSqlNames.PRICE_COLUMN_NAME))
                                .setQuantity(resultSet.getInt(ProductSqlNames.QUANTITY_COLUMN_NAME))
                                .setProductType(resultSet.getInt(ProductSqlNames.TYPE_ID_COLUMN_NAME))
                                .setVisible(resultSet.getBoolean(ProductSqlNames.VISIBLE_COLUMN_NAME))
                                .build()));
            }
        }
        return products;
    }

    @Override
    public int save(Product product) throws DaoException {
        validate(product);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.ProductSql.getInsertSingleSQL())) {

            setSingleObjectQueryParams(preparedStatement, product);

            return preparedStatement.executeUpdate();

        } catch (Exception exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }
    }

    @Override
    public int saveMany(final List<Product> products) throws DaoException {
        List<Product> cleanProducts;

        cleanProducts = cleanupProductList(products);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.ProductSql.getInsertMultipleSQL(cleanProducts.size()))) {


            ListIterator<Product> listIterator = cleanProducts.listIterator();
            int counter = 0;
            while (listIterator.hasNext()) {
                Product product = listIterator.next();
                preparedStatement.setString(5 * counter + 1, product.getName());
                preparedStatement.setFloat(5 * counter + 2, product.getPrice());
                preparedStatement.setInt(5 * counter + 3, product.getQuantity());
                preparedStatement.setInt(5 * counter + 4, product.getProductType());
                preparedStatement.setBoolean(5 * counter + 5, product.getVisible());
                counter++;
            }

            return preparedStatement.executeUpdate();
        } catch (Exception exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }
    }

    @Override
    public List<IdentifiedDTO<Product>> get(ProductFilter filter) throws DaoException {
        List<IdentifiedDTO<Product>> productList;

        validate(filter);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.ProductSql.getMultipleSelectSQL(filter))) {

            setSelectParams(preparedStatement, filter);

            productList = getSelectedProducts(preparedStatement);

        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }

        return productList;
    }

    @Override
    public void delete(int id) throws DaoException {

        validateId(id);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.ProductSql.getDeleteSql())) {

            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }
    }

    @Override
    public void update(Product product, int id) throws DaoException {

        validate(product);
        validateId(id);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.ProductSql.getUpdateSQL())) {


            setSingleObjectQueryParams(preparedStatement, product);
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();


        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }
    }

    @Override
    public List<IdentifiedDTO<Product>> getSet(final ProductFilter filter, int offset, int size) throws DaoException {
        List<IdentifiedDTO<Product>> productList;

        validate(filter);
        validate(offset, size);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.ProductSql.getMultipleLimitedSelectSQL(filter))) {
            setLimitedSelectParams(preparedStatement, filter, offset, size);

            productList = getSelectedProducts(preparedStatement);

        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }

        return productList;
    }

    @Override
    public int getQuantity(final ProductFilter filter) throws DaoException {
        int quantity;

        validate(filter);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.ProductSql.getCountSetsSql(filter))) {
            setSelectParams(preparedStatement, filter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                quantity = resultSet.getInt(1);
            }

        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }
        return quantity;
    }
}