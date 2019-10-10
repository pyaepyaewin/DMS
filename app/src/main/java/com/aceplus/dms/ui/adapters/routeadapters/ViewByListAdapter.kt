package com.aceplus.dms.ui.adapters.routeadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.routeviewholders.ViewByListViewHolder
import com.aceplus.domain.model.routedataclass.ViewByListDataClass
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class ViewByListAdapter: BaseRecyclerViewAdapter<ViewByListViewHolder, ViewByListDataClass>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewByListViewHolder {
        val view= LayoutInflater.from(p0.context).inflate(R.layout.list_row_route,p0,false)
        return ViewByListViewHolder(view)
    }
}