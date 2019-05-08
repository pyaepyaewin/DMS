package com.aceplus.domain.networkresponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse<T> : BaseResponseVO<T>() {

    @SerializedName("user_id")
    @Expose
    private var userId: String? = null

    @SerializedName("route")
    @Expose
    private var route: Int? = null

    @SerializedName("tablet_id")
    @Expose
    private var tabletKey: Int? = null

    @SerializedName("max_id")
    @Expose
    private var maxKey: Int? = null

}