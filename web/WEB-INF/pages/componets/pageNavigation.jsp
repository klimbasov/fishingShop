<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/25/2022
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
</head>
<c:if test="${requestScope.pageAmount>0}">
<div class='barStyle'>
    <nav class='barStyle'>
        <ul class='barStyle'>
            <li>
                <form method="get">
                    <input type="hidden" name="command_get" value="${requestScope.navigationCommand}">
                    <input type="hidden" name="page" value="1">
                    <input type="hidden" name="id" value="${requestScope.id}">
                    <input type="hidden" name="subName" value="${requestScope.subName}">
                    <input type="hidden" name="lowPrice" value="${requestScope.lowPrice}">
                    <input type="hidden" name="highPrice" value="${requestScope.highPrice}">
                    <button type='submit'>1</button>
                </form>
            </li>
            <li>
                <form method="get" action="products">
                    <input type="hidden" name="command_get" value="${requestScope.navigationCommand}"/>
                    <input type="hidden" name="page" value="${requestScope.previousPage}">
                    <input type="hidden" name="id" value="${requestScope.id}">
                    <input type="hidden" name="subName" value="${requestScope.subName}">
                    <input type="hidden" name="lowPrice" value="${requestScope.lowPrice}">
                    <input type="hidden" name="highPrice" value="${requestScope.highPrice}">
                    <button type='submit'><</button>
                </form>
            </li>
            <c:forEach var="conter" begin="${requestScope.lowPage}" end="${requestScope.highPage}">
                <li>
                    <form method="get">
                        <input type="hidden" name="command_get" value="${requestScope.navigationCommand}"/>
                        <input type="hidden" name="page" value="${conter}">
                        <input type="hidden" name="id" value="${requestScope.id}">
                        <input type="hidden" name="subName" value="${requestScope.subName}">
                        <input type="hidden" name="lowPrice" value="${requestScope.lowPrice}">
                        <input type="hidden" name="highPrice" value="${requestScope.highPrice}">
                        <c:if test="${conter == requestScope.page}">
                            <button id="selected" type='submit'>${conter}</button>
                        </c:if>
                        <c:if test="${conter != requestScope.page}">
                            <button type='submit'>${conter}</button>
                        </c:if>
                    </form>
                </li>
            </c:forEach>
            <li>
                <form method="get">
                    <input type="hidden" name="command_get" value="${requestScope.navigationCommand}"/>
                    <input type="hidden" name="page" value="${requestScope.nextPage}">
                    <input type="hidden" name="id" value="${requestScope.id}">
                    <input type="hidden" name="subName" value="${requestScope.subName}">
                    <input type="hidden" name="lowPrice" value="${requestScope.lowPrice}">
                    <input type="hidden" name="highPrice" value="${requestScope.highPrice}">
                    <button type='submit'>></button>
                </form>
            </li>
            <li>
                <form method="get">
                    <input type="hidden" name="command_get" value="${requestScope.navigationCommand}"/>
                    <input type="hidden" name="page" value="${requestScope.pageAmount}">
                    <input type="hidden" name="id" value="${requestScope.id}">
                    <input type="hidden" name="subName" value="${requestScope.subName}">
                    <input type="hidden" name="lowPrice" value="${requestScope.lowPrice}">
                    <input type="hidden" name="highPrice" value="${requestScope.highPrice}">
                    <button type='submit'>${requestScope.pageAmount}</button>
                </form>
            </li>
        </ul>
    </nav>
</div>
</c:if>
</html>
