package com.aceplus.domain.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassDiscountResponse {

    @SerializedName("aceplusStatusCode")
    @Expose
    private Integer aceplusStatusCode;
    @SerializedName("aceplusStatusMessage")
    @Expose
    private String aceplusStatusMessage;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("data")
    @Expose
    private List<ClassDiscountData> dataForClassDiscount = null;

    public Integer getAceplusStatusCode() {
        return aceplusStatusCode;
    }

    public void setAceplusStatusCode(Integer aceplusStatusCode) {
        this.aceplusStatusCode = aceplusStatusCode;
    }

    public String getAceplusStatusMessage() {
        return aceplusStatusMessage;
    }

    public void setAceplusStatusMessage(String aceplusStatusMessage) {
        this.aceplusStatusMessage = aceplusStatusMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ClassDiscountData> getDataForClassDiscount() {
        return dataForClassDiscount;
    }

    public void setDataForClassDiscount(List<ClassDiscountData> dataForClassDiscount) {
        this.dataForClassDiscount = dataForClassDiscount;
    }
}