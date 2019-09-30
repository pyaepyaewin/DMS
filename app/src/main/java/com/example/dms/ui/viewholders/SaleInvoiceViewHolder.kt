package com.example.dms.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.data.database.table.InvoiceItem
import kotlinx.android.synthetic.main.sales.view.*
import kotlin.math.roundToInt

class SaleInvoiceViewHolder(private val view: View,
                            private val onClickQty: (position: Int, currentQty: Int) -> Unit,
                            private val onClickFoc: (position: Int, currentFoc: Boolean) -> Unit,
                            private val onclickDisc: (position: Int, currentDisc: Float) -> Unit):RecyclerView.ViewHolder(view)
{
    fun setData(invoiceInvoiceItem: InvoiceItem, position: Int){

        view.name.text = invoiceInvoiceItem.productId
        view.um.text = invoiceInvoiceItem.um

        view.qty.text = invoiceInvoiceItem.qty.toString()
        view.qty.setOnClickListener { onClickQty(position, invoiceInvoiceItem.qty) }

        val salePrice = invoiceInvoiceItem.price.toFloat().roundToInt()
        view.price.text = salePrice.toString()

        val promoPrice = (salePrice - ((salePrice * invoiceInvoiceItem.discount) / 100)).roundToInt()
        view.promoPrice.text = promoPrice.toString()

        val amount = promoPrice * invoiceInvoiceItem.qty
        view.amount.text = amount.toString()

        view.chb.isSelected = invoiceInvoiceItem.foc
        view.chb.setOnCheckedChangeListener { cb, checked ->
            onClickFoc(position, checked)
        }

        view.discount.text = invoiceInvoiceItem.discount.toString()
        view.discount.setOnClickListener { onclickDisc(position, invoiceInvoiceItem.discount) }
    }

}