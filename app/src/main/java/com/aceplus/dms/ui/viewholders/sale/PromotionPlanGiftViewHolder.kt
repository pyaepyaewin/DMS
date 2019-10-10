package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.custom_simple_list_item_1.view.*
import kotlinx.android.synthetic.main.list_row_promotion.view.*

class PromotionPlanGiftViewHolder(itemView: View) : BaseViewHolder<Promotion>(itemView) {

    override fun setData(data: Promotion) {

        itemView.apply {

            price.visibility = View.GONE

            if(!data.name.isNullOrEmpty()) productName.text = data.name
            else productName.visibility = View.GONE

            if (data.promotion_quantity != 0) qty.text = data.promotion_quantity.toString()
            else qty.visibility = View.GONE

        }

    }

}