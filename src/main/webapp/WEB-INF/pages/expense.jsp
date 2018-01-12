<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <c:if test="${message ne null}">
        <div class="alert alert-warning">
            <strong>${message}</strong>
        </div>
    </c:if>
    <c:if test="${expense ne null || showAddForm}">
        <div class="jumbotron">
            <h1 class="page-header"><c:out value = "${expense.description}"/></h1>
            <form method="post" class="form-horizontal">
                <div class="form-group">
                    <label class="control-label col-sm-2" for="description">Description:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="description" value="<c:out value = "${expense.description}"/>" name="description">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="amount">Amount:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="amount" value="${expense.amount}" name="amount">
                        <select class="form-control" name="currency" id="currency">
                            <option value="USD" ${expense.currency == 'USD' ? 'selected' : ''}>USD</option>
                            <option value="EUR" ${expense.currency == 'EUR' ? 'selected' : ''}>EUR</option>
                            <option value="RUB" ${expense.currency == 'RUB' ? 'selected' : ''}>RUB</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="person">Person:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="person" value="<c:out value = "${expense.person}"/>" name="person">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="date">Date:</label>
                    <div class="col-sm-10">
                        <input type="date" class="form-control" id="date"
                        <c:if test="${expense.date ne null}">
                          value="${expense.date}"
                        </c:if> name="date">
                    </div>
                </div>
                <div class="form-group">
                    <c:choose>
                        <c:when test="${showAddForm}">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default" name="add">Add</button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default" name="update">Update</button>
                                <button type="submit" class="btn btn-default" name="delete">Delete</button>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
            </form>
        </div>
    </c:if>
</template:page>
