package com.example.dms.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.network.response.Customer
import kotlinx.android.synthetic.main.customer.view.*

class CustomerViewHolder(private val view: View,
                         private val onClick: (customerData:Customer) -> Unit):RecyclerView.ViewHolder(view) {
    fun setData(data: Customer) {
        view.apply {
            customerName.text = data.CUSTOMER_NAME
            customerAddress.text = data.ADDRESS

            setOnClickListener { onClick(data) }
        }
    }
}