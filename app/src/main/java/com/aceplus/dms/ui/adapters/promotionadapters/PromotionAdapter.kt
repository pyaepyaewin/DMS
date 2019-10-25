package com.aceplus.dms.ui.adapters.promotionadapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.aceplus.dms.ui.fragments.promotiofragments.*

class PromotionAdapter(fm: FragmentManager, var itemList: List<ViewPagerItem>) :
    FragmentStatePagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment? {
        return itemList[p0].fragment

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return itemList[position].title
    }

    override fun getCount(): Int {
        return itemList.size
    }

}

data class ViewPagerItem(var fragment: Fragment, var title: String)
