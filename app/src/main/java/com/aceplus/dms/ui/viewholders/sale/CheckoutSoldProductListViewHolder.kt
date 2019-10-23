package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sold_product_checkout.view.*

class CheckoutSoldProductListViewHolder(itemView: View): BaseViewHolder<SoldProductInfo>(itemView) {

    override fun setData(data: SoldProductInfo) {

        itemView.name.text = data.product.product_name
        itemView.um.text = data.product.um
        itemView.qty.text = data.quantity.toString()
        itemView.price.text = Utils.formatAmount(data.product.selling_price!!.toDouble())
        itemView.discount.text = Utils.formatAmount(data.promoPriceByDiscount)
        itemView.amt.text = Utils.formatAmount(data.totalAmt)

    }

}