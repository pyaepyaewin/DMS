package com.example.dms.network.request

data class saleRequest (
    var site_activation_key:String,
    var tablet_activation_key:String,
    var user_id:String,
    var password:String,
    var route:Int,
    var date : String,
    var data:List<String>,
    var status:String)
{}
