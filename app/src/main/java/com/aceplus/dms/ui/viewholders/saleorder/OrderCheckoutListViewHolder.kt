package com.aceplus.dms.ui.viewholders.saleorder

import android.view.View
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sold_product_checkout.view.*

class OrderCheckoutListViewHolder(itemView: View): BaseViewHolder<SoldProductInfo>(itemView) {

    override fun setData(data: SoldProductInfo) {

        itemView.um.visibility = View.GONE
        itemView.foc.visibility = View.GONE

        itemView.name.text = data.product.product_name
        itemView.qty.text = data.quantity.toString()
        itemView.price.text = Utils.formatAmount(data.product.selling_price?.toDouble() ?: 0.0)
//        itemView.discount.text = Utils.formatAmount(data.promoPriceByDiscount)
//        itemView.amt.text = Utils.formatAmount(data.totalAmt)
        itemView.discount.text = Utils.formatAmount(data.product.selling_price?.toDouble() ?: 0.0)
        itemView.amt.text = (data.quantity * data.product.selling_price!!.toInt()).toString()

    }

}