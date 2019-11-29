package com.aceplus.dms.ui.viewholders.promotionviewholder

import android.view.View
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_volume_discount.view.*

class VolumeDiscountViewHolder (itemView: View): BaseViewHolder<VolumeDiscountDataClass>(itemView)
{
    override fun setData(data: VolumeDiscountDataClass) {
        itemView.row_vd_from_date.text = data.start_date.substring(0, 10)
        itemView.row_vd_to_date.text = data.end_date.substring(0, 10)
        itemView.row_vd_item_discount_from.text = data.from_sale_amount
        itemView.row_vd_item_discount_to.text = data.to_sale_amount
        itemView.row_vd_item_discount_percent.text = data.discount_percent
        itemView.row_vd_item_discount_amount.text = data.discount_amount
        itemView.row_vd_item_discount_price.text = data.discount_price
        if (data.exclude != null) {
            if (data.exclude == "0") {
                itemView.row_vd_volume_discount_exclude.text = "No"
            } else {
                itemView.row_vd_volume_discount_exclude.text = "Yes"

            }

        }
    }
}