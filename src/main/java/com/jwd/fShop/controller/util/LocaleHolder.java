package com.jwd.fShop.controller.util;

import com.jwd.fShop.controller.constant.LocaleAliases;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.Objects.isNull;

public class LocaleHolder {
    private static final Locale DEFAULT_LOCALE = Locale.US;
    private static Map<String, Locale> holder;
    private static LocaleHolder instance;

    LocaleHolder() {
        init();
    }

    public static LocaleHolder getInstance() {
        return isNull(instance) ? new LocaleHolder() : instance;
    }

    private void init() {
        holder = new HashMap<>();

        holder.put(LocaleAliases.ALIAS_EN, Locale.US);
        holder.put(LocaleAliases.ALIAS_RU, new Locale("ru", "RU"));
    }

    public Locale getLocale(String alias) {
        Locale spottedLocale = holder.get(alias);
        return isNull(spottedLocale) ? DEFAULT_LOCALE : spottedLocale;
    }

    public Locale gaeDefault() {
        return DEFAULT_LOCALE;
    }
}
