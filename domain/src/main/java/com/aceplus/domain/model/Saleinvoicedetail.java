package com.aceplus.domain.model;

/**
 * Created by phonelin on 2/9/17.
 */

public class Saleinvoicedetail {

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getDiscountpercent() {
        return discountpercent;
    }

    public void setDiscountpercent(Double discountpercent) {
        this.discountpercent = discountpercent;
    }

    String productName,quantity;
    Double discountAmount,totalAmount, discountpercent;

}
