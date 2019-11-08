package com.aceplus.domain.model.sale.salecancel

data class SoldProductDataClass(val product_id:String,
                           val s_price:Double,
                           val p_price:Double,
                           val promotion_price:Double,
                           val sale_quantity:Int,
                           val total_amount:Double,
                           val discount_amount:Double,
                           val product_name:String,
                           val um:String,
                           val remaining_quantity:Int) {
}