package com.aceplus.dms.ui.viewholders.creditcollection

import android.graphics.Color
import android.view.View
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.model.credit.CreditInvoice
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.credit_collection_list_row.view.*

class CreditCollectionCheckOutViewHolder(itemView: View,val onClick: (data: Credit) -> Unit):BaseViewHolder<Credit>(itemView){
    override fun setData(data: Credit) {
        itemView.invoice_id.text=data.invoice_no
        itemView.invoice_date.text=data.invoice_date
        val amt:Double=data.amount
        val pay:Double=data.pay_amount
        val unpaid:Double=amt-pay

        itemView.invoice_amount.text=unpaid.toString()

        if (unpaid === 0.0 || pay === amt) {
           itemView.status_txt.setText("Paid")
                //setTextColor("#00aa88")
        }
        itemView.setOnClickListener {
            onClick(data)
        }

    }
}