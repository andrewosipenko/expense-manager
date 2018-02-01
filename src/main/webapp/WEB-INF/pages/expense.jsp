<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page >
    <c:if test="${not empty flashMessage}">
        <p align="center"  >${flashMessage}</p>
    </c:if>
    <div class="jumbotron">
        <p class="lead">Here you can see expense details</p>
    </div>
    <table class="table table-hover">
        <thead>
            <tr>
                <th>Expense</th>
                <th>Amount</th>
                <th>Person</th>
                <th>Currency</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <form method="POST">
                    <td width="200%"><textarea  name="description">${expense.description}</textarea></td>
                    <td><input name="amount" type="number" value="${expense.amount}"></td>
                    <td><input name="person" type="text" value="${expense.person}"></td>
                    <td><input name="currency" type="text" value="${expense.currency}"></td>
                    <td><input name="date" type="date" value="${expense.date}" ></td>
                    <td><input class="btn btn-outline-primary" type="submit" value="Update"></td>
                    <td>
                        <button formaction="/deleteExpense/${expense.id}" class="btn btn-outline-primary" type="submit">Delete</button>
                    </td>
                </form>
            </tr>
        </tbody>
    </table>
</template:page>
