package com.aceplus.domain.model.forApi.other;

import com.aceplus.domain.model.forApi.*;
import com.aceplus.domain.model.forApi.customer.CustomerFeedback;
import com.aceplus.domain.model.forApi.product.ProductCategory;
import com.aceplus.domain.model.forApi.product.ProductType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 2/13/17.
 */

public class GeneralData {


    @SerializedName("District")
    @Expose
    private List<District> district = null;
    @SerializedName("Township")
    @Expose
    private List<Township> township = null;
    @SerializedName("StreetCode")
    @Expose
    private List<Street> streets = null;
    @SerializedName("Category")
    @Expose
    private List<ProductCategory> category = null;
    @SerializedName("GroupCode")
    @Expose
    private List<GroupCode> groupCode = null;
    @SerializedName("Class")
    @Expose
    private List<ClassOfProduct> _clases = null;
    @SerializedName("UM")
    @Expose
    private List<UM> uM = null;
    @SerializedName("ProductType")
    @Expose
    private List<ProductType> productType = null;
    @SerializedName("StateDivision")
    @Expose
    private List<StateDivision> stateDivision = null;
    @SerializedName("Location")
    @Expose
    private List<Location> location = null;
    @SerializedName("CustomerFeedback")
    @Expose
    private List<CustomerFeedback> customerFeedbacks = null;
    @SerializedName("Currency")
    @Expose
    private List<Currency> currencyList = null;

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }

    public List<Township> getTownship() {
        return township;
    }

    public void setTownship(List<Township> township) {
        this.township = township;
    }

    public List<ProductCategory> getProductCategory() {
        return category;
    }

    public void setProductCategory(List<ProductCategory> category) {
        this.category = category;
    }

    public List<GroupCode> getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(List<GroupCode> groupCode) {
        this.groupCode = groupCode;
    }

    public List<ClassOfProduct> getClass_() {
        return _clases;
    }

    public void setClass_(List<ClassOfProduct> _clases) {
        this._clases = _clases;
    }

    public List<UM> getUM() {
        return uM;
    }

    public void setUM(List<UM> uM) {
        this.uM = uM;
    }

    public List<ProductType> getProductType() {
        return productType;
    }

    public void setProductType(List<ProductType> productType) {
        this.productType = productType;
    }

    public List<StateDivision> getStateDivision() {
        return stateDivision;
    }

    public void setStateDivision(List<StateDivision> stateDivision) {
        this.stateDivision = stateDivision;
    }

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    public List<CustomerFeedback> getCustomerFeedbacks() {
        return customerFeedbacks;
    }

    public void setCustomerFeedbacks(List<CustomerFeedback> customerFeedbacks) {
        this.customerFeedbacks = customerFeedbacks;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }
}
