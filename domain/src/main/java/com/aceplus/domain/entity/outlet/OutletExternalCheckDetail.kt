package com.aceplus.domain.entity.outlet

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.aceplus.domain.typeconverter.StringListTypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "outlet_external_check_detail")
class OutletExternalCheckDetail {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "outlet_external_check_id")
    @SerializedName("outlet_external_check_id")
    @Expose
    var outlet_external_check_id: Int = 0

    @TypeConverters(StringListTypeConverter::class)
    @ColumnInfo(name = "image_list")
    @SerializedName("image_list")
    @Expose
    var image_list: List<String> = ArrayList()


}