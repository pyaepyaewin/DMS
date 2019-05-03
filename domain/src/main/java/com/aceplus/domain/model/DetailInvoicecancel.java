package com.aceplus.domain.model;

public class DetailInvoicecancel {
    public String productname;
    public int qty;
    public double disAmt;
    public double totalAmt;
    public DetailInvoicecancel(String productname, int qty, double disAmt, double totalAmt){
        this.productname = productname;
        this.qty = qty;
        this.disAmt = disAmt;
        this.totalAmt = totalAmt;
    }
}
