<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/22/2022
  Time: 12:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<body>
<c:if test="${message != null}">
    <div class="message">
        <span>${message}</span>
    </div>
</c:if>
<c:remove var="message" scope="session"/>
</body>
</html>
