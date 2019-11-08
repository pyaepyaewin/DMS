package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sold_product_checkout.view.*

class SaleCancelDetailViewHolder(
    itemView: View

) : BaseViewHolder<SoldProductInfo>(itemView) {

    override fun setData(data: SoldProductInfo) {
        itemView.apply {
            name.text = data.product.product_name
            um.text = data.product.um
            //  qty.text = data.quantity.toString()
            price.text = data.product.selling_price
            //  discount.text = data.promotionPrice.toString()
            //  val qty: Int = data.quantity
            val price: Double = data.product.selling_price!!.toDouble()
            //  val amt1 = qty * price
            //  amt.text=amt1.toString()

            //setOnLongClickListener {
            // onLongClickProduct(data, position)
            // true
        }

    }

    fun setData(data: SoldProductInfo, position: Int) {


    }
}



