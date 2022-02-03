package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.constant.RedirectionPaths;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.exception.InvalidArgumentException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;
import static java.util.Objects.isNull;

public class ToBasket extends AbstractCommand implements Command {

    public ToBasket() {
        super(Role.USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            int id = ParameterParser.parseInt(req.getParameter("id"));
            int quantity = ParameterParser.parseInt(req.getParameter("quantity"));
            HttpSession session = req.getSession();
            Map<Integer, Integer> basket = (Map<Integer, Integer>) session.getAttribute(Attributes.ATTRIBUTE_BASKET);

            if (isNull(basket)) {
                basket = new HashMap<>();
                session.setAttribute(Attributes.ATTRIBUTE_BASKET, basket);
            }
            int existing = isNull(basket.get(id)) ? 0 : basket.get(id);
            basket.put(id, quantity + existing);
            resp.sendRedirect(RedirectionPaths.TO_PRODUCT + "&id=" + id);
        } catch (IOException | AccessViolationException | InvalidArgumentException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }
}
