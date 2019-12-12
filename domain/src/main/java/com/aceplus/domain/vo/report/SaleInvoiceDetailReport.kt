package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo
import android.os.Parcel
import android.os.Parcelable

class SaleInvoiceDetailReport(
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "product_id")
    var productId:  String,
    @ColumnInfo(name = "selling_price")
    var sellingPrice:  String? = null,
    @ColumnInfo(name = "purchase_price")
    var purchasePrice:  String? = null,
    @ColumnInfo(name = "discount_type")
    var discountType: String? = null,
    @ColumnInfo(name = "category_id")
    var categoryId:  String? = null,
    @ColumnInfo(name = "group_id")
    var groupId:  String? = null,
    @ColumnInfo(name = "class_id")
    var classId: String? = null,
    @ColumnInfo(name = "remaining_quantity")
    var remainingQuantity: Int = 0,
    @ColumnInfo(name = "um")
    var um: String? = null,
    @ColumnInfo(name = "product_name")
    val productName: String,
    @ColumnInfo(name = "sale_quantity")
    val saleQuantity: Int,
    @ColumnInfo(name = "discount_amount")
    val discountAmount: String,
    @ColumnInfo(name = "total_amount")
    val totalAmount: Double,
    @ColumnInfo(name = "s_price")
    var sPrice: Double,
    @ColumnInfo(name = "discount_percent")
    var discountPercent: Double = 0.0,
    @ColumnInfo(name = "promotion_price")
    var promotionPrice: Double = 0.0,
    @ColumnInfo(name = "item_discount_amount")
    var itemDiscountAmount: Double = 0.0,
    @ColumnInfo(name = "item_discount_percent")
    var itemDiscountPercent: Double = 0.0
)