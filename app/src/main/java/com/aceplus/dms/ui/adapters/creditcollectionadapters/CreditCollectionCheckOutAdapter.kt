package com.aceplus.dms.ui.adapters.creditcollectionadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.creditcollection.CreditCollectionCheckOutViewHolder
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionCheckoutDataClass
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class CreditCollectionCheckOutAdapter(private val onClick: (data: CreditCollectionCheckoutDataClass) -> Unit)
    : BaseRecyclerViewAdapter<CreditCollectionCheckOutViewHolder, CreditCollectionCheckoutDataClass>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CreditCollectionCheckOutViewHolder {
        val view= LayoutInflater.from(p0.context).inflate(R.layout.credit_collection_list_row,p0,false)
        return CreditCollectionCheckOutViewHolder(view,onClick)
    }
}