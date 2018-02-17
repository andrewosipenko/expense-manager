<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page statisticsTabIsActive="${true}" enabledAddButton="${true}" >
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
            var backgroundColor = [];
            var hoverBackgroundColor = [];

            for (var i = 0;i < ${people.size()}; i++) {
                var r = Math.floor(256 * Math.random());
                var g = Math.floor(150 * Math.random() + 100);
                var b = Math.floor(200 * Math.random() + 50);
                backgroundColor.push("rgba(" + r + "," + g + "," + b + ",1)");
                hoverBackgroundColor.push("rgba(" + r + "," + g + "," + b + ",0.5)");
            }
            var myPieChart = new Chart(ctxP, {
                type: 'pie',
                data: {
                    labels: [
                        <c:forEach var="person" items="${people}" >
                        "<c:out value = "${person.name}"/>",
                        </c:forEach>
                            ],
                    datasets: [
                        {
                            data: [
                                <c:forEach var="person" items="${people}" >
                                "<c:out value = "${person.amount}"/>",
                                </c:forEach>
                            ],
                            backgroundColor: backgroundColor ,
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
                        <th>Person</th>
                        <th>Amount</th>
                        <th>Person</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="debtor" items="${debtors}">
                        <tr>
                            <td><c:out value = "${debtor.name}"/></td>
                            <td><c:out value = "owes ${debtor.amount} $ to"/></td>
                            <td><c:out value = "${debtor.person}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template:page>
