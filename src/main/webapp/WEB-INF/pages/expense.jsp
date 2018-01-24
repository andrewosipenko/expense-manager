<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page expensesTabIsActive="${false} " statisticsTabIsActive="${false}">
    <div class="jumbotron">
        <h1 class="display-3">Expense details</h1>
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
</template:page>
