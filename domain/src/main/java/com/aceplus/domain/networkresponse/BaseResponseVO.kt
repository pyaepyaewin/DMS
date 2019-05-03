package com.aceplus.domain.networkresponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class BaseResponseVO<T> {

    @SerializedName("aceplusStatusCode")
    @Expose
    var aceplusStatusCode: String ?= "500"

    @SerializedName("aceplusStatusMessage")
    @Expose
    var aceplusStatusMessage: String ?="Connection Failed"

    @SerializedName("data")
    @Expose
    var data: T? = null

}