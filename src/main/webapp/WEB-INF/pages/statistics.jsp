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
            //pie
            var ctxP = document.getElementById("pieChart").getContext('2d');
            var myPieChart = new Chart(ctxP, {
                type: 'pie',
                data: {
                    labels: ${chartNames},
                    datasets: [
                        {
                            data: ${chartData},
                            backgroundColor: ["#F7464A", "#46BFBD", "#FDB45C", "#949FB1", "#4D5360"],
                            hoverBackgroundColor: ["#FF5A5E", "#5AD3D1", "#FFC870", "#A8B3C5", "#616774"]
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
                        <th>Person</th>
                        <th>Amount</th>
                        <th>Person</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="debt" items="${debts}">
                        <tr>
                            <td>${debt.debtor}</td>
                            <td>owes ${debt.amount}$ to</td>
                            <td>${debt.creditor}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:if test="${empty debts}">
                    <div class="alert alert-success" role="alert">
                        There is no debts
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</template:page>
