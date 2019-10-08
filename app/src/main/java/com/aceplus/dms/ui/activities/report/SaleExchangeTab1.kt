package com.aceplus.dms.ui.activities.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.shared.ui.activities.BaseFragment

class SaleExchangeTab1 : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_sale_return_report, container, false)
        return view
    }
}