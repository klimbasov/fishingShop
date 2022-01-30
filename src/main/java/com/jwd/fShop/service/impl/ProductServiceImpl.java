package com.jwd.fShop.service.impl;

import com.jwd.fShop.dao.ProductDao;
import com.jwd.fShop.dao.daoHolder.DaoHolder;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductFilter;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.exception.ServiceException;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    static private final int DEFAULT_PAGE_SIZE = 5;
    static private int pageSize;
    private final ProductDao productStorage;


    public ProductServiceImpl() {
        pageSize = DEFAULT_PAGE_SIZE;
        productStorage = DaoHolder.getInstance().getProductDao();
    }

    @Override
    public void add(Product product) throws ServiceException {

        try {
            productStorage.save(product);
        } catch (DaoException exception) {
            throw new ServiceException("in ProductServiceImpl: in addProduct(Product) while setting product in dao", exception);
        }
    }

    @Override
    public List<Product> getPage(final ProductFilter filter, int page) throws ServiceException {
        List<Product> products;
        int offset = (page - 1) * pageSize;

        try {
            products = productStorage.getSet(filter, offset, pageSize);
        } catch (DaoException exception) {
            throw new ServiceException("in ProductServiceImpl: in getProduct(ProductFilter) while getting products from dao", exception);
        }
        return products;
    }

    @Override
    public int getPagesQuantity(ProductFilter productFilter) throws ServiceException {
        int quantity = 0;
        int totalValue = 0;

        try {
            totalValue = productStorage.getQuantity(productFilter);
            quantity = totalValue / pageSize + (totalValue % pageSize == 0 ? 0 : 1);
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + ": in getProduct(ProductFilter).", exception);
        }
        return quantity;
    }

    @Override
    public Product getById(int id) throws ServiceException {
        Product product = null;
        ProductFilter filter = new ProductFilter.Builder().setId(id).build();
        List<Product> existingProducts;

        try {
            existingProducts = productStorage.get(filter);
            if (!existingProducts.isEmpty()) {
                product = existingProducts.get(0);
            }
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in getProduct(int) while getting product", exception);
        }
        return product;
    }

    @Override
    public void changeById(Product product, int id) throws ServiceException {

        try {
            productStorage.update(product, id);
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in getProduct(int) while getting product", exception);
        }
    }
}
