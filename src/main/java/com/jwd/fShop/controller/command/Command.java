package com.jwd.fShop.controller.command;

import com.jwd.fShop.controller.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Provides command pattern behavior for http request handling. CommandHolder use this to store mapped-to-string
 * behavior user can request to call.
 *
 * @author klimb
 * @see HttpServletResponse
 * @see HttpServletRequest
 */
public interface Command {
    /**
     * @param req  object specifies client request.
     * @param resp response object.
     * @throws CommandException if request or session parameters signature dose not correspond the parameters the
     *                          command requires
     */
    void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException;
}
