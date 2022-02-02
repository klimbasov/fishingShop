package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidArgumentException;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ShowProfile extends AbstractCommand implements Command {
    public ShowProfile() {
        super(Role.USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            int id = (Integer) req.getSession().getAttribute(Attributes.ATTRIBUTE_ID);
            UserService userService = ServiceHolder.getInstance().getUserService();
            User user = userService.getById(id);

            req.setAttribute(Attributes.ATTRIBUTE_USER, user);
            req.setAttribute(Attributes.ATTRIBUTE_ROLE, Role.getRole(user.getRole()).getAlias());
            req.getRequestDispatcher("WEB-INF/pages/profile.jsp").forward(req, resp);
        } catch (IOException | AccessViolationException | ServiceException | InvalidArgumentException | ServletException e) {
            throw new CommandException("In " + this.getClass().getName() + " : access violation.", e);
        }
    }
}
