<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page enabledAddButton="${false}">
    <div class="jumbotron">
    <h1 class="display-3">Track joint expenses easily</h1>
    <p class="lead">Ooops...something went wrong</p>
        <c:if test="${errorMessage ne null}">
            <p class="lead"><c:out value="${errorMessage}"/></p>
        </c:if>
    <hr class="my-4">
    <p class="lead"><a href="${pageContext.request.contextPath}/expenses">Go to main page</a>
    </p>
    </div>
</template:page>