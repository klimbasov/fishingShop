package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Messages;
import com.jwd.fShop.controller.constant.RedirectionPaths;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidArgumentException;
import com.jwd.fShop.controller.util.AttributeSetter;
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

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;
import static java.util.Objects.nonNull;

public class ChangeProduct extends AbstractCommand implements Command {

    public ChangeProduct() {
        super(Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        int id = 0;
        try {
            validateRole(req, resp);

            id = ParameterParser.parseInt(req.getParameter("id"));
            boolean visibility = nonNull(req.getParameter("visibility"));
            String name = req.getParameter("name");
            String type = req.getParameter("type");
            float price = ParameterParser.parseFloat(req.getParameter("price"));
            int quantity = ParameterParser.parseInt(req.getParameter("quantity"));
            ProductService productService = ServiceHolder.getInstance().getProductService();
            TypeService typeService = ServiceHolder.getInstance().getTypeService();

            Product product = new Product.
                    Builder().
                    setName(name).
                    setPrice(price).
                    setQuantity(quantity).
                    setProductType(typeService.getId(type)).
                    setVisible(visibility).
                    build();

            productService.changeById(product, id);
            AttributeSetter.setMessage(req.getSession(), Messages.PRODUCT_CHANGING_SUCCESS);
            resp.sendRedirect(RedirectionPaths.TO_CHANGE_PRODUCT + "&id=" + id);
        } catch (AccessViolationException | IOException | FatalServiceException exception) {
            throw new CommandException(createExceptionMessage(), exception);
        } catch (ServiceException | InvalidArgumentException exception) {
            AttributeSetter.setMessage(req.getSession(), Messages.PRODUCT_CHANGING_FAULT);
            try {
                resp.sendRedirect(RedirectionPaths.TO_CHANGE_PRODUCT + "&id=" + id);
            } catch (IOException e) {
                throw new CommandException(createExceptionMessage(), exception);
            }
        }
    }
}
