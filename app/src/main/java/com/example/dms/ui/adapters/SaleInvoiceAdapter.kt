package com.example.dms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.data.database.table.InvoiceItem
import com.example.dms.ui.viewholders.SaleInvoiceViewHolder

class SaleInvoiceAdapter(private val onClickQty: (position: Int, currentQty: Int) -> Unit,
                         private val onClickFoc: (position: Int, currentFoc: Boolean) -> Unit,
                         private val onclickDisc: (position: Int, currentDisc: Float) -> Unit,
                         private val calculateNetAmount: () -> Unit):RecyclerView.Adapter<SaleInvoiceViewHolder>() {

    private var itemList: MutableList<InvoiceItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleInvoiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sales, parent, false)
        return SaleInvoiceViewHolder(view,onClickQty,onClickFoc,onclickDisc)
    }

    override fun getItemCount(): Int {
        return itemList.size

    }

    override fun onBindViewHolder(holder: SaleInvoiceViewHolder, position: Int) {
        holder.setData(itemList[position], position)
    }
    fun addRow(selectedItemList: MutableList<InvoiceItem>){
        this.itemList = selectedItemList
        notifyItemInserted(itemList.size - 1)
        calculateNetAmount()
    }

    fun updateRow(selectedItemList: MutableList<InvoiceItem>, position: Int){
        this.itemList = selectedItemList
        notifyItemChanged(position)
        calculateNetAmount()
    }

}