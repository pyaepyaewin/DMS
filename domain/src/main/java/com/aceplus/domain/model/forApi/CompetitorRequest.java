package com.aceplus.domain.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 7/10/17.
 */

public class CompetitorRequest {

    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("site_activation_key")
    @Expose
    private String siteActivationKey;
    @SerializedName("tablet_activation_key")
    @Expose
    private String tabletActivationKey;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("data")
    @Expose
    private List<CompetitorRequestData> competitorRequestList = null;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<CompetitorRequestData> getCompetitorRequestList() {
        return competitorRequestList;
    }

    public void setCompetitorRequestList(List<CompetitorRequestData> competitorRequestList) {
        this.competitorRequestList = competitorRequestList;
    }
}
