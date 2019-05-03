package com.aceplus.domain.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceIssueItemList {

    @SerializedName("DeviceIssueRequest")
    @Expose
    private List<DeviceIssueItem_Request> deviceIssueItem_requests = null;

    public List<DeviceIssueItem_Request> getDeviceIssueItem_requests() {
        return deviceIssueItem_requests;
    }

    public void setDeviceIssueItem_requests(List<DeviceIssueItem_Request> deviceIssueItem_requests) {
        this.deviceIssueItem_requests = deviceIssueItem_requests;
    }
}
