package com.aceplus.dms.ui.viewholders.promotionviewholder

import android.view.View
import com.aceplus.domain.model.promotionDataClass.CategoryDiscountDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_category_discount_quantity.view.*

class CategoryDiscountViewHolder (itemView: View): BaseViewHolder<CategoryDiscountDataClass>(itemView) {
    override fun setData(data: CategoryDiscountDataClass) {
        itemView.txt_categoryName.text=data.category_name
        itemView.txt_fromQty.text=data.from_quantity
        itemView.txt_toQty.text=data.to_quantity
        itemView.txt_discountCategoryPercent.text=data.discount_percent
    }


}