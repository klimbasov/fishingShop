package com.jwd.fShop.controller;

import com.jwd.fShop.controller.command.CommandHolder;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class FrontController extends HttpServlet {
    private static final Logger logger = LogManager.getRootLogger();
    private CommandHolder commandHolder;

    @Override
    public void init() throws ServletException {
        try {
            this.commandHolder = new CommandHolder();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ServletException(exception.getMessage(), exception);
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            String command = req.getParameter(Attributes.ATTRIBUTE_COMMAND_GET);
            commandHolder.getGetCommandByAlias(command).execute(req, resp);
        } catch (CommandException exception) {
            exceptionHandler(req, resp, exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            String commandName = req.getParameter(Attributes.ATTRIBUTE_COMMAND_POST);
            commandHolder.getPostCommandByAlias(commandName).execute(req, resp);
        } catch (CommandException exception) {
            exceptionHandler(req, resp, exception);
        }
    }

    private void exceptionHandler(HttpServletRequest req, HttpServletResponse resp, Exception exception) throws ServletException {
        logger.error(exception.getMessage());
        try {
            AttributeSetter.setMessage(req.getSession(), exception.getMessage());
            req.getRequestDispatcher("WEB-INF/pages/error/error.jsp").forward(req, resp);
        } catch (IOException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
