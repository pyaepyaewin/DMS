package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.domain.entity.customer.Customer
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.custom_simple_list_item_1.view.*

class PromotionPlanGiftViewHolder(itemView: View) :
    BaseViewHolder<Customer>(itemView) {

    override fun setData(data: Customer) {
//        itemView.tvCustomerName.text = data.customer_name
    }

}