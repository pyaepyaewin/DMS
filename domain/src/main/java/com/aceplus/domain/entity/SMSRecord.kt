package com.aceplus.domain.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "sms_record")
class SMSRecord {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "send_date")
    @SerializedName("send_date")
    @Expose
    var send_date: String? = null

    @ColumnInfo(name = "message_body")
    @SerializedName("message_body")
    @Expose
    var message_body: String? = null

    @ColumnInfo(name = "phone_no")
    @SerializedName("phone_no")
    @Expose
    var phone_no: String? = null

}