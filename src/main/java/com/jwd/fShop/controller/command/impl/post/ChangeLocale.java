package com.jwd.fShop.controller.command.impl.post;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.impl.AbstractCommand;
import com.jwd.fShop.controller.exception.AccessViolationException;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.util.LocaleHolder;
import com.jwd.fShop.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.jstl.core.Config;

import java.io.IOException;
import java.util.Locale;

public class ChangeLocale extends AbstractCommand implements Command {

    public ChangeLocale() {
        super(Role.UNREGISTERED);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateRole(req, resp);

            HttpSession session = req.getSession();
            String localeAlias = req.getParameter("locale");
            Locale locale = LocaleHolder.getInstance().getLocale(localeAlias);

            Config.set(session, Config.FMT_LOCALE, locale);

            resp.sendRedirect(req.getHeader("Referer"));
        } catch (AccessViolationException | IOException exception) {
            throw new CommandException(exception);
        }
    }
}
//jakarta.servlet.jsp.jstl.fmt.locale.session
