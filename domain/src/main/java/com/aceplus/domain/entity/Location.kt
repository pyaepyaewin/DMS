package com.aceplus.domain.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "location")
class Location {

    @PrimaryKey
    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    var location_id:  String = ""

    @ColumnInfo(name = "location_no")
    @SerializedName("location_no")
    @Expose
    var location_no:  String? = null

    @ColumnInfo(name = "location_name")
    @SerializedName("location_name")
    @Expose
    var location_name: String? = null

    @ColumnInfo(name = "branch_id")
    @SerializedName("branch_id")
    @Expose
    var branch_id:  String? = null

    @ColumnInfo(name = "branch_name")
    @SerializedName("branch_name")
    @Expose
    var branch_name: String? = null

}