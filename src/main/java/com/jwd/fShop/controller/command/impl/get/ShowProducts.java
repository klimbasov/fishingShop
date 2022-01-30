package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductFilter;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ShowProducts extends AbstractCommand implements Command {
    private static final int RANGE = 3;

    public ShowProducts() {
        super(Role.UNREGISTERED);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

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

            }
            try {
                highPrice = Float.parseFloat(req.getParameter("highPrice"));
            } catch (NumberFormatException | NullPointerException e) {
            }

            ProductFilter productFilter = new ProductFilter.Builder().
                    setName(subName).
                    setPriceRange(lowPrice, highPrice).
                    build();

            products = productService.getPage(productFilter, page);
            pageAmount = productService.getPagesQuantity(productFilter);

            AttributeSetter.setPageNavigation(req, page, pageAmount, RANGE);
            req.setAttribute("products", products);
            req.setAttribute("subName", subName);
            req.setAttribute("lowPrice", lowPrice);
            req.setAttribute("highPrice", highPrice);
            req.getRequestDispatcher("WEB-INF/pages/products.jsp").forward(req, resp);
        } catch (Exception exception) {
            throw new CommandException("In " + this.getClass().getName() + " : in execute().", exception);
        }
    }
}
