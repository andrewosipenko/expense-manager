<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page expensesTabIsActive="${true}">
    <c:if test="${not empty flashMessage}">
        <p align="center">${flashMessage}</p>
    </c:if>
    <div class="jumbotron">
        <h1 class="display-3">Track joint expenses easily</h1>
        <p class="lead">On this page you can see list of your group expenses</p>
        <hr class="my-4">
        <p>Also adding new expenses, updating and watching the statistics are available</p>
        <c:if test="${empty expenses}">
            <p>To add new expense just click on the button bellow</p>
            <p class="lead">
                <a class="btn btn-success" href="<c:url value="/expense-group/${sessionScope.UUID}/expenses/add"/>">Add expense</a>
            </p>
        </c:if>
    </div>
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
                    <td><c:out value="${expense.description}"/></td>
                    <td><c:out value="${expense.amount}"/></td>
                    <td><c:out value="${expense.person}"/></td>
                    <td>
                        <a class="btn btn-outline-primary" href="<c:url value="/expense-group/${sessionScope.UUID}/expenses/${expense.id}"/>">View</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</template:page>
