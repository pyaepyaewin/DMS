package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.SoldProduct
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.custom_simple_list_item_1.view.*
import kotlinx.android.synthetic.main.list_row_sold_product_with_custom_discount.view.*

class SoldProductViewHolder(itemView: View, val onLongClickProduct: (data: SoldProduct) -> Unit) :
    BaseViewHolder<Product>(itemView) {

    override fun setData(data: Product) {
        itemView.apply {

        }
    }

}