package com.aceplus.dms.ui.activities.creditcollection

import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.PrintInvoiceActivity
import com.aceplus.dms.ui.adapters.creditcollectionadapters.CreditCollectionCheckOutAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.creditcollection.CreditCollectionCheckOutViewModel
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.model.credit.CreditInvoice
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_credit_collection.*
import kotlinx.android.synthetic.main.activity_sale_checkout.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreditCollectionCheckoutActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    override val layoutId: Int
        get() = R.layout.activity_credit_collection
    private var listViewPosition = 0
    var calculateList = mutableListOf<Credit>()
    var total = 0.0
    private val df = DecimalFormat(".##")
    private var refundAmount: Double = 0.0
    private var creditDate: String=""
    private var invoiceId:String=""
    private var invoiceAmount: Double = 0.0

    var data1 : Credit = Credit()

    private val creditCollectionCheckOutAdapter: CreditCollectionCheckOutAdapter by lazy {
        CreditCollectionCheckOutAdapter(this::onClickNoticeListItem)
    }
    private val creditCollectionCheckOutViewModel: CreditCollectionCheckOutViewModel by viewModel()


    companion object {
        fun getIntent(
            context: Context,
            customerId: String,
            customerName: String

        ): Intent {
            val creditCheckOutIntent = Intent(context, CreditCollectionCheckoutActivity::class.java)
            creditCheckOutIntent.putExtra("CustomerID", customerId)
            creditCheckOutIntent.putExtra("CustomerName", customerName)

            return creditCheckOutIntent
        }
    }


    private fun onClickNoticeListItem(data: Credit) {
         data1 = data
//        total_pay_layout.visibility = View.GONE
//        item_pay_layout.visibility = View.VISIBLE
        date_txt.text = data.invoice_date
        invno_txt.text = data.invoice_no
        item_pay_edit.setText("")
        invoiceAmount=data.amount
        item_pay_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var tempPayAmount = 0.0
                var tempNetAmount = 0.0
                if (p0.toString() != "" && invno_total_amount_txt.text.toString() != "" && !p0.toString().endsWith(
                        "."
                    )
                ) {
                    tempPayAmount =
                        p0.toString().replace(",", "").toDouble()
                    tempNetAmount =invoiceAmount

                } else {
                    refund_txt.text = "0"
                }

                if (tempPayAmount > tempNetAmount) {
                    refund_txt.text = Utils.formatAmount(tempPayAmount - tempNetAmount)
                }            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                "Nothing to do"
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty() && !p0.toString().endsWith(".")) {
                    val convertedString = p0.toString()
                    //                    convertedString = Utils.formatAmount(Double.parseDouble(charSequence.toString().replace(",", "")));
                    if (item_pay_edit.text.toString() != convertedString && convertedString.isNotEmpty()) {
                        item_pay_edit.setText(convertedString)
                        item_pay_edit.setSelection(item_pay_edit.text.length)
                    }
                }
            }
        })



    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val saleManId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, this)
        val salePersonName = AppUtils.getStringFromShp(Constant.SALEMAN_NAME, this)
        side_total_amt_layout!!.visibility = View.GONE
        side_credit_amt_layout!!.visibility = View.GONE
        side_pay_amt_layout!!.visibility = View.GONE
        creditDate = SimpleDateFormat("yyyy/MM/dd").format(Date())

        var customerId = intent.getSerializableExtra("CustomerID") as String
        var customerName = intent.getSerializableExtra("CustomerName") as String
        var townShipName = creditCollectionCheckOutViewModel.getTownShipName(customerId.toInt())


        payment_amount_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

                var tempPayAmount = 0.0
                var tempNetAmount = 0.0
                if (p0.toString() != "" && invno_total_amount_txt.text.toString() != "" && !p0.toString().endsWith(
                        "."
                    )
                ) {
                    tempPayAmount =
                      p0.toString().replace(",", "").toDouble()
                    tempNetAmount =
                        total_amount_txt.text.toString().replace(
                            ",",
                            ""
                        ).toDouble()
                }
                else {
                    refund_txt.text = "0"
                }

                if (tempPayAmount > tempNetAmount) {
                    refund_txt.text = Utils.formatAmount(tempPayAmount - tempNetAmount)
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                "Nothing to do"
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (p0!!.isNotEmpty()){
//                    calculateRefundAmount()
//                }
                if (p0.toString().isNotEmpty() && !p0.toString().endsWith(".")) {
                    val convertedString = p0.toString()
                    //                    convertedString = Utils.formatAmount(Double.parseDouble(charSequence.toString().replace(",", "")));
                    if (payment_amount_edit.text.toString() != convertedString && convertedString.isNotEmpty()) {
                        payment_amount_edit.setText(convertedString)
                        payment_amount_edit.setSelection(payment_amount_edit.text.length)
                    }
                }




//                val convertedString = p0.toString()
//                if (payment_amount_edit.text.toString() != convertedString && convertedString.isNotEmpty()) {
//                    payment_amount_edit.setText(convertedString)
//                    payment_amount_edit.setSelection(payment_amount_edit.text.length)
//                }
            }

        })

        //val customerName=creditCollectionCheckOutViewModel.getCustomerName(customerId)

        cancel_img.setOnClickListener {
            onBackPressed()
            true
        }
        save_img.setOnClickListener {
            Utils.askConfirmationDialog("Save", "Do you want to confirm?", "", this) {

                if (receipt_person_edit.text.toString().isEmpty()) {

                    AlertDialog.Builder(this)
                        .setTitle("Alert")
                        .setMessage("Your must provide 'Receipt Person'.")
                        .setPositiveButton("OK") { arg0, arg1 ->
                            receipt_person_edit.requestFocus()
                        }
                        .show()

                }
                if (total_pay_layout.visibility == View.VISIBLE) {
                    if (payment_amount_edit.text.toString() == "") {
                        payment_amount_edit.error = "Please enter pay amount"
                    } else {
                        calculateList =
                            creditCollectionCheckOutViewModel.calculatePayAmount(payment_amount_edit.text.toString()) as MutableList<Credit>
                        creditCollectionCheckOutViewModel.insertCashReceiveData(calculateList)
                        val creditInvoiceList = mutableListOf<CreditInvoice>()
                        calculateList.map {
                            val creditInvoice = CreditInvoice()
                            creditInvoice.id = it.id
                            creditInvoice.amt = it.amount
                            creditInvoice.customerId = it.customer_id
                            creditInvoice.creditAmt = it.amount - it.pay_amount
                            creditInvoice.invoiceDate = it.invoice_date
                            creditInvoice.invoiceNo = it.invoice_no
                            creditInvoice.invoiceStatus = it.invoice_status
                            creditInvoice.payAmt = it.pay_amount
                            creditInvoice.refund = it.refund
                            creditInvoice.saleManId = it.sale_man_id!!.toInt()
                            creditInvoice.saleStatus = it.sale_status
                            creditInvoiceList.add(creditInvoice)
                        }

                        startActivityForResult(
                            PrintInvoiceActivity.getIntentFromCredit(
                                this,
                                creditInvoiceList,
                                listViewPosition,
                                townShipName,
                                salePersonName.toString(),
                                customerName
                            ),Utils.RQ_BACK_TO_CUSTOMER
                        )
                    }
                }
            }
        }
        creditCollectionCheckOutViewModel.creditCollectionCheckOutSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                creditCollectionCheckOutAdapter.setNewList(it as ArrayList<Credit>)
                var totalUnpaid = 0.0
                var totalPaid = 0.0
                total = 0.0

                for (i in it) {
                    var amt = i.amount
                    var paid = i.pay_amount
                    var unpaid = amt - paid
                    total += amt
                    totalPaid += paid
                    totalUnpaid += unpaid
                //    creditCollectionCheckOutViewModel.updatePayAmount(payment_amount_edit.text.toString().toDouble(),i.invoice_no!!)


                }
                total_amount_txt.text = Utils.formatAmount(total)
                total_advance_pay_txt.text = Utils.formatAmount(totalPaid)
                remaining_pay_amount_txt.text = Utils.formatAmount(totalUnpaid)
                customer_name_txt.text = customerName
                payment_amount_edit!!.text = null
                item_pay_edit!!.text = null

            })



        creditCollectionCheckOutViewModel.creditCollectionCheckOutErrorState.observe(
            this,
            android.arch.lifecycle.Observer {
            })
        rvCreditCheckOut.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = creditCollectionCheckOutAdapter
        }
        creditCollectionCheckOutViewModel.loadCreditCollectionCheckOut(customerId)


    }



    fun calculateRefundAmount() {

       if (data1.invoice_no != null && payment_amount_edit.text.isNotBlank()){
          val payAmount = payment_amount_edit.text.toString().toDouble()
           if(payAmount>invoiceAmount)
           {
               var refund=payAmount-invoiceAmount
               refund_txt.text = Utils.formatAmount(refund)
           }
     else
           {
               refund_txt.text="0"
           }
           // Toast.makeText(this,(payAmount - invoiceAmount).toString(),Toast.LENGTH_SHORT).show()
        }
       else if(payment_amount_edit.text.isNotBlank()){
            val payAmount = payment_amount_edit.text.toString().toDouble()
           if(payAmount>invoiceAmount)
           {
               var refund=payAmount-invoiceAmount
               refund_txt.text = Utils.formatAmount(refund)
           }
           else
           {

               refund_txt.text="0"
           }

          // Toast.makeText(this,(payAmount - total).toString(),Toast.LENGTH_SHORT).show()
      }

//        if (payment_amount_edit.text.isNotBlank()) {
//            val payAmount = payment_amount_edit.text.toString().toDouble()
//            var refundAmount = payAmount - total
//
//            if (refundAmount >= 0) {
//                refundAmount = df.format(refundAmount).toDouble()
//                refund_txt.text = Utils.formatAmount(refundAmount)
//            } else refund_txt.text = "0"
//
//            this.refundAmount = refundAmount
//        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Utils.RQ_BACK_TO_CUSTOMER)
            if (resultCode == Activity.RESULT_OK){
                setResult(Activity.RESULT_OK)
                finish()
            }
    }
}












