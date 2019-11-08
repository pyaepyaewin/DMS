package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo
import android.os.Parcel
import android.os.Parcelable

class SaleInvoiceDetailReport(
    @ColumnInfo(name = "product_name")
    val productName: String,
    @ColumnInfo(name = "sale_quantity")
    val saleQuantity: Int,
    @ColumnInfo(name = "discount_amount")
    val discountAmount: String,
    @ColumnInfo(name = "total_amount")
    val totalAmount: Double,
    @ColumnInfo(name = "s_price")
    var sellingPrice: Double,
    @ColumnInfo(name = "discount_percent")
    var discountPercent: Double = 0.0,
    @ColumnInfo(name = "promotion_price")
    var promotionPrice: Double = 0.0,
    @ColumnInfo(name = "item_discount_amount")
    var itemDiscountAmount: Double = 0.0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productName)
        parcel.writeInt(saleQuantity)
        parcel.writeString(discountAmount)
        parcel.writeDouble(totalAmount)
        parcel.writeDouble(sellingPrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SaleInvoiceDetailReport> {
        override fun createFromParcel(parcel: Parcel): SaleInvoiceDetailReport {
            return SaleInvoiceDetailReport(parcel)
        }

        override fun newArray(size: Int): Array<SaleInvoiceDetailReport?> {
            return arrayOfNulls(size)
        }
    }
}