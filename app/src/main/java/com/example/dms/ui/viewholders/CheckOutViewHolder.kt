package com.example.dms.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.data.database.table.InvoiceItem
import kotlinx.android.synthetic.main.checkout.view.*
import kotlin.math.roundToInt

class CheckOutViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
    fun setData(invoiceInvoiceItem:InvoiceItem)
    {
view.product.text=invoiceInvoiceItem.productId
        view.um.text=invoiceInvoiceItem.um
        view.qty.text=invoiceInvoiceItem.qty.toString()
        view.price.text=invoiceInvoiceItem.price
        val salePrice = invoiceInvoiceItem.price.toFloat().roundToInt()
        view.price.text = salePrice.toString()

        val promoPrice = (salePrice - ((salePrice * invoiceInvoiceItem.discount) / 100)).roundToInt()
        view.promo.text = promoPrice.toString()

        val amount = promoPrice * invoiceInvoiceItem.qty
        view.amt.text = amount.toString()


    }
}