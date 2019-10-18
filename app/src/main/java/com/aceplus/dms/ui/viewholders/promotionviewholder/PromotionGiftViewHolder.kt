package com.aceplus.dms.ui.viewholders.promotionviewholder

import android.view.View
import com.aceplus.domain.model.promotionDataClass.PromotionGiftDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder

import kotlinx.android.synthetic.main.list_row_promotion_gift.view.*

class PromotionGiftViewHolder(itemView: View): BaseViewHolder<PromotionGiftDataClass>(itemView)  {
    override fun setData(data: PromotionGiftDataClass) {
        itemView.txt_product_name.text=data.product_name
        itemView.txt_fromQty.text=data.from_quantity
        itemView.txt_toQty.text=data.to_quantity
        itemView.txt_promotion_gift.text=data.product_name
        itemView.txt_promotion_gift_quantity.text=data.quantity
    }
}