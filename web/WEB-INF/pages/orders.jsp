<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/27/2022
  Time: 6:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>fShop</title>
    <c:set var="navigationCommand" value="orders" scope="request"/>
<%--    <c:set var="additionalParameter" value="id" scope="request"/>--%>
</head>
<body>
<jsp:include page="componets/header.jsp"/>
<jsp:include page="componets/pageNavigation.jsp"/>
<jsp:include page="componets/message.jsp"/>
<jsp:include page="componets/ordersPage.jsp"/>
</body>
</html>
