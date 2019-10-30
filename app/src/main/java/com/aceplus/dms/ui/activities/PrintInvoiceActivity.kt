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
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.sale.SoldProductPrintListAdapter
import com.aceplus.dms.utils.BluetoothService
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.PrintInvoiceViewModel
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.promotion.Promotion
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

    }

    private val printInvoiceViewModel: PrintInvoiceViewModel by viewModel()
    private val soldProductPrintListAdapter: SoldProductPrintListAdapter by lazy { SoldProductPrintListAdapter(printMode) }

    private var invoice: Invoice? = null
    private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()
    private var promotionList: ArrayList<Promotion> = ArrayList()
    private var printMode: String = ""
    private var taxType: String = ""
    private var taxPercent: Int = 0
    private var branchCode: Int = 0
    private var creditFlg: String? = null

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
        })

        print_img.setOnClickListener {onConnecting()}

    }

    private fun getIntentData(){
        invoice = intent.getParcelableExtra(IE_INVOICE)
        soldProductList = intent.getParcelableArrayListExtra(IE_SOLD_PRODUCT_LIST)
        promotionList = intent.getParcelableArrayListExtra(IE_PROMOTION_LIST)
        printMode = intent.getStringExtra(IE_PRINT_MODE)
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
            // ToDo
        } else if (printMode == "D"){
            // ToDo
        } else if (printMode == "SR"){
            // ToDo
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

        } else if (printMode == "S"){

            Utils.saveInvoiceImageIntoGallery(invoice!!.invoice_id, this, myBitmap, "Sale")

        } else if (printMode == "RP"){

        } else if (printMode == "D"){

        } else if (printMode == "SR"){

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
                onPrint()
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

}
