<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <c:if test="${message ne null}">
        <div class="alert alert-warning">
            <strong>${message}</strong>
        </div>
    </c:if>
    <c:if test="${expense ne null}">
        <h1 class="page-header">${expense.description}</h1>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Property</th>
                <th>Value</th>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <strong>ID</strong>
                    </td>
                    <td>${expense.id}</td>
                </tr>
                <tr>
                    <td>
                        <strong>Description</strong>
                    </td>
                    <td>${expense.description}</td>
                </tr>
                <tr>
                    <td>
                        <strong>Amount</strong>
                    </td>
                    <td>${expense.amount}</td>
                </tr>
                <tr>
                    <td>
                        <strong>Currency</strong>
                    </td>
                    <td>${expense.currency}</td>
                </tr>
                <tr>
                    <td>
                        <strong>Person</strong>
                    </td>
                    <td>${expense.person}</td>
                </tr>
                <tr>
                    <td>
                        <strong>Date</strong>
                    </td>
                    <td>${expense.date}</td>
                </tr>
                <tr>
                    <td>
                        <strong>Expense Group</strong>
                    </td>
                    <td>${expense.expenseGroup}</td>
                </tr>
            </tbody>
        </table>
    </c:if>
</template:page>
