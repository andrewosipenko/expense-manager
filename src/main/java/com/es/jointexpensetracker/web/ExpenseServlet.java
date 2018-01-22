package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.exception.DataNotFoundException;
import com.es.jointexpensetracker.model.Expense;
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
import java.util.Currency;

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
        try {
            if(request.getPathInfo().equals("/add")){
                request.getRequestDispatcher(EXPENSE_ADD_JSP_PATH).forward(request, response);
            } else {
                Expense expense = loadExpense(service, request.getPathInfo().substring(1));
                request.setAttribute(
                        "expense",
                        expense
                );
                request.getRequestDispatcher(EXPENSE_JSP_PATH).forward(request, response);
            }
        } catch (DataNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ExpenseService service = ExpenseServiceSingleton.getInstance();
        try {
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
            ExpenseServiceSingleton.getInstance().save();
            response.sendRedirect(getServletContext().getContextPath() + "/expenses");
        } catch (DataNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void delete(HttpServletRequest request, ExpenseService service) throws DataNotFoundException{
        Expense expense = loadExpense(service, request.getParameter("id"));
        service.removeExpense(expense);
        notificationService.attachMessage(
                request,
                String.format(SUCCESS_MESSAGE_TEMPLATE, expense.getDescription(), "deleted")
        );
    }

    private void create(HttpServletRequest request, ExpenseService service){
        Expense expense = service.createExpense(
                request.getParameter("description"),
                new BigDecimal(request.getParameter("amount")),
                Currency.getInstance(request.getParameter("currency")),
                request.getParameter("person"),
                LocalDate.parse(request.getParameter("date"))
        );

        notificationService.attachMessage(
                request,
                String.format(SUCCESS_MESSAGE_TEMPLATE, expense.getDescription(), "created")
        );
    }

    private void update(HttpServletRequest request, ExpenseService service) throws DataNotFoundException{
        Expense expense = loadExpense(service, request.getPathInfo().substring(1));
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
        notificationService.attachMessage(
                request,
                String.format(SUCCESS_MESSAGE_TEMPLATE, expense.getDescription(), "updated")
        );
    }

    private Expense loadExpense(ExpenseService service, String id)
            throws DataNotFoundException{
        long expenseId = Long.parseLong(id);
        return service.loadExpenseById(expenseId);
    }
}
