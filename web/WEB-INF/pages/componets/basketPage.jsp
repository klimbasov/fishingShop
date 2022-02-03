<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/25/2022
  Time: 3:40 PM
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
                <div class='product_list info_slot'>
                    <a class='product_href' href="?command_get=product&id=${product.id}">
                        <span class='product_text'>
                                ${product.DTO.name}
                        </span>
                    </a>
                </div>
                <div>
                        <span class='product_list quantity'>
                                ${product.DTO.quantity}  <fmt:message key="label.piece" bundle="${userPageLang}"/>
                        </span>
                </div>
                <div>
                        <span class='product_list price'>
                                ${product.DTO.price} $
                        </span>
                </div>
                <div class="search">
                    <form method="post">
                        <input type="hidden" name="command" value="outBasket">
                        <input type="hidden" name="bunch_id" value="${product.id}">
                        <button type="submit"><fmt:message key="label.cancel" bundle="${userPageLang}"/></button>
                    </form>
                </div>
            </div>
            <img alt="" src='resources/images/tick.jpg'>

        </li>
    </c:forEach>
</ul>
</html>
