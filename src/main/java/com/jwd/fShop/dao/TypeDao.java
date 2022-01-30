package com.jwd.fShop.dao;

import com.jwd.fShop.dao.exception.DaoException;

import java.util.Map;

public interface TypeDao {

    int save(String name) throws DaoException;

    Map<Integer, String> load() throws DaoException;
}
