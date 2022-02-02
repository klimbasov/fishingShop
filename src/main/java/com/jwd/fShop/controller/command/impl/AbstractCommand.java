package com.jwd.fShop.controller.command.impl;


import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.InvalidSessionException;
import com.jwd.fShop.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;
import static java.util.Objects.isNull;

public abstract class AbstractCommand {
    protected final Role role;

    protected AbstractCommand(final Role role) {
        this.role = role;
    }

    protected void validateSessionRole(final Role role) throws InvalidSessionException {
        if (isNull(role)) {
            if (!(this.role == Role.UNREGISTERED)) {
                throw new InvalidSessionException();
            }
        } else {
            if (this.role.getPriority() > role.getPriority()) {
                throw new InvalidSessionException();
            }
        }
    }

    protected void validateRole(HttpServletRequest req, HttpServletResponse resp) throws AccessViolationException {
        Role role = (Role) req.getSession().getAttribute("role");
        try {
            validateSessionRole(role);
        } catch (InvalidSessionException exception) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new AccessViolationException(createExceptionMessage("validation violation"), exception);
        }
    }
}
