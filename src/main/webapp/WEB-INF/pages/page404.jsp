<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page >
    <div class="jumbotron">
        <h1 class="display-3">There is no such page.</h1>
        <c:if test="${not empty flashMessage}">
            <hr class="my-4">
            <p>${flashMessage}</p>
            <p>Just go to <a class="hyperlink" href="<c:url value="/"/>">home page</a> to create a new group</p>
        </c:if>
    </div>
</template:page>