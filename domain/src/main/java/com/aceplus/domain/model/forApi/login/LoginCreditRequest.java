package com.aceplus.domain.model.forApi.login;

import com.aceplus.domain.model.forApi.credit.CreditApi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by aceplus_mobileteam on 6/13/17.
 */

public class LoginCreditRequest {

    @SerializedName("site_activation_key")
    @Expose
    private String siteActivationKey;
    @SerializedName("tablet_activation_key")
    @Expose
    private String tabletActivationKey;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("route")
    @Expose
    private Integer route;
    @SerializedName("tablet_key")
    @Expose
    private String tabletKey;
    @SerializedName("data")
    @Expose
    private ArrayList<CreditApi> data = null;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRoute() {
        return route;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public ArrayList<CreditApi> getData() {
        return data;
    }

    public void setData(ArrayList<CreditApi> data) {
        this.data = data;
    }

    public String getTabletKey() {
        return tabletKey;
    }

    public void setTabletKey(String tabletKey) {
        this.tabletKey = tabletKey;
    }

}
