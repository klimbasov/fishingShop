package com.jwd.fShop.service.util;

import com.jwd.fShop.controller.constant.ExceptionMessages;
import com.jwd.fShop.domain.*;
import com.jwd.fShop.exception.InvalidArgumentException;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class Validator {
    private static final Pattern NAME_PATTERN = Pattern.compile("\\p{Alpha}\\p{Alnum}{3,29}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("\\p{Alnum}{3,30}");

    public static void validate(Product product) {
        if (isNull(product)) {
            throw new InvalidArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
        if (isNull(product.getName()) &&
                isNull(product.getQuantity()) &&
                isNull(product.getPrice()) &&
                isNull(product.getProductType()) &&
                isNull(product.getVisibility())) {
            throw new InvalidArgumentException(ExceptionMessages.PARTIALLY_INITIALIZED);
        }
    }

    public static void validate(Order order) {
        if (isNull(order)) {
            throw new InvalidArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
        if (isNull(order.getOrderingDate()) &&
                isNull(order.getOrderingTime())
        ) {
            throw new InvalidArgumentException(ExceptionMessages.PARTIALLY_INITIALIZED);
        }
    }

    public static void validate(ProductFilter filter) {
        if (isNull(filter)) {
            throw new InvalidArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
    }

    public static void validate(UserFilter filter) {
        if (isNull(filter)) {
            throw new InvalidArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
    }

    public static void validate(List<ProductBunch> list) {
        if (isNull(list)) {
            throw new InvalidArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
        if (list.isEmpty()) {
            throw new InvalidArgumentException(ExceptionMessages.INVALID_ARGUMENT_VALUE);
        }
    }

    public static void validateUsername(String username) {
        if (isNull(username)) {
            throw new InvalidArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
        if (!NAME_PATTERN.matcher(username).matches()) {
            throw new InvalidArgumentException(ExceptionMessages.INVALID_ARGUMENT_VALUE);
        }
    }

    public static void validatePassword(String password) {
        if (isNull(password)) {
            throw new InvalidArgumentException(ExceptionMessages.NULL_ARGUMENT);
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidArgumentException(ExceptionMessages.INVALID_ARGUMENT_VALUE);
        }
    }

    public static void validatePositive(int... nums) {
        for (int num : nums) {
            if (num < 1) {
                throw new InvalidArgumentException(ExceptionMessages.INVALID_ARGUMENT_VALUE);
            }
        }
    }

}
