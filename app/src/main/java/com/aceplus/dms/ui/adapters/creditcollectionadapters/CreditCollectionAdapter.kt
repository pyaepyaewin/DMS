package com.aceplus.dms.ui.adapters.creditcollectionadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.creditcollection.CreditCollectionViewHolder
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionDataClass
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class CreditCollectionAdapter(private val onClick: (data: CreditCollectionDataClass) -> Unit):BaseRecyclerViewAdapter<CreditCollectionViewHolder, CreditCollectionDataClass>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CreditCollectionViewHolder {
        val view=LayoutInflater.from(p0.context).inflate(R.layout.customer_credit_list_row,p0,false)
        return CreditCollectionViewHolder(view,onClick)
    }

}