<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page expensesTabIsActive="${true}">
    <template:jumbotron>
        <p class="lead">
            <a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
        </p>
    </template:jumbotron>

    <c:if test="${sessionScope.containsKey(\"message\")}">
        <template:alert-success message="${sessionScope.get(\"message\")}"/>
    </c:if>

    <table class="table table-hover">
        <thead>
            <tr>
                <th>Expense</th>
                <th>Amount</th>
                <th>Person</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="expense" items="${expenses}">
                <tr>
                    <td>${expense.description}</td>
                    <td>${expense.amount}</td>
                    <td>${expense.person}</td>
                    <td>
                        <a class="btn btn-outline-primary" href="expenses/${expense.id}">View</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</template:page>
