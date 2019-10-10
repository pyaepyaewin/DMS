package com.aceplus.dms.ui.adapters.promotionadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.promotionviewholder.ClassDiscountByGiftViewHolder
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByGiftDataClass
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class ClassDiscountByGiftAdapter: BaseRecyclerViewAdapter<ClassDiscountByGiftViewHolder, ClassDiscountByGiftDataClass>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ClassDiscountByGiftViewHolder {
        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.list_row_class_dis_gift, p0, false)
        return ClassDiscountByGiftViewHolder(view)

    }
}