package com.aceplus.dms.ui.fragments.report

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SaleExchangePagerAdapter
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.activity_saleexchange_report.*

class SalesExchangeReportFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.activity_saleexchange_report, container, false)
        saleExchangeTab_layout.addTab(saleExchangeTab_layout.newTab().setText("Sale Return"))
        saleExchangeTab_layout.addTab(saleExchangeTab_layout.newTab().setText("Sale"))
        val adapter = SaleExchangePagerAdapter(childFragmentManager, saleExchangeTab_layout.tabCount)
        saleExchangePager.adapter = adapter
        saleExchangePager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(saleExchangeTab_layout))

        saleExchangeTab_layout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                saleExchangePager.currentItem = tab.position
            }


            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        return view
    }
}