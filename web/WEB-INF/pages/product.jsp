<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/24/2022
  Time: 8:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
  <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
</head>
<jsp:include page="componets/header.jsp"/>
<fmt:setLocale value = "en"/>
<fmt:setBundle basename = "locale/locale" var = "lang"/>
<body>
<div class="productInfo" id="content">
  <div class='product_info group'>
    <div class='product_info half'>
      <div class='product_info field'>
        <img src="resources/images/tick.jpg">
      </div>
    </div>
    <div class='product_info half'>
      <div class='product_info field'>
        <ul class='product_info text_list'>
          <li>
            <div class='product_info name'>
              <span><fmt:message key = "label.name" bundle = "${lang}"/></span>
            </div>
            <div class='product_info value'>
              <span>${product.name}</span>
            </div>
          </li>
          <li>
            <div class='product_info name'>
              <span><fmt:message key = "label.price" bundle = "${lang}"/></span>
            </div>
            <div class='product_info value'>
              <span>${product.price}</span>
            </div>
          </li>
          <li>
            <div class='product_info name'>
              <span><fmt:message key = "label.piece" bundle = "${lang}"/></span>
            </div>
            <div class='product_info value'>
              <span>${product.quantity}</span>
            </div>
          </li>
        </ul>
      </div>
      <div class='product_info field'>
        <c:if test="${sessionScope.role !=null}">
          <ul class='product_info action_list'>
            <li>
              <form method="post">
                <input type='hidden' name='command' value='toBasket'/>
                <input type='hidden' name='id' value="${product.id}" />
                <input type='number' min="1" max="1000" value="1" name='quantity' pattern="[0-9]{,3}" oninput="this.value = Math.min(Math.abs(this.value), 1000)"/>
                <button type="submit"><fmt:message key = "label.to_basket" bundle = "${lang}"/></button>
              </form>
            </li>
            <c:if test="${sessionScope.role.alias == 'admin'}">
              <li>
                <form method="get">
                  <input type='hidden' name='command_get' value='changeProduct' />
                  <input type='hidden' name='product_id' value="${product.id}" />
                  <button type="submit"><fmt:message key = "label.change" bundle = "${lang}"/></button>
                </form>
              </li>
            </c:if>
          </ul>
        </c:if>
      </div>
    </div>
  </div>
</div>
</body>
</html>
