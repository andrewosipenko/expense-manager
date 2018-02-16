package com.es.jointexpensetracker.model;

import java.math.BigDecimal;

public class PersonTotalChartItem {
    private final String name;
    private final BigDecimal totalAmount;
    private String hexColor;
    private String hexHighlightedColor;

    public PersonTotalChartItem(String name, BigDecimal totalAmount) {
        this.name = name;
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public String getHexColor() {
        return hexColor;
    }

    public String getHexHighlightedColor() {
        return hexHighlightedColor;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public void setHexHighlightedColor(String hexHighlightedColor) {
        this.hexHighlightedColor = hexHighlightedColor;
    }
}
