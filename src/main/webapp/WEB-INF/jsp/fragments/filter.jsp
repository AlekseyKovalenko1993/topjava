<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form method="get" action="http://localhost:8080/topjava/meals">
    <input type="hidden" name="filter" value="filter">
    <dl>
        <dt><spring:message code="meal.startDate"/>:</dt>
        <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
    </dl>
    <dl>
        <dt><spring:message code="meal.endDate"/>:</dt>
        <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
    </dl>
    <dl>
        <dt><spring:message code="meal.startTime"/>:</dt>
        <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
    </dl>
    <dl>
        <dt><spring:message code="meal.endTime"/>:</dt>
        <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
    </dl>
    <button type="submit"><spring:message code="meal.filter"/></button>
</form>
