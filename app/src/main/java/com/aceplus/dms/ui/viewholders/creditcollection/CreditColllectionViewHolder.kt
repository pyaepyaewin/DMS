package com.aceplus.dms.ui.viewholders.creditcollection

import android.view.View
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.customer_credit_list_row.view.*

class CreditColllectionViewHolder(itemView: View,val onClick: (data: CreditCollectionDataClass) -> Unit):BaseViewHolder<CreditCollectionDataClass>(itemView) {
    override fun setData(data: CreditCollectionDataClass) {
        itemView.credit_customer_name.text=data.customer_name
        itemView.credit_totalamt.text= data.amount.toString()
        //var paidAmt:Double=data.pay_amount
        itemView.credit_paidamt.text=data.pay_amount.toString()
        var creditAmt:Double=data.amount
        var unpaidAmt:Double=creditAmt-data.pay_amount!!
        itemView.credit_unpaidamt.text=unpaidAmt.toString()
        itemView.setOnClickListener {
            onClick(data)
        }
    }
}