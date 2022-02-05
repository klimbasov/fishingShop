package com.jwd.fShop.service;

import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Order;
import com.jwd.fShop.domain.ProductBunch;
import com.jwd.fShop.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Specifies an ordering business logic, maintains operations on orders required by application.
 *
 * @see ProductBunch
 */
public interface OrderService {
    /**
     * Used to make an order.
     *
     * @param productBunches list of product id-quantity pairs to order
     * @param userId         id of the user, hwo has made an order
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    void add(List<ProductBunch> productBunches, int userId) throws ServiceException;

    /**
     * Used to get order instance by its id.
     *
     * @param id id of the order
     * @return An <code>Order</code> instance or, if such does not exist, <code>null</code>
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    Optional<IdentifiedDTO<Order>> getById(int id) throws ServiceException;

    /**
     * Used to get a range of Orders of specified user. Size of the range depends on implementation of the service.
     *
     * @param userId id of the user
     * @param page   ordinal number of the page
     * @return <code>List</code> of <code>Order</code>s. If there is no orders to the
     * specified <code>page</code>, <code>List</code> will be empty
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    List<IdentifiedDTO<Order>> getPage(int userId, int page) throws ServiceException;

    /**
     * Used to get available pages quantity to the specified user
     *
     * @param userId id of the user
     * @return how many pages are available for the user
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    int getPageQuantity(int userId) throws ServiceException;

    /**
     * Used to get a range of {@link ProductBunch} of specified Order. Size of the range depends on implementation of the service.
     *
     * @param order instance, holding ordered products
     * @param page   ordinal number of the page
     * @return <code>List</code> of <code>ProductBrunch</code>s. If there is no orders to the
     * specified <code>page</code>, <code>List</code> will be empty
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    List<ProductBunch> getOrderedProductsPage(Order order, int page) throws ServiceException;

    /**
     * Used to get available pages quantity to the specified order
     *
     * @param order instance, holding ordered products
     * @return how many pages are available for the product
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    int getOrderedProductsPageQuantity(Order order) throws ServiceException;
}
