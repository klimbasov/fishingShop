package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidArgumentException;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
            User user = userService.getById(id);

            req.setAttribute(Attributes.ATTRIBUTE_USER, user);
            req.setAttribute(Attributes.ATTRIBUTE_ROLE, Role.getRole(user.getRole()).getAlias());
            req.getRequestDispatcher("WEB-INF/pages/profile.jsp").forward(req, resp);
        } catch (IOException |
                ServiceException |
                ServletException |
                AccessViolationException |
                InvalidArgumentException exception) {
            throw new CommandException("In " + this.getClass().getName() + ".", exception);
        }
    }
}