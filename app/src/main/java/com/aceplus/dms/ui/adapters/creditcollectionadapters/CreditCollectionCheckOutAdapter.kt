package com.aceplus.dms.ui.adapters.creditcollectionadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.creditcollection.CreditCollectionCheckOutViewHolder
import com.aceplus.dms.ui.viewholders.saleorder.OrderedProductListViewHolder
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.model.credit.CreditInvoice
import com.aceplus.domain.model.customer.CustomerCredit
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class CreditCollectionCheckOutAdapter(private val onClick: (data: Credit,position:Int,id:String) -> Unit)
    : BaseRecyclerViewAdapter<CreditCollectionCheckOutViewHolder, Credit>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CreditCollectionCheckOutViewHolder {
        val view= LayoutInflater.from(p0.context).inflate(R.layout.credit_collection_list_row,p0,false)
        return CreditCollectionCheckOutViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: CreditCollectionCheckOutViewHolder, position: Int) {
        holder.setData(mDataList[position])
    }
}