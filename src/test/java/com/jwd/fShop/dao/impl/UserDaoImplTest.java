package com.jwd.fShop.dao.impl;

import com.jwd.fShop.dao.UserDao;
import com.jwd.fShop.dao.config.DataBaseConfig;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {
    private static final String PROPERTIES_PATH = "db_test.properties";
    private static final String INIT_PROPERTIES_PATH = "dbTestInit.properties";
    private static final String SCRIPT_PATH = "src/test/resources/scripts/TestDatabaseScript.sql";

    private UserDao userDao;
    private DataBaseConfig dataBaseConfig;
    private ScriptRunner scriptRunner;
    private boolean isNonInit = true;

    @BeforeEach
    void setUp() throws SQLException, FileNotFoundException, FatalDaoException {
        if (isNonInit) {
            dataBaseConfig = new DataBaseConfig(INIT_PROPERTIES_PATH);
            Connection connection = dataBaseConfig.getConnection();
            scriptRunner = new ScriptRunner(connection);
        }
        createTestDatabase();
        if (isNonInit) {
            userDao = new UserDaoImpl(PROPERTIES_PATH);
        }
        isNonInit = false;

    }

    private void createTestDatabase() throws SQLException, FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(SCRIPT_PATH));
        scriptRunner.runScript(reader);
    }

    @Test
    void save() {
        String userName = "SomeOne";
        String passwordHash = "123123";
        int role = 1;
        User user = new User.Builder()
                .setName(userName)
                .setHashedPassword(passwordHash)
                .setRole(role)
                .setRegistrationDate(new Date(Date.from(Instant.now()).getTime()))
                .setRegistrationTime(new Time(Time.from(Instant.now()).getTime()))
                .build();

        assertDoesNotThrow(() -> userDao.save(user));
    }

    @Test
    void get() throws DaoException {
        User user1 = new User.Builder()
                .setName("user1")
                .setHashedPassword("12345")
                .setRole(1)
                .setRegistrationDate(new Date(Date.from(Instant.now()).getTime()))
                .setRegistrationTime(new Time(Time.from(Instant.now()).getTime()))
                .build();
        User user2 = new User.Builder()
                .setName("user2")
                .setHashedPassword("12345")
                .setRole(1)
                .setRegistrationDate(new Date(Date.from(Instant.now()).getTime()))
                .setRegistrationTime(new Time(Time.from(Instant.now()).getTime()))
                .build();
        User user3 = new User.Builder()
                .setName("user3")
                .setHashedPassword("12345")
                .setRole(1)
                .setRegistrationDate(new Date(Date.from(Instant.now()).getTime()))
                .setRegistrationTime(new Time(Time.from(Instant.now()).getTime()))
                .build();
        List<User> expected = Arrays.asList(user1, user2, user3);
        List<User> actual;
        UserFilter userFilter = new UserFilter.Builder().setUserSubname("user").build();

        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);

        actual = userDao.get(userFilter);


        assertTrue(compareUserLists(actual, expected));
    }

    @Test
    void update() throws DaoException {
        User user = new User.Builder()
                .setName("user1")
                .setHashedPassword("12345")
                .setRole(1)
                .setRegistrationDate(new Date(Date.from(Instant.now()).getTime()))
                .setRegistrationTime(new Time(Time.from(Instant.now()).getTime()))
                .build();
        int actual;
        int expected = 2;
        int userId;

        userDao.save(user);
        userId = userDao.get(new UserFilter.Builder().setUserSubname(user.getName()).build()).get(0).getId();
        userDao.update(new User.Builder().setRole(expected).build(), userId);
        actual = userDao.get(new UserFilter.Builder().setId(userId).build()).get(0).getRole();

        assertEquals(actual, expected);
    }

    @Test
    void getSet() throws DaoException {
        User user1 = new User.Builder()
                .setName("user1")
                .setHashedPassword("12345")
                .setRole(1)
                .setRegistrationDate(new Date(Date.from(Instant.now()).getTime()))
                .setRegistrationTime(new Time(Time.from(Instant.now()).getTime()))
                .build();
        User user2 = new User.Builder()
                .setName("user2")
                .setHashedPassword("12345")
                .setRole(1)
                .setRegistrationDate(new Date(Date.from(Instant.now()).getTime()))
                .setRegistrationTime(new Time(Time.from(Instant.now()).getTime()))
                .build();
        User user3 = new User.Builder()
                .setName("user3")
                .setHashedPassword("12345")
                .setRole(1)
                .setRegistrationDate(new Date(Date.from(Instant.now()).getTime()))
                .setRegistrationTime(new Time(Time.from(Instant.now()).getTime()))
                .build();
        UserFilter userFilter = new UserFilter.Builder().setUserSubname("user").build();

        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);

        List<User> expectedUsersSetWithOneElement = Collections.singletonList(user2);
        List<User> expectedUsersSetWithLackOfElements = Collections.singletonList(user3);
        List<User> expectedUsersSetWithSomeElements = Arrays.asList(user1, user2);
        List<User> expectedUsersSetOutOfBound = new LinkedList<>();

        List<User> actualUsersSetWithOneElement = userDao.getSet(userFilter, 1, 1);
        List<User> actualUsersSetWithLackOfElements = userDao.getSet(userFilter, 2, 2);
        List<User> actualUsersSetWithSomeElements = userDao.getSet(userFilter, 0, 2);
        List<User> actualUsersSetOutOfBound = userDao.getSet(userFilter, 5, 2);

        assertTrue(compareUserLists(expectedUsersSetWithOneElement, actualUsersSetWithOneElement));
        assertTrue(compareUserLists(expectedUsersSetWithLackOfElements, actualUsersSetWithLackOfElements));
        assertTrue(compareUserLists(expectedUsersSetWithSomeElements, actualUsersSetWithSomeElements));
        assertTrue(compareUserLists(expectedUsersSetOutOfBound, actualUsersSetOutOfBound));
    }

    @Test
    void getQuantity() throws DaoException {
        int expected;
        int actual;
        UserFilter userFilter = new UserFilter.Builder().setUserSubname("u").build();

        expected = userDao.get(userFilter).size();
        actual = userDao.getQuantity(userFilter);
        assertEquals(expected, actual);
    }

    boolean compareUserLists(List<User> expected, List<User> actual) {
        if (expected.size() != actual.size()) {
            return false;
        }
        int size = expected.size();
        for (int counter = 0; counter < size; counter++) {
            if (!compareUsers(expected.get(counter), actual.get(counter))) {
                return false;
            }
        }
        return true;
    }

    boolean compareUsers(User expected, User actual) {
        return Objects.equals(expected.getName(), actual.getName())
                && Objects.equals(expected.getRole(), actual.getRole())
                && Objects.equals(expected.getRegistrationDate(), actual.getRegistrationDate())
                && Objects.equals(expected.getRegistrationTime(), actual.getRegistrationTime());
    }
}