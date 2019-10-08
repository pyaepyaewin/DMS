package com.aceplus.dms.ui.adapters.promotionadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.promotionviewholder.PromotionGiftViewHolder
import com.aceplus.domain.model.promotionDataClass.PromotionGiftDataClass
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class PromotionGiftAdapter: BaseRecyclerViewAdapter<PromotionGiftViewHolder, PromotionGiftDataClass>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PromotionGiftViewHolder {
        val view= LayoutInflater.from(p0.context).inflate(R.layout.list_row_promotion_gift,p0,false)
        return PromotionGiftViewHolder(view)
    }
}