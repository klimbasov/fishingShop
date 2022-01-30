package com.jwd.fShop.controller.exception;

public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(final String message) {
        super(message);
    }

    public InvalidArgumentException(final String message, final Exception cause) {
        super(message, cause);
    }

    public InvalidArgumentException(final Exception cause) {
        super(cause);
    }
}
