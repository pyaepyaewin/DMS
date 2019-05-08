package com.aceplus.domain.model.forApi.tsale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aceplus_mobileteam on 6/5/17.
 */

public class TSaleFeedback {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String invoiceDate;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("saleperson_id")
    @Expose
    private int saleManId;
    @SerializedName("customer_feedback_id")
    @Expose
    private int customerFeedbackId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("route_id")
    @Expose
    private int route_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(int saleManId) {
        this.saleManId = saleManId;
    }

    public int getCustomerFeedbackId() {
        return customerFeedbackId;
    }

    public void setCustomerFeedbackId(int customerFeedbackId) {
        this.customerFeedbackId = customerFeedbackId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * getter and setter method of route_id
     * @return route_id, @param route_id
     */
    public int getRoute_id(){
        return route_id;
    }
    public void setRoute_id(int route_id){
        this.route_id = route_id;
    }
}
