package com.example.dms.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.data.database.table.InvoiceReport
import kotlinx.android.synthetic.main.invoice.view.*

class ReportViewHolder (private val view: View,private val onClickInvoice:(invoiceId:String)->Unit): RecyclerView.ViewHolder(view) {
    fun setData(reportItem: InvoiceReport) {
        view.txtInvoiceNo.text = reportItem.id
        view.txtCustomerName.text = reportItem.CUSTOMER_NAME
        view.txtDate.text=reportItem.date
        view.txtDiscPercent.text=reportItem.discountPrice
        view.txtDiscAmt.text=reportItem.discountAmount
        view.setOnClickListener{
            onClickInvoice(reportItem.id)
        }
    }
}
