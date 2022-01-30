package com.jwd.fShop.service.impl;

import com.jwd.fShop.dao.OrderDao;
import com.jwd.fShop.dao.daoHolder.DaoHolder;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.Order;
import com.jwd.fShop.domain.ProductBunch;
import com.jwd.fShop.service.OrderService;
import com.jwd.fShop.service.exception.ServiceException;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.nonNull;

public class OrderServiceImpl implements OrderService {
    static private final int DEFAULT_PAGE_SIZE = 5;
    static private int pageSize;
    private final OrderDao orderDao;

    public OrderServiceImpl() {
        pageSize = DEFAULT_PAGE_SIZE;
        orderDao = DaoHolder.getInstance().getOrderDao();

    }

    @Override
    public void add(List<ProductBunch> productBunches, int userId) throws ServiceException {
        if (nonNull(productBunches)) {
            Order order = new Order.Builder().setUserId(userId).
                    setProductBunchList(productBunches).
                    setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                    setOrderingTime(new Time(Time.from(Instant.now()).getTime())).
                    build();
            try {
                orderDao.save(order);
            } catch (DaoException exception) {
                throw new ServiceException("In " + this.getClass().getName() + " in register(User)", exception);
            }
        }
    }

    @Override
    public Order getById(int id) throws ServiceException {
        Order spotted = null;
        try {
            spotted = orderDao.getById(id);
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in register(User)", exception);
        }
        return spotted;
    }

    @Override
    public List<Order> getPage(int userId, int page) throws ServiceException {
        List<Order> orders = new LinkedList<>();
        int offset = (page - 1) * pageSize;

        try {
            orders = orderDao.getSet(userId, offset, pageSize);
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in register(User)", exception);
        }
        return orders;
    }

    @Override
    public int getPageQuantity(int userId) throws ServiceException {
        int quantity = 0;
        int totalValue = 0;

        try {
            totalValue = orderDao.getQuantity(userId);
            quantity = totalValue / pageSize + (totalValue % pageSize == 0 ? 0 : 1);
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in register(User)", exception);
        }
        return quantity;
    }
}
