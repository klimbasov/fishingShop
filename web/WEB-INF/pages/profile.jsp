<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/26/2022
  Time: 11:17 AM
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
<fmt:setBundle basename = "locale/locale" var = "lang"/>
<div class="productInfo" id="content">
    <div class='product_info group'>
        <div class='product_info half'>
            <div class='product_info field'>
                <img alt="" src="resources/images/tick.jpg">
            </div>
        </div>
        <div class='product_info half'>
            <div class='product_info field'>
                <ul class='product_info text_list'>
                    <li>
                        <div class='product_info name'>
                            <span><fmt:message key = "label.profile.name" bundle = "${lang}"/></span>
                        </div>
                        <div class='product_info value'>
                            <span>${requestScope.user.DTO.name}</span>
                        </div>
                    </li>
                    <li>
                        <div class='product_info name'>
                            <span><fmt:message key = "label.profile.role" bundle = "${lang}"/></span>
                        </div>
                        <div class='product_info value'>
                            <span>${requestScope.role}</span>
                        </div>
                    </li>
                    <li>
                        <div class='product_info name'>
                            <span><fmt:message key = "label.profile.registration_date" bundle = "${lang}"/></span>
                        </div>
                        <div class='product_info value'>
                            <span>${requestScope.user.DTO.registrationDate}</span>
                        </div>
                    </li>
                    <li>
                        <div class='product_info name'>
                            <span><fmt:message key = "label.profile.registration_time" bundle = "${lang}"/></span>
                        </div>
                        <div class='product_info value'>
                            <span>${requestScope.user.DTO.registrationTime}</span>
                        </div>
                    </li>
                </ul>
            </div>
            <div class='product_info field'>
                <ul class='product_info action_list'>
                    <li>
                        <form method="get">
                            <input type='hidden' name='command_get' value='changeUser'/>
                            <input type='hidden' name='id' value="${requestScope.user.id}" />
                            <button type="submit"><fmt:message key = "button.profile.change" bundle = "${lang}"/></button>
                        </form>
                    </li>
                    <li>
                        <form method="get">
                            <input type='hidden' name='command_get' value='orders'/>
                            <input type='hidden' name='id' value="${requestScope.user.id}" />
                            <button type="submit"><fmt:message key = "button.profile.orders" bundle = "${lang}"/></button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
