<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/27/2022
  Time: 5:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:setBundle basename="locale/locale" var="userPageLang"/>
<ul class="product_list" id="list">
    <c:forEach var="user" items="${requestScope.orders}">
        <li>
            <div class='product_list info_group'>
                <div class='product_list info_slot'>
                    <a class='product_href' href="?command_get=order&id=${user.id}">
                        <span class='product_text'>
                                ${user.id}
                        </span>
                    </a>
                </div>
                <div>
                        <span class='product_list quantity'>
                                <fmt:message key="label.ordering_date"
                                             bundle="${userPageLang}"/>: ${user.DTO.orderingDate}
                        </span>
                </div>
            </div>
        </li>
    </c:forEach>
</ul>
</html>
