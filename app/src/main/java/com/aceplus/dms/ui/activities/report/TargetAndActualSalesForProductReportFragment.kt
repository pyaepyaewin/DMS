package com.aceplus.dms.ui.activities.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.shared.ui.activities.BaseFragment

class TargetAndActualSalesForProductReportFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_product_report,container, false)
    }

}