<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page >
    <div class="jumbotron">
         <h1 class="display-3">Ooops, something went wrong.</h1>
         <p class="lead">${exception}</p>

    </div>
</template:page>
