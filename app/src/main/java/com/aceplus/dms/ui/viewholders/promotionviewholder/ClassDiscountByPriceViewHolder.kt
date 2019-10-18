package com.aceplus.dms.ui.viewholders.promotionviewholder

import android.view.View
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByPriceDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_category_discount_quantity.view.*
import kotlinx.android.synthetic.main.tab_fragment_category_discount_quantity.view.*

class ClassDiscountByPriceViewHolder(itemView: View) :
    BaseViewHolder<ClassDiscountByPriceDataClass>(itemView) {
    override fun setData(data: ClassDiscountByPriceDataClass) {
        itemView.txt_categoryName.text = data.class_id.toString()
        itemView.txt_fromQty.text = data.from_quantity
        itemView.txt_toQty.text = data.to_quantity
        itemView.txt_fromAmt.text = data.from_amount
        itemView.txt_toAmt.text = data.to_amount
        itemView.txt_discountCategoryPercent.text = data.discount_percent

    }
}