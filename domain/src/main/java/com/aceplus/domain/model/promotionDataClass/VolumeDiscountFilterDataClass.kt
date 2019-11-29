package com.aceplus.domain.model.promotionDataClass

class VolumeDiscountFilterDataClass (val start_date:String,
                                     val end_date:String,
                                     val from_sale_amount:String,
                                     val to_sale_amount:String,
                                     val name:String,
                                     val category_name:String,
                                     val discount_percent:String?,
                                     val discount_amount:String?,
                                     val discount_price:String?,
                                     val exclude:String){
}