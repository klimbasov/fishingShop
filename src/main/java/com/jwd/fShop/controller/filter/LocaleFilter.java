package com.jwd.fShop.controller.filter;

import com.jwd.fShop.controller.util.LocaleHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.jstl.core.Config;

import java.io.IOException;
import java.util.Locale;

import static java.util.Objects.isNull;

public class LocaleFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (isNull(session)) {
            session = req.getSession();
            Locale locale = LocaleHolder.getInstance().gaeDefault();
            Config.set(session, Config.FMT_LOCALE, locale);
        }
        chain.doFilter(req, res);
    }
}
