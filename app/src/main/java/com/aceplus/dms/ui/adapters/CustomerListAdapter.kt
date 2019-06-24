package com.aceplus.dms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.CustomerViewHolder
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class CustomerListAdapter(private val onClickCustomer: (data: Customer) -> Unit) : BaseRecyclerViewAdapter<CustomerViewHolder, Customer>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomerViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.custom_simple_list_item_1, p0, false)
        return CustomerViewHolder(view,onClickCustomer)
    }
}