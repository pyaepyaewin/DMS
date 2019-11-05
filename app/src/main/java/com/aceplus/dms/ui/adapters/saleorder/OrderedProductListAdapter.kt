package com.aceplus.dms.ui.adapters.saleorder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.saleorder.OrderedProductListViewHolder
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class OrderedProductListAdapter(
    private val onLongClickProduct: (data: SoldProductInfo, position: Int) -> Unit,
    private val onFocCheckChange: (data: SoldProductInfo, isChecked: Boolean, position: Int) -> Unit,
    private val onClickQtyButton: (data: SoldProductInfo, position: Int) -> Unit,
    private val isDelivery: Boolean
): BaseRecyclerViewAdapter<OrderedProductListViewHolder, SoldProductInfo>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderedProductListViewHolder {

        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.list_row_sold_product_with_custom_discount, p0, false)

        return OrderedProductListViewHolder(view, onLongClickProduct, onFocCheckChange, onClickQtyButton, isDelivery)

    }

    override fun onBindViewHolder(holder: OrderedProductListViewHolder, position: Int) {

        holder.setData(mDataList[position], position)

    }

}