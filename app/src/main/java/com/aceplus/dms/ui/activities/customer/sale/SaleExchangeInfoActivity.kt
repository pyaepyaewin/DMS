package com.aceplus.dms.ui.activities.customer.sale

import android.app.Activity
import android.arch.lifecycle.Observer
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.Toast
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.PrintInvoiceActivity
import com.aceplus.dms.ui.adapters.sale.SaleExchangeInfoAdapter
import com.aceplus.dms.utils.BluetoothService
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.PrintInvoiceViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.sale_exchange_info_layout.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class SaleExchangeInfoActivity: BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.sale_exchange_info_layout

    companion object{

        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"
        private const val IE_SALE_RETURN_INVOICE_ID_KEY = "SALE_RETURN_INVOICE_ID_KEY"
        private const val IE_INVOICE = "IE_INVOICE"
        private const val IE_SOLD_PRODUCT_LIST = "IE_SOLD_PRODUCT_LIST"
        private const val IE_PROMOTION_LIST = "IE_PROMOTION_LIST"

        private const val IR_REQUEST_CONNECT_DEVICE = 1
        private const val IR_REQUEST_ENABLE_BT = 2

        fun getIntentFromSaleCheckout(
            context: Context,
            customer: Customer,
            returnInvNo: String,
            invoice: Invoice,
            soldProductList: ArrayList<SoldProductInfo>,
            promotionList: ArrayList<Promotion>
        ): Intent {
            val intent = Intent(context, SaleExchangeInfoActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customer)
            intent.putExtra(IE_SALE_RETURN_INVOICE_ID_KEY, returnInvNo)
            intent.putExtra(IE_INVOICE, invoice)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            return intent
        }

    }

    private val printInvoiceViewModel: PrintInvoiceViewModel by viewModel()
    private val saleExchangeInfoAdapterForReturn by lazy { SaleExchangeInfoAdapter() }
    private val saleExchangeInfoAdapterForExchange by lazy { SaleExchangeInfoAdapter() }

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBluetoothService: BluetoothService? = null
    private var invoice: Invoice? = null
    private var customer: Customer? = null
    private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()
    private var promotionList: ArrayList<Promotion> = ArrayList()
    private var saleReturnInvoiceNo: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        getIntentData()
        setupUI()
        catchEvents()
        getInfoAndSetData()

    }

    override fun onStart() {
        super.onStart()

        if (!mBluetoothAdapter!!.isEnabled) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, IR_REQUEST_ENABLE_BT)
        } else {
            if (mBluetoothService == null)
                mBluetoothService = BluetoothService(this, mHandler)
        }

    }

    override fun onResume() {
        super.onResume()

        if (mBluetoothService != null) {
            if (mBluetoothService!!.state == BluetoothService.STATE_NONE)
                mBluetoothService!!.start()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        if (mBluetoothService != null)
            mBluetoothService!!.stop()

    }

    private fun getIntentData(){
        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        invoice = intent.getParcelableExtra(IE_INVOICE)
        saleReturnInvoiceNo = intent.getStringExtra(IE_SALE_RETURN_INVOICE_ID_KEY)
        soldProductList = intent.getParcelableArrayListExtra(IE_SOLD_PRODUCT_LIST)
        promotionList = intent.getParcelableArrayListExtra(IE_PROMOTION_LIST)
    }

    private fun setupUI(){

        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val height = point.y

        val exListLayout = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 4)
        exListLayout.leftMargin = 10
        exListLayout.rightMargin = 10
        exListLayout.addRule(RelativeLayout.BELOW, R.id.sale_return_headerLayout)
        sx_info_sale_exchange_list.layoutParams = exListLayout

        val exListLayout1 = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 4)
        exListLayout1.leftMargin = 10
        exListLayout1.rightMargin = 10
        exListLayout1.addRule(RelativeLayout.BELOW, R.id.sale_exchange_headerLayout)
        sx_info_sale_list.layoutParams = exListLayout1

        sx_info_sale_exchange_list.adapter = saleExchangeInfoAdapterForReturn
        sx_info_sale_exchange_list.layoutManager = LinearLayoutManager(this)

        sx_info_sale_list.adapter = saleExchangeInfoAdapterForExchange
        sx_info_sale_list.layoutManager = LinearLayoutManager(this)

    }

    private fun catchEvents(){

        confirmAndPrint_img.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        print_img.setOnClickListener { "ToDo" }

    }

    private fun getInfoAndSetData(){

        tvDate.text = Utils.getCurrentDate(false)
        tvCustomerName.text = customer!!.customer_name
        //tvSaleReturnAmount
        //tvSaleReturnDiscAmount
        //tvSaleExchangeAmount
        //tvPayAmountFromCustomer
        //tvRefundToCustomer

        printInvoiceViewModel.getSaleReturnProductInfo(saleReturnInvoiceNo!!)
        printInvoiceViewModel.getExchangeProductInfo(soldProductList)

        printInvoiceViewModel.saleReturnProducts.observe(this, Observer {
            if (it != null) saleExchangeInfoAdapterForReturn.setNewList(it as ArrayList<Product>)
        })

        printInvoiceViewModel.exchangeProducts.observe(this, Observer {
            if (it != null) saleExchangeInfoAdapterForExchange.setNewList(it as ArrayList<Product>)
        })

    }

    private val mHandler = Handler {

        when (it.what) {

            Constant.HM_MESSAGE_STATE_CHANGE -> {
                when (it.arg1) {
                    BluetoothService.STATE_CONNECTED -> Toast.makeText(this, "Connected with device", Toast.LENGTH_SHORT).show() }
            }
            Constant.HM_MESSAGE_DEVICE_NAME -> {
                val connectedDeviceName = it.data.getString(PrintInvoiceActivity.DEVICE_NAME)
                Toast.makeText(this, "Connected to $connectedDeviceName", Toast.LENGTH_SHORT).show()
                //printInvoiceViewModel.getRelatedDataAndPrint(invoice!!.customer_id!!, invoice!!.sale_person_id!!, orderedInvoice?.sale_man_id)
            }
            Constant.HM_MESSAGE_TOAST -> {
                Toast.makeText(this, it.data.getString(PrintInvoiceActivity.TOAST), Toast.LENGTH_SHORT).show()
            }
            Constant.HM_MESSAGE_CONNECTION_LOST -> {
                Toast.makeText(this, "Device connection was lost!", Toast.LENGTH_SHORT).show()
            }
            Constant.HM_MESSAGE_UNABLE_CONNECT -> {
                Toast.makeText(this, "Unable to connect device!", Toast.LENGTH_SHORT).show()
            }

        }
        true
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage("Please click Confirm Button!")
            .setPositiveButton("Ok", null)
            .show()
    }

}