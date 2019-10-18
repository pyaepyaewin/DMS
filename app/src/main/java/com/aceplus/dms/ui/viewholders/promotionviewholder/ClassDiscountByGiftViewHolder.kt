package com.aceplus.dms.ui.viewholders.promotionviewholder

import android.view.View
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByGiftDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_class_dis_gift.view.*

class ClassDiscountByGiftViewHolder(itemView: View) :
    BaseViewHolder<ClassDiscountByGiftDataClass>(itemView) {
    override fun setData(data: ClassDiscountByGiftDataClass) {
        itemView.txt_categoryName.text = data.name
        itemView.txt_fromQty.text = data.from_quantity
        itemView.txt_toQty.text = data.to_quantity
        itemView.txt_fromAmt.text = data.from_amount
        itemView.txt_toAmt.text = data.to_amount
        itemView.txt_product_name.text = data.product_name
        itemView.txt_giftQty.text = data.quantity

    }
}