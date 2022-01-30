package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.RedirectionPaths;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignOut extends AbstractCommand implements Command {
    public SignOut() {
        super(Role.UNREGISTERED);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            req.getSession().invalidate();
            resp.sendRedirect(RedirectionPaths.TO_INDEX);
        } catch (Exception e) {
            throw new CommandException("In " + this.getClass().getName() + " while forwarding.", e);
        }
    }
}
