package com.jwd.fShop.service.impl;

import com.jwd.fShop.dao.UserDao;
import com.jwd.fShop.dao.daoHolder.DaoHolder;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.util.Hasher;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class UserServiceImpl implements UserService {
    static private final int DEFAULT_PAGE_SIZE = 5;
    static private int pageSize;
    private final UserDao userDao;

    public UserServiceImpl() {
        pageSize = DEFAULT_PAGE_SIZE;
        userDao = DaoHolder.getInstance().getUserDao();

    }


    @Override
    public User register(String name, String password, Role role) throws ServiceException {
        String hashedPassword = Hasher.hash(password);
        UserFilter filter = new UserFilter.Builder().
                setUserSubname(name).
                fullName(true).
                build();
        List<User> existed;
        User spotted;
        User registerUser = new User.Builder().
                setName(name).
                setHashedPassword(hashedPassword).
                setRegistrationDate(new Date(Date.from(Instant.now()).getTime())).
                setRegistrationTime(new Time(Time.from(Instant.now()).getTime())).
                setRole(role.getPriority()).
                build();
        User resultUser = null;

        try {
            existed = userDao.get(filter);
            if (existed.isEmpty()) {
                userDao.save(registerUser);
                existed = userDao.get(filter);
                spotted = existed.get(0);
                resultUser = new User.Builder().
                        setId(spotted.getId()).
                        setName(name).
                        setRole(role.getPriority()).
                        build();
            }

        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in register(User)", exception);
        }
        return resultUser;
    }

    @Override
    public User authorize(String name, String password) throws ServiceException {
        String hashedPassword = Hasher.hash(password);
        UserFilter filter = new UserFilter.Builder().
                setUserSubname(name).
                fullName(true).
                setSubHashPass(hashedPassword).
                build();
        User resultUser = null;
        List<User> exists;

        try {
            exists = userDao.get(filter);
            if (!exists.isEmpty()) {
                User spotted = exists.get(0);
                resultUser = new User.Builder().
                        setId(spotted.getId()).
                        setName(name).
                        setRole(spotted.getRole()).
                        build();
            }

        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in register(User)", exception);
        }
        return resultUser;
    }

    @Override
    public User changeRole(int userId, int role) throws ServiceException {
        User user = new User.Builder().
                setRole(role).
                build();
        UserFilter filter = new UserFilter.Builder().
                setId(userId).
                build();
        return updateAndGetUserInternal(userId, user, filter);
    }

    @Override
    public User changePassword(int userId, String password) throws ServiceException {
        String hashedPassword = Hasher.hash(password);
        User user = new User.Builder().
                setHashedPassword(hashedPassword).
                build();
        UserFilter filter = new UserFilter.Builder().setId(userId).build();
        return updateAndGetUserInternal(userId, user, filter);
    }

    @Override
    public User changeName(int userId, String username) throws ServiceException {
        User user = new User.Builder().
                setName(username).
                build();
        UserFilter filter = new UserFilter.Builder().
                setId(userId).
                build();
        return updateAndGetUserInternal(userId, user, filter);
    }

    @Override
    public User getById(int id) throws ServiceException {
        User spotted = null;
        UserFilter filter = new UserFilter.Builder().
                setId(id).
                build();
        List<User> exists;
        try {
            exists = userDao.get(filter);
            if (!exists.isEmpty()) {
                spotted = exists.get(0);
            }
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in register(User)", exception);
        }
        return spotted;
    }

    @Override
    public List<User> getPage(UserFilter filter, int page) throws ServiceException {
        List<User> users = new LinkedList<>();
        try {
            users = userDao.getSet(filter, (page - 1) * pageSize, pageSize);
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in register(User)", exception);
        }
        return users;
    }

    @Override
    public int getPagesQuantity(UserFilter filter) throws ServiceException {
        int quantity = 0;
        int totalValue = 0;
        try {
            totalValue = userDao.getQuantity(filter);
            quantity = totalValue / pageSize + (totalValue % pageSize == 0 ? 0 : 1);
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in register(User)", exception);
        }
        return quantity;
    }

    private User updateAndGetUserInternal(int userId, User user, UserFilter filter) throws ServiceException {
        User updated = null;
        List<User> exists;
        try {
            userDao.update(user, userId);
            exists = userDao.get(filter);
            if (!exists.isEmpty()) {
                updated = exists.get(0);
            }
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in register(User)", exception);
        }
        return updated;
    }
}
