package com.aceplus.dms.ui.adapters.promotionadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.promotionviewholder.PromotionPriceViewHolder
import com.aceplus.domain.model.promotionDataClass.PromotionPriceDataClass
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class PromotionPriceAdapter:
    BaseRecyclerViewAdapter<PromotionPriceViewHolder, PromotionPriceDataClass>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PromotionPriceViewHolder {
        val view= LayoutInflater.from(p0.context).inflate(R.layout.list_row_promotion_price,p0,false)
        return PromotionPriceViewHolder(view)
    }
}