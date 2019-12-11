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
import com.aceplus.data.utils.Constant
//import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.customer.saleorder.SaleOrderCheckoutActivity
import com.aceplus.dms.ui.adapters.sale.SoldProductPrintListAdapter
import com.aceplus.dms.utils.BluetoothService
import com.aceplus.dms.utils.PrintUtils
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.PrintInvoiceViewModel
import com.aceplus.dms.viewmodel.customer.delivery.DeliveryViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.credit.CreditInvoice
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.vo.RelatedDataForPrint
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale_print.*
import kotlinx.android.synthetic.main.activity_sale_print.headerLayout
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.io.Serializable


class PrintInvoiceActivity : BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = com.aceplus.dms.R.layout.activity_sale_print

    companion object {

        private const val IE_SOLD_PRODUCT_LIST = "IE_SOLD_PRODUCT_LIST"
        private const val IE_INVOICE = "IE_INVOICE"
        private const val ORDERED_INVOICE_KEY = "ordered_invoice_key"
        private const val IE_PROMOTION_LIST = "IE_PROMOTION_LIST"
        private const val IE_PRINT_MODE = "IE_PRINT_MODE"
        private const val SALE_MAN_NAME = "SALE_MAN_NAME"
        private const val CUSTOMER_NAME = "CUSTOMER_NAME"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"
        private const val CUSTOMER_TOWNSHIP = "CUSTOMER_TOWNSHIP"
        private const val IE_SALE_RETURN_LIST = "IE_SALE_RETURN_LIST"

        private const val IR_REQUEST_CONNECT_DEVICE = 1
        private const val IR_REQUEST_ENABLE_BT = 2

        const val TAG = "Main_Activity"
        const val DEBUG = true
        const val DEVICE_NAME = "device_name"
        const val TOAST = "toast"

        fun newIntentFromSaleCheckout(
            context: Context,
            invoice: Invoice,
            soldProductList: ArrayList<SoldProductInfo>,
            promotionList: ArrayList<Promotion>
        ): Intent {
            val intent = Intent(context, PrintInvoiceActivity::class.java)
            intent.putExtra(IE_INVOICE, invoice)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            intent.putExtra(IE_PRINT_MODE, "S")
            return intent
        }

        fun newIntentFromSaleOrderCheckout(
            context: Context,
            invoice: Invoice,
            soldProductList: ArrayList<SoldProductInfo>,
            promotionList: ArrayList<Promotion>
        ): Intent{
            val intent = Intent(context, PrintInvoiceActivity::class.java)
            intent.putExtra(IE_INVOICE, invoice)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            intent.putExtra(IE_PRINT_MODE, "S")
            // ToDo - check for ordered invoice
            return intent
        }

        fun newIntentFromSaleReturn(
            context: Context,
            invoice: Invoice,
            returnProductList: ArrayList<SoldProductInfo>
        ): Intent{
            val intent = Intent(context, PrintInvoiceActivity::class.java)
            intent.putExtra(IE_INVOICE, invoice)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, returnProductList)
            intent.putExtra(IE_PRINT_MODE, "SR")
            //To Notice - there's no promotion list
            return intent
        }

        fun getIntentFromCredit(
            context: Context,
            credit: MutableList<CreditInvoice>,
            position: Int,
            customerTownShip: String,
            salePersonName: String,
            customerName: String
        ): Intent {
            val printIntent = Intent(context, PrintInvoiceActivity::class.java)
            printIntent.putExtra("CREDIT", credit as Serializable)
            printIntent.putExtra("CURSOR_POSITION", position)
            //printIntent.putExtra("CREDIT_FLG", "CREDIT")
            printIntent.putExtra(IE_PRINT_MODE, "C")
            printIntent.putExtra(CUSTOMER_TOWNSHIP, customerTownShip)
            printIntent.putExtra(SALE_MAN_NAME, salePersonName)
            printIntent.putExtra(CUSTOMER_NAME, customerName)
            return printIntent
        }

        fun newIntentFromSaleHistoryActivity(
            context: Context,
            invoice: Invoice?,
            soldProductList: List<SoldProductInfo>,
            promotionList: ArrayList<Promotion>
        ): Intent? {
            val intent = Intent(context, PrintInvoiceActivity::class.java)
            intent.putExtra(IE_INVOICE, invoice)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList as ArrayList<SoldProductInfo>)
            intent.putExtra(IE_PROMOTION_LIST,promotionList)
            intent.putExtra(IE_PRINT_MODE, "RP")
            return intent
        }

        fun newIntent(saleOrderCheckoutActivity: SaleOrderCheckoutActivity,soldProductList: ArrayList<SoldProductInfo>,mode:String,orderInvoice:Deliver,customer: Customer,invoice: Invoice): Intent? {
            val intent = Intent(saleOrderCheckoutActivity, PrintInvoiceActivity::class.java)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PRINT_MODE, mode)
            intent.putExtra(ORDERED_INVOICE_KEY, orderInvoice)
            intent.putExtra(IE_CUSTOMER_DATA, customer)
            intent.putExtra(IE_INVOICE,invoice)
            return intent
        }

        fun newIntentFromSaleCancelCheckout(
            context: Context,
            invoice: Invoice,
            soldProductList: ArrayList<SoldProductInfo>
        ): Intent {
            val intent = Intent(context, PrintInvoiceActivity::class.java)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PRINT_MODE, "S")
            intent.putExtra(IE_INVOICE, invoice)
            return intent
        }

    }

    private val printInvoiceViewModel: PrintInvoiceViewModel by viewModel()
    private val printDeliveryViewModel: DeliveryViewModel by viewModel()
    private val soldProductPrintListAdapter: SoldProductPrintListAdapter by lazy { SoldProductPrintListAdapter(printMode)}
    private var invoice: Invoice? = null
    private var customer: Customer? = null
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
    private var customerName: String? = null
    private var orderPerson :String? = null
    private var salePerson :String? = null
    private var customerTownShip: String? = null
    private var orderedInvoice: Delivery? = null
    private var orderedDInvoice: Deliver? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBluetoothService: BluetoothService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        } catch (e: Exception) {
            Log.d("Bluetooth Error", e.toString())
        }

        getIntentData()
        catchEvents()
        getTaxInfoAndSetData()

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

    private fun catchEvents() {

        cancel_img.setOnClickListener {onBackPressed()}

        printInvoiceViewModel.salePersonName.observe(this, Observer {
            saleMan.text = it
            salePersonName = it
        })

        print_img.setOnClickListener { onConnecting() }

        printInvoiceViewModel.relatedDataForPrint.observe(this, Observer {
            if (it != null) {
                relatedDataForPrint = it
                onPrint()
                printInvoiceViewModel.relatedDataForPrint.value = null
            }
        })

    }

    private fun getIntentData() {

        if (intent.getSerializableExtra("CREDIT") != null) creditList = intent.getSerializableExtra("CREDIT") as ArrayList<CreditInvoice>
        if (intent.getParcelableExtra<Invoice>(IE_INVOICE) != null) invoice = intent.getParcelableExtra(IE_INVOICE)
        if (intent.getParcelableArrayListExtra<SoldProductInfo>(IE_SOLD_PRODUCT_LIST) != null) soldProductList = intent.getParcelableArrayListExtra(IE_SOLD_PRODUCT_LIST)
        if (intent.getParcelableArrayListExtra<Promotion>(IE_PROMOTION_LIST) != null) promotionList = intent.getParcelableArrayListExtra(IE_PROMOTION_LIST)
        if (intent.getStringExtra(SALE_MAN_NAME) != null) salePersonName = intent.getStringExtra(SALE_MAN_NAME)
        if (intent.getStringExtra(CUSTOMER_NAME) != null) customerName = intent.getStringExtra(CUSTOMER_NAME)
        if (intent.getStringExtra(CUSTOMER_TOWNSHIP) != null) customerTownShip = intent.getStringExtra(CUSTOMER_TOWNSHIP)
        if (intent.getParcelableArrayListExtra<SoldProductInfo>(IE_SALE_RETURN_LIST) != null) saleReturnList = intent.getParcelableArrayListExtra(IE_SALE_RETURN_LIST)
        if (intent.getStringExtra(IE_PRINT_MODE) != null) printMode = intent.getStringExtra(IE_PRINT_MODE)
        if (intent.getSerializableExtra(ORDERED_INVOICE_KEY) != null) orderedDInvoice = intent.getSerializableExtra(ORDERED_INVOICE_KEY) as Deliver
        if (intent.getSerializableExtra(IE_CUSTOMER_DATA) != null) customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)

    }

    private fun getOrderPerson(saleManId:Int){
        printDeliveryViewModel.userNameDataList.observe(this, Observer {
            for (i in it!!){
                deliver_order_person.text = i.user_name!!
                orderPerson = i.user_name!!
            }
        })
        printDeliveryViewModel.loadOrderPerson(saleManId)
    }

    private fun getSalePerson(salePersonId:String){
        printDeliveryViewModel.userNameDataList.observe(this, Observer {
            for (i in it!!){
                deliver_person.text = i.user_name!!
                salePerson = i.user_name!!
            }
        })
        printDeliveryViewModel.loadOrderPerson(salePersonId.toInt())
    }

    private fun getTaxInfoAndSetData() {

        printInvoiceViewModel.getTaxInfo()
        printInvoiceViewModel.taxInfo.observe(this, Observer {
            if (it != null) {
                taxType = it.first
                taxPercent = it.second
                branchCode = it.third
                setDataToWidgets()
            }
        })

    }

    private fun setDataToWidgets() {

        if (printMode == "S" || printMode == "RP") {
            saleDate.text = Utils.getCurrentDate(false)
            invoiceId.text = invoice!!.invoice_id
            printInvoiceViewModel.getSalePersonName(invoice!!.sale_person_id!!)
            branch.text = branchCode.toString()

            if (printMode == "S" || printMode == "RP") {
                print_soldProductList.adapter = soldProductPrintListAdapter
                print_soldProductList.layoutManager = LinearLayoutManager(this)
                soldProductPrintListAdapter.setNewList(soldProductList)
            }

            setPromotionProductListView() // ToDo - no promo list
            print_totalAmount.text = Utils.formatAmount(invoice!!.total_amount!!.toDouble())

            if (invoice!!.total_discount_amount != 0.0)
                print_totalDiscount.text = Utils.formatAmount(invoice!!.total_discount_amount)

            if (taxType.equals("E", true)) {
                if (invoice!!.total_amount!!.isNotBlank())
                    print_net_amount.text = Utils.formatAmount(invoice!!.total_amount!!.toDouble() - invoice!!.total_discount_amount + invoice!!.tax_amount)
            } else {
                if (invoice!!.total_amount!!.isNotBlank())
                    print_net_amount.text = Utils.formatAmount(invoice!!.total_amount!!.toDouble() - invoice!!.total_discount_amount)
            }

            print_prepaidAmount.text = Utils.formatAmount(invoice!!.pay_amount?.toDouble() ?: 0.0)
            print_discountAmount.text = "${Utils.formatAmount(invoice!!.total_discount_amount)} (${invoice?.total_discount_percent ?: 0.00}%)"

        }
        else if (printMode == "C") {

            crditPrintHeaderLayout1.visibility = View.VISIBLE
            salePrintHeaderLayout1.visibility = View.GONE
            headerLayout.visibility = View.GONE
            salePrintHeaderLayout1.visibility = View.GONE
            printForSaleLayout.visibility = View.GONE
            salePrintHeaderLayout2.visibility = View.GONE
            val date = creditList[0].invoiceDate
            credit_date.text = date.toString()
            credit_invoice_no.text = creditList[0].invoiceNo
            credit_sale_man.text = salePersonName
            credit_customer_name.text = customerName
            //val address =
            credit_township_name.text = customerTownShip
            credit_receive_no.text = creditList[0].invoiceNo

            credit_total_amt.textSize = 30f
            credit_discount.textSize = 30f
            credit_net_amount.textSize = 30f
            credit_receive_amt.textSize = 30f

            credit_total_amt.text = Utils.formatAmount(creditList[0].amt)
            credit_net_amount.text = Utils.formatAmount(creditList[0].amt)
            credit_receive_amt.text = Utils.formatAmount(creditList[0].payAmt)
            credit_discount.text = "0.0 (0%)"

        } else if (printMode == "D") {
            deliverPrintHeaderLayout1.visibility = View.VISIBLE
            salePrintHeaderLayout1.visibility = View.GONE
            salePrintHeaderLayout2.visibility = View.GONE
            llPrepaid.visibility = View.VISIBLE
            deliver_customer_name.text = orderedDInvoice!!.customerName
            deliver_township_name.text = orderedDInvoice!!.customerAddres
            deliver_order_invoice_no.text = orderedDInvoice!!.invoiceNo
            deliver_invoice_no.text = invoice!!.invoice_id
            deliver_date.text = invoice!!.due_date
            getOrderPerson(orderedDInvoice!!.saleManId)
            getSalePerson(invoice!!.sale_person_id!!)
            print_totalAmount.text = orderedDInvoice!!.amount.toString()
            print_totalDiscount.text = orderedDInvoice!!.discount.toString()
            print_net_amount.text = (orderedDInvoice!!.amount - orderedDInvoice!!.paidAmount).toString()
            print_prepaidAmount.text = Utils.formatAmount(invoice!!.pay_amount!!.toDouble())
            print_prepaidAmountShow.text = Utils.formatAmount(orderedDInvoice!!.paidAmount)
            print_discountAmount.text = orderedDInvoice!!.discountPercent.toString()

            soldProductPrintListAdapter.setNewList(soldProductList)
            print_soldProductList.adapter = soldProductPrintListAdapter
            print_soldProductList.layoutManager = LinearLayoutManager(this)

        } else if (printMode == "SR") {

            saleDate.text = Utils.getCurrentDate(false)
            invoiceId.text = invoice!!.invoice_id
            printInvoiceViewModel.getSalePersonName(invoice!!.sale_person_id!!)
            branch.text = branchCode.toString()

            print_soldProductList.adapter = soldProductPrintListAdapter
            print_soldProductList.layoutManager = LinearLayoutManager(this)
            soldProductPrintListAdapter.setNewList(soldProductList)

            //setPromotionProductListView() // Cuz there is no promo list for return

            print_totalAmount.text = Utils.formatAmount(invoice!!.total_amount!!.toDouble())

            if (invoice!!.total_discount_amount != 0.0)
                print_totalDiscount.text = Utils.formatAmount(invoice!!.total_discount_amount)

            if (taxType.equals("E", true)) {
                if (invoice!!.total_amount!!.isNotBlank())
                    print_net_amount.text = Utils.formatAmount(invoice!!.total_amount!!.toDouble() - invoice!!.total_discount_amount + invoice!!.tax_amount)
            } else {
                if (invoice!!.total_amount!!.isNotBlank())
                    print_net_amount.text = Utils.formatAmount(invoice!!.total_amount!!.toDouble() - invoice!!.total_discount_amount)
            }

            print_prepaidAmount.text = Utils.formatAmount(invoice!!.pay_amount?.toDouble() ?: 0.0)
            print_discountAmount.text = "${Utils.formatAmount(invoice!!.total_discount_amount)} (${invoice?.total_discount_percent ?: 0.00}%)"

            // To check - everything except promo list is same to S and RP - can combine

        }
    }

    private fun onConnecting() {
        startActivityForResult(
            DeviceListActivity.getIntentFromPrintInvoice(this),
            IR_REQUEST_CONNECT_DEVICE
        )
    }

    @Suppress("DEPRECATION")
    private fun onPrint(){

        val v1 = window.decorView.rootView
        v1.isDrawingCacheEnabled = true
        val myBitmap = v1.drawingCache
        val saleManName = if (invoice!!.receipt_person_name.isNullOrBlank()) "To Find" else invoice!!.receipt_person_name

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
                ) //To check sale person name
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
                saleManName,
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
            ) //To check sale person name

        } else if (printMode == "D"){

            Utils.saveInvoiceImageIntoGallery(invoice!!.invoice_id, this, myBitmap, "Deliver") // Doesn't work
            val editProductList = printInvoiceViewModel.arrangeProductList(soldProductList, promotionList)
            val customerData: Customer = relatedDataForPrint!!.customer
            PrintUtils.printDeliverWithHSPOS(
                this,
                customerData.customer_name,
                customerData.address,
                orderedDInvoice!!.invoiceNo!!,
                orderPerson,
                invoice!!.invoice_id,
                salePerson,
                relatedDataForPrint!!.routeName,
                relatedDataForPrint!!.customerTownShipName,
                invoice!!,
                editProductList,
                promotionList,
                orderedDInvoice!!.paidAmount,
                PrintUtils.PRINT_FOR_NORMAL_SALE,
                PrintUtils.FOR_OTHERS,
                mBluetoothService!!,
                relatedDataForPrint!!.companyInfo
            ) //To check sale person name

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
            ) //To check sale person name

        }

    }

    private fun setPromotionProductListView() {
        // ToDo - show promo list
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            IR_REQUEST_CONNECT_DEVICE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val address =
                        data?.getStringExtra(DeviceListActivity.IR_EXTRA_DEVICE_ADDRESS)
                    if (BluetoothAdapter.checkBluetoothAddress(address)) {
                        val device = mBluetoothAdapter!!.getRemoteDevice(address)
                        mBluetoothService?.connect(device)
                    }
                }
            }
            IR_REQUEST_ENABLE_BT -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (mBluetoothService == null)
                        mBluetoothService = BluetoothService(this, mHandler)
                } else {
                    Toast.makeText(this, "Bluetooth isn't enabled!", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }

    }

    private val mHandler = Handler {

        when (it.what) {

            Constant.HM_MESSAGE_STATE_CHANGE -> {
                Log.i(TAG, "MESSAGE_STATE_CHANGE ${it.arg1}")
                when (it.arg1) {
                    BluetoothService.STATE_CONNECTED -> Toast.makeText(this, "Connected with device", Toast.LENGTH_SHORT).show() }
            }
            Constant.HM_MESSAGE_DEVICE_NAME -> {
                val connectedDeviceName = it.data.getString(DEVICE_NAME)
                Toast.makeText(this, "Connected to $connectedDeviceName", Toast.LENGTH_SHORT).show()
                printInvoiceViewModel.getRelatedDataAndPrint(invoice!!.customer_id!!, invoice!!.sale_person_id!!, orderedInvoice?.sale_man_id) //Sale man id to name
            }
            Constant.HM_MESSAGE_TOAST -> {
                Toast.makeText(this, it.data.getString(TOAST), Toast.LENGTH_SHORT).show()
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
        if (printMode == "D"){
            val intent = Intent(this,CustomerVisitActivity::class.java)
            startActivityForResult(intent,Constant.RQC_BACK_TO_CUSTOMER_VISIT)
        }
        if (printMode == "RP"){
            val intent = Intent(this,ReportActivity::class.java)
            startActivityForResult(intent,Constant.RQC_BACK_TO_REPORT)
        }
       // setResult(Activity.RESULT_OK)
        finish()
    }

}

