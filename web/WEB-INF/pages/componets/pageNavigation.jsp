<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/25/2022
  Time: 2:48 PM
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
<c:if test="${pageAmount>0}">
<div class='barStyle'>
    <nav class='barStyle'>
        <ul class='barStyle'>
            <li>
                <form method="get">
                    <input type="hidden" name="command_get" value="${navigationCommand}">
                    <input type="hidden" name="page" value="1">
                    <input type="hidden" name="id" value="${id}">
                    <input type="hidden" name="subName" value="${subName}">
                    <input type="hidden" name="lowPrice" value="${lowPrice}">
                    <input type="hidden" name="highPrice" value="${highPrice}">
                    <button type='submit'>1</button>
                </form>
            </li>
            <li>
                <form method="get" action="products">
                    <input type="hidden" name="command_get" value="${navigationCommand}"/>
                    <input type="hidden" name="page" value="${previousPage}">
                    <input type="hidden" name="id" value="${id}">
                    <input type="hidden" name="subName" value="${subName}">
                    <input type="hidden" name="lowPrice" value="${lowPrice}">
                    <input type="hidden" name="highPrice" value="${highPrice}">
                    <button type='submit'><</button>
                </form>
            </li>
            <c:forEach var="conter" begin="${lowPage}" end="${highPage}">
                <li>
                    <form method="get">
                        <input type="hidden" name="command_get" value="${navigationCommand}"/>
                        <input type="hidden" name="page" value="${conter}">
                        <input type="hidden" name="id" value="${id}">
                        <input type="hidden" name="subName" value="${subName}">
                        <input type="hidden" name="lowPrice" value="${lowPrice}">
                        <input type="hidden" name="highPrice" value="${highPrice}">
                        <c:if test="${conter == page}">
                            <button id="selected" type='submit'>${conter}</button>
                        </c:if>
                        <c:if test="${conter != page}">
                            <button type='submit'>${conter}</button>
                        </c:if>
                    </form>
                </li>
            </c:forEach>
            <li>
                <form method="get">
                    <input type="hidden" name="command_get" value="${navigationCommand}"/>
                    <input type="hidden" name="page" value="${nextPage}">
                    <input type="hidden" name="id" value="${id}">
                    <input type="hidden" name="subName" value="${subName}">
                    <input type="hidden" name="lowPrice" value="${lowPrice}">
                    <input type="hidden" name="highPrice" value="${highPrice}">
                    <button type='submit'>></button>
                </form>
            </li>
            <li>
                <form method="get">
                    <input type="hidden" name="command_get" value="${navigationCommand}"/>
                    <input type="hidden" name="page" value="${pageAmount}">
                    <input type="hidden" name="id" value="${id}">
                    <input type="hidden" name="subName" value="${subName}">
                    <input type="hidden" name="lowPrice" value="${lowPrice}">
                    <input type="hidden" name="highPrice" value="${highPrice}">
                    <button type='submit'>${pageAmount}</button>
                </form>
            </li>
        </ul>
    </nav>
</div>
</c:if>
</html>
