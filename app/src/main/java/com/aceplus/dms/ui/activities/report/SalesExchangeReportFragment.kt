package com.aceplus.dms.ui.activities.report

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SaleExchangePagerAdapter
import com.aceplus.shared.ui.activities.BaseFragment

class SalesExchangeReportFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.activity_saleexchange_report, container, false)
        val tabLayout = view.findViewById(R.id.saleExchangeTab_layout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Sale Return"))
        tabLayout.addTab(tabLayout.newTab().setText("Sale"))

        val viewPager = view.findViewById(R.id.saleExchangePager) as ViewPager
        val adapter = SaleExchangePagerAdapter(childFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }


            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        return view
    }
}