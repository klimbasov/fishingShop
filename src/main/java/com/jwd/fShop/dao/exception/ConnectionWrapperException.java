package com.jwd.fShop.dao.exception;

public class ConnectionWrapperException extends Exception {
    public ConnectionWrapperException(final String message) {
        super(message);
    }

    public ConnectionWrapperException(final String message, final Exception cause) {
        super(message, cause);
    }

    public ConnectionWrapperException(final Exception cause) {
        super(cause);
    }
}
