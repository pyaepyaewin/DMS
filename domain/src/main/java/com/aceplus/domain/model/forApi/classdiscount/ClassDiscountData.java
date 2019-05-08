package com.aceplus.domain.model.forApi.classdiscount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassDiscountData {

    @SerializedName("ClassDiscountByPrice")
    @Expose
    private List<ClassDiscountPriceForApi> classDiscountPriceForApis = null;

    public List<ClassDiscountPriceForApi> getClassDiscountPriceForApis() {
        return classDiscountPriceForApis;
    }

    public void setClassDiscountPriceForApis(List<ClassDiscountPriceForApi> classDiscountPriceForApis) {
        this.classDiscountPriceForApis = classDiscountPriceForApis;
    }
}
