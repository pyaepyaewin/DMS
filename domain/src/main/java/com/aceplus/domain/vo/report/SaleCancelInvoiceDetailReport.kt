package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class SaleCancelInvoiceDetailReport (
    @ColumnInfo(name ="product_name" )
    val productName: String,
    @ColumnInfo(name = "sale_quantity")
    val soldQuantity: Double,
    @ColumnInfo(name ="discount_amount" )
    val discountAmount: String,
    @ColumnInfo(name ="total_amount" )
    val totalAmount: Double
)