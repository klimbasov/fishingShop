package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.exception.InvalidArgumentException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

public class ShowAdministration extends AbstractCommand implements Command {
    public ShowAdministration() {
        super(Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            req.getRequestDispatcher("WEB-INF/pages/administration.jsp").forward(req, resp);
        } catch (IOException | AccessViolationException | InvalidArgumentException | ServletException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }
}
