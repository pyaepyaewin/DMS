package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_product_print.view.*
import java.text.DecimalFormat

class SoldProductPrintListViewHolder(itemView: View, private val printMode: String): BaseViewHolder<SoldProductInfo>(itemView){

    override fun setData(data: SoldProductInfo) {

        itemView.name.text = data.product.product_name
        itemView.qty.text = data.quantity.toString()

        var price = data.product.selling_price?.toDouble() ?: 0.0
        if (data.promotionPrice != 0.0) price = data.promotionPrice
        itemView.price.text = Utils.formatAmount(price)

        if (data.focAmount > 0 || data.focPercent > 0){

            itemView.rl_discountItem.visibility = View.VISIBLE
            var discount = ""
            if (data.focPercent > 0) discount = "----- ${data.focPercent}% -----"
            if (data.focAmount > 0) discount = "----- ${data.focAmount} -----"
            if (data.focAmount > 0 && data.focPercent > 0){
                val df = DecimalFormat(".##")
                discount = "----- ${df.format(data.focPercent)} -----"
            }

            itemView.tv_discountTxt.text = discount
            var disAmt = data.itemDiscountAmount * data.quantity
            itemView.tv_dis_amt.text = disAmt.toString()

        } else{
            itemView.rl_discountItem.visibility = View.GONE
        }

        itemView.amt.text = Utils.formatAmount(data.totalAmt)

        if (printMode == "SR")
            itemView.amt.text = Utils.formatAmount(price * data.quantity)


    }

}