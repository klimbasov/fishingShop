package com.jwd.fShop.controller.exception;

public class UnhandledException extends RuntimeException {
    public UnhandledException() {
        super();
    }

    public UnhandledException(final String message) {
        super(message);
    }

    public UnhandledException(final String message, final Exception cause) {
        super(message, cause);
    }

    public UnhandledException(final Exception cause) {
        super(cause);
    }
}
