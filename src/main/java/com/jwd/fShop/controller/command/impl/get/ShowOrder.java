package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.*;
import com.jwd.fShop.exception.InvalidArgumentException;
import com.jwd.fShop.service.OrderService;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;


public class ShowOrder extends AbstractCommand implements Command {
    private static final int RANGE = 3;

    public ShowOrder() {
        super(Role.USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            int id = ParameterParser.parseInt(req.getParameter("id"));
            int page = ParameterParser.parseInt(req.getParameter("page"), 1);
            OrderService orderService = ServiceHolder.getInstance().getOrderService();
            Optional<IdentifiedDTO<Order>> order = orderService.getById(id);
            if (order.isPresent()) {
                Order orderDto = order.get().getDTO();
                int pageAmount = orderService.getOrderedProductsPageQuantity(orderDto);
                List<ProductBunch> productBunchesPage = orderService.getOrderedProductsPage(orderDto, page);
                List<Product> orderedProductsPage = collectProducts(productBunchesPage);

                req.setAttribute("id", id);
                req.setAttribute("order", order.get());
                req.setAttribute("products", orderedProductsPage);
                AttributeSetter.setPageNavigation(req, page, pageAmount, RANGE);
                req.getRequestDispatcher("WEB-INF/pages/order.jsp").forward(req, resp);
            } else {
                exceptionHandler(resp, HttpServletResponse.SC_NOT_FOUND, createExceptionMessage());
            }
        } catch (IOException |
                AccessViolationException |
                ServiceException |
                InvalidArgumentException |
                ServletException exception) {
            exceptionHandler(resp, createExceptionMessage(), exception);
        }
    }

    private List<Product> collectProducts(List<ProductBunch> productBunches) throws ServiceException {
        ProductService productService = ServiceHolder.getInstance().getProductService();
        List<IdentifiedDTO<Product>> dtoList = productService.getByBunches(productBunches);
        return dtoList.stream().map(IdentifiedDTO::getDTO).collect(Collectors.toList());
    }
}
