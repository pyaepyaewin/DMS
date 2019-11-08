package com.aceplus.dms.ui.activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.sale.SoldProductPrintListAdapter
import com.aceplus.dms.utils.BluetoothService
import com.aceplus.dms.utils.PrintUtils
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.PrintInvoiceViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.credit.CreditInvoice
import com.aceplus.domain.vo.RelatedDataForPrint
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale_print.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class PrintInvoiceActivity : BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale_print

    companion object{

        private const val IE_SOLD_PRODUCT_LIST = "IE_SOLD_PRODUCT_LIST"
        private const val IE_INVOICE = "IE_INVOICE"
        private const val IE_PROMOTION_LIST = "IE_PROMOTION_LIST"
        private const val IE_PRINT_MODE = "IE_PRINT_MODE"
        private const val IE_SALE_RETURN_LIST = "IE_SALE_RETURN_LIST"
        private const val IE_CREDIT_LIST = "IE_CREDIT_LIST"

        private const val IR_REQUEST_CONNECT_DEVICE = 1
        private const val IR_REQUEST_ENABLE_BT = 2

        const val HM_MESSAGE_STATE_CHANGE = 1
        const val HM_MESSAGE_READ = 2
        const val HM_MESSAGE_WRITE = 3
        const val HM_MESSAGE_DEVICE_NAME = 4
        const val HM_MESSAGE_TOAST = 5
        const val HM_MESSAGE_CONNECTION_LOST = 6
        const val HM_MESSAGE_UNABLE_CONNECT = 7

        const val TAG = "Main_Activity"
        const val DEBUG = true
        const val DEVICE_NAME = "device_name"
        const val TOAST = "toast"

        fun newIntentFromSaleCheckout(
            context: Context, invoice: Invoice, soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>
        ): Intent{
            val intent = Intent(context, PrintInvoiceActivity::class.java)
            intent.putExtra(IE_INVOICE, invoice)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            intent.putExtra(IE_PRINT_MODE, "S")
            return intent
        }

        fun getIntent(
            context: Context,
            customerName: String,
            address: String,
            receiveNo: String,
            collectPerson: String,
            date: String,
            totalAmount: String,
            discount: String,
            receive: String
        ): Intent {
            val printIntent = Intent(context, PrintInvoiceActivity::class.java)
            printIntent.putExtra("CustomerName", customerName)
            printIntent.putExtra("Address", address)
            printIntent.putExtra("ReceiveNo", receiveNo)
            printIntent.putExtra("CollectPerson", collectPerson)
            printIntent.putExtra("Date", date)
            printIntent.putExtra("TotalAmount", totalAmount)
            printIntent.putExtra("Discount", discount)
            printIntent.putExtra("Receive", receive)


            printIntent.putExtra("CREDIT_FLG", "CREDIT")
            printIntent.putExtra("PRINT_MODE", "C")
            return printIntent
        }

    }

    private val printInvoiceViewModel: PrintInvoiceViewModel by viewModel()
    private val soldProductPrintListAdapter: SoldProductPrintListAdapter by lazy { SoldProductPrintListAdapter(printMode) }

    private var invoice: Invoice? = null
    private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()
    private var promotionList: ArrayList<Promotion> = ArrayList()
    private var saleReturnList: ArrayList<SoldProductInfo> = ArrayList()
    private var creditList: ArrayList<CreditInvoice> = ArrayList()
    private var pos = 0
    private var printMode: String = ""
    private var taxType: String = ""
    private var taxPercent: Int = 0
    private var branchCode: Int = 0
    private var creditFlg: String? = null
    private var relatedDataForPrint: RelatedDataForPrint? = null
    private var salePersonName: String? = null
    private var orderedInvoice: Delivery? = null

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBluetoothService: BluetoothService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        } catch (e: Exception){
            Log.d("Bluetooth Error", e.toString())
        }

        getIntentData()
        catchEvents()
        getTaxInfoAndSetData()

        print_soldProductList.adapter = soldProductPrintListAdapter
        print_soldProductList.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()

        if (!mBluetoothAdapter!!.isEnabled){
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, IR_REQUEST_ENABLE_BT)
        } else{
            if (mBluetoothService == null)
                mBluetoothService = BluetoothService(this, mHandler)
        }

    }

    override fun onResume() {
        super.onResume()

        if (mBluetoothService != null){
            if (mBluetoothService!!.state == BluetoothService.STATE_NONE)
                mBluetoothService!!.start()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        if (mBluetoothService != null)
            mBluetoothService!!.stop()

    }

    private fun catchEvents(){

        cancel_img.setOnClickListener { onBackPressed() }

        printInvoiceViewModel.salePersonName.observe(this, Observer {
            saleMan.text = it
            salePersonName = it
        })

        print_img.setOnClickListener {onConnecting()}

        printInvoiceViewModel.relatedDataForPrint.observe(this, Observer {
            if (it != null){
                relatedDataForPrint = it
                onPrint()
            }
        })

    }

    private fun getIntentData(){
        if (intent.getParcelableExtra<Invoice>(IE_INVOICE) != null) invoice = intent.getParcelableExtra(IE_INVOICE)
        if (intent.getParcelableArrayListExtra<SoldProductInfo>(IE_SOLD_PRODUCT_LIST) != null) soldProductList = intent.getParcelableArrayListExtra(IE_SOLD_PRODUCT_LIST)
        if (intent.getParcelableArrayListExtra<Promotion>(IE_PROMOTION_LIST) != null) promotionList = intent.getParcelableArrayListExtra(IE_PROMOTION_LIST)
        if (intent.getStringExtra(IE_PRINT_MODE) != null) printMode = intent.getStringExtra(IE_PRINT_MODE)
        if (intent.getParcelableArrayListExtra<SoldProductInfo>(IE_SALE_RETURN_LIST) != null) saleReturnList = intent.getParcelableArrayListExtra(IE_SALE_RETURN_LIST)
    }

    private fun getTaxInfoAndSetData(){

        printInvoiceViewModel.getTaxInfo()

        printInvoiceViewModel.taxInfo.observe(this, Observer {
            if (it != null){
                taxType = it.first
                taxPercent = it.second
                branchCode = it.third
                setDataToWidgets()
            }
        })

    }

    private fun setDataToWidgets(){

        if (printMode == "S" || printMode == "RP"){

            saleDate.text = Utils.getCurrentDate(false)
            invoiceId.text = invoice!!.invoice_id
            printInvoiceViewModel.getSalePersonName(invoice!!.sale_person_id!!)
            branch.text = branchCode.toString()
            soldProductPrintListAdapter.setNewList(soldProductList)
            setPromotionProductListView()
            print_totalAmount.text = Utils.formatAmount(invoice!!.total_amount!!.toDouble())

            if (invoice!!.total_discount_amount != 0.0)
                print_totalDiscount.text = Utils.formatAmount(invoice!!.total_discount_amount)

            if (taxType.equals("E", true)){
                if (invoice!!.total_amount!!.isNotBlank())
                    print_net_amount.text = Utils.formatAmount(invoice!!.total_amount!!.toDouble() - invoice!!.total_discount_amount + invoice!!.tax_amount)
            } else{
                if (invoice!!.total_amount!!.isNotBlank())
                    print_net_amount.text = Utils.formatAmount(invoice!!.total_amount!!.toDouble() - invoice!!.total_discount_amount)
            }

            print_prepaidAmount.text = Utils.formatAmount(invoice!!.pay_amount?.toDouble() ?: 0.0)
            print_discountAmount.text = "${Utils.formatAmount(invoice!!.total_discount_amount)} (${invoice!!.total_discount_percent}%)"

        } else if (printMode == "C"){

            printForSaleLayout.visibility = View.GONE
            // ToDo - Who develop credit

        } else if (printMode == "D"){

            // ToDo - Who develop delivery

        } else if (printMode == "SR"){

            // ToDo - Who develop ??

        }

    }

    private fun onConnecting(){
        startActivityForResult(DeviceListActivity.getIntentFromPrintInvoice(this), IR_REQUEST_CONNECT_DEVICE)
    }

    @Suppress("DEPRECATION")
    private fun onPrint(){

        val v1 = window.decorView.rootView
        v1.isDrawingCacheEnabled = true
        val myBitmap = v1.drawingCache

        if (printMode == "C" && !creditFlg.isNullOrBlank()){

            Utils.saveInvoiceImageIntoGallery(creditList[pos].invoiceNo, this, myBitmap, "Credit Collect") // Doesn't work
            if (creditList.isNotEmpty()){
                val customerData: Customer = relatedDataForPrint!!.customer
                PrintUtils.printCreditWithHSPOS(
                    this,
                    customerData.customer_name,
                    customerData.address,
                    creditList[pos].invoiceNo,
                    salePersonName,
                    relatedDataForPrint!!.routeName,
                    relatedDataForPrint!!.customerTownShipName,
                    creditList[pos],
                    mBluetoothService!!,
                    relatedDataForPrint!!.companyInfo
                )
            }

        } else if (printMode == "S"){

            Utils.saveInvoiceImageIntoGallery(invoice!!.invoice_id, this, myBitmap, "Sale") // Doesn't work
            val editProductList = printInvoiceViewModel.arrangeProductList(soldProductList, promotionList)
            val customerData: Customer = relatedDataForPrint!!.customer
            //invoice!!.printMode = "sale" // Doesn't exist in invoice, in all condition ?? add or param pass?
            PrintUtils.printWithHSPOS(
                this,
                customerData.customer_name,
                customerData.address,
                invoice!!.invoice_id,
                salePersonName,
                relatedDataForPrint!!.routeName,
                relatedDataForPrint!!.customerTownShipName,
                invoice!!,
                editProductList,
                promotionList,
                PrintUtils.PRINT_FOR_NORMAL_SALE,
                PrintUtils.FOR_OTHERS,
                mBluetoothService!!,
                relatedDataForPrint!!.companyInfo,
                "sale"
            )

        } else if (printMode == "RP"){

            Utils.saveInvoiceImageIntoGallery(invoice!!.invoice_id, this, myBitmap, "Sale") // Doesn't work
            val editProductList = printInvoiceViewModel.arrangeProductList(soldProductList, promotionList)
            val customerData: Customer = relatedDataForPrint!!.customer
            PrintUtils.printWithHSPOS(
                this,
                customerData.customer_name,
                customerData.address,
                invoice!!.invoice_id,
                salePersonName,
                relatedDataForPrint!!.routeName,
                relatedDataForPrint!!.customerTownShipName,
                invoice!!,
                editProductList,
                promotionList,
                PrintUtils.PRINT_FOR_NORMAL_SALE,
                PrintUtils.FOR_OTHERS,
                mBluetoothService!!,
                relatedDataForPrint!!.companyInfo,
                "report"
            )

        } else if (printMode == "D"){

            Utils.saveInvoiceImageIntoGallery(invoice!!.invoice_id, this, myBitmap, "Deliver") // Doesn't work
            val editProductList = printInvoiceViewModel.arrangeProductList(soldProductList, promotionList)
            val customerData: Customer = relatedDataForPrint!!.customer
            PrintUtils.printDeliverWithHSPOS(
                this,
                customerData.customer_name,
                customerData.address,
                orderedInvoice!!.invoice_no!!,
                relatedDataForPrint!!.orderSalePersonName,
                invoice!!.invoice_id,
                salePersonName,
                relatedDataForPrint!!.routeName,
                relatedDataForPrint!!.customerTownShipName,
                invoice!!,
                editProductList,
                promotionList,
                orderedInvoice!!.paid_amount?.toDouble() ?: 0.0,
                PrintUtils.PRINT_FOR_NORMAL_SALE,
                PrintUtils.FOR_OTHERS,
                mBluetoothService!!,
                relatedDataForPrint!!.companyInfo
            )

        } else if (printMode == "SR"){

            Utils.saveInvoiceImageIntoGallery(invoice!!.invoice_id, this, myBitmap, "Sale") // Doesn't work
            val editProductList = printInvoiceViewModel.arrangeProductList(soldProductList, promotionList)
            val customerData: Customer = relatedDataForPrint!!.customer
            PrintUtils.printWithHSPOS(
                this,
                customerData.customer_name,
                customerData.address,
                invoice!!.invoice_id,
                salePersonName,
                relatedDataForPrint!!.routeName,
                relatedDataForPrint!!.customerTownShipName,
                invoice!!,
                editProductList,
                promotionList,
                PrintUtils.PRINT_FOR_NORMAL_SALE,
                PrintUtils.FOR_OTHERS,
                mBluetoothService!!,
                relatedDataForPrint!!.companyInfo,
                null
            )

        }

    }

    private fun setPromotionProductListView(){
        // ToDo - show promo list
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            IR_REQUEST_CONNECT_DEVICE -> {
                if (resultCode == Activity.RESULT_OK){
                    val address = data?.getStringExtra(DeviceListActivity.IR_EXTRA_DEVICE_ADDRESS)
                    if (BluetoothAdapter.checkBluetoothAddress(address)){
                        val device = mBluetoothAdapter!!.getRemoteDevice(address)
                        mBluetoothService?.connect(device)
                    }
                }
            }
            IR_REQUEST_ENABLE_BT -> {
                if (resultCode == Activity.RESULT_OK){
                    if (mBluetoothService == null)
                        mBluetoothService = BluetoothService(this, mHandler)
                } else{
                    Toast.makeText(this, "Bluetooth isn't enabled!", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }

    }

    private val mHandler = Handler {

        when(it.what){

            HM_MESSAGE_STATE_CHANGE -> {
                if (DEBUG) Log.i(TAG, "MESSAGE_STATE_CHANGE ${it.arg1}")
                when(it.arg1){
                    BluetoothService.STATE_CONNECTED -> Toast.makeText(this, "Connected with device", Toast.LENGTH_SHORT).show()
                }
            }
            HM_MESSAGE_DEVICE_NAME -> {
                val connectedDeviceName = it.data.getString(DEVICE_NAME)
                Toast.makeText(this, "Connected to $connectedDeviceName", Toast.LENGTH_SHORT).show()
                printInvoiceViewModel.getRelatedDataAndPrint(invoice!!.customer_id!!, invoice!!.sale_person_id!!, orderedInvoice?.sale_man_id)
            }
            HM_MESSAGE_TOAST -> {
                Toast.makeText(this, it.data.getString(TOAST), Toast.LENGTH_SHORT).show()
            }
            HM_MESSAGE_CONNECTION_LOST -> {
                Toast.makeText(this, "Device connection was lost!", Toast.LENGTH_SHORT).show()
            }
            HM_MESSAGE_UNABLE_CONNECT -> {
                Toast.makeText(this, "Unable to connect device!", Toast.LENGTH_SHORT).show()
            }

        }

        true
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        finish()
    }

}


