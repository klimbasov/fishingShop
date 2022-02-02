package com.jwd.fShop.dao.impl;

import com.jwd.fShop.dao.OrderDao;
import com.jwd.fShop.dao.connectionPool.ConnectionPool;
import com.jwd.fShop.dao.connectionPool.ConnectionWrapper;
import com.jwd.fShop.dao.constant.OrdersProductsSqlNames;
import com.jwd.fShop.dao.constant.UsersOrdersSqlNames;
import com.jwd.fShop.dao.exception.ConnectionPoolException;
import com.jwd.fShop.dao.exception.ConnectionWrapperException;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.dao.util.QueryFactory;
import com.jwd.fShop.domain.Order;
import com.jwd.fShop.domain.ProductBunch;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.jwd.fShop.dao.util.ArgumentValidator.validate;
import static com.jwd.fShop.dao.util.ArgumentValidator.validateId;

public class OrderDaoImpl implements OrderDao {

    private final ConnectionPool connectionPool;

    public OrderDaoImpl(final String path) throws FatalDaoException {
        try {
            this.connectionPool = ConnectionPool.getInstance(path);
        } catch (ConnectionPoolException e) {
            throw new FatalDaoException(e.getMessage());
        }
    }

    @Override
    public void save(Order order) throws DaoException {
        int lastInsertId;

        validate(order);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool, false);
             PreparedStatement userOrderPreparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.OrderSql.getInsertInUsersOrdersSql());
             PreparedStatement lastInsertIdPreparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.OrderSql.getSelectLastIdSql());
             PreparedStatement orderProductsPreparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.OrderSql.getInsertSingleInOrdersProductsSql(order.getProductBunchList().size()))) {

            userOrderPreparedStatement.setInt(1, order.getUserId());
            userOrderPreparedStatement.setDate(2, order.getOrderingDate());
            userOrderPreparedStatement.execute();

            ResultSet resultSet = lastInsertIdPreparedStatement.executeQuery();
            resultSet.next();
            lastInsertId = resultSet.getInt(1);

            int length = order.getProductBunchList().size();
            List<ProductBunch> productBunches = order.getProductBunchList();
            for (int counter = 0; counter < length; counter++) {
                orderProductsPreparedStatement.setInt(counter * 3 + 1, lastInsertId);
                orderProductsPreparedStatement.setInt(counter * 3 + 2, productBunches.get(counter).getQuantity());
                orderProductsPreparedStatement.setInt(counter * 3 + 3, productBunches.get(counter).getProductId());
            }

            orderProductsPreparedStatement.executeUpdate();

            connectionWrapper.commit();

        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException("In " + this.getClass().getName() + " in void save(Order)", exception);
        }
    }

    @Override
    public Order getById(int id) throws DaoException {
        Order order = null;

        validateId(id);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.OrderSql.getSelectOrderProductsSql())) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Order.Builder orderBuilder = new Order.Builder().
                            setId(id).
                            setOrderingDate(resultSet.getDate(UsersOrdersSqlNames.ORDERING_DATE_COLUMN_NAME)).
                            setUserId(resultSet.getInt(UsersOrdersSqlNames.USER_ID_COLUMN_NAME));
                    List<ProductBunch> productBunches = new LinkedList<>();
                    do {
                        productBunches.add(new ProductBunch(
                                resultSet.getInt(OrdersProductsSqlNames.PRODUCT_ID_COLUMN_NAME),
                                resultSet.getInt(OrdersProductsSqlNames.QUANTITY_COLUMN_NAME)
                        ));
                    } while (resultSet.next());
                    orderBuilder.setProductBunchList(productBunches);

                    order = orderBuilder.build();
                }
            }


        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(exception);
        }
        return order;
    }

    @Override
    public List<Order> get(int userId) throws DaoException {
        List<Order> orders = new LinkedList<>();

        validateId(userId);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.OrderSql.getSelectUserOrdersSql())) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(
                            new Order.Builder().
                                    setUserId(userId).
                                    setId(resultSet.getInt(UsersOrdersSqlNames.ID_COLUMN_NAME)).
                                    setOrderingDate(resultSet.getDate(UsersOrdersSqlNames.ORDERING_DATE_COLUMN_NAME)).
                                    build()
                    );
                }
            }
        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(exception);
        }
        return orders;
    }

    @Override
    public List<Order> getSet(int userId, int offset, int size) throws DaoException {
        List<Order> orders = new LinkedList<>();

        validateId(userId);
        validate(size, offset);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.OrderSql.getLimitedSqlSelectUserOrders())) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, size);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(
                            new Order.Builder().
                                    setUserId(userId).
                                    setId(resultSet.getInt(UsersOrdersSqlNames.ID_COLUMN_NAME)).
                                    setOrderingDate(resultSet.getDate(UsersOrdersSqlNames.ORDERING_DATE_COLUMN_NAME)).
                                    build()
                    );
                }
            }

        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(exception);
        }
        return orders;
    }

    @Override
    public int getQuantity(int userId) throws DaoException {
        int quantity;

        validateId(userId);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.OrderSql.getCountSql())) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                quantity = resultSet.getInt(1);
            }

        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException("in ProductDaoImpl: in getSetQuantity(UserFilter, int)", exception);
        }
        return quantity;
    }
}
