package com.aceplus.dms.ui.viewholders.saleorder

import android.view.View
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sold_product_with_custom_discount.view.*

class OrderedProductListViewHolder(
    itemView: View,
    val onLongClickProduct: (data: SoldProductInfo, position: Int) -> Unit,
    val onFocCheckChange: (data: SoldProductInfo, isChecked: Boolean, position: Int) -> Unit,
    val onClickQtyButton: (data: SoldProductInfo, position: Int) -> Unit,
    private val isDelivery: Boolean
): BaseViewHolder<SoldProductInfo>(itemView) {

    override fun setData(data: SoldProductInfo) { "Nothing" }

    fun setData(data: SoldProductInfo, position: Int){

        itemView.apply {

            name.text = data.product.product_name
            um.text = data.product.um
            orderedQuantity.visibility = if (isDelivery) View.VISIBLE else View.GONE

            qty.text = data.quantity.toString()
            qty.setOnClickListener { onClickQtyButton(data, position) }

            val sellingPrice = data.product.selling_price?.toDouble()
            price.text = Utils.formatAmount(sellingPrice)

            var promoPrice = sellingPrice ?: 0.0
            if (data.promotionPrice != 0.0) promoPrice = data.promotionPrice
            promotionPrice.text = Utils.formatAmount(promoPrice)

            amount.text = Utils.formatAmount(data.totalAmt)

            FocCheck.isChecked = data.isFocIsChecked
            FocCheck.setOnClickListener { onFocCheckChange(data, FocCheck.isChecked, position) }

            btnfocPercent.visibility = View.GONE

            // ToDo - if it's delivery
            if (isDelivery) {
                orderedQuantity.text = data.orderedQuantity.toString()
                amount.text = (data.quantity * sellingPrice!!.toInt()).toString()
                qty.isEnabled = false
                FocCheck.isEnabled = false
            }

            setOnLongClickListener {
                onLongClickProduct(data, position)
                true
            }

        }

    }

}