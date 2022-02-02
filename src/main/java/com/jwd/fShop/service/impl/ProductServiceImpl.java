package com.jwd.fShop.service.impl;

import com.jwd.fShop.dao.ProductDao;
import com.jwd.fShop.dao.daoHolder.DaoHolder;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductFilter;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

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
            throw new ServiceException(createExceptionMessage(), exception);
        }
    }

    @Override
    public List<IdentifiedDTO<Product>> getPage(final ProductFilter filter, int page) throws ServiceException {
        List<IdentifiedDTO<Product>> products;
        int offset = (page - 1) * pageSize;

        try {
            products = productStorage.getSet(filter, offset, pageSize);
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return products;
    }

    @Override
    public int getPagesQuantity(ProductFilter productFilter) throws ServiceException {
        int totalValue;
        try {
            totalValue = productStorage.getQuantity(productFilter);
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return (totalValue + pageSize - 1) / pageSize;
    }

    @Override
    public Optional<IdentifiedDTO<Product>> getById(int id) throws ServiceException {
        Optional<IdentifiedDTO<Product>> product = Optional.empty();
        ProductFilter filter = new ProductFilter.Builder().setId(id).build();
        try {
            List<IdentifiedDTO<Product>> existing = productStorage.get(filter);
            if (!existing.isEmpty()) {
                product = Optional.of(existing.get(0));
            }
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
        return product;
    }

    @Override
    public void changeById(Product product, int id) throws ServiceException {
        try {
            productStorage.update(product, id);
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(), exception);
        }
    }
}
