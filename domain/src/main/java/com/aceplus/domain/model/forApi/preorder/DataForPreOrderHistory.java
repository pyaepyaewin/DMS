package com.aceplus.domain.model.forApi.preorder;

import com.aceplus.domain.model.forApi.preorder.PreOrderHistoryForApi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 8/8/2017.
 */

public class DataForPreOrderHistory {

    /**
     * preorder
     */
    @SerializedName("SaleOrder")
    @Expose
    private List<PreOrderHistoryForApi> preOrderHistoryForApiList = null;

    public List<PreOrderHistoryForApi> getPreOrderHistoryForApiList() {
        return preOrderHistoryForApiList;
    }

    public void setPreOrderHistoryForApiList(List<PreOrderHistoryForApi> preOrderHistoryForApiList) {
        this.preOrderHistoryForApiList = preOrderHistoryForApiList;
    }
}
