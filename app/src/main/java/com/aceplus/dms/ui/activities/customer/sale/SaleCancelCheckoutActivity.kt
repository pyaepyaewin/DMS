package com.aceplus.dms.ui.activities.customer.sale

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.util.Log.i
import android.view.View
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.LoginActivity
import com.aceplus.dms.ui.activities.PrintInvoiceActivity
import com.aceplus.dms.ui.adapters.sale.SaleCancelCheckoutAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.salecancelviewmodel.SaleCancelCheckOutViewModel
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.forApi.invoice.InvoiceDetail
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale_checkout.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SaleCancelCheckoutActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    override val layoutId: Int
        get() = R.layout.activity_sale_checkout

    var totalAmt = 0.0
    private var netAmount: Double = 0.0
    private var volDisAmount: Double = 0.0
    private var refundAmount: Double = 0.0
    private var taxPercent: Int = 0
    private var taxAmt: Double = 0.0
    private var totalDiscountAmount: Double = 0.0
    private var taxType: String = ""
    private var volDisPercent: Double = 0.0
    private var salePersonId: String? = null
    private var invoice = arrayListOf<Invoice>()
    var totalQtyForInvoice = 0
    val invoiceProductList: ArrayList<InvoiceProduct> = ArrayList()
    var indexList: ArrayList<Int> = ArrayList()


    private val df = DecimalFormat(".##")


    companion object {
        fun getSaleCancelDetailIntent(
            context: Context,
            soldProductList: ArrayList<SoldProductInfo>,
            invoiceID: String,
            date: String,
            customerID: String,
            customerName: String,
            indexList: ArrayList<Int>


        ): Intent {
            val saleCancelDetailIntent = Intent(context, SaleCancelCheckoutActivity::class.java)
            saleCancelDetailIntent.putExtra("SOLD_PRODUCT_LIST", soldProductList)
            saleCancelDetailIntent.putExtra("INVOICE_ID", invoiceID)
            saleCancelDetailIntent.putExtra("DATE", date)
            saleCancelDetailIntent.putExtra("CUSTOMER_ID", customerID)
            saleCancelDetailIntent.putExtra("CUSTOMER_NAME", customerName)
            saleCancelDetailIntent.putIntegerArrayListExtra("INDEX_LIST", indexList)


            return saleCancelDetailIntent

        }

    }

    private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()

    private val saleCancelCheckOutAdapter: SaleCancelCheckoutAdapter by lazy {
        SaleCancelCheckoutAdapter()
    }
    private val saleCancelCheckOutViewModel: SaleCancelCheckOutViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        advancedPaidAmountLayout.visibility = View.GONE
        volDisForPreOrderLayout.visibility = View.GONE
        volumeDiscountLayout2.visibility = View.GONE
        llSaleStatus.visibility = View.GONE
        tableHeaderDiscount.text = "Promotion Price"
        soldProductList = intent.getParcelableArrayListExtra("SOLD_PRODUCT_LIST")
        indexList = intent.getIntegerArrayListExtra("INDEX_LIST")
        saleDateTextView.text = Utils.getCurrentDate(false)


        for (i in soldProductList) {

            val amt = i.quantity.toDouble() * i.product.selling_price!!.toDouble()
            totalAmt += amt
        }
        tvTotalAmount.text = totalAmt.toString()
        tvNetAmount.text = totalAmt.toString()
        tvInvoiceId.text = intent.getStringExtra("INVOICE_ID")
        saleCancelCheckOutAdapter.setNewList(soldProductList)
        rvSoldProductList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = saleCancelCheckOutAdapter
        }
        saleCancelCheckOutViewModel.taxPercentSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {

                taxPercent = it?.get(0)!!.tax!!
                taxType = it?.get(0)!!.tax_type.toString()
                netAmount = totalAmt
                val taxAmt = calculateTax()
                if (taxType.equals("E", ignoreCase = true)) {
                    tax_label_saleCheckout.text = "Tax (Exclude) : "
                    netAmount = totalAmt - totalDiscountAmount + taxAmt
                } else {
                    tax_label_saleCheckout.text = "Tax (Include) : "
                    netAmount = totalAmt - totalDiscountAmount
                }
                tax_txtView.text = Utils.formatAmount(taxAmt)


            })

        saleCancelCheckOutViewModel.taxPercentErrorState.observe(
            this,
            android.arch.lifecycle.Observer {
                i("Tag", it)
            })

        saleCancelCheckOutViewModel.loadTaxPercent()


        saleCancelCheckOutViewModel.soldInvoiceListSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {

                it?.let {
                    if (it.isNotEmpty()) {
                        startActivity(
                            PrintInvoiceActivity.newIntentFromSaleCancelCheckout(
                                this,
                                it[0],
                                soldProductList
                            )
                        )

                        saleCancelCheckOutViewModel.soldInvoiceListSuccessState.value = null
                        saleCancelCheckOutViewModel.soldInvoiceListErrorState.value = null

                    }
                }


            })

        saleCancelCheckOutViewModel.soldInvoiceListErrorState.observe(
            this,
            android.arch.lifecycle.Observer {
                Log.i("111", "11111111111")
            })


        btn_disOk.setOnClickListener { calculateDiscPercentToAmt() }
        btn_amtOk.setOnClickListener { calculateDiscAmtToPercent() }
        edt_dueDate.setOnClickListener { chooseDueDate() }
        confirmAndPrint_img.setOnClickListener {
            Utils.askConfirmationDialog(
                "Save",
                "Do you want to confirm?",
                "save",
                this,
                this::onClickSaveButton
            )
        }
        activity_sale_checkout_radio_bank.setOnCheckedChangeListener { button, isChecked ->
            bank_branch_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
            bank_account_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
        }


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

    private fun onClickSaveButton(type: String) {

        if (type == "save") {

            var paymentMethod = when (activity_sale_checkout_radio_group.checkedRadioButtonId) {
                R.id.activity_sale_checkout_radio_bank -> "B"
                R.id.activity_sale_checkout_radio_cash -> "CA"
                else -> ""
            }

            if (validationInput(paymentMethod == "B")) {

                if (refundAmount < 0 || payAmount.text.isBlank()) {
                    //setInvoiceId()
                    saveData("CR")
                } else {
                    //setInvoiceId()
                    saveData("CA")
                }

            }

        }
    }

    private fun saveData(cashOrLoanOrBank: String) {
        val invoiceId = intent.getStringExtra("INVOICE_ID")
        val customerId = intent.getStringExtra("CUSTOMER_ID")
        val saleDate = Utils.getCurrentDate(true)
        val payAmt = if (payAmount.text.isNotBlank()) payAmount.text.toString().toDouble() else 0.0
        val receiptPerson = receiptPerson.text.toString()
        val invoiceTime = Utils.getCurrentDate(true)
        val deviceId = Utils.getDeviceId(this)
        salePersonId = saleCancelCheckOutViewModel.getSaleManID()
        var dueDate = ""
        if (cashOrLoanOrBank == "CR") dueDate = saleDate
        if (edt_dueDate.text.isNotBlank()) dueDate = edt_dueDate.text.toString()
        //old code // saleCancelCheckOutViewModel.deleteInvoiceProductForLongClick(invoiceId,indexList)
//old code//        saleCancelCheckOutViewModel.saveCheckoutData(
//            customerId,
//            saleDate,
//            invoiceId,
//            payAmt,
//            refundAmount,
//            receiptPerson,
//            salePersonId!!,
//            invoiceTime,
//            dueDate,
//            deviceId,
//            cashOrLoanOrBank,
//            soldProductList,
//            totalAmt,
//            taxAmt,
//            edit_txt_branch_name.text.toString(),
//            edit_txt_account_name.text.toString(),
//            volDisAmount,
//            volDisPercent
//        )
//old code        saleCancelCheckOutViewModel.loadSoldInvoiceData(invoiceId)
        saleCancelCheckOutViewModel.deleteInvoiceData(invoiceId)
        saleCancelCheckOutViewModel.deleteInvoiceProductData(invoiceId)
        saleCancelCheckOutViewModel.saveCheckoutData(
            customerId,
            saleDate,
            invoiceId,
            payAmt,
            refundAmount,
            receiptPerson,
            salePersonId!!,
            invoiceTime,
            dueDate,
            deviceId,
            cashOrLoanOrBank,
            soldProductList,
            totalAmt,
            taxAmt,
            edit_txt_branch_name.text.toString(),
            edit_txt_account_name.text.toString(),
            volDisAmount,
            volDisPercent
        )
       saleCancelCheckOutViewModel.loadSoldInvoiceData(invoiceId)

    }


    private fun validationInput(withBankInfo: Boolean): Boolean {

        var dateAndPayment = false
        var name = false
        var bank = false
        var acc = false
        receiptPerson.error = null
        edit_txt_branch_name.error = null
        edit_txt_account_name.error = null

        if (edt_dueDate.text.isNotBlank() || payAmount.text.isNotBlank()) {

            dateAndPayment = true

        } else if (payAmount.text.isBlank()) {

            AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("You must specify due date.")
                .setPositiveButton("OK", null)
                .show()

        }

        if (receiptPerson.text.isNotBlank()) name = true
        else receiptPerson.error = "Please enter receipt person"

        return if (withBankInfo) {
            if (edit_txt_branch_name.text.isNotBlank()) bank = true
            else edit_txt_branch_name.error = "Please enter bank name"

            if (edit_txt_account_name.text.isNotBlank()) acc = true
            else edit_txt_account_name.error = "Please enter bank account"

            dateAndPayment && name && bank && acc
        } else {
            dateAndPayment && name
        }

    }

    private fun calculateTax(): Double {

        var taxAmt = 0.0
        if (taxPercent != 0)
            taxAmt = netAmount / 21

        this.taxAmt = taxAmt

        return taxAmt

    }
}




