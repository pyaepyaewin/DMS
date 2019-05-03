package com.aceplus.domain.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassDiscountByPriceItem {

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("ClassDiscountId")
    @Expose
    private String ClassDiscountId;

    @SerializedName("ClassId")
    @Expose
    private String ClassId;

    @SerializedName("FromQuantity")
    @Expose
    private String FromQuantity;

    @SerializedName("ToQuantity")
    @Expose
    private String ToQuantity;

    @SerializedName("FromAmount")
    @Expose
    private String FromAmount;

    @SerializedName("ToAmount")
    @Expose
    private String ToAmount;

    @SerializedName("DiscountPercent")
    @Expose
    private String DiscountPercent;

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

    String stockName;
    int stockQty;

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
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getFromQuantity() {
        return FromQuantity;
    }

    public void setFromQuantity(String fromQuantity) {
        FromQuantity = fromQuantity;
    }

    public String getToQuantity() {
        return ToQuantity;
    }

    public void setToQuantity(String toQuantity) {
        ToQuantity = toQuantity;
    }

    public String getFromAmount() {
        return FromAmount;
    }

    public void setFromAmount(String fromAmount) {
        FromAmount = fromAmount;
    }

    public String getToAmount() {
        return ToAmount;
    }

    public void setToAmount(String toAmount) {
        ToAmount = toAmount;
    }

    public String getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        DiscountPercent = discountPercent;
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

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }
}
