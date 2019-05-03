package com.aceplus.domain.model.routeSchedule_v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteSchedule_v2 {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("ScheduleNo")
    @Expose
    private String scheduleNo;
    @SerializedName("ScheduleDate")
    @Expose
    private String scheduleDate;
    @SerializedName("SaleManId")
    @Expose
    private String saleManId;
    @SerializedName("RouteId")
    @Expose
    private String routeId;

    @SerializedName("Comission")
    @Expose
    private String comission;

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
    private String updatedDate;
    @SerializedName("UpdatedUserID")
    @Expose
    private String updatedUserID;
    @SerializedName("RouteSchedule_Item")
    @Expose
    private List<RouteScheduleItem_v2> routeScheduleItem = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getComission() {
        return comission;
    }

    public void setComission(String comission) {
        this.comission = comission;
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

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUserID() {
        return updatedUserID;
    }

    public void setUpdatedUserID(String updatedUserID) {
        this.updatedUserID = updatedUserID;
    }

    public List<RouteScheduleItem_v2> getRouteScheduleItem() {
        return routeScheduleItem;
    }

    public void setRouteScheduleItem(List<RouteScheduleItem_v2> routeScheduleItem) {
        this.routeScheduleItem = routeScheduleItem;
    }
}
