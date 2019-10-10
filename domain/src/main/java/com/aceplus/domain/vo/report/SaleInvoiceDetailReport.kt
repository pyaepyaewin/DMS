package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class SaleInvoiceDetailReport(
    @ColumnInfo(name ="product_name" )
    val productName: String,
    @ColumnInfo(name = "sold_quantity")
    val soldQuantity: Int,
    @ColumnInfo(name ="discount_amount" )
    val discountAmount: String,
    @ColumnInfo(name ="total_amount" )
    val totalAmount: Double
)