package com.aceplus.domain.entity.tdiscount

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "t_discount_by_category_quantity_item")
class TDiscountByCategoryQuantityItem {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "t_promotion_plan_id")
    @SerializedName("t_promotion_plan_id")
    @Expose
    var t_promotion_plan_id: String? = null

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: String? = null

    @ColumnInfo(name = "currency_id")
    @SerializedName("currency_id")
    @Expose
    var currency_id: Int = 0

    @ColumnInfo(name = "from_quantity")
    @SerializedName("from_quantity")
    @Expose
    var from_quantity: String? = null

    @ColumnInfo(name = "to_quantity")
    @SerializedName("to_quantity")
    @Expose
    var to_quantity: String? = null

    @ColumnInfo(name = "discount_percent")
    @SerializedName("discount_percent")
    @Expose
    var discount_percent: String? = null

    @ColumnInfo(name = "discount_amount")
    @SerializedName("discount_amount")
    @Expose
    var discount_amount: String? = null

    @ColumnInfo(name = "new_sale_price")
    @SerializedName("new_sale_price")
    @Expose
    var new_sale_price: String? = null

    @ColumnInfo(name = "active")
    @SerializedName("active")
    @Expose
    var active: String? = null

    @ColumnInfo(name = "created_date")
    @SerializedName("created_date")
    @Expose
    var created_date: String? = null

    @ColumnInfo(name = "created_user_id")
    @SerializedName("created_user_id")
    @Expose
    var created_user_id: Int = 0

    @ColumnInfo(name = "updated_date")
    @SerializedName("updated_date")
    @Expose
    var updated_date: String? = null

    @ColumnInfo(name = "updated_user_id")
    @SerializedName("updated_user_id")
    @Expose
    var updated_user_id: Int = 0

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null
}