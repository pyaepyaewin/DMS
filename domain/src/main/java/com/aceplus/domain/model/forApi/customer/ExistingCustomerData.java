package com.aceplus.domain.model.forApi.customer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExistingCustomerData {

    public List<ExistingCustomerForApi> getCustomerForApiList() {
        return customerForApiList;
    }

    public void setCustomerForApiList(List<ExistingCustomerForApi> customerForApiList) {
        this.customerForApiList = customerForApiList;
    }

    @SerializedName("Customer")
    @Expose
    private List<ExistingCustomerForApi> customerForApiList = null;
}
