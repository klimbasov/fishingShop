package com.jwd.fShop.dao.connectionPool;

import com.jwd.fShop.dao.exception.ConnectionWrapperException;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.util.ExceptionMessageCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

public class ConnectionWrapper implements AutoCloseable {
    private final Connection connection;
    private final ConnectionPool connectionPool;

    public ConnectionWrapper(ConnectionPool connectionPool) throws ConnectionWrapperException {
        this.connectionPool = connectionPool;
        try {
            this.connection = this.connectionPool.getConnection();
        } catch (DaoException exception) {
            throw new ConnectionWrapperException(exception);
        }
    }

    public ConnectionWrapper(ConnectionPool connectionPool, boolean autoCommit) throws ConnectionWrapperException {
        this.connectionPool = connectionPool;
        try {
            this.connection = this.connectionPool.getConnection();
            connection.setAutoCommit(autoCommit);
        } catch (DaoException | SQLException exception) {
            throw new ConnectionWrapperException(exception);
        }
    }

    public PreparedStatement getPreparedStatement(final String sql) throws ConnectionWrapperException {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException exception) {
            throw new ConnectionWrapperException(createExceptionMessage(),exception);
        }
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    @Override
    public void close() {
        try {
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
        } catch (SQLException ignored) {
        }
        connectionPool.retrieveConnection(connection);
    }
}
