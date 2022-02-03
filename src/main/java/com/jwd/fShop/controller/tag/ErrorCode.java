package com.jwd.fShop.controller.tag;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class ErrorCode extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        JspWriter out = pageContext.getOut();
        try {
            out.print(response.getStatus());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return super.doStartTag();
    }
}
