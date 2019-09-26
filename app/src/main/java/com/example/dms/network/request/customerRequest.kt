package com.example.dms.network.request

data class customerRequest (
    var site_activation_key:String,
    var tablet_activation_key:String,
    var user_id:String,
    var password:String,
    var route:Int,
    var tablet_key : String,
    var date:String,
    var data:List<String>)
//{
//val customer=customerRequest("1234567","1234567","T1",
//    "YWNlcGx1cw==",4,"2019-08-25","")
//    var gson : Gson = Gson()
//
//        var param : String = gson.toJson(customer)
//}
