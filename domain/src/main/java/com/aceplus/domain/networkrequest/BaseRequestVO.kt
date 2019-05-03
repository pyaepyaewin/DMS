package com.aceplus.domain.networkrequest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseRequestVO<T>{

    @SerializedName("site_activation_key")
    @Expose
    private var siteActivationKey: String? = null

    @SerializedName("tablet_activation_key")
    @Expose
    private var tabletActivationKey: String? = null

    @SerializedName("user_id")
    @Expose
    private var userId: String? = null

    @SerializedName("password")
    @Expose
    private var password: String? = null

    @SerializedName("route")
    @Expose
    private var route: String? = null

    @SerializedName("data")
    @Expose
    private var data: List<T>? = null

}