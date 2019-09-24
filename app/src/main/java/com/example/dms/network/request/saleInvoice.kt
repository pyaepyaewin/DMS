package com.example.dms.network.request

import java.io.Serializable

data class saleInvoice(var id: String,
                  var product: String,
                  var um: String,
                  var qty: Int,
                  var price: String,
                  var foc: Boolean,
                  var discount: Float):Serializable
{

}