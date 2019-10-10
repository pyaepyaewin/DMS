package com.aceplus.domain.entity.predefine

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import com.aceplus.domain.model.route.Route_Township
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "township")
class Township {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "township_id")
    @SerializedName("township_id")
    @Expose
    var township_id:  String? = null

    @ColumnInfo(name = "township_name")
    @SerializedName("township_name")
    @Expose
    var township_name: String? = null


}