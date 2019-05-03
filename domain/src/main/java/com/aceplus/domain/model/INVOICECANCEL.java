package com.aceplus.domain.model;

public class INVOICECANCEL {
    public String invoiceId;
    public String customerName;
    public Double totalAmount;
    public Double disAmount;
    public Double netAmount;

    public INVOICECANCEL(String invoiceId, String customerName, Double totalAmount, Double disAmount, Double netAmount){
        this.invoiceId = invoiceId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.disAmount = disAmount;
        this.netAmount = netAmount;
    }

    /**
     * getter and setter method of invoiceid
     */
    public void setInvoiceid(String invoiceid){
        this.invoiceId = invoiceid;
    }
    public String getInvoiceId(){
        return invoiceId;
    }

    /**
     * getter and setter method of customerName
     */
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }
    public String getCustomerName(){
        return customerName;
    }

    /**
     * getter and setter method of TotalAmount
     */
    public void setTotalAmount(Double totalAmount){
        this.totalAmount = totalAmount;
    }
    public Double getTotalAmount(){
        return totalAmount;
    }
    /**
     * getter and setter method of DisAmount
     */
    public void setDisAmount(Double disAmount){
        this.disAmount = disAmount;
    }

    public Double getDisAmount() {
        return disAmount;
    }

    /**
     * getter and setter method of NetAmount
     */
    public void setNetAmount(Double netAmount){
        this.netAmount = netAmount;
    }

    public Double getNetAmount() {
        return netAmount;
    }
}
