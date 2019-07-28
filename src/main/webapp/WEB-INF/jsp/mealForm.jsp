<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>
        <c:choose>
            <c:when test="${empty meal.id}">
                <spring:message code="meal.addMeal"/>
            </c:when>
            <c:otherwise>
                <spring:message code="meal.editMeal"/>
            </c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <c:url value="http://localhost:8080/topjava/" var="home"/>
    <h3><a href="${home}"><spring:message code="app.home"/></a></h3>
    <hr>
    <h2>
        <c:choose>
            <c:when test="${empty meal.id}">
                <spring:message code="meal.addMeal"/>
            </c:when>
            <c:otherwise>
                <spring:message code="meal.editMeal"/>
            </c:otherwise>
        </c:choose>
    </h2>
    <form method="post" action="save">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.dateTime"/>:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/>:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.сalories"/>:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="meal.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="app.cancel"/></button>
    </form>
</section>
</body>
</html>
