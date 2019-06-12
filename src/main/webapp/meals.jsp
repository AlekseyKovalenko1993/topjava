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
    <c:forEach var="num" items="${map}">
            <tr style="color:<c:out value="${num.value.excess ? 'red' : 'greenyellow'}"/>">
                <td align="center">${num.value.dateTime}</td>
                <td align="center">${num.value.description}</td>
                <td align="center">${num.value.calories}</td>
                <td align="center">
                    <a href="/topjava/meals?action=edit&id=${num.key}">Edit</a>
                    <a href="/topjava/meals?action=delete&id=${num.key}">Delete</a>
                </td>
            </tr>
    </c:forEach>
</table>
<h2 align="center"><a href="/topjava/meals?action=add">Add Meal</a></h2>
</body>
</html>
