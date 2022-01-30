package com.jwd.fShop.dao.util;

import com.jwd.fShop.dao.constant.*;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.ProductFilter;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class QueryFactory {

    private QueryFactory() {
    }

    private static void setStatementPair(StringBuilder sqlBuilder, String trueSeparator, String falseSeparator, String name, String dependency, boolean isNotFirstElement) {
        if (isNotFirstElement) {
            sqlBuilder.append(trueSeparator);
        } else {
            sqlBuilder.append(falseSeparator);
        }
        sqlBuilder.append(name).append(dependency);
    }

    public static class UserSql {

        private static final String SQL_INSERT_SINGLE = "INSERT INTO " +
                UserSqlNames.TABLE_NAME +
                " (" + UserSqlNames.USERNAME_COLUMN_NAME +
                ", " + UserSqlNames.PASSWORD_HASH_COLUMN_NAME +
                ", " + UserSqlNames.ROLE_COLUMN_NAME +
                ", " + UserSqlNames.REGISTRATION_DATE_COLUMN_NAME +
                ", " + UserSqlNames.REGISTRATION_TIME_COLUMN_NAME +
                ") Values (?, ?, ?, ?, ?)";

        private static final String SQL_SELECT_MULTIPLE = "SELECT " +
                UserSqlNames.ID_COLUMN_NAME +
                ", " + UserSqlNames.USERNAME_COLUMN_NAME +
                ", " + UserSqlNames.ROLE_COLUMN_NAME +
                ", " + UserSqlNames.REGISTRATION_DATE_COLUMN_NAME +
                ", " + UserSqlNames.REGISTRATION_TIME_COLUMN_NAME + " FROM " + UserSqlNames.TABLE_NAME;

        private static final String SQL_LIMIT = "LIMIT ?, ?";

        private static final String SQL_COUNT_SETS = "SELECT COUNT(*) FROM "
                + UserSqlNames.TABLE_NAME;
        private static final String SQL_UPRATE_USERNAME = "UPDATE "
                + UserSqlNames.TABLE_NAME
                + " SET "
                + UserSqlNames.USERNAME_COLUMN_NAME
                + " = ? WHERE "
                + UserSqlNames.ID_COLUMN_NAME
                + " = ?;";
        private static final String SQL_UPRATE_PASSWORD = "UPDATE "
                + UserSqlNames.TABLE_NAME
                + " SET "
                + UserSqlNames.PASSWORD_HASH_COLUMN_NAME
                + " = ? WHERE "
                + UserSqlNames.ID_COLUMN_NAME
                + " = ?;";
        private static final String SQL_UPRATE_ROLE = "UPDATE "
                + UserSqlNames.TABLE_NAME
                + " SET "
                + UserSqlNames.ROLE_COLUMN_NAME
                + " = ? WHERE "
                + UserSqlNames.ID_COLUMN_NAME
                + " = ?;";

        public static String getUpdateSQL(final User user) throws DaoException {
            String sql = null;
            if (isNull(user)) {
                throw new DaoException("in generateUpdateStatement(List<String>, List<String>) : some arguments are null.");
            }

            if (nonNull(user.getName())) {
                sql = SQL_UPRATE_USERNAME;
            }
            if (nonNull(user.getHashedPassword())) {
                sql = SQL_UPRATE_PASSWORD;
            }
            if (nonNull(user.getRole())) {
                sql = SQL_UPRATE_ROLE;
            }
            if (isNull(sql)) {
                throw new DaoException("");
            }
            return sql;
        }

        public static String getMultipleSelectSQL(final UserFilter userFilter) {
            StringBuilder sql = generateSelectConditionOnBase(userFilter, SQL_SELECT_MULTIPLE);
            sql.append(';');

            return sql.toString();
        }

        public static String getMultipleLimitedSelectSQL(final UserFilter userFilter) {
            StringBuilder sql = generateSelectConditionOnBase(userFilter, SQL_SELECT_MULTIPLE);
            sql.append(' ').append(SQL_LIMIT).append(';');

            return sql.toString();
        }

        public static String getInsertSingleSQL() {
            return SQL_INSERT_SINGLE;
        }

        public static String getCountSetsSql(final UserFilter userFilter) {
            StringBuilder sql = generateSelectConditionOnBase(userFilter, SQL_COUNT_SETS).append(';');

            return sql.toString();
        }

        private static StringBuilder generateSelectConditionOnBase(final UserFilter userFilter, final String base) {
            boolean isNotFirstElement = false;
            StringBuilder sql = new StringBuilder();
            sql.append(base);
            if (userFilter.isUserSubname()) {
                if (userFilter.isFullName()) {
                    setStatementPair(sql, " AND ", " WHERE ", UserSqlNames.USERNAME_COLUMN_NAME, " = ?", false);
                } else {
                    setStatementPair(sql, " AND ", " WHERE ", UserSqlNames.USERNAME_COLUMN_NAME, " LIKE ?", false);
                }
                isNotFirstElement = true;
            }
            if (userFilter.isId()) {
                setStatementPair(sql, " AND ", " WHERE ", UserSqlNames.ID_COLUMN_NAME, " = ?", isNotFirstElement);
                isNotFirstElement = true;
            }
            if (userFilter.isSubHashPass()) {
                setStatementPair(sql, " AND ", " WHERE ", UserSqlNames.PASSWORD_HASH_COLUMN_NAME, " = ?", isNotFirstElement);
            }
            if (userFilter.isHighDate()) {
                setStatementPair(sql, " AND ", " WHERE ", UserSqlNames.REGISTRATION_DATE_COLUMN_NAME, " <= ?", isNotFirstElement);
                isNotFirstElement = true;
            }
            if (userFilter.isLowDate()) {
                setStatementPair(sql, " AND ", " WHERE ", UserSqlNames.REGISTRATION_DATE_COLUMN_NAME, " >= ?", isNotFirstElement);
                isNotFirstElement = true;
            }
            if (userFilter.isHighDate()) {
                setStatementPair(sql, " AND ", " WHERE ", UserSqlNames.REGISTRATION_TIME_COLUMN_NAME, " <= ?", isNotFirstElement);
                isNotFirstElement = true;
            }
            if (userFilter.isLowDate()) {
                setStatementPair(sql, " AND ", " WHERE ", UserSqlNames.REGISTRATION_TIME_COLUMN_NAME, " >= ?", isNotFirstElement);
                isNotFirstElement = true;
            }
            if (userFilter.isRole()) {
                setStatementPair(sql, " AND ", " WHERE ", UserSqlNames.ROLE_COLUMN_NAME, " = ?", isNotFirstElement);
            }
            return sql;
        }
    }

    public static class ProductSql {

        public static final String SQL_DELETE = "UPDATE "
                + ProductSqlNames.TABLE_NAME
                + " SET "
                + ProductSqlNames.VISIBLE_COLUMN_NAME
                + " = ? WHERE "
                + ProductSqlNames.ID_COLUMN_NAME
                + " = ?;";
        private static final String SQL_INSERT_SINGLE = "INSERT INTO " +
                ProductSqlNames.TABLE_NAME +
                " (" + ProductSqlNames.NAME_COLUMN_NAME +
                ", " + ProductSqlNames.PRICE_COLUMN_NAME +
                ", " + ProductSqlNames.QUANTITY_COLUMN_NAME +
                ", " + ProductSqlNames.TYPE_ID_COLUMN_NAME +
                ", " + ProductSqlNames.VISIBLE_COLUMN_NAME +
                ") Values (?, ?, ?, ?, ?);";
        private static final String SQL_SELECT_MULTIPLE = "SELECT " +
                ProductSqlNames.ID_COLUMN_NAME +
                ", " + ProductSqlNames.NAME_COLUMN_NAME +
                ", " + ProductSqlNames.PRICE_COLUMN_NAME +
                ", " + ProductSqlNames.QUANTITY_COLUMN_NAME +
                ", " + ProductSqlNames.TYPE_ID_COLUMN_NAME +
                ", " + ProductSqlNames.VISIBLE_COLUMN_NAME + " FROM " + ProductSqlNames.TABLE_NAME;
        private static final String SQL_LIMIT = "LIMIT ?, ?";
        private static final String SQL_COUNT_SETS = "SELECT COUNT(*) FROM "
                + ProductSqlNames.TABLE_NAME;
        private static final String SQL_UPRATE = "UPDATE "
                + ProductSqlNames.TABLE_NAME
                + " SET "
                + ProductSqlNames.NAME_COLUMN_NAME
                + " = ?, "
                + ProductSqlNames.PRICE_COLUMN_NAME
                + " = ?, "
                + ProductSqlNames.QUANTITY_COLUMN_NAME
                + " = ?, "
                + ProductSqlNames.TYPE_ID_COLUMN_NAME
                + " = ?, "
                + ProductSqlNames.VISIBLE_COLUMN_NAME
                + " = ? "
                + " WHERE "
                + ProductSqlNames.ID_COLUMN_NAME
                + " = ?;";

        public static String getUpdateSQL() {
            return SQL_UPRATE;
        }

        public static String getMultipleSelectSQL(final ProductFilter productFilter) {
            StringBuilder sql = generateSelectConditionOnBase(productFilter, SQL_SELECT_MULTIPLE);
            sql.append(';');

            return sql.toString();
        }

        public static String getMultipleLimitedSelectSQL(final ProductFilter productFilter) {
            StringBuilder sql = generateSelectConditionOnBase(productFilter, SQL_SELECT_MULTIPLE);
            sql.append(' ').append(SQL_LIMIT).append(';');

            return sql.toString();
        }

        public static String getInsertSingleSQL() {
            return SQL_INSERT_SINGLE;
        }

        public static String getInsertMultipleSQL(int valuesQuantity) {
            StringBuilder sqlBuilder = new StringBuilder(SQL_INSERT_SINGLE);
            sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
            for (int counter = 1; counter < valuesQuantity; counter++) {
                sqlBuilder.append(", (?, ?, ?, ?, ?)");
            }
            sqlBuilder.append(';');
            return sqlBuilder.toString();
        }

        public static String getCountSetsSql(final ProductFilter productFilter) {
            StringBuilder sql = generateSelectConditionOnBase(productFilter, SQL_COUNT_SETS).append(';');

            return sql.toString();
        }

        public static String getDeleteSql() {
            return SQL_DELETE;
        }

        private static StringBuilder generateSelectConditionOnBase(final ProductFilter productFilter, final String base) {
            boolean isNotFirstElement = false;
            StringBuilder sql = new StringBuilder();
            sql.append(base);
            if (productFilter.isName()) {
                setStatementPair(sql, " AND ", " WHERE ", ProductSqlNames.NAME_COLUMN_NAME, " LIKE ?", false);
                isNotFirstElement = true;
            }
            if (productFilter.isId()) {
                setStatementPair(sql, " AND ", " WHERE ", ProductSqlNames.ID_COLUMN_NAME, " = ?", isNotFirstElement);
                isNotFirstElement = true;
            }
            if (productFilter.isProductType()) {
                setStatementPair(sql, " AND ", " WHERE ", ProductSqlNames.TYPE_ID_COLUMN_NAME, " = ?", isNotFirstElement);
            }
            if (productFilter.isHighPrice()) {
                setStatementPair(sql, " AND ", " WHERE ", ProductSqlNames.PRICE_COLUMN_NAME, " <= ?", isNotFirstElement);
                isNotFirstElement = true;
            }
            if (productFilter.isLowPrice()) {
                setStatementPair(sql, " AND ", " WHERE ", ProductSqlNames.PRICE_COLUMN_NAME, " >= ?", isNotFirstElement);
            }
            return sql;
        }
    }

    public static class OrderSql {

        private static final String SQL_INSERT_SINGLE_IN_USERS_ORDERS = "insert into " +
                UsersOrdersSqlNames.TABLE_NAME +
                " (" + UsersOrdersSqlNames.USER_ID_COLUMN_NAME +
                ", " + UsersOrdersSqlNames.ORDERING_DATE_COLUMN_NAME +
                ") values (?, ?);";

        private static final String SQL_SELECT_LAST_ID = "select last_insert_id();";

        private static final String SQL_INSERT_SINGLE_IN_ORDERS_PRODUCTS = "INSERT INTO " +
                OrdersProductsSqlNames.TABLE_NAME +
                " (" + OrdersProductsSqlNames.ID_COLUMN_NAME +
                ", " + OrdersProductsSqlNames.QUANTITY_COLUMN_NAME +
                ", " + OrdersProductsSqlNames.PRODUCT_ID_COLUMN_NAME +
                ") VALUES (?, ?, ?)";

        private static final String SQL_ITEM = "(?, ?, ?)";

        private static final String SQL_SELECT_USER_ORDERS = "SELECT " +
                UsersOrdersSqlNames.ID_COLUMN_NAME +
                ", " + UsersOrdersSqlNames.ORDERING_DATE_COLUMN_NAME +
                " FROM " + UsersOrdersSqlNames.TABLE_NAME +
                " WHERE " + UsersOrdersSqlNames.USER_ID_COLUMN_NAME +
                " = ?";


        private static final String SQL_SELECT_ORDER = "SELECT " +
                OrdersProductsSqlNames.PRODUCT_ID_COLUMN_NAME +
                ", " + OrdersProductsSqlNames.QUANTITY_COLUMN_NAME +
                ", " + UsersOrdersSqlNames.USER_ID_COLUMN_NAME +
                ", " + UsersOrdersSqlNames.ORDERING_DATE_COLUMN_NAME +
                " FROM " + OrdersProductsSqlNames.TABLE_NAME +
                " JOIN " + UsersOrdersSqlNames.TABLE_NAME +
                " ON " + OrdersProductsSqlNames.TABLE_NAME + "." +
                UsersOrdersSqlNames.ID_COLUMN_NAME + " = " +
                UsersOrdersSqlNames.TABLE_NAME + "." +
                OrdersProductsSqlNames.ID_COLUMN_NAME +
                " WHERE " + OrdersProductsSqlNames.TABLE_NAME + "." +
                OrdersProductsSqlNames.ID_COLUMN_NAME + " = ?";

        private static final String SQL_LIMIT = "LIMIT ?, ?";

        private static final String SQL_COUNT_SETS = "SELECT COUNT(*) FROM " +
                UsersOrdersSqlNames.TABLE_NAME +
                " WHERE " +
                UsersOrdersSqlNames.USER_ID_COLUMN_NAME +
                " = ?;";

        public static String getInsertInUsersOrdersSql() {
            return SQL_INSERT_SINGLE_IN_USERS_ORDERS;
        }

        public static String getSelectLastIdSql() {
            return SQL_SELECT_LAST_ID;
        }

        public static String getInsertSingleInOrdersProductsSql(int unitQuantity) {
            StringBuilder sqlQueryBuilder = new StringBuilder().append(SQL_INSERT_SINGLE_IN_ORDERS_PRODUCTS);
            for (int counter = 1; counter < unitQuantity; counter++) {
                sqlQueryBuilder.append(", ").append(SQL_ITEM);
            }
            sqlQueryBuilder.append(';');
            return sqlQueryBuilder.toString();
        }

        public static String getSelectOrderProductsSql() {
            return SQL_SELECT_ORDER + ";";
        }

        public static String getSelectUserOrdersSql() {
            return SQL_SELECT_USER_ORDERS + ";";
        }

        public static String getSqlLimitedSelectOrderProducts() {
            return SQL_SELECT_ORDER + " " + SQL_LIMIT + ";";
        }

        public static String getLimitedSqlSelectUserOrders() {
            return SQL_SELECT_USER_ORDERS + " " + SQL_LIMIT + ";";
        }

        public static String getCountSql() {
            return SQL_COUNT_SETS;
        }
    }

    public static class TypeSql {
        private static final String SQL_INSERT_SINGLE = "INSERT into " + TypeSqlNames.TABLE_NAME + " (" + TypeSqlNames.NAME_COLUMN_NAME + ") VALUES (?);";
        private static final String SQL_SELECT_ALL = "SELECT * FROM " + TypeSqlNames.TABLE_NAME + ";";
        private static final String SQL_SELECT_LAST_ID = "select last_insert_id();";

        public static String getInsertSingleSQL() {
            return SQL_INSERT_SINGLE;
        }

        public static String getSelectAllSql() {
            return SQL_SELECT_ALL;
        }

        public static String getSelectLastIdSql() {
            return SQL_SELECT_LAST_ID;
        }
    }
}
