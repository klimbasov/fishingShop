package com.jwd.fShop.service.serviceHolder;

import com.jwd.fShop.service.OrderService;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.TypeService;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.exception.FatalServiceException;
import com.jwd.fShop.service.impl.OrderServiceImpl;
import com.jwd.fShop.service.impl.ProductServiceImpl;
import com.jwd.fShop.service.impl.TypeServiceImpl;
import com.jwd.fShop.service.impl.UserServiceImpl;

import java.util.Objects;

public class ServiceHolder {
    private static ServiceHolder INSTANCE;
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final TypeService typeService;

    private ServiceHolder() throws FatalServiceException {
        productService = new ProductServiceImpl();
        userService = new UserServiceImpl();
        orderService = new OrderServiceImpl();
        typeService = new TypeServiceImpl();
    }

    public static ServiceHolder getInstance() throws FatalServiceException {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new ServiceHolder();
        }
        return INSTANCE;
    }


    public ProductService getProductService() {
        return productService;
    }

    public UserService getUserService() {
        return userService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public TypeService getTypeService() {
        return typeService;
    }
}
