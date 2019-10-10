package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class ProductBalanceReport(
    @ColumnInfo(name = "product_name")
    var productName: String,
    @ColumnInfo(name = "total_quantity")
    var totalQuantity: Int ,
    @ColumnInfo(name = "order_quantity")
    var orderQuantity: Int,
    @ColumnInfo(name = "sold_quantity")
    var soldQuantity: Int,
    @ColumnInfo(name = "exchange_quantity")
    var exchangeQuantity: Int,
    @ColumnInfo(name = "return_quantity")
    var returnQuantity: Int,
    @ColumnInfo(name = "delivery_quantity")
    var deliveryQuantity: Int,
    @ColumnInfo(name = "present_quantity")
    var presentQuantity: Int,
    @ColumnInfo(name = "remaining_quantity")
    var remainingQuantity: Int


    )