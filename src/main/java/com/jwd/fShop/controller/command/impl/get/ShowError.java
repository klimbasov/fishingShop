package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.domain.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

public class ShowError extends AbstractCommand implements Command {
    public ShowError() {
        super(Role.UNREGISTERED);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            req.getRequestDispatcher("WEB-INF/pages/error/error.jsp").forward(req, resp);
        } catch (ServletException | IOException | AccessViolationException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }
}
