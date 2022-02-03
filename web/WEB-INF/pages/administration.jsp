<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/24/2022
  Time: 4:10 PM
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
<fmt:setBundle basename="locale/locale" var="lang"/>
<form class="barStyle" method="get">
    <input type="hidden" name="command_get" value="users"/>
    <button type='submit'><fmt:message key="label.users" bundle="${lang}"/></button>
</form>
<form class="barStyle" method="get">
    <input type="hidden" name="command_get" value="addProduct"/>
    <button type='submit'><fmt:message key="label.add_product" bundle="${lang}"/></button>
</form>
</body>
</html>
