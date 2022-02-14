<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/25/2022
  Time: 3:32 PM
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
<fmt:setBundle basename="locale/locale" var="orderLang"/>
<div class="search" id="search">
    <ul>
        <li>
            <form method="post">
                <input type="hidden" name="command" value="order"/>
                <button type='submit'><fmt:message key="label.order" bundle="${orderLang}"/></button>
            </form>
        </li>
        <span>
            <fmt:message key="label.order" bundle="${orderLang}"/> <c:out value="${requestScope.totalPrice}"/> $
        </span>
        <li>
            <form method="post">
                <input type="hidden" name="command" value="emptyBasket"/>
                <button type='submit'><fmt:message key="label.cancel" bundle="${orderLang}"/></button>
            </form>
        </li>
    </ul>
</div>
</html>
