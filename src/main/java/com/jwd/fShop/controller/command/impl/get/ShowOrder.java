package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Order;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.ProductBunch;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class ShowOrder extends AbstractCommand implements Command {
    private static final int RANGE = 3;
    private static final int PAGE_SIZE = 4;

    public ShowOrder() {
        super(Role.USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            ProductService productService = ServiceHolder.getInstance().getProductService();
            int id = ParameterParser.parseInt(req.getParameter("id"));
            Order order = ServiceHolder.getInstance().getOrderService().getById(id);
            List<ProductBunch> orderedProducts = order.getProductBunchList();
            int page = ParameterParser.parseInt(req.getParameter("page"), 1);
            int pageAmount = orderedProducts.size() / PAGE_SIZE + (orderedProducts.size() % PAGE_SIZE == 0 ? 0 : 1);
            ListIterator<ProductBunch> productBunchListIterator = orderedProducts.listIterator((page - 1) * PAGE_SIZE);
            List<Product> orderedProductsPage = new LinkedList<>();

            int counter = 0;
            while (productBunchListIterator.hasNext() && counter++ < PAGE_SIZE) {
                ProductBunch productBunch = productBunchListIterator.next();
                orderedProductsPage.add(new Product.Builder(productService.getById(productBunch.getProductId())).setQuantity(productBunch.getQuantity()).build());
            }

            req.setAttribute("id", id);
            req.setAttribute("order", order);
            req.setAttribute("products", orderedProductsPage);
            AttributeSetter.setPageNavigation(req, page, pageAmount, RANGE);
            req.getRequestDispatcher("WEB-INF/pages/order.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new CommandException("In " + this.getClass().getName() + " : access violation.", e);
        }
    }
}
