package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.util.FormParser;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;

public class ExpenseFormParser extends FormParser {
    private String person;
    private String description;
    private BigDecimal amount;
    private Currency currency;
    private LocalDate date;

    ExpenseFormParser(HttpServletRequest request) {
        setDelimiter("<br/>- ");

        // Validate person
        String person = request.getParameter("person");
        if (person == null || person.equals(""))
            setErrorState("Person must be correctly specified non-empty string");

        // Validate description
        String description = request.getParameter("description");
        if (description == null)
            setErrorState("Description parameter is not specified");

        // Validate date
        LocalDate date = null;
        try {
            date = LocalDate.parse(request.getParameter("date"));
        } catch (DateTimeParseException | NullPointerException e) {
            setErrorState("Failed to parse date parameter");
        }

        // Validate currency
        Currency currency = null;
        try {
            currency = Currency.getInstance(request.getParameter("currency"));
        } catch (IllegalArgumentException | NullPointerException e) {
            setErrorState("Failed to parse currency parameter");
        }

        // Validate amount
        BigDecimal amount = null;
        try {
            amount = new BigDecimal(request.getParameter("amount"));
            if (amount.compareTo(BigDecimal.valueOf(0)) < 0)
                setErrorState("Amount must be correct decimal number greater than 0");
        } catch (NumberFormatException | NullPointerException e) {
            setErrorState("Amount must be correct decimal number greater than 0");
        }

        if (isValid()) {
            this.person = person;
            this.description = description;
            this.amount = amount;
            this.currency = currency;
            this.date = date;
        }
    }

    String getPerson() {
        return person;
    }

    String getDescription() {
        return description;
    }

    BigDecimal getAmount() {
        return amount;
    }

    Currency getCurrency() {
        return currency;
    }

    LocalDate getDate() {
        return date;
    }

    @Override
    public String getErrorMessage() {
        return isValid() ? null : "Update failed for some reasons:<br/>- " + super.getErrorMessage();
    }
}
