<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/24/2022
  Time: 3:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ex" uri="custom" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
    <title>fShop</title>
</head>
<body>
<jsp:include page="../componets/header.jsp"/>
<p class="error">
    Error <ex:errorCode/>
</p>
<%--<jsp:include page="../componets/message.jsp"/>--%>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="locale/locale" var="lang"/>


</body>
</html>