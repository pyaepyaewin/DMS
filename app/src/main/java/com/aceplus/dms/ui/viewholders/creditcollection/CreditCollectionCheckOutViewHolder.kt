package com.aceplus.dms.ui.viewholders.creditcollection

import android.view.View
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionCheckoutDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.credit_collection_list_row.view.*

class CreditCollectionCheckOutViewHolder(itemView: View,val onClick: (data: CreditCollectionCheckoutDataClass) -> Unit):BaseViewHolder<CreditCollectionCheckoutDataClass>(itemView){
    override fun setData(data: CreditCollectionCheckoutDataClass) {
        itemView.invoice_id.text=data.invoice_id
        itemView.invoice_date.text=data.invoice_date
        itemView.invoice_amount.text=data.amount



    }
}