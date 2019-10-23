package com.aceplus.domain.entity.product

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull

@Entity(tableName = "product")
class Product() : Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "product_id")
    @SerializedName("product_id")
    @Expose
    var product_id:  String? = null

    @ColumnInfo(name = "product_name")
    @SerializedName("product_name")
    @Expose
    var product_name: String? = null

    @ColumnInfo(name = "category_id")
    @SerializedName("category_id")
    @Expose
    var category_id:  String? = null

    @ColumnInfo(name = "group_id")
    @SerializedName("group_id")
    @Expose
    var group_id:  String? = null

    @ColumnInfo(name = "total_quantity")
    @SerializedName("total_quantity")
    @Expose
    var total_quantity: Int = 0

    @ColumnInfo(name = "remaining_quantity")
    @SerializedName("remaining_quantity")
    @Expose
    var remaining_quantity: Int = 0

    @ColumnInfo(name = "selling_price")
    @SerializedName("selling_price")
    @Expose
    var selling_price:  String? = null

    @ColumnInfo(name = "purchase_price")
    @SerializedName("purchase_price")
    @Expose
    var purchase_price:  String? = null

    @ColumnInfo(name = "discount_type")
    @SerializedName("discount_type")
    @Expose
    var discount_type: String? = null

    @ColumnInfo(name = "um")
    @SerializedName("um")
    @Expose
    var um: String? = null

    @ColumnInfo(name = "sold_quantity")
    @SerializedName("sold_quantity")
    @Expose
    var sold_quantity: Int = 0

    @ColumnInfo(name = "order_quantity")
    @SerializedName("order_quantity")
    @Expose
    var order_quantity: Int = 0

    @ColumnInfo(name = "exchange_quantity")
    @SerializedName("exchange_quantity")
    @Expose
    var exchange_quantity: Int = 0

    @ColumnInfo(name = "return_quantity")
    @SerializedName("return_quantity")
    @Expose
    var return_quantity: Int = 0

    @ColumnInfo(name = "delivery_quantity")
    @SerializedName("delivery_quantity")
    @Expose
    var delivery_quantity: Int = 0

    @ColumnInfo(name = "present_quantity")
    @SerializedName("present_quantity")
    @Expose
    var present_quantity: Int = 0

    @ColumnInfo(name = "device_issue_status")
    @SerializedName("device_issue_status")
    @Expose
    var device_issue_status: String? = null

    @ColumnInfo(name = "class_id")
    @SerializedName("class_id")
    @Expose
    var class_id: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        product_id = parcel.readString()
        product_name = parcel.readString()
        category_id = parcel.readString()
        group_id = parcel.readString()
        total_quantity = parcel.readInt()
        remaining_quantity = parcel.readInt()
        selling_price = parcel.readString()
        purchase_price = parcel.readString()
        discount_type = parcel.readString()
        um = parcel.readString()
        sold_quantity = parcel.readInt()
        order_quantity = parcel.readInt()
        exchange_quantity = parcel.readInt()
        return_quantity = parcel.readInt()
        delivery_quantity = parcel.readInt()
        present_quantity = parcel.readInt()
        device_issue_status = parcel.readString()
        class_id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(product_id)
        parcel.writeString(product_name)
        parcel.writeString(category_id)
        parcel.writeString(group_id)
        parcel.writeInt(total_quantity)
        parcel.writeInt(remaining_quantity)
        parcel.writeString(selling_price)
        parcel.writeString(purchase_price)
        parcel.writeString(discount_type)
        parcel.writeString(um)
        parcel.writeInt(sold_quantity)
        parcel.writeInt(order_quantity)
        parcel.writeInt(exchange_quantity)
        parcel.writeInt(return_quantity)
        parcel.writeInt(delivery_quantity)
        parcel.writeInt(present_quantity)
        parcel.writeString(device_issue_status)
        parcel.writeString(class_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}