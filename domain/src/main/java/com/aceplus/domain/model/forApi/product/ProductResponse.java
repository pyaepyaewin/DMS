package com.aceplus.domain.model.forApi.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haker on 2/9/17.
 */

public class ProductResponse {
    @SerializedName("aceplusStatusCode")
    @Expose
    private Integer aceplusStatusCode;
    @SerializedName("aceplusStatusMessage")
    @Expose
    private String aceplusStatusMessage;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("route")
    @Expose
    private Integer route;
    @SerializedName("data")
    @Expose
    private List<DataForProduct> dataForProductList = null;

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

    public Integer getRoute() {
        return route;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public List<DataForProduct> getDataForProductList() {
        return dataForProductList;
    }

    public void setDataForProductList(List<DataForProduct> dataForProductList) {
        this.dataForProductList = dataForProductList;
    }
}
