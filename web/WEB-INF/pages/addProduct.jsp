<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/24/2022
  Time: 4:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <title>fShop</title>
</head>
<body>
<jsp:include page="componets/header.jsp"/>
<fmt:setLocale value = "en"/>
<fmt:setBundle basename = "locale/locale" var = "lang"/>
<jsp:include page="componets/message.jsp"/>
<fmt:setLocale value = "en"/>
<fmt:setBundle basename = "locale/locale" var = "headerLang"/>
<div class="addProduct_block">
    <FORM id="addProductForm" method="post" class="addProduct" >
        <div>
            <span><fmt:message key = "label.name" bundle = "${headerLang}"/></span>
            <input type="text" size="40" name="name" autocomplete="off" spellcheck="false">
            </p>
        </div>
        <div>
            <span><fmt:message key = "label.price" bundle = "${headerLang}"/></span>
            <input type="text" size="40" name="price" autocomplete="off" spellcheck="false">
            </span>
        </div>
        <div>
            <span><fmt:message key = "label.type" bundle = "${headerLang}"/></span>
            <select name="type">
                <c:forEach var="name" items="${typeNames}">
                    <option value="${name}">${name}</option>
                </c:forEach>
            </select>
            </p>
        </div>
        <div>
            <span><fmt:message key = "label.quantity" bundle = "${headerLang}"/></span>
            <input type="text" size="40" name="quantity" autocomplete="off" spellcheck="false">
            </p>
        </div>
        <input type="hidden" name="command" value="addProduct"/>
        <button type="submit"><fmt:message key = "label.apply" bundle = "${headerLang}"/></button>
    </FORM>
</div>


</body>
</html>
