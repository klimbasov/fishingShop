<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/25/2022
  Time: 3:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:setBundle basename = "locale/locale" var = "orderPageLang"/>
<ul class="product_list" id="list">
    <c:forEach var="product" items="${requestScope.products}" >
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
                                ${product.quantity}  <fmt:message key = "label.piece" bundle = "${orderPageLang}"/>
                        </span>
                </div>
                <div>
                        <span class='product_list price'>
                                ${product.price} $
                        </span>
                </div>
        <c:if test="${sessionScope.role.alias == 'admin'}">
            <div class='product_info name'>
                <c:if test="${product.visible == true}">
                    <span><fmt:message key = "label.visibility.visible" bundle = "${orderPageLang}"/></span>
                </c:if>
                <c:if test="${product.visible == false}">
                    <span><fmt:message key = "label.visibility.invisible" bundle = "${orderPageLang}"/></span>
                </c:if>
            </div>
        </c:if>
            </div>
            <img alt="" src='resources/images/tick.jpg'>

        </li>
    </c:forEach>
</ul>
</html>
