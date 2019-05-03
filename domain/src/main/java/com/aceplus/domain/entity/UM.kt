package com.aceplus.domain.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "um")
class UM {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String? = null

    @ColumnInfo(name = "code")
    @SerializedName("code")
    @Expose
    var code: String? = null

}