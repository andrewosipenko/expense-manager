<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page >
    <c:if test="${not empty flashMessage}">
        <p align="center">${flashMessage}</p>
    </c:if>
    <div class="jumbotron">
        <c:choose>
            <c:when  test="${expense ne null}">
                <p class="lead">Here you can see expense details</p>
            </c:when>
            <c:otherwise>
                <p class="lead">Here you can add new expense</p>
            </c:otherwise>
        </c:choose>
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
                    <td width="200%"><textarea  name="description"><c:out value="${expense.description}"/></textarea></td>
                    <td><input name="amount" type="number" value="<c:out value="${expense.amount}"/>"></td>
                    <td><input name="person" type="text" value="<c:out value="${expense.person}"/>"></td>
                    <td><input name="currency" type="text" value="<c:out value="${expense.currency}"/>"></td>
                    <td><input name="date" type="date" value="<c:out value="${expense.date}"/>" ></td>
                    <c:choose>

                        <c:when test="${expense ne null}">
                            <td><input class="btn btn-outline-primary" type="submit" value="Update"></td>
                            <td>
                                <button formaction="<c:url value="/expense-group/${sessionScope.UUID}/deleteExpense/${expense.id}"/>" class="btn btn-outline-primary" type="submit">Delete</button>
                            </td>
                        </c:when>

                        <c:otherwise>
                            <td>
                                <button  class="btn btn-outline-primary" type="submit">Add</button>
                            </td>
                        </c:otherwise>

                    </c:choose>
                </form>
            </tr>
        </tbody>
    </table>
</template:page>
