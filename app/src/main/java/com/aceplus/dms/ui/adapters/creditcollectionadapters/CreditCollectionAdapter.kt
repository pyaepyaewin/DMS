package com.aceplus.dms.ui.adapters.creditcollectionadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.creditcollection.CreditColllectionViewHolder
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionDataClass
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class CreditCollectionAdapter(private val onClick: (data: CreditCollectionDataClass) -> Unit):BaseRecyclerViewAdapter<CreditColllectionViewHolder, CreditCollectionDataClass>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CreditColllectionViewHolder {
        val view=LayoutInflater.from(p0.context).inflate(R.layout.customer_credit_list_row,p0,false)
        return CreditColllectionViewHolder(view,onClick)
    }

}