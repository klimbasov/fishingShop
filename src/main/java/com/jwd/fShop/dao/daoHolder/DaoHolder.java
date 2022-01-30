package com.jwd.fShop.dao.daoHolder;

import com.jwd.fShop.dao.OrderDao;
import com.jwd.fShop.dao.ProductDao;
import com.jwd.fShop.dao.TypeDao;
import com.jwd.fShop.dao.UserDao;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.dao.impl.OrderDaoImpl;
import com.jwd.fShop.dao.impl.ProductDaoImpl;
import com.jwd.fShop.dao.impl.TypeDaoImpl;
import com.jwd.fShop.dao.impl.UserDaoImpl;

import java.util.Objects;

public class DaoHolder {
    private static final String DATABASE_CONFIG_PATH = "db.properties";
    private static DaoHolder INSTANCE;
    private final UserDao userDao;
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final TypeDao typeDao;

    private DaoHolder() throws FatalDaoException {
        userDao = new UserDaoImpl(DATABASE_CONFIG_PATH);
        productDao = new ProductDaoImpl(DATABASE_CONFIG_PATH);
        orderDao = new OrderDaoImpl(DATABASE_CONFIG_PATH);
        typeDao = new TypeDaoImpl(DATABASE_CONFIG_PATH);
    }

    public static DaoHolder getInstance() throws FatalDaoException {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new DaoHolder();
        }
        return INSTANCE;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public TypeDao getTypeDao() {
        return typeDao;
    }
}
