package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.SoldProduct
import com.aceplus.domain.model.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.custom_simple_list_item_1.view.*
import kotlinx.android.synthetic.main.list_row_sold_product_with_custom_discount.view.*

class SoldProductViewHolder(
    itemView: View,
    val onLongClickProduct: (data: SoldProduct) -> Unit
) : BaseViewHolder<SoldProductInfo>(itemView) {

    override fun setData(data: SoldProductInfo) {

        itemView.apply {

            name.text = data.product.product_name
            um.text = data.product.um
            qty.text = data.quantity.toString()
            price.text = data.product.selling_price

            FocCheck.isChecked = data.isFocIsChecked
            FocCheck.setOnCheckedChangeListener { compoundButton, isChecked ->  }

        }

    }

}