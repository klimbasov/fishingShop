package com.jwd.fShop.dao;

import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.Order;

import java.util.List;

/**
 * Specifies communication with database to maintain orders operation.
 */
public interface OrderDao {
    /**
     * Used to save user to database.
     * @param order order instance to save
     * @throws DaoException if some kind of exception occurred while execution or invalid argument was passed.
     * Usually warps cause exception.
     */
    void save(final Order order) throws DaoException;

    /**
     * Used to get an order by specified id from database.
     * @param id order's id
     * @return <code>Order</code> instance or <code>null</code>, if such does not exist,
     * @throws DaoException if some kind of exception occurred while execution or invalid argument was passed.
     * Usually warps cause exception.
     */
    Order getById(int id) throws DaoException;

    List<Order> get(int userId) throws DaoException;

    List<Order> getSet(int userId, int offset, int size) throws DaoException;

    int getQuantity(int userId) throws DaoException;
}
