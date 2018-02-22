<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<template:page expensesTabIsActive="${true}" enabledAddButton="${true}" enabledMenuBar="${true}" >
    <div class="jumbotron">
        <h1 class="display-3">Expense details</h1>
    </div>

    <c:if test="${message ne null}">
        <div class="alert alert-danger">
            <strong><c:out value = "${message}"/></strong>
        </div>
    </c:if>

    <form method="post">
        <div class="lead">
            <table class="table table-hover">
                <tbody>
                <tr>
                    <td>
                        <label for="description">Description</label>
                        <input name="description" type="text" class="form-control" id="description"
                               autofocus="true" value="${expense.description  ne null ? expense.description : ''}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                    <label for="amount">Amount</label>
                    <input name="amount" type="number" class="form-control" id="amount"
                               autofocus="true" value="${expense.amount  ne null ? expense.amount : ''}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                    <label for="person">Person</label>
                    <input name="person" type="text" class="form-control" id="person"
                               autofocus="true" value="${expense.person ne null ? expense.person : ''} "/>
                    </td>
                </tr>
                <tr>
                    <td>
                    <label for="currency">Currency</label>
                        <select class="form-control" name="currency" id="currency" autofocus="true" id="currency">
                        <c:forEach var="currency" items="${currencies}">
                            <option ${currency == expense.currency ? 'selected' : ''}>${currency}</option>
                        </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                    <label for="date">Date</label>
                    <input name="date" type="date" class="form-control" id="date"
                               autofocus="true" value="${expense.date}"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <c:choose>
            <c:when test="${expense ne null}">
                <div class="d-flex justify-content-between">
                    <input class ="btn btn-success" type="submit" value="Update" name="update" />
                    <input class="btn btn-danger" type="submit" value="Delete" name="delete" />
                </div>
            </c:when>
            <c:otherwise>
                <input class ="btn btn-success" type="submit" value="Add" name="add" />
            </c:otherwise>
        </c:choose>

    </form>
</template:page>

