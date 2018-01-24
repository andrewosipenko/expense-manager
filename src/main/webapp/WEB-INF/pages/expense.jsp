<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page expensesTabIsActive="${true}">
    <table class="table table-hover" style="margin-top: 30px">
        <thead>
            <tr>
                <th>Person</th>
                <th>Amount</th>
                <th>Description</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${expense.person}</td>
                <td>${expense.amount} ${expense.currency.getCurrencyCode()}</td>
                <td>${expense.description}</td>
                <td>${expense.date}</td>
            </tr>
        </tbody>
    </table>
</template:page>
