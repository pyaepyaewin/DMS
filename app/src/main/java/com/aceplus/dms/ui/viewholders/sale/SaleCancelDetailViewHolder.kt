package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.dms.R
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sold_product_checkout.view.*

class SaleCancelDetailViewHolder(
    itemView: View, val onClickQtyButton: (data: SoldProductInfo, position: Int) -> Unit,
    val onLongClickSoldProductListItem: (data: SoldProductInfo, position: Int) -> Unit
) : BaseViewHolder<SoldProductInfo>(itemView) {

    override fun setData(data: SoldProductInfo) {
        itemView.apply {
            name.text = data.product.product_name
            um.text = data.product.um
            qty.text = data.quantity.toString()
            itemView.qty.setBackgroundColor(itemView.resources.getColor(R.color.colorAccent))
            qty.setOnClickListener { onClickQtyButton(data, position) }
            price.text = data.product.selling_price
            discount.text = data.promotionPrice.toString()
            val qty: Int = data.quantity
            val price: Double = data.product.selling_price!!.toDouble()
            amt.text = data.totalAmt.toString()

            setOnLongClickListener {
                onLongClickSoldProductListItem(data,adapterPosition)
                true
            }
        }

    }




}




