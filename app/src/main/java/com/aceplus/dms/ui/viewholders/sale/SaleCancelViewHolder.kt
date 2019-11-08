package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.row_sale_cancel_activity.view.*

class SaleCancelViewHolder(itemView: View, val onClick: (data: SaleCancelItem) -> Unit) :BaseViewHolder<SaleCancelItem>(itemView){
    override fun setData(data: SaleCancelItem) {
        itemView.row_sale_cancel_customer_name.text=data.customer_name
        itemView.row_sale_cancel_sale_invoice.text=data.invoice_id
        itemView.row_sale_cancel_date.text=data.sale_date
        itemView.row_sale_cancel_total_qty.text=data.total_quantity.toString()
        itemView.row_sale_cancel_total_sale_amt.text=data.total_amount.toString()
        itemView.setOnClickListener {
            onClick(data)
        }
    }
}