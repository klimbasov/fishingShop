package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowAdministration extends AbstractCommand implements Command {
    public ShowAdministration() {
        super(Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            req.getRequestDispatcher("WEB-INF/pages/administration.jsp").forward(req, resp);
        } catch (Exception exception) {
            throw new CommandException("In " + this.getClass().getName() + ". ", exception);
        }
    }
}
