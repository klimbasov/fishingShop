package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidArgumentException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.*;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;


public class ShowOrder extends AbstractCommand implements Command {
    private static final int RANGE = 3;
    private static final int PAGE_SIZE = 4;

    public ShowOrder() {
        super(Role.USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            ProductService productService = ServiceHolder.getInstance().getProductService();
            int id = ParameterParser.parseInt(req.getParameter("id"));
            Optional<IdentifiedDTO<Order>> order = ServiceHolder.getInstance().getOrderService().getById(id);
            if(order.isPresent()){
                List<ProductBunch> orderedProducts = order.get().getDTO().getProductBunchList();
                int page = ParameterParser.parseInt(req.getParameter("page"), 1);
                int pageAmount = (orderedProducts.size() + PAGE_SIZE -1) / PAGE_SIZE;

                ListIterator<ProductBunch> productBunchListIterator = orderedProducts.listIterator((page - 1) * PAGE_SIZE);
                List<Product> orderedProductsPage = new LinkedList<>();
                int counter = 0;
                while (productBunchListIterator.hasNext() && counter++ < PAGE_SIZE) {
                    ProductBunch productBunch = productBunchListIterator.next();
                    Optional<IdentifiedDTO<Product>> dto = productService.getById(productBunch.getProductId());
                    dto.ifPresent(productIdentifiedDTO -> orderedProductsPage.add(new Product.Builder(productIdentifiedDTO.getDTO()).setQuantity(productBunch.getQuantity()).build()));
                }

                req.setAttribute("id", id);
                req.setAttribute("order", order.get());
                req.setAttribute("products", orderedProductsPage);
                AttributeSetter.setPageNavigation(req, page, pageAmount, RANGE);
                req.getRequestDispatcher("WEB-INF/pages/order.jsp").forward(req, resp);
            }else {
                throw new CommandException("Not found");
            }
        } catch (IOException |
                AccessViolationException |
                ServiceException |
                InvalidArgumentException |
                ServletException exception) {
            throw new CommandException(createExceptionMessage(), exception);
        }
    }
}
