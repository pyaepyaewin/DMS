package com.aceplus.domain.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 4/23/18.
 */

public class TDiscountByCategoryQuantity {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("PromotionPlanNo")
    @Expose
    private String promotionPlanNo;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("Sunday")
    @Expose
    private String sunday;
    @SerializedName("Monday")
    @Expose
    private String monday;
    @SerializedName("Tuesday")
    @Expose
    private String tuesday;
    @SerializedName("Wednesday")
    @Expose
    private String wednesday;
    @SerializedName("Thursday")
    @Expose
    private String thursday;
    @SerializedName("Friday")
    @Expose
    private String friday;
    @SerializedName("Saturday")
    @Expose
    private String saturday;
    @SerializedName("SHour")
    @Expose
    private String sHour;
    @SerializedName("SMinute")
    @Expose
    private String sMinute;
    @SerializedName("SShif")
    @Expose
    private String sShif;
    @SerializedName("EHour")
    @Expose
    private String eHour;
    @SerializedName("EMinute")
    @Expose
    private String eMinute;
    @SerializedName("EShif")
    @Expose
    private String eShif;
    @SerializedName("PromotionType")
    @Expose
    private String promotionType;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("CategoryId")
    @Expose
    private String categoryId;
    @SerializedName("CurrencyId")
    @Expose
    private String currencyId;
    @SerializedName("UMId")
    @Expose
    private String uMId;
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
    @SerializedName("ApprovedDate")
    @Expose
    private Object approvedDate;
    @SerializedName("ApprovedUserID")
    @Expose
    private Object approvedUserID;
    @SerializedName("TDiscountByCategoryQuantityItem")
    @Expose
    private List<TDiscountByCategoryQuantityItem> tDiscountByCategoryQuantityItem = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPromotionPlanNo() {
        return promotionPlanNo;
    }

    public void setPromotionPlanNo(String promotionPlanNo) {
        this.promotionPlanNo = promotionPlanNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSHour() {
        return sHour;
    }

    public void setSHour(String sHour) {
        this.sHour = sHour;
    }

    public String getSMinute() {
        return sMinute;
    }

    public void setSMinute(String sMinute) {
        this.sMinute = sMinute;
    }

    public String getSShif() {
        return sShif;
    }

    public void setSShif(String sShif) {
        this.sShif = sShif;
    }

    public String getEHour() {
        return eHour;
    }

    public void setEHour(String eHour) {
        this.eHour = eHour;
    }

    public String getEMinute() {
        return eMinute;
    }

    public void setEMinute(String eMinute) {
        this.eMinute = eMinute;
    }

    public String getEShif() {
        return eShif;
    }

    public void setEShif(String eShif) {
        this.eShif = eShif;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getUMId() {
        return uMId;
    }

    public void setUMId(String uMId) {
        this.uMId = uMId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTS() {
        return tS;
    }

    public void setTS(String tS) {
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

    public Object getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Object approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Object getApprovedUserID() {
        return approvedUserID;
    }

    public void setApprovedUserID(Object approvedUserID) {
        this.approvedUserID = approvedUserID;
    }

    public List<TDiscountByCategoryQuantityItem> getTDiscountByCategoryQuantityItem() {
        return tDiscountByCategoryQuantityItem;
    }

    public void setTDiscountByCategoryQuantityItem(List<TDiscountByCategoryQuantityItem> tDiscountByCategoryQuantityItem) {
        this.tDiscountByCategoryQuantityItem = tDiscountByCategoryQuantityItem;
    }


}
