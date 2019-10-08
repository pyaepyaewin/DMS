package com.aceplus.dms.viewmodel.report

import android.view.View
import com.aceplus.domain.model.customer.Customer
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.custom_simple_list_item_1.view.*

class CustomerNameViewHolder(private val view: View):BaseViewHolder<Customer> (view){
    override fun setData(data: Customer) {
        view.apply {
            tvCustomerName.text = data.customerName
        }
    }
}