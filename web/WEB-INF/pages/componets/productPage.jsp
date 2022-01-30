<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/25/2022
  Time: 3:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:setBundle basename = "locale/locale" var = "orderPageLang"/>
<ul class="product_list" id="list">
    <c:forEach var="product" items="${products}" >
        <li>

            <div class='product_list info_group'>
                <div class='product_list info_slot'>
                    <a class='product_href' href="?command_get=product&id=${product.id}">
                        <span class='product_text'>
                                ${product.name}
                        </span>
                    </a>
                </div>
                <div>
                        <span class='product_list quantity'>
                                ${product.quantity}  <fmt:message key = "label.price" bundle = "${orderPageLang}"/>
                        </span>
                </div>
                <div>
                        <span class='product_list price'>
                                ${product.price} $
                        </span>
                </div>
            </div>
            <img src='resources/images/tick.jpg'>

        </li>
    </c:forEach>
</ul>
</html>
