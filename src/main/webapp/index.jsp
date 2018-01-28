<%@ page import="com.es.jointexpensetracker.service.ExpenseServiceSingleton" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% response.sendRedirect("expense-group/" + ExpenseServiceSingleton.getInstance().getExpenseGroup() + "/expenses"); %>
