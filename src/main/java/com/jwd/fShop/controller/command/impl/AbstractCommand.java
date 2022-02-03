package com.jwd.fShop.controller.command.impl;


import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidSessionException;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.exception.InvalidArgumentException;
import com.jwd.fShop.service.exception.FatalServiceException;
import com.jwd.fShop.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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

    protected void exceptionHandler(HttpServletResponse response, String message, Exception exception) throws CommandException {
        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        if (exception instanceof IOException) {
            status = HttpServletResponse.SC_NOT_FOUND;
        }
        if (exception instanceof AccessViolationException) {
            status = HttpServletResponse.SC_FORBIDDEN;
        }
        if (exception instanceof ServiceException) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        if (exception instanceof InvalidArgumentException) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }
        if (exception instanceof FatalServiceException) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        if (exception instanceof ServletException) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        response.setStatus(status);
        throw new CommandException(message, exception);
    }

    protected void exceptionHandler(HttpServletResponse response, int status, String message, Exception exception) throws CommandException {
        response.setStatus(status);
        throw new CommandException(message, exception);
    }

    protected void exceptionHandler(HttpServletResponse response, int status, String message) throws CommandException {
        response.setStatus(status);
        throw new CommandException(message);
    }
}
