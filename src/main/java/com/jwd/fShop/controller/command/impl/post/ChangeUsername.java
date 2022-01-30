package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.constant.Messages;
import com.jwd.fShop.controller.constant.RedirectionPaths;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidArgumentException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChangeUsername extends AbstractCommand implements Command {
    public ChangeUsername() {
        super(Role.USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        int id = 0;
        String username;

        try {
            validate(req, resp);

            id = ParameterParser.parseInt(req.getParameter("id"));
            username = req.getParameter("username");

            ServiceHolder.getInstance().getUserService().changeName(id, username);
            resp.sendRedirect(RedirectionPaths.TO_PROFILE + "&id=" + id);
        } catch (AccessViolationException | IOException exception) {
            throw new CommandException(exception);
        } catch (ServiceException | InvalidArgumentException exception) {
            AttributeSetter.setMessage(req.getSession(), Messages.USERNAME_CHANGING_FAULT);
            try {
                resp.sendRedirect(RedirectionPaths.TO_CHANGE_USER + "&id=" + id);
            } catch (IOException e) {
                throw new CommandException(exception);
            }
        }
    }

    protected void validate(HttpServletRequest req, HttpServletResponse resp) throws AccessViolationException {
        validateRole(req, resp);

        int userId = (int) req.getSession().getAttribute(Attributes.ATTRIBUTE_ID);
        int requestId = ParameterParser.parseInt(req.getParameter("id"));

        if (requestId != userId) {
            throw new AccessViolationException("Attempt to change alien password.");
        }
    }
}
