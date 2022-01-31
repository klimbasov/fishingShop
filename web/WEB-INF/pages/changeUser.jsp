<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/26/2022
  Time: 12:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>fShop</title>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
    <link rel="stylesheet" type="text/css" href="resources/css/experimental.css">
</head>
<body>
<jsp:include page="componets/header.jsp"/>
<fmt:setBundle basename = "locale/locale" var = "lang"/>
<div class="center">
    <div class="block">
        <c:if test="${sessionScope.userId == requestScope.id}">
            <form method="post" class="form-inline">
                <label><fmt:message key = "label.change_user.name" bundle = "${lang}"/></label>
                <input type="hidden" name="command" value="changeUsername">
                <input type="text" size="40" name="username" autocomplete="off" spellcheck="false">
                <button type="submit"><fmt:message key = "label.apply" bundle = "${lang}"/></button>
            </form>
        </c:if>
        <c:if test="${sessionScope.userId != requestScope.id && sessionScope.role.alias == 'admin'}">
            <form method="post" class="form-inline">
                <label><fmt:message key = "label.change_user.role" bundle = "${lang}"/></label>
                <select name="role">
                    <option value="2"><fmt:message key = "label.user" bundle = "${lang}"/></option>
                    <option value="8"><fmt:message key = "label.admin" bundle = "${lang}"/></option>
                </select>
                <input type="hidden" name="command" value="changeRole">
                <button type="submit"><fmt:message key = "label.apply" bundle = "${lang}"/></button>
            </form>
        </c:if>
    </div>
</div>
</body>
</html>
