<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:page>
    <div>
        <form action="" method="post">
            <div class="row">
                <div class="col">
                    <h2>Expense <span class="text-info">#${expense.id}</span></h2>
                </div>
                <div class="col-7">
                    <input hidden class="data-edit btn btn-success" type="submit" value="Confirm">
                    <input hidden type="button" class="data-edit btn btn-primary" data-toggle="modal" data-target="#exampleModal" value="Delete"/>
                    <input class="pull-right btn btn-primary" type="button" id="toggle-button" onclick="onToggleForm()" value="Edit"/>
                </div>
            </div>
            <div class="row">
                <div class="col-7">
                    <p class="data-view">${expense.description}</p>
                    <input hidden class="data-edit" type="text" name="description" value="${expense.description}"/>
                </div>
                <div class="col-2">
                    <p class="data-view">${expense.person}</p>
                    <input hidden class="data-edit" type="text" name="person" value="${expense.person}"/>
                </div>
                <div class="col-3">
                    <p class="data-view text-right">${expense.date}</p>
                    <input hidden class="data-edit" type="date" name="date" value="${expense.date}"/>
                </div>
            </div>
            <hr class="my-3">
            <div class="row justify-content-end">
                <div class="col-1">
                    Amount
                </div>
                <div class="col-2">
                    <p class="data-view text-right">${expense.amount}</p>
                    <input hidden class="data-edit text-right" type="number" min="0" name="amount" value="${expense.amount}"/>
                </div>
                <div class="col-1">
                    <p class="text-right">${expense.currency}</p>
                </div>
            </div>
        </form>
    </div>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Delete expense</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Are you sure you want to delete '${expense.description}' expense?
                </div>
                <div class="modal-footer">
                    <form action="${pageContext.request.contextPath}/expenses/delete" method="post">
                        <input hidden type="text" name="id" value="${expense.id}"/>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <input type="submit" class="btn btn-primary" value="Confirm"/>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        function onToggleForm() {
            var inputs = document.getElementsByClassName("data-edit");
            for (var i = 0; i < inputs.length; i++) {
                inputs[i].hidden = !inputs[i].hidden;
            }

            var texts = document.getElementsByClassName("data-view");
            for (var i = 0; i < texts.length; i++) {
                texts[i].hidden = !texts[i].hidden;
            }

            var button = document.getElementById("toggle-button");
            button.value = (button.value === "Edit") ? "Cancel" : "Edit";
        }
    </script>
</template:page>