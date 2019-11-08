package com.aceplus.dms.ui.adapters.customer

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.customer.DeliveryActivityViewHolder
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.vo.customer.DeliveryVO
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class DeliveryActivityAdapter(private val onClick: (deliver: DeliveryVO) -> Unit):
    BaseRecyclerViewAdapter<DeliveryActivityViewHolder, DeliveryVO>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DeliveryActivityViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_delivery_report,p0,false)
        return DeliveryActivityViewHolder(view,onClick)
    }
}