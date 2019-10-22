package com.aceplus.domain.entity.promotion

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "promotion")
class Promotion() : Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String? = null

    @ColumnInfo(name = "buy_product_id")
    @SerializedName("buy_product_id")
    @Expose
    var buy_product_id: Int = 0

    @ColumnInfo(name = "buy_quantity")
    @SerializedName("buy_quantity")
    @Expose
    var buy_quantity: Double = 0.0

    @ColumnInfo(name = "buy_amount")
    @SerializedName("buy_amount")
    @Expose
    var buy_amount: Double = 0.0

    @ColumnInfo(name = "product_or_gift")
    @SerializedName("product_or_gift")
    @Expose
    var product_or_gift: String? = null

    @ColumnInfo(name = "promotion_product_id")
    @SerializedName("promotion_product_id")
    @Expose
    var promotion_product_id: Int = 0

    @ColumnInfo(name = "promotion_quantity")
    @SerializedName("promotion_quantity")
    @Expose
    var promotion_quantity: Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()
        buy_product_id = parcel.readInt()
        buy_quantity = parcel.readDouble()
        buy_amount = parcel.readDouble()
        product_or_gift = parcel.readString()
        promotion_product_id = parcel.readInt()
        promotion_quantity = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(buy_product_id)
        parcel.writeDouble(buy_quantity)
        parcel.writeDouble(buy_amount)
        parcel.writeString(product_or_gift)
        parcel.writeInt(promotion_product_id)
        parcel.writeInt(promotion_quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Promotion> {
        override fun createFromParcel(parcel: Parcel): Promotion {
            return Promotion(parcel)
        }

        override fun newArray(size: Int): Array<Promotion?> {
            return arrayOfNulls(size)
        }
    }

}