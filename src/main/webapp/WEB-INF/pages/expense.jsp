<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page expensesTabIsActive="${true}">
    <p>${requestScope['javax.servlet.forward.path_info']}</p>
    <c:if test="${!isAddPage}">
        <c:set var="helper" value="${expense.helper}"/>
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
                    <td><c:out value="${helper.person}"/></td>
                    <td><c:out value="${helper.amount} ${helper.currency.currencyCode}"/></td>
                    <td><c:out value="${helper.description}"/></td>
                    <td><c:out value="${helper.date}"/></td>
                </tr>
            </tbody>
        </table>
    </c:if>
    <div class="jumbotron">
        <c:choose>
            <c:when test="${isAddPage}">
                <h3>Add new expense</h3>
            </c:when>
            <c:otherwise>
                <h3>Update expense</h3>
            </c:otherwise>
        </c:choose>
        <c:set var="formErrorMessage" value="${requestScope[defaultMessageName]}"/>
        <c:if test="${not empty formErrorMessage}">
            <p style="color: red">${formErrorMessage}</p>
        </c:if>
        <form method="post">
            <label>
                <input type="text" name="person" value="${helper.person}"/>
                Person
            </label>
            <span class="pull-right">
                <label>
                    <input type="text" name="amount" value="${helper.amount}" style=""/>
                    Amount
                </label>
                <select style="height:35px; width:70px; vertical-align: top" name="currency">
                    <c:forTokens var="code" items="USD, EUR, RUB, BYN, CNY" delims=", ">
                        <option value="${code}" ${(helper.currency.currencyCode eq code) ? 'selected' : ''}><c:out value="${code}"/></option>
                    </c:forTokens>
                </select>
            </span>
            <label style="width:100%">
                <input type="text" name="description" value="${helper.description}"/>
                Description
            </label><br/>
            <label>
                <input type="date" name="date" value="${helper.date}"/>
                Date
            </label>
            <input style="vertical-align: top" class="btn btn-success pull-right" name="${isAddPage? 'add' : 'update'}" type="submit" value="Submit"/>
        </form>
    </div>
    <c:if test="${!isAddPage}">
        <form method="post" style="margin: 0 auto; text-align: center">
            <input class="btn btn-success" name="delete" type="submit" value="Delete expense"/>
        </form>
    </c:if>
</template:page>
