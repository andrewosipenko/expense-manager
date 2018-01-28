<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <div class="mt-3">
        <h4>Oops( Error occurred</h4>
        <h1>${pageContext.request.getAttribute("javax.servlet.error.status_code")}</h1>
        <p class="mt-1">${pageContext.request.getAttribute("javax.servlet.error.message")}</p>
    </div>
</template:page>