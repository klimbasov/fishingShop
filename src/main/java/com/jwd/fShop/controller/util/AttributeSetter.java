package com.jwd.fShop.controller.util;

import com.jwd.fShop.controller.constant.Attributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AttributeSetter {
    public static void setPageNavigation(HttpServletRequest request, int page, int pageAmount, int visibleRange) {
        int lowPage = 1;
        page = Math.max(page, 1);
        int highPage = pageAmount;
        if (pageAmount > 2 * visibleRange + 1) {
            lowPage = max(1, min(page - visibleRange, pageAmount - 2 * visibleRange));
            highPage = lowPage + 2 * visibleRange;

        }
        int previousPage = page <= 1 ? 1 : page - 1;
        int nextPage = page >= pageAmount ? pageAmount : page + 1;

        request.setAttribute("page", page);
        request.setAttribute("lowPage", lowPage);
        request.setAttribute("highPage", highPage);
        request.setAttribute("pageAmount", pageAmount);
        request.setAttribute("previousPage", previousPage);
        request.setAttribute("nextPage", nextPage);
    }

    public static void setMessage(HttpSession session, String message) {
        session.setAttribute(Attributes.ATTRIBUTE_MESSAGE, message);
    }
}
