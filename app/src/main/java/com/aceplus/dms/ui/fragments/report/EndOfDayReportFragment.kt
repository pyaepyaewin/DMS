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
        //sale man name list
        endOfDayReportViewModel.saleManDataList.observe(this, Observer {
            for (i in it!!) {
                fragment_daily_report_sale_man.text = i.user_name
            }
        })
        //route name list
        endOfDayReportViewModel.saleManRouteNameDataList.observe(this, Observer {
            for (i in it!!) {
                fragment_daily_report_route.text = i.route_name
            }
        })
        //start time and end time list
        endOfDayReportViewModel.startTimeAndEndTimeList.observe(this, Observer {
            for (i in it!!) {
                fragment_daily_report_start_time.text = i.start_time
                fragment_daily_report_end_time.text = i.end_time
            }
        })
        //get total exchange and sale count from Pair List
        endOfDayReportViewModel.saleTargetAndSaleManReportSuccessState.observe(this, Observer {
            if (it != null) {
                var saleAmount = 0.0
                var exchangeAmount = 0.0
                for (i in it!!.second) {
                    saleAmount += i.total_amount!!.toDouble()
                    exchangeAmount += i.pay_amount!!.toDouble()
                }
                fragment_daily_report_total_exchange.text = exchangeAmount.toString()
                fragment_daily_report_total_sale.text = saleAmount.toString()
                fragment_daily_report_total_sale_count.text = it!!.second.size.toString()
            } else {
                fragment_daily_report_total_sale_count.text = "0"
            }
        })
        //customer list
        endOfDayReportViewModel.customerDataList.observe(this, Observer {
            if (it != null) {
                fragment_daily_report_total_customer.text = it!!.size.toString()
            } else {
                fragment_daily_report_total_customer.text = "0"
            }
        })
        //new customer list
        endOfDayReportViewModel.dataForNewCustomerList.observe(this, Observer {
            fragment_daily_report_new_customer.text = it!!.size.toString()
        })
        //total sale order list
        endOfDayReportViewModel.totalSaleOrderList.observe(this, Observer {
            var orderAmount = 0
            if (it != null) {
                for (i in it!!) {
                    orderAmount += i.net_amount!!.toInt()
                }
                fragment_daily_report_total_order_sale.text = orderAmount.toString()
                fragment_daily_report_total_order_count.text = it.size.toString()
            } else {
                fragment_daily_report_total_order_count.text = "0"
                fragment_daily_report_total_order_sale.text = "0"
            }
        })
        //total sale exchange list
        endOfDayReportViewModel.totalSaleExchangeList.observe(this, Observer {
            if (it != null) {
                fragment_daily_report_sale_exchange.text = it.size.toString()
            } else {
                fragment_daily_report_sale_exchange.text = "0"
            }
        })
        //total sale return list
        endOfDayReportViewModel.totalSaleReturnList.observe(this, Observer {
            var returnAmount = 0
            if (it != null) {
                for (i in it!!) {
                    returnAmount += i.pay_amount.toInt()
                }
                fragment_daily_report_total_return.text = returnAmount.toString()
                fragment_daily_report_sale_return.text = it.size.toString()
            } else {
                fragment_daily_report_total_return.text = "0"
                fragment_daily_report_sale_return.text = "0"
            }
        })
        //total cash receive list
        endOfDayReportViewModel.totalCashReceiptList.observe(this, Observer {
            if (it != null) {
                var netCash = 0
                for (i in it!!) {
                    netCash += i.pay_amount!!.toInt()
                }
                fragment_daily_report_net_cash.text = netCash.toString()
                fragment_daily_report_total_cash_receipt_count.text = it.size.toString()
                fragment_daily_report_total_cash_receipt.text = netCash.toString()
            } else {
                fragment_daily_report_total_cash_receipt.text = "0"
                fragment_daily_report_total_cash_receipt_count.text = "0"
            }
        })
        //plan customer list
        endOfDayReportViewModel.planCustomerList.observe(this, Observer {
            if (it != null) {
                fragment_daily_report_plan_customer.text = it.size.toString()
            }
        })
        //not visited count list
        endOfDayReportViewModel.dataNotVisitedCountList.observe(this, Observer {
            if (it != null) {
                fragment_daily_report_not_visited_count.text = it.size.toString()
            }
        })
        endOfDayReportViewModel.loadSaleManList()
        endOfDayReportViewModel.loadSaleManRouteNameDataList()
        endOfDayReportViewModel.loadStartTimeAndEndTimeList()
        endOfDayReportViewModel.loadSaleTargetAndSaleManReport()
        endOfDayReportViewModel.loadCustomer()
        endOfDayReportViewModel.loadTotalSaleOrderList()
        endOfDayReportViewModel.loadTotalSaleExchangeList()
        endOfDayReportViewModel.loadTotalSaleReturnList()
        endOfDayReportViewModel.loadTotalCashReceiptList()
        endOfDayReportViewModel.loadPlanCustomerList()
        endOfDayReportViewModel.loadDataForNewCustomerList()
        endOfDayReportViewModel.loadDataNotVisitedCountList()
    }
}