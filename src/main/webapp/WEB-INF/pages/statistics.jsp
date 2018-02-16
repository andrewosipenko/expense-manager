<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page statisticsTabIsActive="${true}">
    <br/>
    <h1 class="page-header">Total expenses per person</h1>
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-8">
                <canvas id="pieChart"></canvas>
            </div>
        </div>
    </div>
    <script>
        $( document ).ready(function() {
            //pie
            var ctxP = document.getElementById("pieChart").getContext('2d');
            var myPieChart = new Chart(ctxP, {
                type: 'pie',
                data: {
                    labels: [
                        <c:forEach var="dataItem" items="${chartData}">
                            "<c:out value="${dataItem.name}"/>",
                        </c:forEach>
                    ],
                    datasets: [
                        {
                            data: [
                                <c:forEach var="dataItem" items="${chartData}">
                                    ${dataItem.totalAmount},
                                </c:forEach>
                            ],
                            backgroundColor: [
                                <c:forEach var="dataItem" items="${chartData}">
                                    "${dataItem.hexColor}",
                                </c:forEach>
                            ],
                            hoverBackgroundColor: [
                                <c:forEach var="dataItem" items="${chartData}">
                                    "${dataItem.hexHighlightedColor}",
                                </c:forEach>
                            ]
                        }
                    ]
                },
                options: {
                    responsive: true
                }
            });
        });
    </script>
    <br/>
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
                        <c:forEach var="debt" items="${debts}">
                            <tr>
                                <td><c:out value="${debt.debtorName}"/></td>
                                <td><c:out value="owes ${debt.amount}${debtsCurrency.symbol} to"/></td>
                                <td><c:out value="${debt.creditorName}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template:page>
