package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Order;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.OrderService;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

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
            List<Order> orders = orderService.getPage(id, page);
            int pageAmount = orderService.getPageQuantity(id);

            AttributeSetter.setPageNavigation(req, page, pageAmount, RANGE);
            req.setAttribute("orders", orders);
            req.setAttribute("id", id);
            req.getRequestDispatcher("WEB-INF/pages/orders.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new CommandException("In " + this.getClass().getName() + " : access violation.", e);
        }
    }
}
