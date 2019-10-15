package com.aceplus.dms.ui.adapters.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.sale.SoldProductViewHolder
import com.aceplus.domain.VO.SoldProductInfo
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SoldProductListAdapter(
    private val onLongClickCustomer: (data: SoldProductInfo, position: Int) -> Unit,
    private val onFocCheckChange: (data: SoldProductInfo, isChecked: Boolean, position: Int) -> Unit,
    private val onClickQtyButton: (data: SoldProductInfo, position: Int) -> Unit,
    private val onClickFocButton: (soldProduct: SoldProductInfo, position: Int) -> Unit,
    private val isDelivery: Boolean
) : BaseRecyclerViewAdapter<SoldProductViewHolder, SoldProductInfo>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SoldProductViewHolder {

        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.list_row_sold_product_with_custom_discount, p0, false)
        return SoldProductViewHolder(view, onLongClickCustomer, onFocCheckChange, onClickQtyButton, onClickFocButton, isDelivery)

    }

    override fun onBindViewHolder(holder: SoldProductViewHolder, position: Int) {

        holder.setData(mDataList[position], position)

    }

}