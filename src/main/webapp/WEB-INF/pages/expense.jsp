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
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Expense</th>
            <th>Amount</th>
            <th>Person</th>
            <th>Currency</th>
            <th>Date</th>
            <th>Expense Group</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>${expense.description}</td>
                <td>${expense.amount}</td>
                <td>${expense.person}</td>
                <td>${expense.currency}</td>
                <td>${expense.date}</td>
                <td>${expense.expenseGroup}</td>
            </tr>
        </tbody>
    </table>
    <a class="btn btn-outline-primary" href="expenses.jsp">Back</a>
</template:page>
