package com.jwd.fShop.dao;

import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.Order;

import java.util.List;

public interface OrderDao {
    void save(final Order order) throws DaoException;

    Order getById(int id) throws DaoException;

    List<Order> get(int userId) throws DaoException;

    List<Order> getSet(int userId, int offset, int size) throws DaoException;

    int getQuantity(int userId) throws DaoException;
}
