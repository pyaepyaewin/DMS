package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.domain.model.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sold_product_with_custom_discount.view.*

class SoldProductViewHolder(
    itemView: View,
    val onLongClickProduct: (data: SoldProductInfo, position: Int) -> Unit,
    val onFocCheckChange: (data: SoldProductInfo, isChecked: Boolean, position: Int) -> Unit,
    val onClickQtyButton: (data: SoldProductInfo, position: Int) -> Unit,
    val onClickFocButton: (soldProduct: SoldProductInfo, position: Int) -> Unit
) : BaseViewHolder<SoldProductInfo>(itemView) {

    override fun setData(data: SoldProductInfo) {

    }

    fun setData(data: SoldProductInfo, position: Int){

        itemView.apply {

            name.text = data.product.product_name
            um.text = data.product.um

            qty.text = data.quantity.toString()
            qty.setOnClickListener { onClickQtyButton(data, position) }

            price.text = data.product.selling_price

            FocCheck.isChecked = data.isFocIsChecked
            FocCheck.setOnClickListener { onFocCheckChange(data, FocCheck.isChecked, position) }

            if (data.isFocTypePercent) btnfocPercent.text = data.focPercent.toString()
            else btnfocPercent.text = data.focAmount.toString()
            btnfocPercent.setOnClickListener { onClickFocButton(data, position) }

            setOnLongClickListener {
                onLongClickProduct(data, position)
                true
            }

        }

    }

}