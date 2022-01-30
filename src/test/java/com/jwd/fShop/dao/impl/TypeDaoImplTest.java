package com.jwd.fShop.dao.impl;

import com.jwd.fShop.dao.TypeDao;
import com.jwd.fShop.dao.config.DataBaseConfig;
import com.jwd.fShop.dao.constant.TypeSqlNames;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TypeDaoImplTest {
    private static final String PROPERTIES_PATH = "db_test.properties";
    private static final String INIT_PROPERTIES_PATH = "dbTestInit.properties";
    private static final String INIT_SCRIPT_PATH = "src/test/resources/scripts/TestDatabaseScript.sql";


    private TypeDao typeDao;
    private DataBaseConfig dataBaseConfig;
    private ScriptRunner scriptRunner;
    private boolean isNonInit = true;

    @BeforeEach
    void setUp() throws FatalDaoException, SQLException, FileNotFoundException {
        if (isNonInit) {
            dataBaseConfig = new DataBaseConfig(INIT_PROPERTIES_PATH);
            Connection connection = dataBaseConfig.getConnection();
            scriptRunner = new ScriptRunner(connection);
        }
        createTestDatabase();
        if (isNonInit) {
            typeDao = new TypeDaoImpl(PROPERTIES_PATH);
        }
        isNonInit = false;
    }

    private void createTestDatabase() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(INIT_SCRIPT_PATH));
        scriptRunner.runScript(reader);
    }


    @Test
    void load() throws SQLException, DaoException {
        Map<Integer, String> typesMap;
        String[] types = {"first", "second", "third"};
        List<String> expected = Arrays.asList(types);
        List<String> actual;

        dataBaseConfig.getConnection()
                .createStatement()
                .execute("INSERT INTO fshop_test.types (typeName) VALUES ('" + types[0] + "'), ('" + types[1] + "'), ('" + types[2] + "');");

        typesMap = typeDao.load();
        actual = new LinkedList<>(typesMap.values());

        assertIterableEquals(expected, actual);
    }

    @Test
    void save() throws DaoException, SQLException {
        String type = "first";
        String expected = type;
        String actual;

        typeDao.save(type);

        ResultSet resultSet = dataBaseConfig.getConnection()
                .createStatement()
                .executeQuery("SELECT * From fshop_test.types;");
        resultSet.next();
        actual = resultSet.getString(TypeSqlNames.NAME_COLUMN_NAME);

        assertEquals(expected, actual);

    }
}