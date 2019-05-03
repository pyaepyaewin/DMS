package com.aceplus.domain.entity.predefine

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "street")
class Street {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "street_id")
    @SerializedName("street_id")
    @Expose
    var street_id: Int = 0

    @ColumnInfo(name = "street_name")
    @SerializedName("street_name")
    @Expose
    var street_name: String? = null


}