<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<template:page expensesTabIsActive="${true}" enabledAddButton="${true}" >
    <div class="jumbotron">
        <h1 class="display-3">Expense details</h1>
    </div>

    <c:if test="${message ne null}">
        <div class="alert alert-danger">
            <strong>${message}</strong>
        </div>
    </c:if>

    <form method="post" >
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
                <td><input name="description" type="text" class="form-control"
                       autofocus="true" value="${expense.description}"/></td>
                <td><input name="amount" type="number" class="form-control"
                           autofocus="true" value="${expense.amount}"/></td>
                <td><input name="person" type="text" class="form-control"
                           autofocus="true" value="${expense.person}"/></td>
                <td><select class="form-control" name="currency" id="currency" autofocus="true">
                    <c:forEach var="currency" items="${currencies}">
                        <option ${currency == expense.currency ? 'selected' : ''}>${currency}</option>
                    </c:forEach>
                </select></td>
                <td><input name="date" type="date" class="form-control"
                           autofocus="true" value="${expense.date}"/></td>
                <td><input name="expenseGroup" type="text" class="form-control"
                           autofocus="true" value="${expense.expenseGroup}"/></td>
            </tr>

        </tbody>
    </table>
        <div class="d-flex justify-content-between">
            <input class ="btn btn-success" type="submit" value="Update" name="update" />
            <input class="btn btn-danger" type="submit" value="Delete" name="delete" />
        </div>
    </form>
</template:page>

