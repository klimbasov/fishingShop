package com.jwd.fShop.service.util;

import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.service.exception.ServiceException;

public class PageCounter {
    public static int countPages(int elementsAmount, int pageSize){
        return (elementsAmount+pageSize-1) / pageSize;
    }
}
