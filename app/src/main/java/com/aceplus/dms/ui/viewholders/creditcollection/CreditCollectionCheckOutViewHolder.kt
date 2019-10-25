package com.aceplus.dms.ui.viewholders.creditcollection

import android.graphics.Color
import android.view.View
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionCheckoutDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.credit_collection_list_row.view.*

class CreditCollectionCheckOutViewHolder(itemView: View,val onClick: (data: CreditCollectionCheckoutDataClass) -> Unit):BaseViewHolder<CreditCollectionCheckoutDataClass>(itemView){
    override fun setData(data: CreditCollectionCheckoutDataClass) {
        itemView.invoice_id.text=data.invoice_no
        itemView.invoice_date.text=data.invoice_date
        val amt:Double=data.amount.toDouble()
        val pay:Double=data.pay_amount.toDouble()
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