package com.aceplus.dms.ui.adapters.promotionadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.promotionviewholder.VolumeDiscountViewHolder
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountDataClass
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class VolumeDiscountAdapter: BaseRecyclerViewAdapter<VolumeDiscountViewHolder, VolumeDiscountDataClass>()  {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VolumeDiscountViewHolder {
        val view= LayoutInflater.from(p0.context).inflate(R.layout.list_row_volume_discount,p0,false)
        return VolumeDiscountViewHolder(view)
    }

}