package com.example.dms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.network.response.Customer
import com.example.dms.ui.viewholders.CustomerViewHolder

class CustomerAdapter(private val onClick: (notice: Customer) -> Unit) : RecyclerView.Adapter<CustomerViewHolder>()
{
    private var customerDataList = emptyList<Customer>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.customer, parent, false)
        return CustomerViewHolder(view = view, onClick = onClick)
    }

    override fun getItemCount(): Int {
        return customerDataList.size
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.setData(customerDataList[position])
    }
    fun setNewList(customerData: List<Customer>) {
        this.customerDataList = customerData
        notifyDataSetChanged()
    }
}