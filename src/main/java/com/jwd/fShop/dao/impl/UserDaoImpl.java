package com.jwd.fShop.dao.impl;

import com.jwd.fShop.dao.UserDao;
import com.jwd.fShop.dao.connectionPool.ConnectionPool;
import com.jwd.fShop.dao.connectionPool.ConnectionWrapper;
import com.jwd.fShop.dao.exception.ConnectionPoolException;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.dao.util.QueryFactory;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.jwd.fShop.dao.constant.UserSqlNames.*;
import static com.jwd.fShop.dao.util.ArgumentValidator.validate;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class UserDaoImpl implements UserDao {
    private final ConnectionPool connectionPool;

    public UserDaoImpl(final String path) throws FatalDaoException {
        try {
            connectionPool = ConnectionPool.getInstance(path);
        } catch (ConnectionPoolException e) {
            throw new FatalDaoException(e.getMessage());
        }
    }

    private static void setUpdateParams(PreparedStatement preparedStatement, User user, int id) throws SQLException {
        if (nonNull(user.getName())) {
            preparedStatement.setString(1, user.getName());
        }
        if (nonNull(user.getHashedPassword())) {
            preparedStatement.setString(1, user.getHashedPassword());
        }
        if (nonNull(user.getRole())) {
            preparedStatement.setInt(1, user.getRole());
        }
        preparedStatement.setInt(2, id);
    }

    private static void setLimitedSelectParams(PreparedStatement preparedStatement, UserFilter userFilter, int offset, int size) throws SQLException {
        int counter = setSelectFilterParams(preparedStatement, userFilter);
        preparedStatement.setInt(counter, offset);
        preparedStatement.setInt(counter + 1, size);
    }

    private static void setSelectParams(final PreparedStatement preparedStatement, final UserFilter userFilter) throws Exception {
        setSelectFilterParams(preparedStatement, userFilter);
    }

    private static int setSelectFilterParams(final PreparedStatement preparedStatement, final UserFilter userFilter) throws SQLException {
        int counter = 1;
        if (userFilter.isUserSubname()) {
            if (userFilter.isFullName()) {
                preparedStatement.setString(counter++, userFilter.getUserSubname());
            } else {
                preparedStatement.setString(counter++, "%" + userFilter.getUserSubname() + "%");
            }
        }
        if (userFilter.isId()) {
            preparedStatement.setInt(counter++, userFilter.getId());
        }
        if (userFilter.isSubHashPass()) {
            preparedStatement.setString(counter++, userFilter.getSubHashPass());
        }
        if (userFilter.isHighDate()) {
            preparedStatement.setDate(counter++, userFilter.getHighDate());
        }
        if (userFilter.isLowDate()) {
            preparedStatement.setDate(counter++, userFilter.getLowDate());
        }
        if (userFilter.isHighTime()) {
            preparedStatement.setTime(counter++, userFilter.getHighTime());
        }
        if (userFilter.isLowTime()) {
            preparedStatement.setTime(counter++, userFilter.getLowTime());
        }
        if (userFilter.isRole()) {
            preparedStatement.setInt(counter++, userFilter.getRole());
        }
        return counter;
    }

    @Override
    public void save(User user) throws DaoException {

        validate(user);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement statement = connectionWrapper.getPreparedStatement(QueryFactory.UserSql.getInsertSingleSQL())) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getHashedPassword());
            statement.setInt(3, user.getRole());
            statement.setDate(4, user.getRegistrationDate());
            statement.setTime(5, user.getRegistrationTime());

            statement.executeUpdate();
        } catch (Exception exception) {
            throw new DaoException("in ProductDaoImpl: in setUser(User) while getting connection from connection pool", exception);
        }
    }

    @Override
    public LinkedList<User> get(UserFilter filter) throws DaoException {
        LinkedList<User> users = new LinkedList<>();

        validate(filter);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.UserSql.getMultipleSelectSQL(filter))) {

            setSelectParams(preparedStatement, filter);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(new User.Builder()
                            .setId(resultSet.getInt(ID_COLUMN_NAME))
                            .setName(resultSet.getNString(USERNAME_COLUMN_NAME))
                            .setRegistrationDate(resultSet.getDate(REGISTRATION_DATE_COLUMN_NAME))
                            .setRegistrationTime(resultSet.getTime(REGISTRATION_TIME_COLUMN_NAME))
                            .setRole(resultSet.getInt(ROLE_COLUMN_NAME))
                            .build());
                }
            }
        } catch (Exception exception) {
            throw new DaoException("in ProductDaoImpl: in getUser(String) while getting prepared statement", exception);
        }
        return users;
    }

    @Override
    public void update(User user, int id) throws DaoException {

        validate(id);

        if (isNull(user)) {
            throw new IllegalArgumentException("in ProductDaoImpl: in getUser(String): user was null");
        }

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.UserSql.getUpdateSQL(user))) {

            setUpdateParams(preparedStatement, user, id);

            preparedStatement.executeUpdate();

        } catch (Exception exception) {
            throw new DaoException("in ProductDaoImpl: in getUser(String)", exception);
        }
    }

    @Override
    public List<User> getSet(UserFilter filter, int offset, int size) throws DaoException {
        LinkedList<User> users = new LinkedList<>();

        validate(filter);
        validate(offset, size);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.UserSql.getMultipleLimitedSelectSQL(filter))) {
            setLimitedSelectParams(preparedStatement, filter, offset, size);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(new User.Builder()
                            .setId(resultSet.getInt(ID_COLUMN_NAME))
                            .setName(resultSet.getNString(USERNAME_COLUMN_NAME))
                            .setRegistrationDate(resultSet.getDate(REGISTRATION_DATE_COLUMN_NAME))
                            .setRegistrationTime(resultSet.getTime(REGISTRATION_TIME_COLUMN_NAME))
                            .setRole(resultSet.getInt(ROLE_COLUMN_NAME))
                            .build());
                }
            }
        } catch (Exception exception) {
            throw new DaoException("in ProductDaoImpl: in getUser(String) while getting prepared statement", exception);
        }
        return users;
    }

    @Override
    public int getQuantity(UserFilter filter) throws DaoException {
        int quantity;

        validate(filter);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.UserSql.getCountSetsSql(filter))) {
            setSelectParams(preparedStatement, filter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                quantity = resultSet.getInt(1);
            }

        } catch (Exception exception) {
            throw new DaoException("in ProductDaoImpl: in getSetQuantity(UserFilter, int)", exception);
        }
        return quantity;
    }
}