package com.aceplus.domain.model.credit;

import android.arch.persistence.room.Ignore;

import java.io.Serializable;

/**
 * Created by ESeries on 12/1/2015.
 */
public class CreditInvoice implements Serializable{

    /**
     * id
     */
    private int id;

    /**
     * invoiceNo
     */
    private String invoice_no;

    /**
     * invoiceDate
     */
    private String invoice_date;

    /**
     * customerId
     */
    private int customer_id;

    /**
     * amt
     */
    private double amount;

    /**
     * payAmt
     */
    private double pay_amount;

    /**
     * creditAmt
     */
    @Ignore
    private double creditAmt;

    /**
     * refund
     */
    private double refund;

    /**
     * saleStatus
     */
    private String saleStatus;

    /**
     * invoiceStatus
     */
    private String invoiceStatus;

    /**
     * saleManId
     */
    private int sale_man_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoice_no;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoice_no = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoice_date;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoice_date = invoiceDate;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(int customerId) {
        this.customer_id = customerId;
    }

    public double getAmt() {
        return amount;
    }

    public void setAmt(double amt) {
        this.amount = amt;
    }

    public double getPayAmt() {
        return pay_amount;
    }

    public void setPayAmt(double payAmt) {
        this.pay_amount = payAmt;
    }

    public double getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(double creditAmt) {
        this.creditAmt = creditAmt;
    }

    public double getRefund() {
        return refund;
    }

    public void setRefund(double refund) {
        this.refund = refund;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public int getSaleManId() {
        return sale_man_id;
    }

    public void setSaleManId(int saleManId) {
        this.sale_man_id = saleManId;
    }

    /*
    String customerName = null;
    String invoiceId = null;
    String invoiceDate = null;
    String status = null;
    double amount = 0.0;
    double paidAmount = 0.0;
    double creditAmount = 0.0;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }*/
}
