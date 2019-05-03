package com.aceplussolutions.rms.ui.adapter

import android.content.Context
import android.support.annotation.NonNull
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import android.view.LayoutInflater
import android.support.v7.widget.RecyclerView
import java.util.*

abstract class BaseRvAdapter<V : BaseViewHolder<O>,O>(context: Context) : RecyclerView.Adapter<V>() {
    protected var mDataList: ArrayList<O>
    protected var mInflater: LayoutInflater

    init {
        mDataList = ArrayList()
        mInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(@NonNull holder: V, position: Int) {
        holder.setData(mDataList[position])
    }

    override fun getItemCount(): Int {
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
