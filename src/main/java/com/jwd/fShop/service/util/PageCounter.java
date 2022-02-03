package com.jwd.fShop.service.util;

public class PageCounter {
    public static int countPages(int elementsAmount, int pageSize) {
        return (elementsAmount + pageSize - 1) / pageSize;
    }
}
