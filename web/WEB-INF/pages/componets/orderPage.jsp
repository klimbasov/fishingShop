<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/28/2022
  Time: 2:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:setBundle basename="locale/locale" var="userPageLang"/>
<ul class="product_list" id="list">
    <c:forEach var="product" items="${requestScope.products}">
        <li>

            <div class='product_list info_group'>
                <div>
                        <span class='product_text'>
                                <c:out value="${product.name}"/>
                        </span>
                </div>
                <div>
                        <span class='product_list quantity'>
                                <c:out value="${product.quantity}"/>  <fmt:message key="label.piece" bundle="${userPageLang}"/>
                        </span>
                </div>
                <div>
                        <span class='product_list price'>
                                <c:out value="${product.price}"/> $
                        </span>
                </div>

            </div>
            <img alt="" src='resources/images/tick.jpg'>
        </li>
    </c:forEach>
</ul>
</html>
