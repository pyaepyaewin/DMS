package com.aceplus.dms.ui.viewholders

import android.view.View
import com.aceplus.domain.entity.customer.Customer
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.custom_simple_list_item_1.view.*

class CustomerViewHolder(itemView: View, val onClickCustomer: (data: Customer) -> Unit) :
    BaseViewHolder<Customer>(itemView) {

    override fun setData(data: Customer) {
        var address = data.address ?: ""
        if (data.address!!.length >= 10) {
            address = address.substring(0, 10)
            address += "..."
        }
        val customerInfo = data.customer_name + "(" + address + ")"
        itemView.tvCustomerName.text = customerInfo
        itemView.setOnClickListener { onClickCustomer(data) }
    }

}