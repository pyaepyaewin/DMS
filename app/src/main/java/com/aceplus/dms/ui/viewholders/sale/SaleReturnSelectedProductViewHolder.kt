package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.listrow_sale_return.view.*

class SaleReturnSelectedProductViewHolder(
    itemView: View,
    private val onClickQtyButton: (soldProduct: SoldProductInfo, position: Int) -> Unit,
    private val onLongClickSoldProductListItem: (soldProduct: SoldProductInfo, position: Int) -> Unit
) : BaseViewHolder<SoldProductInfo>(itemView) {

    override fun setData(data: SoldProductInfo) {

    }

    fun setData(data: SoldProductInfo, position: Int) {

        itemView.name.text = data.product.product_name
        itemView.um.text = data.product.um
        itemView.qty.text = data.quantity.toString()
        itemView.qty.setOnClickListener { onClickQtyButton(data, position) }
        itemView.price.text = data.product.selling_price
        itemView.setOnLongClickListener {
            onLongClickSoldProductListItem(data, position)
            true
        }

    }

}