package com.example.dms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.data.database.table.Product
import com.example.dms.ui.viewholders.SaleViewHolder

class SaleAdapter(private val onClick: (notice: Product) -> Unit) : RecyclerView.Adapter<SaleViewHolder>()

{
    private var saleDataList = emptyList<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product, parent, false)
        return SaleViewHolder(view = view, onClick = onClick)
    }

    override fun getItemCount(): Int {
        return saleDataList.size
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        holder.setData(saleDataList[position])
    }
    fun setData(saleData: List<Product>) {
        this.saleDataList = saleData
        notifyDataSetChanged()
    }

}