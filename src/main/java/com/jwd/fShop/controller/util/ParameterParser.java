package com.jwd.fShop.controller.util;

import com.jwd.fShop.controller.exception.InvalidArgumentException;

public class ParameterParser {
    public static int parseInt(String parameter, int defaultValue) {
        int result = defaultValue;
        try {
            result = Integer.parseInt(parameter);
        } catch (NumberFormatException | NullPointerException e) {
        }
        return result;
    }

    public static int parseInt(String parameter) {
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Invalid string format.", e);
        } catch (NullPointerException e) {
            throw new InvalidArgumentException("Argument was null", e);
        }
    }

    public static float parseFloat(String parameter, float defaultValue) {
        float result = defaultValue;
        try {
            result = Float.parseFloat(parameter);
        } catch (NumberFormatException | NullPointerException e) {
        }
        return result;
    }

    public static float parseFloat(String parameter) {
        try {
            return Float.parseFloat(parameter);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Invalid string format.", e);
        } catch (NullPointerException e) {
            throw new InvalidArgumentException("Argument was null", e);
        }
    }
}
