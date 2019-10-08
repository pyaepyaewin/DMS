package com.aceplus.dms.ui.viewholders.promotionviewholder

import android.view.View
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountFilterDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_volume_discount_filter.view.*

class VolumeDiscountFilterViewHolder(itemView: View): BaseViewHolder<VolumeDiscountFilterDataClass>(itemView) {
    override fun setData(data: VolumeDiscountFilterDataClass) {
        itemView.row_vd_filter_from_date.text=data.start_date
        itemView.row_vd_filter_to_date.text=data.end_date
        itemView.row_vd_filter_discount_from.text=data.from_sale_amount
        itemView.row_vd_filter_discount_to.text=data.to_sale_amount
        itemView.row_vd_filter_group.text=data.group_code_id
        itemView.row_vd_filter_category.text=data.category_name
        itemView.row_vd_filter_discount_percent.text=data.discount_percent
        itemView.row_vd_filter_discount_amount.text=data.discount_amount
        itemView.row_vd_filter_discount_price.text=data.discount_price
        itemView.row_vd_filter_exclude.text=data.exclude
    }
}