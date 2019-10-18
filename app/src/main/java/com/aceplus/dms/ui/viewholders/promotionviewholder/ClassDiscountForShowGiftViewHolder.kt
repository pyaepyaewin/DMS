package com.aceplus.dms.ui.viewholders.promotionviewholder

import android.view.View
import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowGiftDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_class_dis_gift.view.*

class ClassDiscountForShowGiftViewHolder(itemView: View): BaseViewHolder<ClassDiscountForShowGiftDataClass>(itemView) {
    override fun setData(data: ClassDiscountForShowGiftDataClass) {
        itemView.txt_categoryName.text = data.name.toString()
        itemView.txt_fromQty.text = data.from_quantity
        itemView.txt_toQty.text = data.to_quantity
        itemView.txt_fromAmt.text = data.from_amount
        itemView.txt_toAmt.text = data.to_amount
        itemView.txt_product_name.text = data.name
        itemView.txt_giftQty.text = data.quantity
    }
}