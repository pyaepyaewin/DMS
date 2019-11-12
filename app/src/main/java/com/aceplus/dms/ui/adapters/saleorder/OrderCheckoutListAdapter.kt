package com.aceplus.dms.ui.adapters.saleorder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.saleorder.OrderCheckoutListViewHolder
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class OrderCheckoutListAdapter: BaseRecyclerViewAdapter<OrderCheckoutListViewHolder, SoldProductInfo>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderCheckoutListViewHolder {

        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_sold_product_checkout, p0, false)

        return OrderCheckoutListViewHolder(view)

    }

}