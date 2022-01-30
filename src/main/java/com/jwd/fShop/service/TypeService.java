package com.jwd.fShop.service;

import com.jwd.fShop.service.exception.ServiceException;

/**
 * Specifies an API to backend to access product types.
 */
public interface TypeService {
    /**
     * Used to add new product type.
     *
     * @param name name of the new type
     * @throws ServiceException if some kind of exception occurred while execution or if invalid argument was passed.
     *                          Usually wraps cause exception
     */
    void add(String name) throws ServiceException;

    /**
     * Used to get type id.
     *
     * @param name name of the existing type
     * @return id of the type, if such does not exist, return <code>null</code>
     */
    Integer getId(String name);

    /**
     * Used to get type name by specified id
     *
     * @param id type id
     * @return name of the type, if such does not exist? returns <code>null</code>
     */
    String getName(int id);

    /**
     * Used to get names of all existing types
     *
     * @return types names
     */
    String[] getNames();
}
