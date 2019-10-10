package com.aceplus.dms.ui.adapters.promotionadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.promotionviewholder.ClassDiscountByPriceViewHolder
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByPriceDataClass
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class ClassDiscountByPriceAdapter: BaseRecyclerViewAdapter<ClassDiscountByPriceViewHolder, ClassDiscountByPriceDataClass>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ClassDiscountByPriceViewHolder {
        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.list_row_category_discount_quantity, p0, false)
        return ClassDiscountByPriceViewHolder(view)
    }
}