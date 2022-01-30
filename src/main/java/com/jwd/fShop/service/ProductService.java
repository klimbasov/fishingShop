package com.jwd.fShop.service;

import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductFilter;
import com.jwd.fShop.service.exception.ServiceException;

import java.util.List;

/**
 * Specifies a contract to access business logic, maintains operations on products required by application.
 */
public interface ProductService {

    /**
     * Used to add some product to storage.
     * @param product product to save. Product instance should have all initialized fields exclude <code>id</code>
     *                otherwise it will cause exception. <code>id</code> field is ignored.
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     * Usually warps cause exception.
     */
    void add(Product product) throws ServiceException;

    /**
     * @param id product id to get
     * @return product object or, if such does not exist, <code>null</code>
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     * Usually warps cause exception.
     */
    Product getById(int id) throws ServiceException;

    /**
     * @param product product to save. Product instance should have all initialized fields exclude <code>id</code>
     *                otherwise it will cause exception. <code>id</code> field is ignored.
     * @param id product id to change
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     * Usually warps cause exception.
     */
    void changeById(Product product, int id) throws ServiceException;

    /**
     * Used to get a list of products with filtering.
     * @param productFilter filter object specifies parameters range search for
     * @param pageNumber number of the searched page
     * @return <code>List</code> of the size, specified by service implementation configuration. If there is no page to
     * specified <code>pageNumber</code>, <code>List</code> will be empty
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     * Usually warps cause exception.
     */
    List<Product> getPage(ProductFilter productFilter, int pageNumber) throws ServiceException;

    /**
     * Used to get available page quantity for specified filtering.
     * @param productFilter filter object specifies parameters range search for
     * @return how many pages are available for the specified filter
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     * Usually warps cause exception.
     */
    int getPagesQuantity(ProductFilter productFilter) throws ServiceException;
}
