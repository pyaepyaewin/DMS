package com.aceplus.domain.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassDiscountForShowApi {

    @SerializedName("ClassDiscountForShow")
    @Expose
    private List<ClassDiscountDataForShow> classDiscountPriceForApis = null;

    public List<ClassDiscountDataForShow> getClassDiscountPriceForApis() {
        return classDiscountPriceForApis;
    }

    public void setClassDiscountPriceForApis(List<ClassDiscountDataForShow> classDiscountPriceForApis) {
        this.classDiscountPriceForApis = classDiscountPriceForApis;
    }
}
