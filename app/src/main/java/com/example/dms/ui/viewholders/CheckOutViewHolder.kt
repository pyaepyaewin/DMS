package com.example.dms.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.network.request.saleInvoice
import kotlinx.android.synthetic.main.checkout.view.*
import kotlin.math.roundToInt

class CheckOutViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
    fun setData(saleInvoiceItem:saleInvoice,position:Int)
    {
view.product.text=saleInvoiceItem.product
        view.um.text=saleInvoiceItem.um
        view.qty.text=saleInvoiceItem.qty.toString()
        view.price.text=saleInvoiceItem.price
        val salePrice = saleInvoiceItem.price.toFloat().roundToInt()
        view.price.text = salePrice.toString()

        val promoPrice = (salePrice - ((salePrice * saleInvoiceItem.discount) / 100)).roundToInt()
        view.promo.text = promoPrice.toString()

        val amount = promoPrice * saleInvoiceItem.qty
        view.amt.text = amount.toString()


    }
}