package com.jwd.fShop.controller;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.CommandHolder;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.constant.ExceptionMessages;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class FrontController extends HttpServlet {
    private static final Logger logger = LogManager.getRootLogger();
    private CommandHolder commandHolder;

    @Override
    public void init() throws ServletException {
        try {
            this.commandHolder = new CommandHolder();
        } catch (Exception exception) {
            logger.error(exception);
            throw new ServletException(exception.getMessage(), exception);
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            String alias = req.getParameter(Attributes.ATTRIBUTE_COMMAND_GET);
            Command command = commandHolder.getGetCommandByAlias(alias);
            if (nonNull(command)) {
                command.execute(req, resp);
            } else {
                generateCommandNotFound(resp);
            }
        } catch (CommandException exception) {
            exceptionHandler(req, resp, exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            String alias = req.getParameter(Attributes.ATTRIBUTE_COMMAND_POST);
            Command command = commandHolder.getPostCommandByAlias(alias);
            if (nonNull(command)) {
                command.execute(req, resp);
            } else {
                generateCommandNotFound(resp);
            }
        } catch (CommandException exception) {
            exceptionHandler(req, resp, exception);
        }
    }

    private void generateCommandNotFound(HttpServletResponse response) throws CommandException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        throw new CommandException(ExceptionMessages.COMMAND_NOT_FOUND);
    }

    private void exceptionHandler(HttpServletRequest req, HttpServletResponse resp, Exception exception) throws ServletException {
        logger.error(exception.getMessage());
        try {
            AttributeSetter.setMessage(req.getSession(), exception.getMessage());
            req.getRequestDispatcher("WEB-INF/pages/error/error.jsp").forward(req, resp);
        } catch (IOException e) {
            logger.error(exception);
            throw new ServletException(e.getMessage());
        }
    }
}
