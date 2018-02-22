<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<template:page enabledMenuBar="${false}" >
    <div class="jumbotron">
        <h1 class="display-3">Track joint expenses easily</h1>
        <p class="lead">This simple app helps to track joint expenses for a group of people.</p>
        <hr class="my-4">
        <p>So you can easily share the costs and ensure that everyone spends the same amount of cache</p>
        <p class="lead">
        <form method="post" action="${pageContext.request.contextPath}/newGroup">
            <div class="d-flex justify-content-between">
                <input class ="btn btn-primary btn-lg" type="submit" value="View default Expenst List page"/>
            </div>
        </form>
        </p>
    </div>

</template:page>

