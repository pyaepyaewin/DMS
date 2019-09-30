package com.example.dms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.data.database.table.InvoiceReport
import com.example.dms.ui.viewholders.ReportViewHolder

class ReportAdapter (private val onClickInvoice: (invoiceID: String) -> Unit): RecyclerView.Adapter<ReportViewHolder>() {
    private var invoiceList = emptyList<InvoiceReport>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.invoice, parent, false)
        return ReportViewHolder(view,onClickInvoice)
    }

    override fun getItemCount(): Int {
        return invoiceList.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.setData(invoiceList[position])
    }
    fun setNewList(reportData: List<InvoiceReport>) {
        this.invoiceList = reportData
        notifyDataSetChanged()
    }
}