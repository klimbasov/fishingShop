package com.jwd.fShop.dao.impl;

import com.jwd.fShop.dao.UserDao;
import com.jwd.fShop.dao.connectionPool.ConnectionPool;
import com.jwd.fShop.dao.connectionPool.ConnectionWrapper;
import com.jwd.fShop.dao.exception.ConnectionPoolException;
import com.jwd.fShop.dao.exception.ConnectionWrapperException;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.dao.util.QueryFactory;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.jwd.fShop.dao.constant.UserSqlNames.*;
import static com.jwd.fShop.dao.util.ArgumentValidator.validate;
import static com.jwd.fShop.dao.util.StatementFiller.*;
import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class UserDaoImpl implements UserDao {
    private final ConnectionPool connectionPool;

    public UserDaoImpl(final String path) throws FatalDaoException {
        try {
            connectionPool = ConnectionPool.getInstance(path);
        } catch (ConnectionPoolException e) {
            throw new FatalDaoException(createExceptionMessage(), e);
        }
    }

    private List<IdentifiedDTO<User>> getSelectedUsers(PreparedStatement statement) throws SQLException {
        List<IdentifiedDTO<User>> users = new LinkedList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(
                        new IdentifiedDTO<>(
                                resultSet.getInt(ID_COLUMN_NAME),
                                new User.Builder()
                                        .setName(resultSet.getNString(USERNAME_COLUMN_NAME))
                                        .setRegistrationDate(resultSet.getDate(REGISTRATION_DATE_COLUMN_NAME))
                                        .setRegistrationTime(resultSet.getTime(REGISTRATION_TIME_COLUMN_NAME))
                                        .setRole(resultSet.getInt(ROLE_COLUMN_NAME))
                                        .build())
                );
            }
        }
        return users;
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
        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }
    }

    @Override
    public List<IdentifiedDTO<User>> get(UserFilter filter) throws DaoException {
        List<IdentifiedDTO<User>> users;

        validate(filter);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.UserSql.getMultipleSelectSQL(filter))) {


            setSelectParams(preparedStatement, filter);

            users = getSelectedUsers(preparedStatement);

        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }
        return users;
    }

    @Override
    public void update(User user, int id) throws DaoException {

        validate(id);

        if (isNull(user)) {
            throw new IllegalArgumentException(createExceptionMessage(" user was null"));
        }

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.UserSql.getUpdateSQL(user))) {

            setUpdateParams(preparedStatement, user, id);

            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }
    }

    @Override
    public List<IdentifiedDTO<User>> getSet(UserFilter filter, int offset, int size) throws DaoException {
        List<IdentifiedDTO<User>> users;

        validate(filter);
        validate(offset, size);

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement preparedStatement = connectionWrapper.getPreparedStatement(QueryFactory.UserSql.getMultipleLimitedSelectSQL(filter))) {
            setLimitedSelectParams(preparedStatement, filter, offset, size);

            users = getSelectedUsers(preparedStatement);

        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(createExceptionMessage(), exception);
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

        } catch (SQLException | ConnectionWrapperException exception) {
            throw new DaoException(createExceptionMessage(), exception);
        }
        return quantity;
    }

}
