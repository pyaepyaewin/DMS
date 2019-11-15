package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.dms.R
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sold_product_checkout.view.*

class SaleCancelCheckOutViewHolder (itemView: View): BaseViewHolder<SoldProductInfo>(itemView){
    override fun setData(data: SoldProductInfo) {
        itemView.name.text = data.product.product_name
        itemView.um.text = data.product.um
        itemView.qty.text = data.quantity.toString()
        val price=data.product.selling_price!!.toDouble()
        val qty=data.quantity
        val amt:Double=price*qty
        itemView.price.text = Utils.formatAmount(data.product.selling_price!!.toDouble())
        itemView.discount.text = Utils.formatAmount(data.promoPriceByDiscount)
        itemView.amt.text = Utils.formatAmount(amt)
    }
}