package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class SaleInvoiceReport(
    @ColumnInfo(name = "invoice_id")
    var invoiceId:String,

    @ColumnInfo(name = "customer_name")
    var customerName:String,

    @ColumnInfo(name = "address")
    var address:String,

    @ColumnInfo(name = "total_amount")
    var totalAmount:String,

    @ColumnInfo(name = "total_discount_amount")
    var totalDiscountAmount:Double,
    @ColumnInfo(name = "sale_date")
    var saleDate: String? = null
)