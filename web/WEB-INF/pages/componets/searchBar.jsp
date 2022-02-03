<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/25/2022
  Time: 3:29 PM
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
<fmt:setBundle basename="locale/locale" var="searchPageLang"/>
<div class="search" id="search">
    <ul>
        <li>
            <form id='searchForm' method="get">
                <input type="hidden" name="command_get" value="products"/>
                <input name='subName' type='text'>
                <button type='submit'><fmt:message key="label.search" bundle="${searchPageLang}"/></button>
            </form>
            <form id='filterForm' method="post">
                <button type='button'><fmt:message key="label.filter" bundle="${searchPageLang}"/></button>
            </form>
        </li>
    </ul>
</div>
</html>
