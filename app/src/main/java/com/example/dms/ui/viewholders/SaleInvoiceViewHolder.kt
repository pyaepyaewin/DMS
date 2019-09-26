package com.example.dms.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.network.request.saleInvoice
import kotlinx.android.synthetic.main.sales.view.*
import kotlin.math.roundToInt

class SaleInvoiceViewHolder(private val view: View,
                            private val onClickQty: (position: Int, currentQty: Int) -> Unit,
                            private val onClickFoc: (position: Int, currentFoc: Boolean) -> Unit,
                            private val onclickDisc: (position: Int, currentDisc: Float) -> Unit):RecyclerView.ViewHolder(view)
{
    fun setData(saleInvoiceItem: saleInvoice, position: Int){

        view.name.text = saleInvoiceItem.product
        view.um.text = saleInvoiceItem.um

        view.qty.text = saleInvoiceItem.qty.toString()
        view.qty.setOnClickListener { onClickQty(position, saleInvoiceItem.qty) }

        val salePrice = saleInvoiceItem.price.toFloat().roundToInt()
        view.price.text = salePrice.toString()

        val promoPrice = (salePrice - ((salePrice * saleInvoiceItem.discount) / 100)).roundToInt()
        view.promoPrice.text = promoPrice.toString()

        val amount = promoPrice * saleInvoiceItem.qty
        view.amount.text = amount.toString()

        view.chb.isSelected = saleInvoiceItem.foc
        view.chb.setOnCheckedChangeListener { cb, checked ->
            onClickFoc(position, checked)
        }

        view.discount.text = saleInvoiceItem.discount.toString()
        view.discount.setOnClickListener { onclickDisc(position, saleInvoiceItem.discount) }
    }

}