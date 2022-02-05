package com.jwd.fShop.service.impl;

import com.jwd.fShop.controller.constant.ExceptionMessages;
import com.jwd.fShop.dao.UserDao;
import com.jwd.fShop.dao.daoHolder.DaoHolder;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.util.Hasher;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.jwd.fShop.dao.util.ArgumentValidator.validate;
import static com.jwd.fShop.service.util.Validator.*;
import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

public class UserServiceImpl implements UserService {
    static private final int DEFAULT_PAGE_SIZE = 5;
    static private int pageSize;
    private final UserDao userDao;

    public UserServiceImpl() {
        pageSize = DEFAULT_PAGE_SIZE;
        userDao = DaoHolder.getInstance().getUserDao();

    }


    @Override
    public Optional<IdentifiedDTO<User>> register(String name, String password, Role role) throws ServiceException {
        validateUsername(name);
        validatePassword(password);

        String hashedPassword = Hasher.hash(password);
        UserFilter filter = new UserFilter.Builder().
                setUserSubName(name).
                fullName(true).
                build();
        List<IdentifiedDTO<User>> existed;
        Optional<IdentifiedDTO<User>> spotted = Optional.empty();
        User registerUser = new User.Builder().
                setName(name).
                setHashedPassword(hashedPassword).
                setRegistrationDate(new Date(Date.from(Instant.now()).getTime())).
                setRegistrationTime(new Time(Time.from(Instant.now()).getTime())).
                setRole(role.getPriority()).
                build();

        try {
            existed = userDao.get(filter);
            if (existed.isEmpty()) {
                userDao.save(registerUser);
                existed = userDao.get(filter);
                spotted = Optional.of(existed.get(0));
            }

        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return spotted;
    }

    @Override
    public Optional<IdentifiedDTO<User>> authorize(String name, String password) throws ServiceException {
        validateUsername(name);
        validatePassword(password);

        String hashedPassword = Hasher.hash(password);
        UserFilter filter = new UserFilter.Builder().
                setUserSubName(name).
                fullName(true).
                setSubHashPass(hashedPassword).
                build();
        Optional<IdentifiedDTO<User>> resultUser = Optional.empty();
        List<IdentifiedDTO<User>> exists;

        try {
            exists = userDao.get(filter);
            if (!exists.isEmpty()) {
                resultUser = Optional.of(exists.get(0));
            }

        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return resultUser;
    }

    @Override
    public Optional<IdentifiedDTO<User>> changeRole(int userId, int role) throws ServiceException {
        validatePositive(userId);
        User user = new User.Builder().
                setRole(role).
                build();
        return updateAndGetUserInternal(userId, user);
    }

    @Override
    public Optional<IdentifiedDTO<User>> changePassword(int userId, String password) throws ServiceException {
        validatePassword(password);
        validatePositive(userId);
        String hashedPassword = Hasher.hash(password);
        User user = new User.Builder().
                setHashedPassword(hashedPassword).
                build();
        return updateAndGetUserInternal(userId, user);
    }

    @Override
    public Optional<IdentifiedDTO<User>> changeName(int userId, String username) throws ServiceException {
        validateUsername(username);
        validatePositive(userId);
        Optional<IdentifiedDTO<User>> user;
        User UserWithUpdateFields = new User.Builder().
                setName(username).
                build();
        UserFilter filter = new UserFilter.Builder().
                setUserSubName(username).
                fullName(true).
                build();
        try {
            if (userDao.get(filter).isEmpty()) {
                user = updateAndGetUserInternal(userId, UserWithUpdateFields);
            } else {
                throw new ServiceException(createExceptionMessage(ExceptionMessages.USERNAME_COLLISION));
            }
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return user;
    }

    @Override
    public Optional<IdentifiedDTO<User>> getById(int id) throws ServiceException {
        validatePositive(id);
        Optional<IdentifiedDTO<User>> spotted = Optional.empty();
        UserFilter filter = new UserFilter.Builder().
                setId(id).
                build();
        try {
            List<IdentifiedDTO<User>> exists = userDao.get(filter);
            if (!exists.isEmpty()) {
                spotted = Optional.of(exists.get(0));
            }
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return spotted;
    }

    @Override
    public List<IdentifiedDTO<User>> getPage(UserFilter filter, int page) throws ServiceException {
        validate(filter);
        validatePositive(page);
        List<IdentifiedDTO<User>> users;
        try {
            users = userDao.getSet(filter, (page - 1) * pageSize, pageSize);
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return users;
    }

    @Override
    public int getPagesQuantity(UserFilter filter) throws ServiceException {
        validate(filter);
        int totalValue;
        try {
            totalValue = userDao.getQuantity(filter);
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return (totalValue + pageSize - 1) / pageSize;
    }

    private Optional<IdentifiedDTO<User>> updateAndGetUserInternal(int userId, User user) throws ServiceException {
        UserFilter filter = new UserFilter.Builder().
                setId(userId).
                build();
        Optional<IdentifiedDTO<User>> updated = Optional.empty();
        List<IdentifiedDTO<User>> exists;
        try {
            userDao.update(user, userId);
            exists = userDao.get(filter);
            if (!exists.isEmpty()) {
                updated = Optional.of(exists.get(0));
            }
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return updated;
    }
}
