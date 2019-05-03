package com.aceplus.domain.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phonelin on 3/20/17.
 */

public class DisplayAssessment {

    @SerializedName("InvoiceNo")
    @Expose
    String invoiceNo;
    @SerializedName("InvoiceDate")
    @Expose
    String invoiceDate;
    @SerializedName("CustomerId")
    @Expose
    Integer customerId;
    @SerializedName("SaleManId")
    @Expose
    Integer saleManId;
    @SerializedName("Image")
    @Expose
    String image;
    @SerializedName("ImageNo")
    @Expose
    String imageNo;
    @SerializedName("ImageName")
    @Expose
    String imageName;
    @SerializedName("DateAndTime")
    @Expose
    String dateAndTime;
    @SerializedName("Remark")
    @Expose
    String remark;

    String customerNo;

    String customerName;

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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(Integer saleManId) {
        this.saleManId = saleManId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImageNo() {
        return imageNo;
    }

    public void setImageNo(String imageNo) {
        this.imageNo = imageNo;
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
}
