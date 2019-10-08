package com.aceplus.dms.ui.adapters.report

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.aceplus.dms.ui.activities.report.SaleExchangeTab1
import com.aceplus.dms.ui.activities.report.SaleExchangeTab2

class SaleExchangePagerAdapter(fm: FragmentManager, NoofTabs: Int): FragmentStatePagerAdapter(fm) {
    var NoofTabs:Int = NoofTabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return SaleExchangeTab1()
            }
            1 -> {
                return SaleExchangeTab2()
            }

            else -> return SaleExchangeTab1()
        }

    }

    override fun getCount(): Int {
       return NoofTabs
    }
}