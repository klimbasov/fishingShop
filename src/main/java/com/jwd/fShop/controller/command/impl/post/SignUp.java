package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Messages;
import com.jwd.fShop.controller.constant.RedirectionPaths;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Objects;

import static com.jwd.fShop.controller.constant.Attributes.*;
import static com.jwd.fShop.controller.constant.Parameters.PARAMETER_PASSWORD;
import static com.jwd.fShop.controller.constant.Parameters.PARAMETER_USERNAME;


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
            User user;
            UserService userService = ServiceHolder.getInstance().getUserService();
            HttpSession session = req.getSession();
            user = userService.register(userName, password, Role.USER);
            if (Objects.nonNull(user)) {
                session.invalidate();
                session = req.getSession();
                session.setAttribute(ATTRIBUTE_USERNAME, user.getName());
                session.setAttribute(ATTRIBUTE_ROLE, Role.getRole(user.getRole()));
                session.setAttribute(ATTRIBUTE_ID, user.getId());
                resp.sendRedirect(RedirectionPaths.TO_INDEX);
            } else {
                AttributeSetter.setMessage(session, Messages.SIGN_FAULT);
                resp.sendRedirect(RedirectionPaths.TO_SING_UP);
            }

        } catch (Exception exception) {
            throw new CommandException("in " + this.getClass().getName() + " , adding user", exception);
        }
    }
}
