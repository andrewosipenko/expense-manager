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
    <c:set var="operationSuccessMessage" value="${requestScope[defaultMessageName]}"/>
    <c:if test="${not empty operationSuccessMessage}">
        <p style="color: limegreen"><c:out value="${operationSuccessMessage}"/></p>
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
                <c:set var="helper" value="${expense.helper}"/>
                <tr>
                    <td><c:out value="${helper.description}"/></td>
                    <td><c:out value="${helper.amount} ${helper.currency.symbol}"/></td>
                    <td><c:out value="${helper.person}"/></td>
                    <td>
                        <a class="btn btn-outline-primary" href="expenses/${expense.id}">View</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</template:page>
