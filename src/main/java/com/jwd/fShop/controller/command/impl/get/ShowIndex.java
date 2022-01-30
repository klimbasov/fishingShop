package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ShowIndex extends AbstractCommand implements Command {

    public ShowIndex() {
        super(Role.UNREGISTERED);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            req.getRequestDispatcher("WEB-INF/pages/index.jsp").forward(req, resp);
        } catch (Exception exception) {
            throw new CommandException("in " + this.getClass().getName() + " : in execute() while forwarding request", exception);
        }
    }
}
