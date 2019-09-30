package com.example.dms.ui.viewholders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.network.response.Product
import kotlinx.android.synthetic.main.product.view.*

class SaleViewHolder(private val view: View,
                         private val onClick: (saleData: Product) -> Unit):RecyclerView.ViewHolder(view) {
    fun setData(data: Product) {
        view.apply {
            productName.text = data.Product_name
            Log.d("ok",data.Product_name)


            setOnClickListener { onClick(data) }
        }
    }
}