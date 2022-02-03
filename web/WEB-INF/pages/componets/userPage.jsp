<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/27/2022
  Time: 2:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:setBundle basename="locale/locale" var="userPageLang"/>
<ul class="product_list" id="list">
    <c:forEach var="order" items="${requestScope.users}">
        <li>

            <div class='product_list info_group'>
                <div class='product_list info_slot'>
                    <a class='product_href' href="?command_get=user&id=${order.id}">
                        <span class='product_text'>
                                ${order.DTO.name}
                        </span>
                    </a>
                </div>
                <div>
                        <span class='product_list quantity'>
                                <fmt:message key="label.profile.registration_date"
                                             bundle="${userPageLang}"/>: ${order.DTO.registrationDate}
                        </span>
                </div>
                <div>
                        <span class='product_list quantity'>
                                <fmt:message key="label.profile.registration_time"
                                             bundle="${userPageLang}"/>: ${order.DTO.registrationTime}
                        </span>
                </div>
                <div>
                        <span class='product_list quantity'>
                                <fmt:message key="label.profile.role" bundle="${userPageLang}"/>: ${order.DTO.role}
                        </span>
                </div>
            </div>
            <img alt="" src='resources/images/tick.jpg'>

        </li>
    </c:forEach>
</ul>
</html>
