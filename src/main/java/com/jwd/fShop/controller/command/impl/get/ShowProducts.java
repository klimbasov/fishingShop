package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidArgumentException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductFilter;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

public class ShowProducts extends AbstractCommand implements Command {
    private static final int RANGE = 3;

    public ShowProducts() {
        super(Role.UNREGISTERED);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            HttpSession session = req.getSession(false);
            ProductService productService = ServiceHolder.getInstance().getProductService();
            List<Product> products;
            String subName = req.getParameter("subName");
            Float lowPrice = null;
            Float highPrice = null;
            int page = ParameterParser.parseInt(req.getParameter("page"), 1);
            int pageAmount;

            try {
                lowPrice = Float.parseFloat(req.getParameter("lowPrice"));
            } catch (NumberFormatException | NullPointerException e) {
//todo refactor this
            }
            try {
                highPrice = Float.parseFloat(req.getParameter("highPrice"));
            } catch (NumberFormatException | NullPointerException e) {
            }

            ProductFilter.Builder builder = new ProductFilter.Builder();
            if (nonNull(session)) {
                Role role = (Role) session.getAttribute(Attributes.ATTRIBUTE_ROLE);
                if (nonNull(role)) {
                    if (role == Role.USER) {
                        builder.setVisibility(true);
                    }
                }
            } else {
                builder.setVisibility(true);
            }
            ProductFilter filter = builder.
                    setName(subName).
                    setPriceRange(lowPrice, highPrice).
                    build();

            products = productService.getPage(filter, page);
            pageAmount = productService.getPagesQuantity(filter);

            AttributeSetter.setPageNavigation(req, page, pageAmount, RANGE);
            req.setAttribute("products", products);
            req.setAttribute("subName", subName);
            req.setAttribute("lowPrice", lowPrice);
            req.setAttribute("highPrice", highPrice);
            req.getRequestDispatcher("WEB-INF/pages/products.jsp").forward(req, resp);
        } catch (IOException | AccessViolationException | ServiceException | InvalidArgumentException | ServletException exception) {
            throw new CommandException("In " + this.getClass().getName() + " : in execute().", exception);
        }
    }
}
