package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.exception.DataNotFoundException;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.model.ExpenseKey;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.ExpenseServiceSingleton;
import com.es.jointexpensetracker.service.NotificationService;
import com.es.jointexpensetracker.service.NotificationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseServlet extends HttpServlet {

    private final static String EXPENSE_ADD_JSP_PATH = "/WEB-INF/pages/expense_add.jsp";
    private final static String EXPENSE_JSP_PATH = "/WEB-INF/pages/expense.jsp";
    private final static String SUCCESS_MESSAGE_TEMPLATE = "Expense '%s' was %s successfully";

    private NotificationService notificationService;

    @Override
    public void init() throws ServletException {
        this.notificationService = new NotificationServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ExpenseService service = ExpenseServiceSingleton.getInstance();
        if (request.getPathInfo().equals("/add")) {
            request.getRequestDispatcher(EXPENSE_ADD_JSP_PATH).forward(request, response);
        } else {
            String id = request.getPathInfo().substring(1);
            Expense expense = loadExpense(service, id, getExpenseGroup(request));
            request.setAttribute(
                    "expense",
                    expense
            );
            request.getRequestDispatcher(EXPENSE_JSP_PATH).forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ExpenseService service = ExpenseServiceSingleton.getInstance();
        switch (request.getPathInfo()) {
            case "/delete":
                delete(request, service);
                break;
            case "/add":
                create(request, service);
                break;
            default:
                update(request, service);
                break;
        }
        String expenseGroupPath = (String) request.getAttribute("expenseGroupPath");
        ExpenseServiceSingleton.getInstance().save();
        response.sendRedirect(expenseGroupPath + "/expenses");
    }

    private void delete(HttpServletRequest request, ExpenseService service) throws DataNotFoundException{
        String id = request.getParameter("id");
        Expense expense = loadExpense(service, id, getExpenseGroup(request));
        service.removeExpense(expense);
        notificationService.attachMessage(
                request,
                String.format(SUCCESS_MESSAGE_TEMPLATE, expense.getDescription(), "deleted")
        );
    }

    private void create(HttpServletRequest request, ExpenseService service){
        String expenseGroup = request.getParameter("expenseGroup");
        Expense expense = service.addExpense(expenseGroup);
        updateExpense(request, expense);
        notificationService.attachMessage(
                request,
                String.format(SUCCESS_MESSAGE_TEMPLATE, expense.getDescription(), "created")
        );
    }

    private void update(HttpServletRequest request, ExpenseService service){
        String id = request.getPathInfo().substring(1);
        Expense expense = loadExpense(service, id, getExpenseGroup(request));
        updateExpense(request, expense);
        notificationService.attachMessage(
                request,
                String.format(SUCCESS_MESSAGE_TEMPLATE, expense.getDescription(), "updated")
        );
    }

    private Expense loadExpense(ExpenseService service, String id, String expenseGroup) throws DataNotFoundException{
        ExpenseKey key = new ExpenseKey(
                Long.parseLong(id),
                expenseGroup
        );
        return service.loadExpenseByKey(key);
    }

    private String getExpenseGroup(HttpServletRequest request){
        return request.getParameter("expenseGroup");
    }

    private void updateExpense(HttpServletRequest request, Expense expense){
        expense.setAmount(
                new BigDecimal(request.getParameter("amount"))
        );
        expense.setDescription(
                request.getParameter("description")
        );
        expense.setPerson(
                request.getParameter("person")
        );
        expense.setDate(
                LocalDate.parse(request.getParameter("date"))
        );
    }
}
