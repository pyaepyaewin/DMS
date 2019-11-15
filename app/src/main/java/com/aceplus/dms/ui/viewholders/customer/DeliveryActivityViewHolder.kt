package com.aceplus.dms.ui.viewholders.customer

import android.view.View
import com.aceplus.domain.model.delivery.Deliver
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_delivery_report.view.*

class DeliveryActivityViewHolder(
    private val view: View,
    private val onClick: (deliver: Deliver) -> Unit
) : BaseViewHolder<Deliver>(view) {

    override fun setData(data: Deliver) {
        view.apply {
            tvCustomerName.text = "${data.customerName} (${data.customerAddres})"
            totalAmount.text = data.amount.toString()
            orderRemark.text = data.remark
        }
        view.setOnClickListener { onClick(data) }
    }


}