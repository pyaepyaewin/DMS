package com.aceplus.shared.ui.adapter

import android.content.Context
import android.support.annotation.NonNull
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import android.view.LayoutInflater
import android.support.v7.widget.RecyclerView
import java.util.*

abstract class BaseRecyclerViewAdapter<V : BaseViewHolder<O>, O> : RecyclerView.Adapter<V>() {

    private var mDataList: ArrayList<O>

    init {
        mDataList = ArrayList()
    }

    override fun onBindViewHolder(@NonNull holder: V, position: Int) {
        holder.setData(mDataList[position])
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun addNewItem(myItem: O) {
        mDataList.add(myItem)
        notifyItemInserted(mDataList.size - 1)
    }

    fun setNewList(newList: ArrayList<O>) {
        mDataList = newList
        notifyDataSetChanged()
    }

    fun getDataList(): List<O> {
        return mDataList
    }

}
