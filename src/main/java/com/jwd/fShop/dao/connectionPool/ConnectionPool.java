package com.jwd.fShop.dao.connectionPool;

import com.jwd.fShop.dao.config.DataBaseConfig;
import com.jwd.fShop.dao.exception.ConnectionPoolException;
import com.jwd.fShop.dao.exception.DaoException;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

public class ConnectionPool {

    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());
    private static final int CONNECTIONS_TOTAL = 4;
    private static ConnectionPool INSTANCE;
    private final DataBaseConfig dataBaseConfig;
    private final ArrayBlockingQueue<Connection> pool;
    private final ArrayBlockingQueue<Connection> taken;

    private ConnectionPool(final String path) throws ConnectionPoolException {
        this.dataBaseConfig = new DataBaseConfig(path);
        pool = new ArrayBlockingQueue<>(CONNECTIONS_TOTAL);
        taken = new ArrayBlockingQueue<>(CONNECTIONS_TOTAL);
        initConnectionPool();
    }

    public static synchronized ConnectionPool getInstance(final String path) throws ConnectionPoolException {
        return Objects.isNull(INSTANCE) ? new ConnectionPool(path) : INSTANCE;
    }

    private void initConnectionPool() throws ConnectionPoolException {
        try {
            for (int i = 0; i < CONNECTIONS_TOTAL; i++) {
                pool.add(dataBaseConfig.getConnection());
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e.getMessage());
        }
        LOGGER.info("init pool.size() is " + pool.size());
        LOGGER.info("init taken.size() is " + taken.size());
    }

    public Connection getConnection() throws DaoException {
        Connection newConnection;
        try {
            LOGGER.info("#take()");
            newConnection = pool.take();
            taken.add(newConnection);
        } catch (InterruptedException e) {
            LOGGER.info("Exception: #take()");
            throw new DaoException(e);
        }
        LOGGER.info("pool.size() is " + pool.size());
        LOGGER.info("taken.size() is " + taken.size());
        return newConnection;
    }

    public void retrieveConnection(final Connection connection) {
        if (nonNull(connection)) {
            LOGGER.info("#retrieve(final Connection connection)");
            taken.remove(connection);
            pool.add(connection);
        } else {
            LOGGER.info("retrieve(Connection connection)");
            LOGGER.info("connection=null");
        }
        LOGGER.info("pool.size() is " + pool.size());
        LOGGER.info("taken.size() is " + taken.size());
    }
}
