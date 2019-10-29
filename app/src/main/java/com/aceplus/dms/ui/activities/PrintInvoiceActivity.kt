package com.aceplus.dms.ui.activities

import android.arch.lifecycle.Observer
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.sale.SoldProductPrintListAdapter
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

    private var mBluetoothAdapter: BluetoothAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentData()
        catchEvents()
        getTaxInfoAndSetData()

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        } catch (e: Exception){
            Log.d("Bluetooth Error", e.toString())
        }

        print_soldProductList.adapter = soldProductPrintListAdapter
        print_soldProductList.layoutManager = LinearLayoutManager(this)

    }

    private fun catchEvents(){

        cancel_img.setOnClickListener { onBackPressed() }

        printInvoiceViewModel.salePersonName.observe(this, Observer {
            saleMan.text = it
        })

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

        } else if (printMode == "D"){

        } else if (printMode == "SR"){

        }

    }

    private fun setPromotionProductListView(){
        // ToDo - show promo list
    }

}
