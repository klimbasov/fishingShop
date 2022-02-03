package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.RedirectionPaths;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.exception.InvalidArgumentException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

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
        } catch (IOException | AccessViolationException | InvalidArgumentException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }
}
