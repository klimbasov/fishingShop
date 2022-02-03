package com.jwd.fShop.service.impl;

import com.jwd.fShop.dao.OrderDao;
import com.jwd.fShop.dao.daoHolder.DaoHolder;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Order;
import com.jwd.fShop.domain.ProductBunch;
import com.jwd.fShop.service.OrderService;
import com.jwd.fShop.service.exception.ServiceException;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.jwd.fShop.service.util.Validator.validate;
import static com.jwd.fShop.service.util.Validator.validatePositive;
import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;
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
        validate(productBunches);
        validatePositive(userId);

        if (nonNull(productBunches)) {
            Order order = new Order.Builder().setUserId(userId).
                    setProductBunchList(productBunches).
                    setOrderingDate(new Date(Date.from(Instant.now()).getTime())).
                    setOrderingTime(new Time(Time.from(Instant.now()).getTime())).
                    build();
            try {
                orderDao.save(order);
            } catch (DaoException exception) {
                throw new ServiceException(createExceptionMessage(), exception);
            }
        }
    }

    @Override
    public Optional<IdentifiedDTO<Order>> getById(int id) throws ServiceException {
        Optional<IdentifiedDTO<Order>> spotted;

        validatePositive(id);
        try {
            spotted = orderDao.getById(id);
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return spotted;
    }

    @Override
    public List<IdentifiedDTO<Order>> getPage(int userId, int page) throws ServiceException {
        List<IdentifiedDTO<Order>> orders;
        int offset;

        validatePositive(userId, page);

        offset = (page - 1) * pageSize;
        try {
            orders = orderDao.getSet(userId, offset, pageSize);
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return orders;
    }

    @Override
    public int getPageQuantity(int userId) throws ServiceException {
        int totalValue;

        validatePositive(userId);

        try {
            totalValue = orderDao.getQuantity(userId);
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return (totalValue + pageSize - 1) / pageSize;
    }
}
