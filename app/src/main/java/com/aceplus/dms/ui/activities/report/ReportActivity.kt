package com.aceplus.dms.ui.activities.report

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.aceplus.dms.R
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_report

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ReportActivity::class.java)
        }
    }

    val report: Array<String> = arrayOf(
        "Product Balance Report"
        , "Sales Invoice Report"
        , "Sales Cancel Report"
        , "Unsell Reason Report"
        , "Sales Return Report"
        , "Sales Exchange Report"
//                , "POSM Report"
        , "Deliver Report"
        , "Sales Order Report"
        , "Indirect Sales Report"
        , "Target & Actual Sales For Customer Report"
        , "Target & Actual Sales For SalesMan Report"
        , "Target & Actual Sales Product Report"
//                , "Display Program Report"
//                , "Incentive Program Report"
//                , "Size And Stock Report"
        , "Sales History Report"
        , "Sales Order History Report"
        , "Indirect Sales History Report"
        , "Sales Visit History Report"
        , "End of Day Report"

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val reportsSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, report)
        reportsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        reports.adapter = reportsSpinnerAdapter
        reports.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var fragment = when (p2) {
                    0 -> ProductBalanceReportFragment()
                    1 -> SaleInvoiceReportFragment()
                    2 -> SalesCancelReportFragment()
                    3 -> UnsellReasonReportFragment()
                    4 -> SalesReturnReportFragment()
                    else -> ProductBalanceReportFragment()
                }
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_report, fragment!!)
                transaction.addToBackStack(null)
                transaction.commit()
            }

        }
    }

}
