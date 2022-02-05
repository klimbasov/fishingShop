package com.jwd.fShop.dao.util;

import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductFilter;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.util.Objects.nonNull;

public class StatementFiller {
    public static void setSingleObjectQueryParams(final PreparedStatement preparedStatement, final Product product) throws SQLException {
        preparedStatement.setString(1, product.getName());
        preparedStatement.setFloat(2, product.getPrice());
        preparedStatement.setInt(3, product.getQuantity());
        preparedStatement.setInt(4, product.getProductType());
        preparedStatement.setBoolean(5, product.getVisibility());
    }

    public static void setLimitedSelectParams(PreparedStatement preparedStatement, ProductFilter productFilter, int offset, int size) throws SQLException {
        int counter = setSelectFilterParams(preparedStatement, productFilter);
        preparedStatement.setInt(counter, offset);
        preparedStatement.setInt(counter + 1, size);
    }

    public static void setSelectParams(final PreparedStatement preparedStatement, final ProductFilter productFilter) throws SQLException {
        setSelectFilterParams(preparedStatement, productFilter);
    }

    private static int setSelectFilterParams(final PreparedStatement preparedStatement, final ProductFilter productFilter) throws SQLException {
        int counter = 1;
        if (productFilter.isName()) {
            preparedStatement.setString(counter++, "%" + productFilter.getName() + "%");
        }
        if (productFilter.isId()) {
            preparedStatement.setInt(counter++, productFilter.getId());
        }
        if (productFilter.isProductType()) {
            preparedStatement.setInt(counter++, productFilter.getProductType());
        }
        if (productFilter.isHighPrice()) {
            preparedStatement.setFloat(counter++, productFilter.getHighPrice());
        }
        if (productFilter.isLowPrice()) {
            preparedStatement.setFloat(counter++, productFilter.getLowPrice());
        }
        if (productFilter.isVisibility()) {
            preparedStatement.setBoolean(counter++, productFilter.getVisibility());
        }
        return counter;
    }

    public static void setUpdateParams(PreparedStatement preparedStatement, User user, int id) throws SQLException {
        if (nonNull(user.getName())) {
            preparedStatement.setString(1, user.getName());
        }
        if (nonNull(user.getHashedPassword())) {
            preparedStatement.setString(1, user.getHashedPassword());
        }
        if (nonNull(user.getRole())) {
            preparedStatement.setInt(1, user.getRole());
        }
        preparedStatement.setInt(2, id);
    }

    public static void setLimitedSelectParams(PreparedStatement preparedStatement, UserFilter userFilter, int offset, int size) throws SQLException {
        int counter = setSelectFilterParams(preparedStatement, userFilter);
        preparedStatement.setInt(counter, offset);
        preparedStatement.setInt(counter + 1, size);
    }

    public static void setSelectParams(final PreparedStatement preparedStatement, final UserFilter userFilter) throws SQLException {
        setSelectFilterParams(preparedStatement, userFilter);
    }

    private static int setSelectFilterParams(final PreparedStatement preparedStatement, final UserFilter userFilter) throws SQLException {
        int counter = 1;
        if (userFilter.getUserSubName()) {
            if (userFilter.isFullName()) {
                preparedStatement.setString(counter++, userFilter.getUserSubname());
            } else {
                preparedStatement.setString(counter++, "%" + userFilter.getUserSubname() + "%");
            }
        }
        if (userFilter.isId()) {
            preparedStatement.setInt(counter++, userFilter.getId());
        }
        if (userFilter.isSubHashPass()) {
            preparedStatement.setString(counter++, userFilter.getSubHashPass());
        }
        if (userFilter.isHighDate()) {
            preparedStatement.setDate(counter++, userFilter.getHighDate());
        }
        if (userFilter.isLowDate()) {
            preparedStatement.setDate(counter++, userFilter.getLowDate());
        }
        if (userFilter.isHighTime()) {
            preparedStatement.setTime(counter++, userFilter.getHighTime());
        }
        if (userFilter.isLowTime()) {
            preparedStatement.setTime(counter++, userFilter.getLowTime());
        }
        if (userFilter.isRole()) {
            preparedStatement.setInt(counter++, userFilter.getRole());
        }
        return counter;
    }
}
