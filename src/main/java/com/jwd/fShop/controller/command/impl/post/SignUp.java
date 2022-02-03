package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Messages;
import com.jwd.fShop.controller.constant.RedirectionPaths;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.exception.InvalidArgumentException;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static com.jwd.fShop.controller.constant.Attributes.*;
import static com.jwd.fShop.controller.constant.Parameters.PARAMETER_PASSWORD;
import static com.jwd.fShop.controller.constant.Parameters.PARAMETER_USERNAME;
import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;


public class SignUp extends AbstractCommand implements Command {

    public SignUp() {
        super(Role.UNREGISTERED);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            String userName = req.getParameter(PARAMETER_USERNAME);
            String password = req.getParameter(PARAMETER_PASSWORD);
            Optional<IdentifiedDTO<User>> user;
            UserService userService = ServiceHolder.getInstance().getUserService();
            HttpSession session = req.getSession();
            user = userService.register(userName, password, Role.USER);
            if (user.isPresent()) {
                session.invalidate();
                session = req.getSession();
                session.setAttribute(ATTRIBUTE_USERNAME, user.get().getDTO().getName());
                session.setAttribute(ATTRIBUTE_ROLE, Role.getRole(user.get().getDTO().getRole()));
                session.setAttribute(ATTRIBUTE_USER_ID, user.get().getId());
                resp.sendRedirect(RedirectionPaths.TO_INDEX);
            } else {
                AttributeSetter.setMessage(session, Messages.SIGN_FAULT);
                resp.sendRedirect(RedirectionPaths.TO_SING_UP);
            }

        } catch (IOException | AccessViolationException | ServiceException | InvalidArgumentException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }
}
