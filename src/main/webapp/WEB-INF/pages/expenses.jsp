<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page expensesTabIsActive="${true}">
    <div class="jumbotron">
        <h1 class="display-3">Track joint expenses easily</h1>
        <p class="lead">This simple app helps to track joint expenses for a group of people.</p>
        <hr class="my-4">
        <p>So you can easily share the costs and ensure that everyone spends the same amount of cache</p>
        <p class="lead">
            <a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
        </p>
    </div>
    <c:if test="${updateSuccessMessage != null}">
        <p style="color: limegreen"><c:out value="${updateSuccessMessage}"/></p>
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
                    <td><c:out value="${expense.description}"/></td>
                    <td><c:out value="${expense.amount} ${expense.currency.getSymbol()}"/></td>
                    <td><c:out value="${expense.person}"/></td>
                    <td>
                        <a class="btn btn-outline-primary" href="expenses/${expense.id}">View</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</template:page>
