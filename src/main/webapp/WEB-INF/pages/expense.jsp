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
                <td><c:out value="${expense.person}"/></td>
                <td><c:out value="${expense.amount} ${expense.currency.getCurrencyCode()}"/></td>
                <td><c:out value="${expense.description}"/></td>
                <td><c:out value="${expense.date}"/></td>
            </tr>
        </tbody>
    </table>
    <div class="jumbotron">
        <h3>Update expense</h3>
        <form method="post">
            <label>
                <input type="text" name="person" value="${expense.person}"/>
                Person
            </label>
            <span class="pull-right">
                <label>
                    <input type="text" name="amount" value="${expense.amount}" style=""/>
                    Amount
                </label>
                <select style="height:35px; width:70px; vertical-align: top" name="currency">
                    <c:forTokens var="code" items="USD, EUR, RUB, BYN, CNY" delims=", ">
                        <option value="${code}" ${expense.currency.getCurrencyCode().equals(code) ? 'selected' : ''}><c:out value="${code}"/></option>
                    </c:forTokens>
                </select>
            </span>
            <label style="width:100%">
                <input type="text" name="description" value="${expense.description}"/>
                Description
            </label><br/>
            <label>
                <input type="date" name="date" value="${expense.date}"/>
                Date
            </label>
            <input style="vertical-align: top" class="btn btn-success pull-right" name="update" type="submit" value="Submit"/>
        </form>
        <c:if test="${updateErrorMessage != null}">
            <p style="color: red">${updateErrorMessage}</p>
        </c:if>
    </div>
    <form method="post" style="margin: 0 auto; text-align: center">
        <input class="btn btn-success" name="delete" type="submit" value="Delete expense"/>
    </form>
</template:page>
