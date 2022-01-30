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
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ChangeRole extends AbstractCommand implements Command {
    public ChangeRole() {
        super(Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        int id = 0;
        int newRole;
        HttpSession session = req.getSession();

        try {
            validateRole(req, resp);

            id = ParameterParser.parseInt(req.getParameter("id"));
            newRole = ParameterParser.parseInt(req.getParameter("role"));

            ServiceHolder.getInstance().getUserService().changeRole(id, newRole);
            resp.sendRedirect(RedirectionPaths.TO_USER + "&id=" + id);
        } catch (AccessViolationException exception) {
            throw new CommandException(exception);
        } catch (ServiceException | InvalidArgumentException exception) {
            AttributeSetter.setMessage(session, Messages.ROLE_CHANGING_FAULT);
            try {
                resp.sendRedirect(RedirectionPaths.TO_CHANGE_USER + "&id=" + id);
            } catch (IOException e) {
                throw new CommandException(exception);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
