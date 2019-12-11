package com.aceplus.dms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.VanIssueSelectedProductViewHolder
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class VanIssueProductListAdapter(
    private val onClickBtnQty: (soldProduct: SoldProductInfo, position: Int) -> Unit,
    private val onLongClickSoldProductListItem: (soldProduct: SoldProductInfo, position: Int) -> Unit
): BaseRecyclerViewAdapter<VanIssueSelectedProductViewHolder, SoldProductInfo>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VanIssueSelectedProductViewHolder {

        val view = LayoutInflater.from(p0.context).inflate(R.layout.van_issue_product_list, p0, false)
        return VanIssueSelectedProductViewHolder(view, onClickBtnQty, onLongClickSoldProductListItem)

    }

    override fun onBindViewHolder(holder: VanIssueSelectedProductViewHolder, position: Int) {

        holder.setData(mDataList[position], position)

    }

}