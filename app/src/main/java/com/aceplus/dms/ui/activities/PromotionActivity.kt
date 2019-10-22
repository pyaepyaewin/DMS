package com.aceplus.dms.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.promotionadapters.PromotionAdapter
import com.aceplus.dms.ui.adapters.promotionadapters.ViewPagerItem
import com.aceplus.dms.ui.fragments.promotiofragments.*
import com.aceplussolutions.rms.ui.activities.BaseActivity

class PromotionActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_promotion


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PromotionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.setTabTextColors(Color.WHITE, Color.RED)
        val tabItemList = mutableListOf<ViewPagerItem>()
        tabItemList.add(ViewPagerItem(PromotionPriceFragment(), getString(R.string.promotion_price)))
        tabItemList.add(ViewPagerItem(PromotionGiftFragment(), getString(R.string.promotion_gift)))
        tabItemList.add(ViewPagerItem(VolumeDiscountFragment(), getString(R.string.promotion_volume_discount)))
        tabItemList.add(ViewPagerItem(VolumeDiscountFilterFragment(), getString(R.string.promotion_volume_discount_filter)))
        tabItemList.add(ViewPagerItem(CategoryDiscountFragment(), getString(R.string.categorydiscount)))
        tabItemList.add(ViewPagerItem(ClassDiscountByPriceFragment(), getString(R.string.class_discount_by_price)))
        tabItemList.add(ViewPagerItem(ClassDiscountByGiftFragment(), getString(R.string.class_discount_by_gift)))
        tabItemList.add(ViewPagerItem(ClassDiscountForShowPriceFragment(), getString(R.string.class_discount_for_show_price)))
        tabItemList.add(ViewPagerItem(ClassDiscountForShowGiftFragment(), getString(R.string.class_discount_for_show_gift)))

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val adapter = PromotionAdapter(supportFragmentManager,tabItemList)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager.setCurrentItem(tabLayout.selectedTabPosition)
            }

        }
        )

    }
}
