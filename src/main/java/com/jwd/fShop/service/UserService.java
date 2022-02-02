package com.jwd.fShop.service;

import com.jwd.fShop.domain.Role;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;
import com.jwd.fShop.service.exception.ServiceException;

import java.util.List;

/**
 * Specifies a user business logic, maintains registration, authorization.
 */
public interface UserService {
    /**
     * Used to register new User.
     *
     * @param name     name of the user
     * @param password user's password
     * @param role     role, the user will have
     * @return <code>User</code> instance with uninitialized <code>password</code> field
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    User register(String name, String password, final Role role) throws ServiceException;

    /**
     * Used to authorize user.
     *
     * @param name     name of the user
     * @param password user's password
     * @return <code>User</code> instance with uninitialized <code>password</code> field
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    User authorize(String name, String password) throws ServiceException;

    /**
     * Used to change user's role.
     *
     * @param userId id of the user
     * @param role   new user's role
     * @return <code>User</code> instance with uninitialized <code>password</code> field
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    User changeRole(int userId, int role) throws ServiceException;

    /**
     * Used to change user's password.
     *
     * @param userId   id of the user
     * @param password new user's password
     * @return <code>User</code> instance with uninitialized <code>password</code> field
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    User changePassword(int userId, String password) throws ServiceException;

    /**
     * Used to change user's name.
     *
     * @param userId   id of the user
     * @param username new user's name
     * @return <code>User</code> instance with uninitialized <code>password</code> field
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    User changeName(int userId, String username) throws ServiceException;

    /**
     * Used to get the user by id.
     *
     * @param id id of the user
     * @return <code>User</code> instance with uninitialized <code>password</code> field
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    User getById(int id) throws ServiceException;

    /**
     * Used to get a range of users on specified filtering. Size of the range depends on implementation of the service.
     *
     * @param filter filter object denotes the searching criteria
     * @param page   number of the searched page.
     * @return <code>List</code> of the size, specified by service implementation. If there is no users to
     * specified <code>page</code>, <code>List</code> will be empty
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    List<User> getPage(UserFilter filter, int page) throws ServiceException;

    /**
     * Used to get page quantity for specified filtering.
     *
     * @param filter filter object denotes the searching criteria
     * @return how many pages exist for the specified filter
     * @throws ServiceException if some kind of exception occurred while execution or invalid argument was passed.
     *                          Usually warps cause exception.
     */
    int getPagesQuantity(final UserFilter filter) throws ServiceException;
}
