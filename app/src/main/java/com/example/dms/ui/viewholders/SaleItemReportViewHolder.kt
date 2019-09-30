package com.example.dms.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.data.database.table.InvoiceItemReport
import kotlinx.android.synthetic.main.checkout.view.*


class SaleItemReportViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
    fun setData(invoiceItemReport:InvoiceItemReport)
    {
        view.product.text=invoiceItemReport.Product_name
        view.um.text=invoiceItemReport.um
        view.qty.text=invoiceItemReport.qty.toString()
        view.price.text=invoiceItemReport.price.toString()
        view.promo.text=invoiceItemReport.promo_price
        view.amt.text=invoiceItemReport.amount
    }
}