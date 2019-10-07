package com.aceplus.dms.ui.adapters.promotionadapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.aceplus.dms.ui.promotiofragments.*

class PromotionAdapter(fm: FragmentManager, var mNumOfTabs: Int) :
    FragmentStatePagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment? {
        when (p0) {
            0 -> return PromotionPriceFragment()
            1 -> return PromotionGiftFragment()
            2 -> return VolumeDiscountFragment()
            3 -> return VolumeDiscountFilterFragment()
            4 -> return CategoryDiscountFragment()
            5 -> return ClassDiscountByPriceFragment()
            6 -> return ClassDiscountByGiftFragment()
            7 -> return ClassDiscountForShowPriceFragment()
            8 -> return ClassDiscountForShowGiftFragment()

            else -> return null

        }
    }
        override fun getCount(): Int {
            return mNumOfTabs
        }

    }
