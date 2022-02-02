<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/28/2022
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <title>fShop</title>
    <c:set var="navigationCommand" value="order" scope="request"/>
</head>
<body>
<jsp:include page="componets/header.jsp"/>
<jsp:include page="componets/orderBar.jsp"/>
<jsp:include page="componets/pageNavigation.jsp"/>
<jsp:include page="componets/message.jsp"/>
<jsp:include page="componets/orderPage.jsp"/>
</body>
</html>
