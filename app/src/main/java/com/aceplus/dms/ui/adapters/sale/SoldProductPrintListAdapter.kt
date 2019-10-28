package com.aceplus.dms.ui.adapters.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.sale.SoldProductPrintListViewHolder
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SoldProductPrintListAdapter: BaseRecyclerViewAdapter<SoldProductPrintListViewHolder, SoldProductInfo>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SoldProductPrintListViewHolder {

        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.list_product_print, p0, false)

        return SoldProductPrintListViewHolder(view)

    }

}