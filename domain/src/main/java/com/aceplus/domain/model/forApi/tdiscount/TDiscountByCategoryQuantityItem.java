package com.aceplus.domain.model.forApi.tdiscount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phonelin on 4/23/18.
 */

public class TDiscountByCategoryQuantityItem {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("TPromotionPlanId")
    @Expose
    private String tPromotionPlanId;
    @SerializedName("StockId")
    @Expose
    private String stockId;
    @SerializedName("CurrencyId")
    @Expose
    private String currencyId;
    @SerializedName("FromQuantity")
    @Expose
    private String fromQuantity;
    @SerializedName("ToQuantity")
    @Expose
    private String toQuantity;
    @SerializedName("DiscountPercent")
    @Expose
    private String discountPercent;
    @SerializedName("DiscountAmount")
    @Expose
    private String discountAmount;
    @SerializedName("NewSalePrice")
    @Expose
    private String newSalePrice;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTPromotionPlanId() {
        return tPromotionPlanId;
    }

    public void setTPromotionPlanId(String tPromotionPlanId) {
        this.tPromotionPlanId = tPromotionPlanId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getFromQuantity() {
        return fromQuantity;
    }

    public void setFromQuantity(String fromQuantity) {
        this.fromQuantity = fromQuantity;
    }

    public String getToQuantity() {
        return toQuantity;
    }

    public void setToQuantity(String toQuantity) {
        this.toQuantity = toQuantity;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getNewSalePrice() {
        return newSalePrice;
    }

    public void setNewSalePrice(String newSalePrice) {
        this.newSalePrice = newSalePrice;
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


}
