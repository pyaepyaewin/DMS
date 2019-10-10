package com.aceplus.dms.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.promotionadapters.PromotionAdapter
import com.aceplussolutions.rms.ui.activities.BaseActivity

class PromotionActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_promotion


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context,PromotionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabLayout = findViewById(R.id.tab_layout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setText(R.string.promotion_price))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.promotion_gift))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.promotion_volume_discount))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.promotion_volume_discount_filter))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.categorydiscount))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.class_discount_by_price))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.class_discount_by_gift))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.class_discount_for_show_price))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.class_discount_for_show_gift))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.setTabTextColors(Color.WHITE, Color.RED)


        val viewPager = findViewById(R.id.pager) as ViewPager
        val adapter = PromotionAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.setOnTabSelectedListener(object: TabLayout.OnTabSelectedListener
        {
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
