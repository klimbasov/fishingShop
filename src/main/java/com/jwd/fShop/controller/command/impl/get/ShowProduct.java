package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.constant.ExceptionMessages;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.exception.InvalidArgumentException;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;
import static java.util.Objects.nonNull;

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
            Optional<IdentifiedDTO<Product>> product = productService.getByBunches(id);
            if (product.isPresent()) {
                checkAccess(req.getSession(false), product.get().getDTO());
                req.setAttribute("product", product.get());
                req.getRequestDispatcher("WEB-INF/pages/product.jsp").forward(req, resp);
            } else {
                exceptionHandler(resp, HttpServletResponse.SC_NOT_FOUND, createExceptionMessage());
            }

        } catch (IOException |
                AccessViolationException |
                ServiceException |
                InvalidArgumentException |
                ServletException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }

    private void checkAccess(HttpSession session, Product dto) throws AccessViolationException {
        boolean actualVisibility = dto.getVisibility();
        if (nonNull(session) && actualVisibility==false) {
            Role role = (Role) session.getAttribute(Attributes.ATTRIBUTE_ROLE);
            boolean hasAccessToInvisible = false;
            if (nonNull(role)) {
                if (role == Role.ADMIN) {
                    hasAccessToInvisible = true;
                }
            }
            if(hasAccessToInvisible == false){
                throw new AccessViolationException(ExceptionMessages.ACCESS_VIOLATION);
            }
        }
    }
}
