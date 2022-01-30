package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowProduct extends AbstractCommand implements Command {

    public ShowProduct() {
        super(Role.UNREGISTERED);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            int id = ParameterParser.parseInt(req.getParameter("id"));
            ProductService productService = ServiceHolder.getInstance().getProductService();
            Product product = productService.getById(id);

            req.setAttribute("product", product);
            req.getRequestDispatcher("WEB-INF/pages/product.jsp").forward(req, resp);
        } catch (Exception exception) {
            throw new CommandException("in " + this.getClass().getName() + " : in execute() while forwarding request", exception);
        }
    }
}
