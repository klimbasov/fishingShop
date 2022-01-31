<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 10/12/2021
  Time: 7:25 PM
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
</head>
<body>
<jsp:include page="componets/header.jsp"/>
<jsp:include page="componets/message.jsp"/>
<fmt:setBundle basename = "locale/locale" var = "lang"/>
<div class="registration_block">
    <FORM id="registrationForm" method="post" class="registration">
        <div>
            <span><fmt:message key = "label.username" bundle = "${lang}"/></span>
            <input type="text" size="40" name="username" autocomplete="off" spellcheck="false">
        </div>
        <div>
            <span><fmt:message key = "label.password" bundle = "${lang}"/></span>
            <input type="text" size="40" name="password" autocomplete="off" spellcheck="false">
        </div>
        <input type="hidden" name="command" value="signIn"/>
        <button type="submit"><fmt:message key = "label.sinIn" bundle = "${lang}"/></button>
    </FORM>
</div>

</body>
</html>
