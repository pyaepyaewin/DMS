package com.aceplus.domain.model.routeSchedule_v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteScheduleItem_v2 {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("RouteScheduleId")
    @Expose
    private String routeScheduleId;
    @SerializedName("SrNo")
    @Expose
    private String srNo;
    @SerializedName("CustomerId")
    @Expose
    private String customerId;

    @SerializedName("Active")
    @Expose
    private String active;
    @SerializedName("TS")
    @Expose
    private String tS;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedUserID")
    @Expose
    private String createdUserID;
    @SerializedName("UpdatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("UpdatedUserID")
    @Expose
    private Object updatedUserID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteScheduleId() {
        return routeScheduleId;
    }

    public void setRouteScheduleId(String routeScheduleId) {
        this.routeScheduleId = routeScheduleId;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String gettS() {
        return tS;
    }

    public void settS(String tS) {
        this.tS = tS;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(String createdUserID) {
        this.createdUserID = createdUserID;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getUpdatedUserID() {
        return updatedUserID;
    }

    public void setUpdatedUserID(Object updatedUserID) {
        this.updatedUserID = updatedUserID;
    }
}
