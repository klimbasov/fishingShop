package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.constant.RedirectionPaths;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidArgumentException;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.TypeService;
import com.jwd.fShop.service.exception.FatalServiceException;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddProduct extends AbstractCommand implements Command {

    public AddProduct() {
        super(Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            String name = req.getParameter("name");
            String type = req.getParameter("type");
            Float price = ParameterParser.parseFloat(req.getParameter("price"));
            Integer quantity = ParameterParser.parseInt(req.getParameter("quantity"));
            ProductService productService = ServiceHolder.getInstance().getProductService();
            TypeService typeService = ServiceHolder.getInstance().getTypeService();

            Product product = new Product.
                    Builder().
                    setId(0).
                    setName(name).
                    setPrice(price).
                    setQuantity(quantity).
                    setProductType(typeService.getId(type)).
                    setVisible(true).
                    build();

            productService.add(product);

            req.getSession().setAttribute(Attributes.ATTRIBUTE_MESSAGE, "Product saved successfully");
            resp.sendRedirect(RedirectionPaths.TO_ADD_PRODUCT);
        } catch (AccessViolationException | IOException | FatalServiceException exception) {
            throw new CommandException("in AddProductCommand, while building product", exception);
        } catch (ServiceException | InvalidArgumentException exception) {
            req.getSession().setAttribute(Attributes.ATTRIBUTE_MESSAGE, "Product was not saved.");
            try {
                resp.sendRedirect(RedirectionPaths.TO_ADD_PRODUCT);
            } catch (IOException e) {
                throw new CommandException("in AddProductCommand, while building product", exception);
            }
        }
    }
}