package com.jwd.fShop.dao.exception;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(final String message) {
        super(message);
    }

    public ConnectionPoolException(final String message, final Exception cause) {
        super(message, cause);
    }

    public ConnectionPoolException(final Exception cause) {
        super(cause);
    }
}
