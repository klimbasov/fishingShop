<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/24/2022
  Time: 4:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <title>fShop</title>
</head>
<body>
<jsp:include page="componets/header.jsp"/>
<jsp:include page="componets/message.jsp"/>
<fmt:setBundle basename="locale/locale" var="lang"/>
<div class="addProduct_block">
    <FORM id="addProductForm" method="post" class="addProduct">
        <div>
            <span><fmt:message key="label.name" bundle="${lang}"/></span>
            <input type="text" size="40" name="name" autocomplete="off" spellcheck="false">
        </div>
        <div>
            <span><fmt:message key="label.price" bundle="${lang}"/></span>
            <input type="text" size="40" name="price" autocomplete="off" spellcheck="false">
        </div>
        <div>
            <span><fmt:message key="label.type" bundle="${lang}"/></span>
            <select name="type">
                <c:forEach var="name" items="${requestScope.typeNames}">
                    <option value="${name}">${name}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <span><fmt:message key="label.quantity" bundle="${lang}"/></span>
            <input type="text" size="40" name="quantity" autocomplete="off" spellcheck="false">
        </div>
        <div>
            <span><fmt:message key="label.visibility" bundle="${lang}"/></span>
            <input type="checkbox" name="visibility" checked>
        </div>
        <input type="hidden" name="command" value="addProduct"/>
        <button type="submit"><fmt:message key="label.apply" bundle="${lang}"/></button>
    </FORM>
</div>


</body>
</html>
