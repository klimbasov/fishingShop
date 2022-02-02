package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.constant.ExceptionMessages;
import com.jwd.fShop.controller.constant.Messages;
import com.jwd.fShop.controller.constant.RedirectionPaths;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidArgumentException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.domain.ProductBunch;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.OrderService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class OrderAll extends AbstractCommand implements Command {
    public OrderAll() {
        super(Role.USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            HttpSession session = req.getSession();
            Map<Integer, Integer> basket = (Map<Integer, Integer>) session.getAttribute(Attributes.ATTRIBUTE_BASKET);
            Integer userID = (Integer) session.getAttribute(Attributes.ATTRIBUTE_USER_ID);
            OrderService orderService = ServiceHolder.getInstance().getOrderService();

            if (nonNull(basket)) {
                orderService.add(toProductBunchList(basket), userID);
                session.removeAttribute(Attributes.ATTRIBUTE_BASKET);
                AttributeSetter.setMessage(session, Messages.ORDERING_SUCCESS);
            } else {
                AttributeSetter.setMessage(session, Messages.ORDERING_FAULT_EMPTY_BASkET);
            }
            resp.sendRedirect(RedirectionPaths.TO_INDEX);
        } catch (ServiceException | IOException | AccessViolationException exception) {
            throw new CommandException(createExceptionMessage(),exception);
        }
    }

    private List<ProductBunch> toProductBunchList(Map<Integer, Integer> map) {
        List<ProductBunch> productBunches = new LinkedList<>();

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (isNull(entry.getKey()) || isNull(entry.getValue())) {
                throw new InvalidArgumentException(ExceptionMessages.INVALID_PARAMETER_STRUCTURE);
            }
            productBunches.add(new ProductBunch(entry.getKey(), entry.getValue()));
        }

        return productBunches;
    }
}
