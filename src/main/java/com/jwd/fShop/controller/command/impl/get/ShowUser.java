package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.exception.InvalidArgumentException;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

public class ShowUser extends AbstractCommand implements Command {
    public ShowUser() {
        super(Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            UserService userService = ServiceHolder.getInstance().getUserService();
            int id = ParameterParser.parseInt(req.getParameter("id"));
            Optional<IdentifiedDTO<User>> user = userService.getById(id);

            if (user.isPresent()) {
                req.setAttribute(Attributes.ATTRIBUTE_USER, user.get());
                req.setAttribute(Attributes.ATTRIBUTE_ROLE, Role.getRole(user.get().getDTO().getRole()).getAlias());
                req.getRequestDispatcher("WEB-INF/pages/profile.jsp").forward(req, resp);
            } else {
                exceptionHandler(resp, HttpServletResponse.SC_NOT_FOUND, createExceptionMessage());
            }
        } catch (IOException |
                ServiceException |
                ServletException |
                AccessViolationException |
                InvalidArgumentException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }
}
