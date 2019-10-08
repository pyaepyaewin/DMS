package com.aceplus.shared.ui.adapter

import android.support.annotation.NonNull
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import android.support.v7.widget.RecyclerView
import android.util.Log
import java.util.*

abstract class BaseRecyclerViewAdapter<V : BaseViewHolder<O>,O> : RecyclerView.Adapter<V>() {
    protected var mDataList: ArrayList<O>

    init {
        mDataList = ArrayList()
    }

    override fun onBindViewHolder(@NonNull holder: V, position: Int) {
        holder.setData(mDataList[position])
    }

    override fun getItemCount(): Int {
        Log.d("Name Size","${mDataList.size}")
        return mDataList.size
    }

    fun addNewItem(myItem:O){
        mDataList.add(myItem)
        notifyDataSetChanged()
    }

    fun setNewList(newList: ArrayList<O>) {
        mDataList = newList
        notifyDataSetChanged()
    }

    fun getDataList():List<O>{
        return mDataList
    }
}
