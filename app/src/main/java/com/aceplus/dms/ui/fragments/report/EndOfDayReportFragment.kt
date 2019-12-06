package com.aceplus.dms.ui.fragments.report

import android.app.Activity
import android.arch.lifecycle.Observer
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.DeviceListActivity
import com.aceplus.dms.utils.BluetoothService
import com.aceplus.dms.utils.PrintUtils
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.model.sale.SaleManDailyReport
import com.aceplus.shared.ui.activities.BaseFragment
import com.aceplussolutions.rms.constants.AppUtils
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.fragment_daily_report_for_sale_man.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.sql.Date
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class EndOfDayReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val endOfDayReportViewModel: ReportViewModel by viewModel()
    private var startTime = ""
    private var getStartTime = ""
    private var endTime = ""
    private var totalCustomer = 0
    private var totalPlanCustomer = 0
    private var newCustomer = 0
    private var saleCount = 0
    private var orderCount = 0
    private var saleReturnCount = 0
    private var saleExchangeCount = 0
    private var cashReceiptCount = 0
    private var totalNotVisitedCount = 0
    private var totalSaleAmt: Double = 0.0
    private var totalPayAmt:Double = 0.0
    private var netAmt:Double = 0.0
    private var totalCashReceive:Double = 0.0
    private var totalOrderAmt:Double = 0.toDouble()
    private var amtArr: Array<Double>? = null
    // Intent request codes
    private val REQUEST_CONNECT_DEVICE = 1
    private val REQUEST_ENABLE_BT = 2
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mService: BluetoothService? = null
    // Debugging
    private val TAG = "Main_Activity"
    private val DEBUG = true
    internal var saleManDailyReport: SaleManDailyReport? = null
    // Message types sent from the BluetoothService Handler
    private val MESSAGE_STATE_CHANGE = 1
    private val MESSAGE_READ = 2
    private val MESSAGE_WRITE = 3
    private val MESSAGE_DEVICE_NAME = 4
    private val MESSAGE_TOAST = 5
    private val MESSAGE_CONNECTION_LOST = 6
    private val MESSAGE_UNABLE_CONNECT = 7
    // Key names received from the BluetoothService Handler
    val DEVICE_NAME = "device_name"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_report_for_sale_man, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        //Set Up UI
        fragment_daily_report_sale_man.text = AppUtils.getStringFromShp(Constant.SALEMAN_NAME, activity!!)
        val saleManId = AppUtils.getStringFromShp(Constant.SALEMAN_ID,activity!!)!!.toInt()
        startTime = AppUtils.getStringFromShp(Constant.START_TIME,activity!!)!!
        if (saleManId != null){
            endOfDayReportViewModel.routeNameForEndOfDayReport.observe(this, Observer {
                for (i in it!!) {
                    fragment_daily_report_route.text  = i.route_name!!
                }

            })
            endOfDayReportViewModel.loadRouteNameForEndOfDayReport(saleManId.toString())
        }
        fragment_daily_report_date.text = endOfDayReportViewModel.setCurrentDate()
        fragment_daily_report_end_time.text = endOfDayReportViewModel.setEndTime()
        endTime = endOfDayReportViewModel.setEndTime()
        //......................//
        endOfDayReportViewModel.endOfDayReportData.observe(this, Observer { it ->
            fragment_daily_report_total_sale.text = Utils.formatAmount(it!!.totalPayAmt)
            totalCustomer = it.customerCount
            totalPlanCustomer = it.planCustomerCount
            newCustomer = it.newCustomerCount
            saleCount = it.totalSaleCount
            saleReturnCount = it.saleReturnCount
            saleExchangeCount = it.saleExchangeCount
            cashReceiptCount = it.totalCashCount
            orderCount = it.preOrderList.size
            totalCashReceive = it.totalCashAmount
            amtArr = it.amtArr
            totalNotVisitedCount = it.notVisitCount
            totalSaleAmt = it.totalPayAmt
            getStartTime = it.startTime
            var advancePaymentAmount = 0
            for (i in it!!.preOrderList){
                advancePaymentAmount += i.advance_payment_amount!!.toDouble().roundToInt()
            }
            totalOrderAmt = advancePaymentAmount.toDouble()
            netAmt = (it.totalPayAmt + advancePaymentAmount + it.totalCashAmount - it.amtArr[0] - it.amtArr[1])
            fragment_daily_report_start_time.text = getStartTime
            fragment_daily_report_total_order_sale.text = advancePaymentAmount.toString()
            fragment_daily_report_total_order_count.text = (it!!.preOrderList.size).toString()
            fragment_daily_report_total_return.text = Utils.formatAmount(it.amtArr[0])
            fragment_daily_report_total_exchange.text = Utils.formatAmount(it.amtArr[0])
            fragment_daily_report_sale_return.text = it!!.saleReturnCount.toString()
            fragment_daily_report_sale_exchange.text = it.saleExchangeCount.toString()
            fragment_daily_report_total_cash_receipt.text = it.totalCashAmount.toString()
            fragment_daily_report_total_cash_receipt_count.text = it.totalCashCount.toString()
            fragment_daily_report_net_cash.text = (it.totalPayAmt + advancePaymentAmount + it.totalCashAmount - it.amtArr[0] - it.amtArr[1]).toString()
            fragment_daily_report_total_sale_count.text = it.totalSaleCount.toString()
            fragment_daily_report_total_customer.text = it.customerCount.toString()
            fragment_daily_report_new_customer.text = it.newCustomerCount.toString()
            fragment_daily_report_plan_customer.text = it.planCustomerCount.toString()
            fragment_daily_report_not_visited_count.text = it.notVisitCount.toString()
        })
        endOfDayReportViewModel.loadEndOFDayReport(sdf.format(java.util.Date()))

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            activity!!.finish()
        } else {
            if (!mBluetoothAdapter!!.isEnabled) {
                mBluetoothAdapter!!.enable()
            }
        }
        mService = BluetoothService(activity, mHandler)
        val printImg = activity!!.findViewById(R.id.print_img) as ImageView
        printImg.setOnClickListener {
            onConnecting()
        }
    }

    private fun onConnecting(){
        val serverIntent = Intent(activity, DeviceListActivity::class.java)
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE)
    }

    private fun onPrint() {
        saleManDailyReport = SaleManDailyReport()
        saleManDailyReport!!.saleMan = fragment_daily_report_sale_man.text.toString()
        saleManDailyReport!!.routeName = fragment_daily_report_route.text.toString()
        saleManDailyReport!!.date = fragment_daily_report_date.text.toString()
        saleManDailyReport!!.startTime = getStartTime
        saleManDailyReport!!.endTime = endTime
        saleManDailyReport!!.saleAmt = totalSaleAmt
        saleManDailyReport!!.orderAmt = totalOrderAmt
        saleManDailyReport!!.exchangeAmt = amtArr!![1]
        saleManDailyReport!!.returnAmt = amtArr!![0]
        saleManDailyReport!!.newCustomer = newCustomer
        saleManDailyReport!!.planCustomer = totalPlanCustomer
        saleManDailyReport!!.cashReceive = totalPayAmt
        saleManDailyReport!!.netAmt = netAmt
        saleManDailyReport!!.customerCount = totalCustomer
        saleManDailyReport!!.saleCount = saleCount
        saleManDailyReport!!.orderCount = orderCount
        saleManDailyReport!!.exchangeCount = saleExchangeCount
        saleManDailyReport!!.returnCount = saleReturnCount
        saleManDailyReport!!.cashReceiveCount = cashReceiptCount
        saleManDailyReport!!.notVisitCount = totalNotVisitedCount
        PrintUtils.printForEndOfDayReport(activity!!, saleManDailyReport!!, mService!!)
    }

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_STATE_CHANGE -> {
                    if (DEBUG)
                        Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1)
                    when (msg.arg1) {
                        BluetoothService.STATE_CONNECTED -> Toast.makeText(
                            activity,
                            " Connected with device", Toast.LENGTH_SHORT
                        ).show()
                        BluetoothService.STATE_CONNECTING -> {
                        }
                        BluetoothService.STATE_LISTEN, BluetoothService.STATE_NONE -> {
                        }
                    }
                }
                MESSAGE_WRITE -> {
                }
                MESSAGE_READ -> {
                }
                MESSAGE_DEVICE_NAME -> {
                    // save the connected device's name
                    val mConnectedDeviceName = msg.data.getString(DEVICE_NAME)
                    Toast.makeText(
                        activity,
                        "Connected to " + mConnectedDeviceName!!,
                        Toast.LENGTH_SHORT
                    ).show()
                    onPrint()
                }
                MESSAGE_TOAST -> {
                }
                MESSAGE_CONNECTION_LOST // 蓝牙已断�?连接
                -> Toast.makeText(
                    activity,
                    "Device connection was lost", Toast.LENGTH_SHORT
                )
                    .show()
                MESSAGE_UNABLE_CONNECT // 无法连接设备
                -> Toast.makeText(
                    activity,
                    "Unable to connect device", Toast.LENGTH_SHORT
                ).show()
            }/*Toast.makeText(getActivity(),
                            "Not connected. Try Again!", Toast.LENGTH_SHORT)
                            .show();*/
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (DEBUG)
            Log.d(TAG, "onActivityResult $resultCode")
        when (requestCode) {
            REQUEST_CONNECT_DEVICE -> {
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    val address = data!!.extras!!.getString(
                        DeviceListActivity.IR_EXTRA_DEVICE_ADDRESS
                    )
                    // Get the BLuetoothDevice object
                    if (BluetoothAdapter.checkBluetoothAddress(address)) {
                        val device = mBluetoothAdapter!!.getRemoteDevice(address)
                        // Attempt to connect to the device
                        mService!!.connect(device)
                    }
                }
            }
            REQUEST_ENABLE_BT -> {
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a session

                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled")
                    Toast.makeText(
                        activity, "BT not enabled",
                        Toast.LENGTH_SHORT
                    ).show()
                    activity!!.finish()
                }
            }
        }
    }
    override fun onStop() {
        super.onStop()
        if (DEBUG) Log.e(TAG, "-- ON STOP --")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mService != null) mService!!.stop()
        if (DEBUG)
            Log.e(TAG, "--- ON DESTROY ---")
    }
}