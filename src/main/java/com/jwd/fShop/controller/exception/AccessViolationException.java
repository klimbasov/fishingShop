package com.jwd.fShop.controller.exception;

public class AccessViolationException extends Exception {

    public AccessViolationException(final String message) {
        super(message);
    }

    public AccessViolationException(final String message, final Exception cause) {
        super(message, cause);
    }

    public AccessViolationException(final Exception cause) {
        super(cause);
    }
}
