package com.example.dms.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.data.database.table.InvoiceItem
import com.example.dms.data.database.table.InvoiceItemReport
import kotlinx.android.synthetic.main.checkout.view.*


class SaleItemReportViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun setData(invoiceItem: InvoiceItemReport) {
        view.product.text = invoiceItem.Product_name
        view.um.text = invoiceItem.um
        view.qty.text = invoiceItem.qty.toString()
        view.price.text = invoiceItem.price
        view.promo.text = "0"
        var amount = invoiceItem.qty * invoiceItem.price.toInt()
        view.amt.text = amount.toString()


    }
}