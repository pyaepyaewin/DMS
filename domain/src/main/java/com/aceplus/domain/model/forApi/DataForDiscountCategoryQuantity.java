package com.aceplus.domain.model.forApi;

import com.aceplus.domain.model.forApi.tdiscount.TDiscountByCategoryQuantity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 4/23/18.
 */

public class DataForDiscountCategoryQuantity {

    @SerializedName("TDiscountByCategoryQuantity")
    @Expose
    private List<TDiscountByCategoryQuantity> tDiscountByCategoryQuantity = null;

    public List<TDiscountByCategoryQuantity> getTDiscountByCategoryQuantity() {
        return tDiscountByCategoryQuantity;
    }

    public void setTDiscountByCategoryQuantity(List<TDiscountByCategoryQuantity> tDiscountByCategoryQuantity) {
        this.tDiscountByCategoryQuantity = tDiscountByCategoryQuantity;
    }


}
