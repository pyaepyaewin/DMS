package com.aceplus.domain.model.forApi.deviceissue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeviceIssueItem implements Serializable {

    @SerializedName("StockId")
    @Expose
    private String stockId;

    @SerializedName("Quantity")
    @Expose
    private String quantity;


    @SerializedName("InvoiceNo")
    @Expose
    private String invoiceNo;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
}
