package com.aceplus.domain.model.forApi.deviceissue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceIssueItem_Request {

    @SerializedName("InvoiceNo")
    @Expose
    private String invoiceNo;

    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("RouteId")
    @Expose
    private String routeId;

    @SerializedName("Remark")
    @Expose
    private String remark;

    @SerializedName("DeviceIssueRequestItem")
    @Expose
    private List<DeviceIssueItem> deviceIssueItemList = null;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DeviceIssueItem> getDeviceIssueItemList() {
        return deviceIssueItemList;
    }

    public void setDeviceIssueItemList(List<DeviceIssueItem> deviceIssueItemList) {
        this.deviceIssueItemList = deviceIssueItemList;
    }
}
