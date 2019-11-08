package com.aceplus.domain.vo.customer

import android.arch.persistence.room.ColumnInfo

class DeliveryVO(
    @ColumnInfo(name = "CID")
    var cId: Int? = null,
    @ColumnInfo(name = "customer_name")
    var customerName: String? = null,
    @ColumnInfo(name = "address")
    var address: String? = null,
    @ColumnInfo(name = "DID")
    var dId: Int = 0,
    @ColumnInfo(name = "invoice_no")
    var invoiceNo: String? = null,
    @ColumnInfo(name = "amount")
    var amount: String? = null,
    @ColumnInfo(name = "paid_amount")
    var paidAmount: String? = null,
    @ColumnInfo(name = "discount")
    var discount: String? = null,
    @ColumnInfo(name = "discount_percent")
    var discountPercent: String? = null,
    @ColumnInfo(name = "sale_man_id")
    var saleManId: String? = null,
    @ColumnInfo(name = "remark")
    var remark: String? = null


)