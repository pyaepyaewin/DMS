package com.aceplus.domain.model.customer;

public class CustomerFeedback {

    private String invoiceNumber;
    private String invoiceDate;
    private String description;

    public CustomerFeedback(String invoiceNumber, String invoiceDate, String description) {

        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.description = description;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getDescription() {
        return description;
    }
}
