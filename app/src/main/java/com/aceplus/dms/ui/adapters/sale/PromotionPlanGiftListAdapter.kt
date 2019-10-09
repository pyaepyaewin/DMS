package com.aceplus.dms.ui.adapters.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.CustomerViewHolder
import com.aceplus.dms.ui.viewholders.sale.PromotionPlanGiftViewHolder
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class PromotionPlanGiftListAdapter: BaseRecyclerViewAdapter<PromotionPlanGiftViewHolder, Promotion>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PromotionPlanGiftViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_promotion, p0, false)
        return PromotionPlanGiftViewHolder(view)
    }

}