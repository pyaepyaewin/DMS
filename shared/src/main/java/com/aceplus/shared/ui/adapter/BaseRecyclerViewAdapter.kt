package com.aceplus.shared.ui.adapter

import android.support.annotation.NonNull
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlin.collections.ArrayList

abstract class BaseRecyclerViewAdapter<V : BaseViewHolder<O>, O> : RecyclerView.Adapter<V>() {

    protected var mDataList: ArrayList<O>

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

    fun updateList(updatedData: O, position: Int){
        mDataList[position] = updatedData
        notifyItemChanged(position)
    }

    fun removeItem(position: Int){
        mDataList.removeAt(position)
        notifyItemRemoved(position)
    }

}
