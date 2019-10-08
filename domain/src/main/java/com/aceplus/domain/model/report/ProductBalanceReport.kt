package com.aceplus.domain.model.report

import android.arch.persistence.room.ColumnInfo

class ProductBalanceReport(
    @ColumnInfo(name = "product_name")
    var product_name: String,
    @ColumnInfo(name = "total_quantity")
    var total_quantity: Int ,
    @ColumnInfo(name = "order_quantity")
    var order_quantity: Int,
    @ColumnInfo(name = "sold_quantity")
    var sold_quantity: Int,
    @ColumnInfo(name = "exchange_quantity")
    var exchange_quantity: Int,
    @ColumnInfo(name = "return_quantity")
    var return_quantity: Int,
    @ColumnInfo(name = "delivery_quantity")
    var delivery_quantity: Int,
    @ColumnInfo(name = "present_quantity")
    var present_quantity: Int,
    @ColumnInfo(name = "remaining_quantity")
    var remaining_quantity: Int


    )