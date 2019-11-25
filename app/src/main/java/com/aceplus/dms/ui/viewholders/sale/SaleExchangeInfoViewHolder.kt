package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.product.Product
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.row_sale_exchange_info.view.*

class SaleExchangeInfoViewHolder(itemView: View): BaseViewHolder<Product>(itemView) {

    override fun setData(data: Product) {

        itemView.sx_info_row_name.text = data.product_name
        itemView.sx_info_row_qty.text = data.total_quantity.toString()
        itemView.sx_info_row_price.text = Utils.formatAmount(data.selling_price.toString().toDouble())

    }

}