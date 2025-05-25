package com.stylesync.salonreview.model;

public abstract class Feedback {
    protected String customerName;
    protected String date;

    public abstract void submit();

    // Getters and Setters (Encapsulation)
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}