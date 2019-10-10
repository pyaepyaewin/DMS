package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.dms.R
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_product_balance_report.view.*

class ProductBalanceReportViewHolder(private val view: View) :
    BaseViewHolder<com.aceplus.domain.entity.product.Product>(view) {
    override fun setData(data: com.aceplus.domain.entity.product.Product) {
        view.apply {
            if (data.total_quantity == 0 && data.order_quantity == 0 &&
                data.sold_quantity == 0 && data.exchange_quantity == 0 &&
                data.return_quantity == 0 && data.delivery_quantity == 0 &&
                data.present_quantity == 0 && data.remaining_quantity == 0
            ) {
                productName.setTextColor(resources.getColor(R.color.accentColor))
                totalQuantity.setTextColor(resources.getColor(R.color.accentColor))
                orderQuantity.setTextColor(resources.getColor(R.color.accentColor))
                soldQuantity.setTextColor(resources.getColor(R.color.accentColor))
                exchangeQuantity.setTextColor(resources.getColor(R.color.accentColor))
                returnQuantity.setTextColor(resources.getColor(R.color.accentColor))
                deliveryQuantity.setTextColor(resources.getColor(R.color.accentColor))
                presentQuantity.setTextColor(resources.getColor(R.color.accentColor))
                remainingQuantity.setTextColor(resources.getColor(R.color.accentColor))
            }
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