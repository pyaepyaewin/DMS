package com.aceplus.domain.model;

/**
 * Created by aceplus_mobileteam on 6/30/17.
 */

public class IncentiveForUI {

    Integer customerId;

    String customerNo;

    String customerName;

    String incentiveItemName;

    Integer stockId;

    Integer incentiveQuantity;

    Integer paidQuantity;

    Integer saleManId;

    String invoiceNo;

    String invoiceDate;

    String incentiveProgramName;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(Integer saleManId) {
        this.saleManId = saleManId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getIncentiveItemName() {
        return incentiveItemName;
    }

    public void setIncentiveItemName(String incentiveItemName) {
        this.incentiveItemName = incentiveItemName;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getIncentiveQuantity() {
        return incentiveQuantity;
    }

    public void setIncentiveQuantity(Integer incentiveQuantity) {
        this.incentiveQuantity = incentiveQuantity;
    }

    public Integer getPaidQuantity() {
        return paidQuantity;
    }

    public void setPaidQuantity(Integer paidQuantity) {
        this.paidQuantity = paidQuantity;
    }

    public String getIncentiveProgramName() {
        return incentiveProgramName;
    }

    public void setIncentiveProgramName(String incentiveProgramName) {
        this.incentiveProgramName = incentiveProgramName;
    }
}
