package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.domain.User;
import com.jwd.fShop.domain.UserFilter;
import com.jwd.fShop.exception.InvalidArgumentException;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

public class ShowUsers extends AbstractCommand implements Command {
    private static final int RANGE = 3;

    public ShowUsers() {
        super(Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            String subName = req.getParameter("subName");
            UserService userService = ServiceHolder.getInstance().getUserService();
            UserFilter filter = new UserFilter.Builder().
                    setUserSubName(subName).
                    build();
            int pageAmount = userService.getPagesQuantity(filter);
            int page = ParameterParser.parseInt(req.getParameter("page"), 1);
            List<IdentifiedDTO<User>> users = userService.getPage(filter, page);

            AttributeSetter.setPageNavigation(req, page, pageAmount, RANGE);
            req.setAttribute("subName", subName);
            req.setAttribute("users", users);
            req.getRequestDispatcher("WEB-INF/pages/users.jsp").forward(req, resp);
        } catch (IOException |
                AccessViolationException |
                ServiceException |
                InvalidArgumentException |
                ServletException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }
}
