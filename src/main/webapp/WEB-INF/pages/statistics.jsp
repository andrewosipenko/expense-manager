<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page statisticsTabIsActive="${true}">
    <h1 class="page-header">Total expenses per person</h1>
    <c:choose>
        <c:when test="${not empty expenses}">
            <div class="container">
                <div class="row">
                    <div class="col-12 col-md-8">
                        <canvas id="pieChart"></canvas>
                    </div>
                </div>
            </div>

            <script>
                function makeRandomColor() {

                    return '#' + Math.floor(Math.random() * 16777215).toString(16)
                }

                $(document).ready(function () {
                    var names = [<c:forEach items="${expenses}" var="entry" varStatus="counter">
                        "${entry.key}" <c:if test="${counter.count<expenses.size()}">, </c:if>
                        </c:forEach>];
                    var amounts = [<c:forEach items="${expenses}" var="entry" varStatus="counter">
                        "${entry.value}" <c:if test="${counter.count<expenses.size()}">, </c:if>
                        </c:forEach>];
                    var backColors = [<c:forEach items="${expenses}" var="entry" varStatus="counter">
                        makeRandomColor() <c:if test="${counter.count<expenses.size()}">, </c:if>
                        </c:forEach>];
                    var hoverBackColors = backColors;
                    //pie
                    var ctxP = document.getElementById("pieChart").getContext('2d');
                    var myPieChart = new Chart(ctxP, {
                        type: 'pie',
                        data: {
                            labels: names,
                            datasets: [
                                {
                                    data: amounts,
                                    backgroundColor: backColors,
                                    hoverBackgroundColor: hoverBackColors
                                }
                            ]
                        },
                        options: {
                            responsive: true
                        }
                    });
                });
            </script>
        </c:when>
        <c:otherwise>
            <h2>There are no expenses.</h2>
            <p>To see any statistics please add expenses on the <a href="expenses" class="axis-label">main page</a></p>
        </c:otherwise>
    </c:choose>
    <h1 class="page-header">Debts</h1>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Person</th>
                        <th>Amount</th>
                        <th>Person</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${not empty debts}">
                            <c:forEach items="${debts}" var="entry">
                                <tr>
                                    <td>${entry.debtor}</td>
                                    <td>owes ${entry.amount} $ to</td>
                                    <td>${entry.receiver}</td>
                                </tr>
                            </c:forEach>
                        </c:when>

                        <c:otherwise>
                            <tr>
                                <td>No one owes money to anyone</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template:page>
