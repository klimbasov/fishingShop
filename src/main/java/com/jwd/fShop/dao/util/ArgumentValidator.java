package com.jwd.fShop.dao.util;

import com.jwd.fShop.controller.constant.ExceptionMessages;
import com.jwd.fShop.domain.*;

import java.util.List;

import static java.util.Objects.isNull;

public class ArgumentValidator {

    public static void validate(final String str) {
        if (isNull(str)) {
            throw new IllegalArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
    }

    public static void validate(final int... uInts) {
        for (int uInt : uInts) {
            if (uInt < 0) {
                throw new IllegalArgumentException(ExceptionMessages.INVALID_ARGUMENT_VALUE);
            }
        }

    }

    public static void validateId(final int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_ARGUMENT_VALUE);
        }
    }

    public static void validate(final User user) {
        if (isNull(user) ||
                isNull(user.getName()) ||
                isNull(user.getRegistrationDate()) ||
                isNull(user.getRegistrationTime()) ||
                isNull(user.getRole()) ||
                isNull(user.getHashedPassword())) {
            throw new IllegalArgumentException(ExceptionMessages.PARTIALLY_INITIALIZED);
        }
    }

    public static void validate(final Product product) {
        if (isNull(product)) {
            throw new IllegalArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
        if (isNull(product.getName())
                || isNull(product.getPrice())
                || isNull(product.getQuantity())
                || isNull(product.getProductType())
                || isNull(product.getVisible())) {
            throw new IllegalArgumentException(ExceptionMessages.PARTIALLY_INITIALIZED);
        }
    }

    public static void validate(final Order order) {
        if (isNull(order)) {
            throw new IllegalArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
        if (isNull(order.getProductBunchList()) || order.getUserId() <= 0 || isNull(order.getOrderingDate()) || isNull(order.getOrderingTime())) {
            throw new IllegalArgumentException(ExceptionMessages.PARTIALLY_INITIALIZED);
        }
    }

    public static void validate(final List<Product> productList) {
        if (isNull(productList)) {
            throw new IllegalArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
    }

    public static void validate(final UserFilter userFilter) {
        if (isNull(userFilter)) {
            throw new IllegalArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
    }

    public static void validate(final ProductFilter productFilter) {
        if (isNull(productFilter)) {
            throw new IllegalArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
    }
}
