package com.aceplus.dms.ui.fragments.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_daily_report_for_sale_man.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class EndOfDayReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    val endOfDayReportViewModel: ReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_report_for_sale_man, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_daily_report_date.text = endOfDayReportViewModel.setCurrentDate()
        endOfDayReportViewModel.endOfDayReportData.observe(this, Observer { it ->
            if (it != null) {
                fragment_daily_report_sale_man.text = it.userName
                fragment_daily_report_route.text = it.routeName
                fragment_daily_report_start_time.text = it.startTime
                fragment_daily_report_end_time.text = it.endTime
                fragment_daily_report_total_exchange.text = it.totalExchange
                fragment_daily_report_total_sale.text = it.totalSales
                fragment_daily_report_total_sale_count.text = it.totalSalesCount
                fragment_daily_report_total_customer.text = it.totalCustomer
                fragment_daily_report_new_customer.text = it.newCustomer
                if (it.totalSalesOrder.isNullOrEmpty()) {
                    fragment_daily_report_total_order_sale.text = "0"
                } else {
                    fragment_daily_report_total_order_sale.text = it.totalSalesOrder
                }
                if (it.totalOrderCount.isNullOrEmpty()) {
                    fragment_daily_report_total_order_count.text = "0"
                } else {
                    fragment_daily_report_total_order_count.text = it.totalOrderCount
                }
                fragment_daily_report_sale_exchange.text = it.totalSalesExchangeOnly
                if (it.totalReturn.isNullOrEmpty()) {
                    fragment_daily_report_total_return.text = "0"
                } else {
                    fragment_daily_report_total_return.text = it.totalReturn
                }
                if (it.totalSalesReturnOnly.isNullOrEmpty()) {
                    fragment_daily_report_sale_return.text = "0"
                } else {
                    fragment_daily_report_sale_return.text = it.totalSalesReturnOnly
                }
                fragment_daily_report_net_cash.text = it.netCash
                fragment_daily_report_total_cash_receipt_count.text = it.totalCashReceiptCount
                fragment_daily_report_total_cash_receipt.text = it.totalCashReceive
                fragment_daily_report_plan_customer.text = it.planCustomer
                fragment_daily_report_not_visited_count.text = it.notVisitedCount
            }
        })

        endOfDayReportViewModel.loadEndOFDayReport()
    }
}