package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.exception.InvalidArgumentException;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

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
            Optional<IdentifiedDTO<Product>> product = ServiceHolder.getInstance().getProductService().getByBunches(id);
            if (product.isPresent()) {
                req.setAttribute(Attributes.ATTRIBUTE_TYPE_NAMES, types);
                req.setAttribute("product", product.get());
            } else {
                exceptionHandler(resp, HttpServletResponse.SC_NOT_FOUND, createExceptionMessage());
            }
            req.getRequestDispatcher("WEB-INF/pages/changeProduct.jsp").forward(req, resp);
        } catch (InvalidArgumentException |
                AccessViolationException |
                ServletException |
                ServiceException |
                IOException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }
}
