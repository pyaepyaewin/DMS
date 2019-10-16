package com.aceplus.dms.ui.viewholders.routeviewholders

import android.view.View
import com.aceplus.domain.model.routedataclass.ViewByListDataClass
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_route.view.*

class ViewByListViewHolder(itemView: View): BaseViewHolder<ViewByListDataClass>(itemView) {
    override fun setData(data: ViewByListDataClass) {
        itemView.customer_Name.text=data.customer_name
        itemView.township_name.text=data.township_name
        itemView.tvPhone.text=data.phone
        itemView.tvAddress.text=data.address
    }
}