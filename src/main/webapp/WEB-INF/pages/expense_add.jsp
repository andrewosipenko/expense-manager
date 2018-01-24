<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <div>
        <form action="" method="post">
            <div class="row mt-3">
                <div class="col">
                    <h2>New expense</h2>
                </div>
                <div class="col-7 float-left">
                    <button class="data-edit btn btn-success float-right" type="submit">
                        <i class="fa fa-check fa-fw"></i>
                    </button>
                </div>
            </div>
            <div class="row">
                <div class="col-7">
                    <input type="text" name="description" placeholder="Decription" required/>
                </div>
                <div class="col-2">
                    <input type="text" name="person" placeholder="Name" required/>
                </div>
                <div class="col-3">
                    <input type="date" name="date" required/>
                </div>
            </div>
            <hr class="my-3">
            <div class="row justify-content-end">
                <div class="col-2">
                    <p class="mt-1">Amount</p>
                </div>
                <div class="col-2">
                    <input class="text-right" type="number" min="0" name="amount" required/>
                </div>
                <div class="col-2">
                    <select class="custom-select" name="currency">
                        <option value="USD">USD</option>
                        <option value="USD">EUR</option>
                        <option value="USD">BYN</option>
                    </select>
                </div>
            </div>
        </form>
    </div>
</template:page>