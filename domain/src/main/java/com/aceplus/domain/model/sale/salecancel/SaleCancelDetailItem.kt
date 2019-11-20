package com.aceplus.domain.model.sale.salecancel

data class SaleCancelDetailItem(
    var id: Int = 0,
    val total_amount:Double=0.0,
    var product_id: String,
    var product_name: String,
    var category_id: String,

    var group_id: String?,


    var total_quantity: Int = 0,


    var remaining_quantity: Int = 0,


    var selling_price: String,


    var purchase_price: String,


    var discount_type: String,


    var um: String?,


    var sold_quantity: Int = 0,


    var order_quantity: Int = 0,

    var exchange_quantity: Int = 0,


    var return_quantity: Int = 0,

    var delivery_quantity: Int = 0,


    var present_quantity: Int = 0,

    var device_issue_status: String,

    var class_id: String,
    var promotion_price: Double,
    var sale_quantity: String
) {

}


