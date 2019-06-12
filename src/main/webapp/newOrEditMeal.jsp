<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var = "action" value="add"/>
    <c:when test="${param.action eq action}">
        <title>Add</title>
    </c:when>
    <c:otherwise>
        <title>Edit</title>
    </c:otherwise>
</head>
<body>
<c:when test="${empty id}">
    <h2>Add Meal</h2>
</c:when>
<c:otherwise>
    <h2>Edit Meal</h2>
</c:otherwise>
</body>
</html>
