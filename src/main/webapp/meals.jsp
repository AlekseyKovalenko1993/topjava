<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h2 align="center">Еда</h2>
<table align="center">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>calories</th>
        <th>action</th>
    </tr>
    <jsp:useBean id="map" scope="request"  class="java.util.concurrent.ConcurrentHashMap"/>
    <c:forEach var="num" items="${map}">
        <c:set var="value" value="${num.value}"/>
        <jsp:useBean id="value"  type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color:<c:out value="${value.excess ? 'red' : 'greenyellow'}"/>">
            <td align="center"><%=TimeUtil.toString(value.getDateTime())%></td>
            <td align="center">${value.description}</td>
            <td align="center">${value.calories}</td>
            <td align="center">
                <a href="/topjava/meals?action=edit&id=${num.key}">Edit</a>
                <a href="/topjava/meals?action=delete&id=${num.key}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<h3 align="center"><a href="/topjava/meals?action=add">Add Meal</a></h3>
</body>
</html>
