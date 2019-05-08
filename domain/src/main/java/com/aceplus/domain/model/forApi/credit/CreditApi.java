package com.aceplus.domain.model.forApi.credit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 6/13/17.
 */

public class CreditApi {

    @SerializedName("customer_id")
    @Expose
    List<Integer> idList = null;

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }
}
