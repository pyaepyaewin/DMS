package com.aceplus.domain.model.forApi.classdiscount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassDiscountForShowGift {

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("ClassDiscountId")
    @Expose
    private String ClassDiscountId;

    @SerializedName("ClassID")
    @Expose
    private String classId;

    @SerializedName("Quantity")
    @Expose
    private String Quantity;

    @SerializedName("Active")
    @Expose
    private String Active;

    @SerializedName("TS")
    @Expose
    private String TS;

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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getClassDiscountId() {
        return ClassDiscountId;
    }

    public void setClassDiscountId(String classDiscountId) {
        ClassDiscountId = classDiscountId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getTS() {
        return TS;
    }

    public void setTS(String TS) {
        this.TS = TS;
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
}
