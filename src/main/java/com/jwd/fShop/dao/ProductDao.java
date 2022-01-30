package com.jwd.fShop.dao;

import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductFilter;

import java.util.List;

public interface ProductDao {
    int save(Product product) throws DaoException;

    int saveMany(List<Product> products) throws DaoException;

    void delete(int id) throws DaoException;

    void update(Product product, int id) throws DaoException;

    List<Product> get(ProductFilter filter) throws DaoException;

    List<Product> getSet(ProductFilter filter, int offset, int size) throws DaoException;

    int getQuantity(ProductFilter filter) throws DaoException;
}
