package com.jwd.fShop.controller.command.impl.get;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.constant.Attributes;
import com.jwd.fShop.controller.constant.Messages;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidArgumentException;
import com.jwd.fShop.controller.util.AttributeSetter;
import com.jwd.fShop.controller.util.ParameterParser;
import com.jwd.fShop.domain.Product;
import com.jwd.fShop.domain.Role;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.serviceHolder.ServiceHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;


public class ShowBasket extends AbstractCommand implements Command {
    private static final int PAGE_SIZE = 5;
    private static final int VISIBLE_RANGE = 3;

    public ShowBasket() {
        super(Role.USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            HttpSession session = req.getSession();
            ProductService productService = ServiceHolder.getInstance().getProductService();
            List<Product> products = new LinkedList<>();
            Map<Integer, Integer> basket = (Map<Integer, Integer>) session.getAttribute(Attributes.ATTRIBUTE_BASKET);
            int page = ParameterParser.parseInt(req.getParameter("page"), 1);
            int pageAmount = 1;
            float totalPrice = 0;

            if (nonNull(basket)) {
                pageAmount = basket.size() / PAGE_SIZE + (basket.size() % PAGE_SIZE == 0 ? 0 : 1);

                int skipCounter = (page - 1) * PAGE_SIZE;
                int addCounter = 0;
                for (Map.Entry<Integer, Integer> entry : basket.entrySet()) {
                    Product stored = productService.getById(entry.getKey());
                    int orderedPieces = entry.getValue();
                    totalPrice += stored.getPrice() * orderedPieces;
                    if (skipCounter == 0 && addCounter < PAGE_SIZE) {
                        products.add(new Product.Builder(stored).
                                setQuantity(orderedPieces).
                                build());
                        ++addCounter;
                    } else {
                        --skipCounter;
                    }
                }
            } else {
                session.setAttribute(Attributes.ATTRIBUTE_MESSAGE, Messages.EMPTY_BASKET);
            }
            AttributeSetter.setPageNavigation(req, page, pageAmount, VISIBLE_RANGE);
            req.setAttribute(Attributes.ATTRIBUTE_PRODUCTS, products);
            req.setAttribute(Attributes.ATTRIBUTE_TOTAL_PRICE, totalPrice);
            req.getRequestDispatcher("WEB-INF/pages/basket.jsp").forward(req, resp);
        } catch (IOException |
                AccessViolationException |
                ServiceException |
                InvalidArgumentException |
                ServletException exception) {
            throw new CommandException("In " + this.getClass().getName() + ".", exception);
        }
    }
}
