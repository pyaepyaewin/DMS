package com.aceplus.dms.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.*
import android.widget.ArrayAdapter
import com.aceplus.dms.R
import com.aceplus.dms.ui.fragments.routefragments.ViewByListFragment
import com.aceplus.dms.ui.fragments.routefragments.ViewByMapFragment
import com.aceplus.dms.ui.fragments.routefragments.eCalendarFragment
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.model.route.Route
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_route.*

class RouteActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_route
    var routes =
        arrayOf("e-Route : View By Map", "e-Route : View By List", "e-Calendar")


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RouteActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cancel_img.setOnClickListener {
            startActivity(MainActivity.newIntent(this))
        }

        val spinnerAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRoute.adapter = spinnerAdapter
        spinnerRoute.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var frag = when (position) {
                    0 -> ViewByMapFragment()
                    1 -> ViewByListFragment()
                    2 -> eCalendarFragment()

                    else -> null
                }


                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayoutRoute, frag!!)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}

















