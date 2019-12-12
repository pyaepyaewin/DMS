package com.aceplus.dms.ui.viewholders.promotionviewholder

import android.view.View
import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowPriceDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_category_discount_quantity.view.*

class ClassDiscountForShowPriceViewHolder(itemView: View): BaseViewHolder<ClassDiscountForShowPriceDataClass>(itemView) {
    override fun setData(data: ClassDiscountForShowPriceDataClass) {

            itemView.txt_categoryName.text = data.name
            itemView.txt_fromQty.text = data.from_quantity.toInt().toString()
            itemView.txt_toQty.text = data.to_quantity.toInt().toString()
            itemView.txt_fromAmt.text = data.from_amount
            itemView.txt_toAmt.text = data.to_amount
            itemView.txt_discountCategoryPercent.text = data.discount_percent

    }
}