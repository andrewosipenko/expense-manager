package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

public class ExpenseListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute(
            "expenses",
            Arrays.asList(
                new Expense(1L, "Train tickets from Minsk to Warsaw", new BigDecimal(200), "Andrei"),
                new Expense(2L, "Air tickets from Warsaw to Gran Carania and back", new BigDecimal(2000), "Ivan"),
                new Expense(3L, "Restaurant", new BigDecimal(90), "Andrei"),
                new Expense(4L, "Rent a car", new BigDecimal(700), "Sergei"),
                new Expense(5L, "Rent a car", new BigDecimal(500), "Igor"),
                new Expense(6L, "Rent a house", new BigDecimal(2000), "Igor"),
                new Expense(7L, "Restaurant", new BigDecimal(60), "Andrei"),
                new Expense(8L, "Gazoline", new BigDecimal(50), "Sergei"),
                new Expense(9L, "Gazoline", new BigDecimal(50), "Igor"),
                new Expense(10L, "Surfing", new BigDecimal(30), "Sergei"),
                new Expense(11L, "New year party shopping", new BigDecimal(30), "Igor"),
                new Expense(12L, "Surfing", new BigDecimal(30), "Sergei"),
                new Expense(13L, "Air wing", new BigDecimal(50), "Sergei"),
                new Expense(14L, "Bus tickets from Warsaw to Minsk", new BigDecimal(200), "Andrei")
            )
        );
        request.getRequestDispatcher("WEB-INF/pages/expenses.jsp").forward(request, response);
    }
}
