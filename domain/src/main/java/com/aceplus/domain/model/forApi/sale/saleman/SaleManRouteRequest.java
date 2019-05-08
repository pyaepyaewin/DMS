package com.aceplus.domain.model.forApi.sale.saleman;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haker on 4/4/17.
 */

public class SaleManRouteRequest {
    @SerializedName("data")
    @Expose
    private List<DataForSaleManRoute> data = null;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("site_activation_key")
    @Expose
    private String siteActivationKey;
    @SerializedName("tablet_activation_key")
    @Expose
    private String tabletActivationKey;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public List<DataForSaleManRoute> getData() {
        return data;
    }

    public void setData(List<DataForSaleManRoute> data) {
        this.data = data;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSiteActivationKey() {
        return siteActivationKey;
    }

    public void setSiteActivationKey(String siteActivationKey) {
        this.siteActivationKey = siteActivationKey;
    }

    public String getTabletActivationKey() {
        return tabletActivationKey;
    }

    public void setTabletActivationKey(String tabletActivationKey) {
        this.tabletActivationKey = tabletActivationKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
