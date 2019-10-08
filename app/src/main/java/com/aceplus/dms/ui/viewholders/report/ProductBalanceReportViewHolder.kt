package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_product_balance_report.view.*

class ProductBalanceReportViewHolder(private val view: View):BaseViewHolder<com.aceplus.domain.entity.product.Product>(view) {
    override fun setData(data: com.aceplus.domain.entity.product.Product) {
        view.apply {
            productName.text = data.product_name
            totalQuantity.text = data.total_quantity.toString()
            orderQuantity.text = data.order_quantity.toString()
            soldQuantity.text = data.sold_quantity.toString()
            exchangeQuantity.text = data.exchange_quantity.toString()
            returnQuantity.text = data.return_quantity.toString()
            deliveryQuantity.text = data.delivery_quantity.toString()
            presentQuantity.text = data.present_quantity.toString()
            remainingQuantity.text = data.remaining_quantity.toString()
        }
    }
}