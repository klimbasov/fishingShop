package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.constant.Messages;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidArgumentException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class ShowChangeProduct extends AbstractCommand implements Command {
    public ShowChangeProduct() {
        super(Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            int id = ParameterParser.parseInt(req.getParameter("id"));
            String[] types = ServiceHolder.getInstance().getTypeService().getNames();
            Product product = ServiceHolder.getInstance().getProductService().getById(id);
            if (nonNull(product)) {
                req.setAttribute(Attributes.ATTRIBUTE_TYPE_NAMES, types);
                req.setAttribute("product", product);
            } else {
                AttributeSetter.setMessage(req.getSession(), Messages.PRODUCT_NOT_FOUND);
            }
            req.getRequestDispatcher("WEB-INF/pages/changeProduct.jsp").forward(req, resp);
        } catch (InvalidArgumentException |
                AccessViolationException |
                ServletException |
                ServiceException |
                IOException exception) {
            throw new CommandException(exception);
        }
    }
}
