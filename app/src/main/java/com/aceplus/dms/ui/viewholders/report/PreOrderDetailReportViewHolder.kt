package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.model.report.PreOrderDetailReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_pre_order_product.view.*

class PreOrderDetailReportViewHolder(private val view: View):BaseViewHolder<PreOrderDetailReport>(view) {
    override fun setData(data: PreOrderDetailReport) {
        view.apply {
            productName.text = data.product_name
            quantity.text = data.total_quantity.toString()
            totalAmount.text = data.total_amount

        }
    }
}