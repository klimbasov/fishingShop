package com.jwd.fShop.dao.impl;

import com.jwd.fShop.dao.TypeDao;
import com.jwd.fShop.dao.connectionPool.ConnectionPool;
import com.jwd.fShop.dao.connectionPool.ConnectionWrapper;
import com.jwd.fShop.dao.constant.TypeSqlNames;
import com.jwd.fShop.dao.exception.ConnectionPoolException;
import com.jwd.fShop.dao.exception.ConnectionWrapperException;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.dao.util.QueryFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.jwd.fShop.dao.util.ArgumentValidator.validate;

public class TypeDaoImpl implements TypeDao {
    private final ConnectionPool connectionPool;

    public TypeDaoImpl(final String path) {
        try {
            connectionPool = ConnectionPool.getInstance(path);
        } catch (ConnectionPoolException e) {
            throw new FatalDaoException(e.getMessage());
        }
    }

    public Map<Integer, String> load() throws DaoException {
        Map<Integer, String> typesMap = new HashMap<>();

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool);
             PreparedStatement statement = connectionWrapper.getPreparedStatement(QueryFactory.TypeSql.getSelectAllSql())) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    typesMap.put(
                            resultSet.getInt(TypeSqlNames.ID_COLUMN_NAME),
                            resultSet.getString(TypeSqlNames.NAME_COLUMN_NAME)
                    );
                }
            }
        } catch (SQLException | ConnectionWrapperException e) {
            throw new DaoException(e);
        }
        return typesMap;
    }

    @Override
    public int save(String name) throws DaoException {
        validate(name);

        int id = 0;

        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool, false);
             PreparedStatement insertStatement = connectionWrapper.getPreparedStatement(QueryFactory.TypeSql.getInsertSingleSQL());
             PreparedStatement getLastIdStatement = connectionWrapper.getPreparedStatement(QueryFactory.TypeSql.getSelectLastIdSql())) {

            insertStatement.setString(1, name);

            insertStatement.executeUpdate();

            try (ResultSet resultSet = getLastIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }

            connectionWrapper.commit();

        } catch (Exception e) {
            throw new DaoException(" ", e);
        }
        return id;
    }
}
