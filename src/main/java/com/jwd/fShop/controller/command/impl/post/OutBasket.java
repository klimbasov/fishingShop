package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.constant.ExceptionMessages;
import com.jwd.fShop.controller.constant.Parameters;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

import static java.util.Objects.nonNull;

public class OutBasket extends AbstractCommand implements Command {
    public OutBasket() {
        super(Role.USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            int id = ParameterParser.parseInt(req.getParameter(Parameters.PARAMETER_ID));
            HttpSession session = req.getSession();
            Map<Integer, Integer> basket = (Map<Integer, Integer>) session.getAttribute(Attributes.ATTRIBUTE_BASKET);

            if (nonNull(basket)) {
                basket.remove(id);
            }
            if (basket.isEmpty()) {
                session.removeAttribute(Attributes.ATTRIBUTE_BASKET);
            }

            resp.sendRedirect(req.getHeader("Referer"));

        } catch (IllegalArgumentException exception) {
            throw new CommandException(ExceptionMessages.INVALID_PARAMETER_STRUCTURE, exception);
        } catch (IOException | AccessViolationException exception) {
            throw new CommandException(exception);
        }
    }
}
