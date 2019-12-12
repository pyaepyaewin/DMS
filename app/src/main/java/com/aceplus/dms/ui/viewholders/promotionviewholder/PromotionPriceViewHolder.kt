package com.aceplus.dms.ui.viewholders.promotionviewholder

import android.view.View
import com.aceplus.dms.utils.Utils


import com.aceplus.domain.model.promotion.PromotionPriceForReport
import com.aceplus.domain.model.promotionDataClass.PromotionPriceDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_promotion_price.view.*

class PromotionPriceViewHolder(itemView: View):BaseViewHolder<PromotionPriceDataClass>(itemView)
 {
    override fun setData(data: PromotionPriceDataClass) {
        itemView.product_name.text=data.product_name
        itemView.fromQty.text=data.from_quantity.toDouble().toInt().toString()
      itemView.toQty.text=data.to_quantity.toDouble().toInt().toString()
        itemView.promotion_price.text=Utils.formatAmount(data.promotion_price?.toDouble())

    }
}



