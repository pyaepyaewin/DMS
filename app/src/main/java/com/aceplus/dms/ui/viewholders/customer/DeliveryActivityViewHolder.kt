package com.aceplus.dms.ui.viewholders.customer

import android.view.View
import com.aceplus.domain.vo.customer.DeliveryVO
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_delivery_report.view.*

class DeliveryActivityViewHolder(
    private val view: View,
    private val onClick: (deliver: DeliveryVO) -> Unit
) : BaseViewHolder<DeliveryVO>(view) {
    override fun setData(data: DeliveryVO) {
        view.apply {
            tvCustomerName.text = data.customerName
            totalAmount.text = data.amount.toString()
            orderRemark.text = data.remark
        }
        view.setOnClickListener { onClick(data) }
    }
}