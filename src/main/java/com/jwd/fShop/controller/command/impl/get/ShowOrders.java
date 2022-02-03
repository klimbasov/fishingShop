package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.IdentifiedDTO;
import com.jwd.fShop.domain.Order;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.exception.InvalidArgumentException;
import com.jwd.fShop.service.OrderService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

public class ShowOrders extends AbstractCommand implements Command {
    private static final int RANGE = 3;

    public ShowOrders() {
        super(Role.USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            int id = ParameterParser.parseInt(req.getParameter("id"));
            int page = ParameterParser.parseInt(req.getParameter("page"), 1);
            OrderService orderService = ServiceHolder.getInstance().getOrderService();
            List<IdentifiedDTO<Order>> orders = orderService.getPage(id, page);
            int pageAmount = orderService.getPageQuantity(id);

            AttributeSetter.setPageNavigation(req, page, pageAmount, RANGE);
            req.setAttribute("orders", orders);
            req.setAttribute("id", id);
            req.getRequestDispatcher("WEB-INF/pages/orders.jsp").forward(req, resp);
        } catch (IOException |
                AccessViolationException |
                ServiceException |
                InvalidArgumentException |
                ServletException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }
}
