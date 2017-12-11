package com.es.expensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class Expense {
    private Long id;
    private BigDecimal amount;
    private Currency currency;
    private LocalDate date;
}
