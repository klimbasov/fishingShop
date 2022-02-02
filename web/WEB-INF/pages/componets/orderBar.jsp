<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/28/2022
  Time: 3:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
</head>
<fmt:setBundle basename = "locale/locale" var = "orderLang"/>
<div class="search" id="search">
    <ul>
        <span>
            <fmt:message key = "label.order_id" bundle = "${orderLang}"/>: ${requestScope.order.id}
        </span>
        <span>
            <fmt:message key = "label.orderingDate" bundle = "${orderLang}"/>: ${requestScope.order.DTO.orderingDate}
        </span>
        <span>
            <fmt:message key = "label.order" bundle = "${orderLang}"/> ${requestScope.totalPrice} $
        </span>
    </ul>
</div>
</html>
