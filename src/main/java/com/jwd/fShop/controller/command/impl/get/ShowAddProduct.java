package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ShowAddProduct extends AbstractCommand implements Command {

    public ShowAddProduct() {
        super(Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            String[] types = ServiceHolder.getInstance().getTypeService().getNames();
            req.setAttribute(Attributes.ATTRIBUTE_TYPE_NAMES, types);
            req.getRequestDispatcher("WEB-INF/pages/addProduct.jsp").forward(req, resp);
        } catch (Exception exception) {
            throw new CommandException("in " + this.getClass().getName() + " : in execute() while forwarding request", exception);
        }
    }
}
