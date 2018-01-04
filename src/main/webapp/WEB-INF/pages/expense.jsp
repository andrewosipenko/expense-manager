<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:page>
    <div>
        <h2>Expense <span class="text-info">#${expense.id}</span></h2>
        <div class="row">
            <div class="col-8">
                <p>${expense.description}</p>
            </div>
            <div class="col-2">
                <p>${expense.person}</p>
            </div>
            <div class="col-2">
                <p class="text-right">${expense.date}</p>
            </div>
        </div>
        <hr class="my-3">
        <div class="row">
            <div class="col align-self-right">
                <p class="text-right">Amount: ${expense.amount} ${expense.currency}</p>
            </div>
        </div>
    </div>
</template:page>