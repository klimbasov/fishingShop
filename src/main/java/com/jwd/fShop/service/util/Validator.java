package com.jwd.fShop.service.util;

import com.jwd.fShop.domain.*;
import com.jwd.fShop.exception.InvalidArgumentException;

import java.util.List;

import static java.util.Objects.isNull;

public class Validator {
    public static void validate(Product product) {
        if (isNull(product)) {
            throw new InvalidArgumentException("Product was null");
        }
        if (isNull(product.getName()) &&
                isNull(product.getQuantity()) &&
                isNull(product.getPrice()) &&
                isNull(product.getProductType()) &&
                isNull(product.getVisible())) {
            throw new InvalidArgumentException("Product partial initialized");
        }
    }

    public static void validate(Order order) {
        if (isNull(order)) {
            throw new InvalidArgumentException("Product was null");
        }
        if (isNull(order.getOrderingDate()) &&
                isNull(order.getOrderingTime())
        ) {
            throw new InvalidArgumentException("Order partial initialized");
        }
    }

    public static void validate(ProductFilter filter) {
        if (isNull(filter)) {
            throw new InvalidArgumentException("Filter was null");
        }
    }

    public static void validate(UserFilter filter) {
        if (isNull(filter)) {
            throw new InvalidArgumentException("Filter was null");
        }
    }

    public static void validate(List<ProductBunch> list) {
        if (isNull(list)) {
            throw new InvalidArgumentException("Bunches were null");
        }
        if (list.isEmpty()) {
            throw new InvalidArgumentException("Bunches were empty");
        }
    }

    public static void validateUsername(String username) {
        if (isNull(username)) {
            throw new InvalidArgumentException("Bunches were null");
        }
        if (username.length() < 4) {
            throw new InvalidArgumentException("Bunches were empty");
        }
    }

    public static void validatePassword(String password) {
        if (isNull(password)) {
            throw new InvalidArgumentException("Bunches were null");
        }
        if (password.length() < 4) {
            throw new InvalidArgumentException("Bunches were empty");
        }
    }

    public static void validatePositive(int... nums) {
        for (int num : nums) {
            if (num < 1) {
                throw new InvalidArgumentException("");
            }
        }
    }

}
