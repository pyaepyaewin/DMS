package com.example.dms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.InvoiceItemReport
import com.example.dms.ui.viewholders.SaleItemReportViewHolder

class SaleItemReportAdapter: RecyclerView.Adapter<SaleItemReportViewHolder>() {

    private var itemList: List<InvoiceItemReport> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleItemReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.checkout, parent, false)
        return SaleItemReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: SaleItemReportViewHolder, position: Int) {
        holder.setData(itemList[position])
    }

    fun setData(list: List<InvoiceItemReport>){
        this.itemList = list
        notifyDataSetChanged()
    }

}