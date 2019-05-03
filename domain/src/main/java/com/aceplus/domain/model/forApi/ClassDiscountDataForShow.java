package com.aceplus.domain.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassDiscountDataForShow {

    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("ClassDiscountNo")
    @Expose
    private String ClassDiscountNo;

    @SerializedName("Date")
    @Expose
    private String Date;

    @SerializedName("StartDate")
    @Expose
    private String StartDate;

    @SerializedName("EndDate")
    @Expose
    private String EndDate;


    @SerializedName("Sunday")
    @Expose
    private String Sunday;


    @SerializedName("Monday")
    @Expose
    private String Monday;


    @SerializedName("Tuesday")
    @Expose
    private String Tuesday;


    @SerializedName("Wednesday")
    @Expose
    private String Wednesday;


    @SerializedName("Thursday")
    @Expose
    private String Thursday;


    @SerializedName("Friday")
    @Expose
    private String Friday;


    @SerializedName("Saturday")
    @Expose
    private String Saturday;

    @SerializedName("SHour")
    @Expose
    private String SHour;

    @SerializedName("SMinute")
    @Expose
    private String SMinute;

    @SerializedName("SShif")
    @Expose
    private String SShif;


    @SerializedName("EHour")
    @Expose
    private String EHour;

    @SerializedName("EMinute")
    @Expose
    private String EMinute;

    @SerializedName("EShif")
    @Expose
    private String EShif;


    @SerializedName("DiscountType")
    @Expose
    private String DiscountType;

    @SerializedName("Active")
    @Expose
    private String Active;

    @SerializedName("CreatedDate")
    @Expose
    private String CreatedDate;

    @SerializedName("CreatedUserID")
    @Expose
    private String CreatedUserID;

    @SerializedName("UpdatedDate")
    @Expose
    private String UpdatedDate;

    @SerializedName("UpdatedUserID")
    @Expose
    private String UpdatedUserID;

    @SerializedName("TS")
    @Expose
    private String TS;

    @SerializedName("ClassDiscountForShowItem")
    @Expose
    private List<ClassDiscountForShowItem> classDiscountByPriceItems = null;

    @SerializedName("ClassDiscountForShowGift")
    @Expose
    private List<ClassDiscountForShowGift> classDiscountByPriceGifts = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassDiscountNo() {
        return ClassDiscountNo;
    }

    public void setClassDiscountNo(String classDiscountNo) {
        ClassDiscountNo = classDiscountNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getSunday() {
        return Sunday;
    }

    public void setSunday(String sunday) {
        Sunday = sunday;
    }

    public String getMonday() {
        return Monday;
    }

    public void setMonday(String monday) {
        Monday = monday;
    }

    public String getTuesday() {
        return Tuesday;
    }

    public void setTuesday(String tuesday) {
        Tuesday = tuesday;
    }

    public String getWednesday() {
        return Wednesday;
    }

    public void setWednesday(String wednesday) {
        Wednesday = wednesday;
    }

    public String getThursday() {
        return Thursday;
    }

    public void setThursday(String thursday) {
        Thursday = thursday;
    }

    public String getFriday() {
        return Friday;
    }

    public void setFriday(String friday) {
        Friday = friday;
    }

    public String getSaturday() {
        return Saturday;
    }

    public void setSaturday(String saturday) {
        Saturday = saturday;
    }

    public String getSHour() {
        return SHour;
    }

    public void setSHour(String SHour) {
        this.SHour = SHour;
    }

    public String getSMinute() {
        return SMinute;
    }

    public void setSMinute(String SMinute) {
        this.SMinute = SMinute;
    }

    public String getSShif() {
        return SShif;
    }

    public void setSShif(String SShif) {
        this.SShif = SShif;
    }

    public String getEHour() {
        return EHour;
    }

    public void setEHour(String EHour) {
        this.EHour = EHour;
    }

    public String getEMinute() {
        return EMinute;
    }

    public void setEMinute(String EMinute) {
        this.EMinute = EMinute;
    }

    public String getEShif() {
        return EShif;
    }

    public void setEShif(String EShif) {
        this.EShif = EShif;
    }

    public String getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(String discountType) {
        DiscountType = discountType;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedUserID() {
        return CreatedUserID;
    }

    public void setCreatedUserID(String createdUserID) {
        CreatedUserID = createdUserID;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getUpdatedUserID() {
        return UpdatedUserID;
    }

    public void setUpdatedUserID(String updatedUserID) {
        UpdatedUserID = updatedUserID;
    }

    public String getTS() {
        return TS;
    }

    public void setTS(String TS) {
        this.TS = TS;
    }

    public List<ClassDiscountForShowItem> getClassDiscountByPriceItems() {
        return classDiscountByPriceItems;
    }

    public void setClassDiscountByPriceItems(List<ClassDiscountForShowItem> classDiscountByPriceItems) {
        this.classDiscountByPriceItems = classDiscountByPriceItems;
    }

    public List<ClassDiscountForShowGift> getClassDiscountByPriceGifts() {
        return classDiscountByPriceGifts;
    }

    public void setClassDiscountByPriceGifts(List<ClassDiscountForShowGift> classDiscountByPriceGifts) {
        this.classDiscountByPriceGifts = classDiscountByPriceGifts;
    }
}
