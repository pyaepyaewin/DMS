package com.aceplus.domain.entity.competitor

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "competitor_activities")
class CompetitorActivities {

    @ColumnInfo(name = "competitor_activities_id")
    @SerializedName("competitor_activities_id")
    @Expose
    var competitor_activities_id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @Expose
    var date: String? = null

}