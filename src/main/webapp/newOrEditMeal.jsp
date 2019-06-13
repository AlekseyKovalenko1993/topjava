<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <c:if test="${param.action == 'add'}">
        <h1 align="center">Add</h1>
        <c:url value="/meals?action=add" var="var"/>
    </c:if>
    <c:if test="${param.action == 'edit'}">
        <h1 align="center">Edit</h1>
        <c:url value="/meals?action=edit&id=${param.id}" var="var"/>
    </c:if>
</head>
<body>
</body>
</html>
