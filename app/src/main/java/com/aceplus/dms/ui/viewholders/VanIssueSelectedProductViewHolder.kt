package com.aceplus.dms.ui.viewholders

import android.view.View
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.van_issue_product_list.view.*

class VanIssueSelectedProductViewHolder(
    itemView: View,
    private val onClickBtnQty: (soldProduct: SoldProductInfo, position: Int) -> Unit
): BaseViewHolder<SoldProductInfo>(itemView) {

    override fun setData(data: SoldProductInfo) { "Notion" }

    fun setData(data: SoldProductInfo, position: Int) {

        itemView.name.text = data.product.product_name
        itemView.um.text = data.product.um
        itemView.rq.text = data.product.remaining_quantity.toString()
        itemView.qty.text = data.quantity.toString()
        itemView.price.text = Utils.formatAmount(data.product.selling_price?.toDouble() ?: 0.0)
        itemView.amount.text = Utils.formatAmount(data.totalAmt)

        itemView.qty.setOnClickListener { onClickBtnQty(data, position) }

    }

}