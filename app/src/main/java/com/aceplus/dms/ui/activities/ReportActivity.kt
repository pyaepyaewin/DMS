package com.aceplus.dms.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.fragments.report.*
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
        , "Deliver Report"
        , "Pre-order Report"
        , "Target & Actual Sales For Customer Report"
        , "Target & Actual Sales For SalesMan Report"
        , "Target & Actual Sales Product Report"
        , "Sales History Report"
        , "Sales Order History Report"
        , "Sales Visit History Report"
        , "End of Day Report"

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val reportsSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, report)
        reportsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        reports.adapter = reportsSpinnerAdapter
        reports.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var fragment = when (p2) {
                    0 -> ProductBalanceReportFragment()
                    1 -> SaleInvoiceReportFragment()
                    2 -> SalesCancelReportFragment()
                    3 -> UnsellReasonReportFragment()
                    4 -> SalesReturnReportFragment()
                    5 -> SalesExchangeReportFragment()
                    6 -> DeliverReportFragment()
                    7 -> PreOrderReportFragment()
                    8 -> TargetAndActualSalesForCustomerReportFragment()
                    9 -> TargetAndActualSalesForSalesManReportFragment()
                    10 -> TargetAndActualSalesForProductReportFragment()
                    11 -> SalesHistoryReportFragment()
                    12 -> SalesOrderHistoryReportFragment()
                    13 -> SalesVisitHistoryReportFragment()
                    else -> {
                        print_img.visibility = View.VISIBLE
                        EndOfDayReportFragment()
                    }
                }
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_report, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        cancel_img.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        startActivityForResult(intent, Constant.RQC_BACK_TO_HOME)
        finish()
    }

}
