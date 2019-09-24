package com.example.dms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.network.request.saleInvoice
import com.example.dms.ui.viewholders.PrintViewHolder

class PrintAdapter(private val itemList:MutableList<saleInvoice>): RecyclerView.Adapter<PrintViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrintViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.print, parent, false)
        return PrintViewHolder(view)
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: PrintViewHolder, position: Int) {
        holder.setData(itemList[position], position)

    }
}