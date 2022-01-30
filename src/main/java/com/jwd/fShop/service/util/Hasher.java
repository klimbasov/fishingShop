package com.jwd.fShop.service.util;

public class Hasher {
    private static final String salt = "salt";

    public static String hash(final String str) {
        return str + salt;
    }
}
