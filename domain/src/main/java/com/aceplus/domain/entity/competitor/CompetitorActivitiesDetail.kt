package com.aceplus.domain.entity.competitor

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "competitor_activities_detail")
class CompetitorActivitiesDetail {

    @ColumnInfo(name = "competitor_activities_id")
    @SerializedName("competitor_activities_id")
    @Expose
    var competitor_activities_id: Int = 0

    @ColumnInfo(name = "competitor_id")
    @SerializedName("competitor_id")
    @Expose
    var competitor_id: Int = 0

}