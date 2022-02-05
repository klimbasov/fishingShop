<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/20/2022
  Time: 8:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <title>fShop</title>
    <c:set var="command_name" value="products" scope="request"/>
    <c:set var="navigationCommand" value="products" scope="request"/>
</head>
<body>
<jsp:include page="componets/header.jsp"/>
<jsp:include page="componets/message.jsp"/>
<jsp:include page="componets/searchBar.jsp"/>
<jsp:include page="componets/pageNavigation.jsp"/>
<jsp:include page="componets/productPage.jsp"/>
</body>
</html>
