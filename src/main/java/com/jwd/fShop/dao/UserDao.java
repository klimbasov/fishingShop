package com.jwd.fShop.dao;

import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;

import java.util.LinkedList;
import java.util.List;

/**
 * Data access object class interface.
 * A UserDao object is able to provide access to information exchange with database table, holding users.
 *
 * @author klimb
 * @version 1.0
 */
public interface UserDao {
    /**
     * Used to add user to the database table.
     * @param user user to save
     * @throws DaoException if some kind of exception occurred while execution or invalid argument was passed.
     * Usually warps cause exception.
     */
    void save(User user) throws DaoException;

    /**
     * Used to get list of all users by filter object.
     * @param filter filter object specifies parameters range search for
     * @throws DaoException if some kind of exception occurred while execution or invalid argument was passed.
     * Usually warps cause exception.
     */
    List<IdentifiedDTO<User>> get(UserFilter filter) throws DaoException;

    /**
     * Used to update some existing user. User argument's id field must be initialised.
     * @param user - User object, carrying parameters to update. ID must be initialized.
     * @throws DaoException if some kind of exception occurred while execution or invalid argument was passed.
     * Usually warps cause exception.
     */
    void update(User user, int id) throws DaoException;

    /**
     * Used to get list of some range of users by filter object.
     * @param filter - filter required user parameters;
     * @param offset - logical ordinal number of user set. Translated to offset as setSize * setNumber;
     * @param size   - size of user set.
     * @return List of users
     * @throws DaoException - if arguments are not valid? or if some exception is occurred while transaction.
     */
    List<IdentifiedDTO<User>> getSet(UserFilter filter, int offset, int size) throws DaoException;

    /**
     * Used to get quantity of existing sets for the specified filter.
     * @param filter - carrying filtering parameters;
     * @return Quantity of sets for the specified filter.
     */
    int getQuantity(UserFilter filter) throws DaoException;
}
