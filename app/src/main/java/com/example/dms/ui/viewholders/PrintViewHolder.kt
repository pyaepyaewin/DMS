package com.example.dms.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.data.database.table.InvoiceItem
import kotlinx.android.synthetic.main.print.view.*
import kotlin.math.roundToInt

class PrintViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
//    fun setData(printItem:InvoiceItem)
//    {
//        view.product.text=printItem.productId
//        view.qty.text=printItem.qty.toString()
//        view.price.text=printItem.price
//        val salePrice = printItem.price.toFloat().roundToInt()
//
//        val promoPrice = (salePrice - ((salePrice * printItem.discount) / 100)).roundToInt()
//        val amount = promoPrice * printItem.qty
//        view.amount.text=amount.toString()
//
//
//    }
}