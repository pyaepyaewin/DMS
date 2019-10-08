package com.aceplus.dms.ui.adapters.promotionadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.promotionviewholder.VolumeDiscountFilterViewHolder
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountFilterDataClass
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class VolumeDiscountFilterAdapter:BaseRecyclerViewAdapter<VolumeDiscountFilterViewHolder, VolumeDiscountFilterDataClass>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VolumeDiscountFilterViewHolder {
        val view =
        LayoutInflater.from(p0.context).inflate(R.layout.list_row_volume_discount_filter, p0, false)
        return VolumeDiscountFilterViewHolder(view)
    }

}