<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 1/20/2022
  Time: 4:27 PM
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
<jsp:include page="pageParams.jsp"/>
<fmt:setBundle basename="locale/locale" var="lang"/>
<header>
    <nav class="barStyle">
        <ul class="barStyle">
            <li class="barStyle">
                <form method="get">
                    <input type="hidden" name="command_get" value="index"/>
                    <button class="barStyle" type="submit">
                        <fmt:message key="label.home" bundle="${lang}"/><br/>
                    </button>
                </form>
            </li>
            <li class="barStyle">
                <form method="post">
                    <select name="locale" onchange="this.form.submit()">
                        <option selected>--</option>
                        <option value="en">en</option>
                        <option value="ru">ru</option>
                    </select>
                    <input type="hidden" name="command" value="changeLocale"/>
                </form>
            </li>
            <li class="barStyle">
                <form method="get">
                    <input type="hidden" name="command_get" value="products"/>
                    <button class="barStyle" type="submit">
                        <fmt:message key="label.products" bundle="${lang}"/><br/>
                    </button>
                </form>
            </li>
            <c:if test="${sessionScope.role != null}">
                <li class="barStyle">
                    <form method="get">
                        <input type="hidden" name="command_get" value="basket"/>
                        <button class="barStyle" type="submit">
                            <fmt:message key="label.basket" bundle="${lang}"/><br/>
                        </button>
                    </form>
                </li>
                <li class="barStyle">
                    <form method="get">
                        <input type="hidden" name="command_get" value="profile"/>
                        <button class="barStyle" type="submit">
                            <fmt:message key="label.profile" bundle="${lang}"/><br/>
                        </button>
                    </form>
                </li>
                <li class="barStyle">
                    <form method="post">
                        <input type="hidden" name="command" value="signOut"/>
                        <button class="barStyle" type="submit">
                            <fmt:message key="label.logOut" bundle="${lang}"/><br/>
                        </button>
                    </form>
                </li>
            </c:if>
            <c:if test="${sessionScope.role.alias == 'admin'}">
                <li class="barStyle">
                    <form method="get">
                        <input type="hidden" name="command_get" value="administration"/>
                        <button class="barStyle" type="submit">
                            <fmt:message key="label.administration" bundle="${lang}"/><br/>
                        </button>
                    </form>
                </li>
            </c:if>
            <c:if test="${sessionScope.role == null}">
                <li class="barStyle">
                    <form method="get">
                        <input type="hidden" name="command_get" value="signUp"/>
                        <button class="barStyle" type="submit">
                            <fmt:message key="label.singUp" bundle="${lang}"/><br/>
                        </button>

                    </form>
                </li>
                <li class="barStyle">
                    <form method="get">
                        <input type="hidden" name="command_get" value="signIn"/>
                        <button class="barStyle" type="submit">
                            <fmt:message key="label.sinIn" bundle="${lang}"/><br/>
                        </button>
                    </form>
                </li>
            </c:if>
        </ul>
    </nav>
</header>
</html>
