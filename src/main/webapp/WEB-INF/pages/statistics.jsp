<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page statisticsTabIsActive="${true}">
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
            var backgroundColor = [];
            var hoverBackgroundColor = [];

            for (var i = 0;i < ${persons.size()}; i++) {
                var r = Math.floor(50+Math.random() * 150);
                var g = Math.floor(50+Math.random() * 150);
                var b = Math.floor(50+Math.random() * 150);
                backgroundColor.push("rgba(" + r + "," + g + "," + b + ",1)");
                hoverBackgroundColor.push("rgba(" + r + "," + g + "," + b + ",0.75)");
            }
            var ctxP = document.getElementById("pieChart").getContext('2d');
            var myPieChart = new Chart(ctxP, {
                type: 'pie',
                data: {
                    labels: [
                    <c:forEach var="person" items="${persons}">
                        "<c:out value = "${person}"/>",
                    </c:forEach>
                    ],
                    datasets: [
                        {
                            data: [
                                <c:forEach var="amount" items="${amounts}">
                                    ${amount},
                                </c:forEach>
                            ],
                            backgroundColor: backgroundColor,
                            hoverBackgroundColor: hoverBackgroundColor
                        }
                    ]
                },
                options: {
                    responsive: true
                }
            });
        });
    </script>
    <h1 class="page-header">Debts</h1>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Debtor</th>
                        <th>Amount</th>
                        <th>Creditor</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="debt" items="${debts}">
                            <tr>
                                <td><c:out value = "${debt.debtor}"/></td>
                                <td>${debt.amount} ${debt.currency}</td>
                                <td><c:out value = "${debt.creditor}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template:page>
