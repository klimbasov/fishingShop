package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ShowUsers extends AbstractCommand implements Command {
    private static final int RANGE = 3;

    public ShowUsers() {
        super(Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            String subUsername = req.getParameter("subUsername");
            UserService userService = ServiceHolder.getInstance().getUserService();
            UserFilter filter = new UserFilter.Builder().
                    setUserSubname(subUsername).
                    build();
            int pageAmount = userService.getPagesQuantity(filter);
            int page = ParameterParser.parseInt(req.getParameter("page"), 1);
            List<User> users = userService.getPage(filter, page);

            AttributeSetter.setPageNavigation(req, page, pageAmount, RANGE);
            req.setAttribute("users", users);
            req.getRequestDispatcher("WEB-INF/pages/users.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new CommandException("In " + this.getClass().getName() + " : access violation.", e);
        }
    }
}
