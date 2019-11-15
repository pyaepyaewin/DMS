package com.aceplus.dms.ui.activities.customer.sale

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.sale.SaleCancelCheckoutAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.salecancelviewmodel.SaleCancelCheckOutViewModel
import com.aceplus.domain.vo.SoldProductInfo
import kotlinx.android.synthetic.main.activity_sale_checkout.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SaleCancelCheckoutActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    var totalAmt = 0.0
    private var netAmount: Double = 0.0
    private var volDisAmount: Double = 0.0
    private var refundAmount: Double = 0.0
    private var taxPercent: Int = 0
    private var taxAmt: Double = 0.0
    private var totalDiscountAmount: Double = 0.0
    private var totalVolumeDiscount: Double = 0.0
    private var totalVolumeDiscountPercent: Double = 0.0
    private var taxType: String = ""
    private var volDisPercent: Double = 0.0


    private val df = DecimalFormat(".##")


    companion object {
        fun getSaleCancelDetailIntent(
            context: Context,
            soldProductList: ArrayList<SoldProductInfo>, invoiceID: String, date: String

        ): Intent {
            val saleCancelDetailIntent = Intent(context, SaleCancelCheckoutActivity::class.java)
            saleCancelDetailIntent.putExtra("SOLD_PRODUCT_LIST", soldProductList)
            saleCancelDetailIntent.putExtra("INVOICE_ID", invoiceID)
            saleCancelDetailIntent.putExtra("DATE", date)
            return saleCancelDetailIntent

        }

        private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()

//        private val saleCancelCheckOutViewModel: SaleCancelCheckOutViewModel by lazy {
//            ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
//                .get(SaleCancelCheckOutViewModel::class.java)
//        }

        private val saleCancelCheckOutAdapter: SaleCancelCheckoutAdapter by lazy {
            SaleCancelCheckoutAdapter()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_checkout)
        advancedPaidAmountLayout.visibility = View.GONE
        volDisForPreOrderLayout.visibility = View.GONE
        volumeDiscountLayout2.visibility = View.GONE
        llSaleStatus.visibility = View.GONE
        soldProductList = intent.getParcelableArrayListExtra("SOLD_PRODUCT_LIST")


        for (i in soldProductList) {

            val amt = i.quantity.toDouble() * i.product.selling_price!!.toDouble()
            totalAmt += amt
        }
        tvTotalAmount.text = totalAmt.toString()
        tvNetAmount.text = totalAmt.toString()
        //val taxAmt = calculateTax()
       // tax_txtView.text = taxAmt.toString()
        saleDateTextView.text = Utils.getCurrentDate(false)
        tvInvoiceId.text = intent.getStringExtra("INVOICE_ID")
        saleCancelCheckOutAdapter.setNewList(soldProductList)
        rvSoldProductList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = saleCancelCheckOutAdapter
        }
        btn_disOk.setOnClickListener { calculateDiscPercentToAmt() }
        btn_amtOk.setOnClickListener { calculateDiscAmtToPercent() }
        edt_dueDate.setOnClickListener { chooseDueDate() }


    }

    private fun calculateDiscPercentToAmt() {

        if (edtVolumeDiscountPercent.text.toString().isNotBlank() && edtVolumeDiscountPercent.text.toString() != ".") {
            val discountPercent = edtVolumeDiscountPercent.text.toString().toDouble()
            var discountAmount = totalAmt * discountPercent / 100
            var netAmount = totalAmt - discountAmount

            discountAmount = df.format(discountAmount).toDouble()
            netAmount = df.format(netAmount).toDouble()

            edtVolumeDiscountAmt.setText(discountAmount.toString())
            tvNetAmount.text = netAmount.toString()
            this.netAmount = netAmount
            this.volDisAmount = discountAmount
            calculateRefundAmount()
        }

    }

    private fun calculateDiscAmtToPercent() {

        if (edtVolumeDiscountAmt.text.toString().isNotBlank() && edtVolumeDiscountAmt.text.toString() != ".") {
            val discountAmount = edtVolumeDiscountAmt.text.toString().toDouble()
            var discountPercent = 100 * (discountAmount / totalAmt)
            var netAmount = totalAmt - discountAmount

            discountPercent = df.format(discountPercent).toDouble()
            netAmount = df.format(netAmount).toDouble()

            edtVolumeDiscountPercent.setText(discountPercent.toString())
            tvNetAmount.text = netAmount.toString()
            this.netAmount = netAmount
            this.volDisAmount = discountAmount
            calculateRefundAmount()
        }

    }

    fun calculateRefundAmount() {

        if (payAmount.text.isNotBlank()) {
            val payAmount = payAmount.text.toString().toDouble()
            var refundAmount = payAmount - netAmount

            if (refundAmount >= 0) {
                refundAmount = df.format(refundAmount).toDouble()
                refund.text = Utils.formatAmount(refundAmount)
            } else refund.text = "0"

            this.refundAmount = refundAmount
        }

    }

    private fun chooseDueDate() {

        val myCalendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd")

        val dateDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                myCalendar.set(year, month, dayOfMonth)
                edt_dueDate.setText(sdf.format(myCalendar.time))
            },
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )

        dateDialog.show()

    }
}
   // private fun calculateTax(amount: Double): Double {
//        var taxAmt = 0.0
//        if (taxPercent != 0.0) {
//            taxAmt = amount * taxPercent / 100
//        }
//        this.taxAmt = taxAmt
//
//        return taxAmt



