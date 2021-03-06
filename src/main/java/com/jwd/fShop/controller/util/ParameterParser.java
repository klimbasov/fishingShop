package com.jwd.fShop.controller.util;

import com.jwd.fShop.controller.constant.ExceptionMessages;
import com.jwd.fShop.exception.InvalidArgumentException;
import com.jwd.fShop.util.ExceptionMessageCreator;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;
import static java.util.Objects.nonNull;

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
            throw new InvalidArgumentException(createExceptionMessage(ExceptionMessages.INVALID_PARAMETER_VALUE), e);
        } catch (NullPointerException e) {
            throw new InvalidArgumentException(createExceptionMessage(ExceptionMessages.NULL_ARGUMENT), e);
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
            throw new InvalidArgumentException(createExceptionMessage(ExceptionMessages.INVALID_PARAMETER_VALUE), e);
        } catch (NullPointerException e) {
            throw new InvalidArgumentException(createExceptionMessage(ExceptionMessages.NULL_ARGUMENT), e);
        }
    }

    public static Float parseNullableFloat(String parameter) {
        Float result = null;
        if (nonNull(parameter)) {
            try {
                result = Float.parseFloat(parameter);
            } catch (RuntimeException e) {
            }
        }
        return result;
    }

    public static Integer parseNullableInteger(String parameter) {
        Integer result = null;
        if (nonNull(parameter)) {
            try {
                result = Integer.parseInt(parameter);
            } catch (RuntimeException e) {
            }
        }
        return result;
    }
}
