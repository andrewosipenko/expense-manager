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
                    labels: ["Andre", "Igor", "Sergei", "Ivan", "Dark Grey"],
                    datasets: [
                        {
                            data: [300, 50, 100, 40, 120],
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
                        <tr>
                            <td>Andrei</td>
                            <td>owes 200$ to</td>
                            <td>Sergei</td>
                        </tr>
                        <tr>
                            <td>Andrei</td>
                            <td>owes 100$ to</td>
                            <td>Ivan</td>
                        </tr>
                        <tr>
                            <td>Ivan</td>
                            <td>owes 50$ to</td>
                            <td>Sergei</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template:page>
