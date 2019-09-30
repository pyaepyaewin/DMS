package com.example.dms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.data.database.table.InvoiceItem
import com.example.dms.ui.viewholders.CheckOutViewHolder

class CheckOutAdapter(private val itemList:MutableList<InvoiceItem>): RecyclerView.Adapter<CheckOutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckOutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.checkout, parent, false)
        return CheckOutViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CheckOutViewHolder, position: Int) {
        holder.setData(itemList[position])
    }
}