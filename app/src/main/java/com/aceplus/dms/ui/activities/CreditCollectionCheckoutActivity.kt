package com.aceplus.dms.ui.activities

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.creditcollectionadapters.CreditCollectionCheckOutAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.creditcollection.CreditCollectionCheckOutViewModel
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.model.credit.CreditInvoice
import kotlinx.android.synthetic.main.activity_credit_collection.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class CreditCollectionCheckoutActivity : AppCompatActivity(), KodeinAware {
    private var invoiceNo: String? = null
    private var date: String? = null
     var creditInvoiceList = ArrayList<Credit>()

    companion object {
        fun getIntent(
            context: Context,
            customerId: String,
            customerName: String,
            address: String
        ): Intent {
            val creditCheckOutIntent = Intent(context, CreditCollectionCheckoutActivity::class.java)
            creditCheckOutIntent.putExtra("CustomerID", customerId)
            creditCheckOutIntent.putExtra("CustomerName", customerName)
            creditCheckOutIntent.putExtra("CustomerAddress", address)
            return creditCheckOutIntent
        }
    }

    override val kodein: Kodein by kodein()
    private val creditCollectionCheckOutAdapter: CreditCollectionCheckOutAdapter by lazy {
        CreditCollectionCheckOutAdapter(this::onClickNoticeListItem)
    }

    private fun onClickNoticeListItem(data: Credit) {
        total_pay_layout.visibility = View.GONE
        item_pay_layout.visibility = View.VISIBLE
        date_txt.text = data.invoice_date
        invno_txt.text = data.invoice_no
        item_pay_edit.setText("")
        item_pay_edit!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var tempPayAmount = 0.0
                var tempNetAmount = 0.0
                if (s.toString() != "" && invno_total_amount_txt.text.toString() != "" && !s.toString().endsWith(
                        "."
                    )
                ) {
                    tempPayAmount =
                        java.lang.Double.parseDouble(s.toString().replace(",", ""))
                    tempNetAmount = java.lang.Double.parseDouble(
                        invno_total_amount_txt.text.toString().replace(
                            ",",
                            ""
                        )
                    )
                } else {
                    refund_txt.text = "0"
                }

                if (tempPayAmount > tempNetAmount) {
                    refund_txt.text = Utils.formatAmount(tempPayAmount - tempNetAmount)
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && !s.toString().endsWith(".")) {
                    val convertedString = s.toString()
                    if (item_pay_edit!!.text.toString() != convertedString && convertedString.isNotEmpty()) {
                        item_pay_edit!!.setText(convertedString)
                        item_pay_edit!!.setSelection(item_pay_edit!!.text.length)
                    }
                }
            }
        })
        }





    private val creditCollectionCheckOutViewModel: CreditCollectionCheckOutViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(CreditCollectionCheckOutViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_collection)

        side_total_amt_layout!!.visibility = View.GONE
        side_credit_amt_layout!!.visibility = View.GONE
        side_pay_amt_layout!!.visibility = View.GONE

        var customerId = intent.getSerializableExtra("CustomerID") as String
        var customerName = intent.getSerializableExtra("CustomerName") as String
        var customerAddress = intent.getSerializableExtra("CustomerAddress") as String

        cancel_img.setOnClickListener {
            onBackPressed()
            true
        }
//        save_img.setOnClickListener {
//
//
//            Utils.askConfirmationDialog("Save", "Do you want to confirm?", "", this) {
//
//                if (receipt_person_edit.text.toString().isEmpty()) {
//
//                    AlertDialog.Builder(this)
//                        .setTitle("Alert")
//                        .setMessage("Your must provide 'Receipt Person'.")
//                        .setPositiveButton("OK") { arg0, arg1 ->
//                            receipt_person_edit.requestFocus()
//                        }
//                        .show()
//
//                }
//                if (total_pay_layout.visibility == View.VISIBLE) {
//                if (payment_amount_edit.text.toString() == "") {
//                    payment_amount_edit.error = "Please enter pay amount"
//                } else {
//                    val creditInvoiceList = calculatePayAmount(payment_amount_edit.text.toString())
////                    insertIntoDB(creditInvoiceList)
////                    toPrintActivity(creditInvoiceList, listviewPosition)
//                }
//
//            } else {
//                if (item_pay_edit.text.toString() == "") {
//                    item_pay_edit.error = "Please enter pay amount"
//                } else {
//                    val creditInvoiceList = calculatePayAmount(item_pay_edit.text.toString())
////                    insertIntoDB(creditInvoiceList)
////                    toPrintActivity(creditInvoiceList, listviewPosition)
//                }
//            }
//            }
//        }

        creditCollectionCheckOutViewModel.creditCollectionCheckOutSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                creditCollectionCheckOutAdapter.setNewList(it as ArrayList<Credit>)
                var totalUnpaid = 0.0
                var total = 0.0
                var totalPaid = 0.0

                for (i in it) {
                    var amt = i.amount.toDouble()
                    var paid = i.pay_amount.toDouble()
                    var unpaid = amt - paid
                    total += amt
                    totalPaid += paid
                    totalUnpaid += unpaid

                }
                total_amount_txt.text = Utils.formatAmount(total)
                total_advance_pay_txt.text = Utils.formatAmount(totalPaid)
                remaining_pay_amount_txt.text = Utils.formatAmount(totalUnpaid)
                customer_name_txt.text = customerName
                payment_amount_edit!!.setText(null)
                item_pay_edit!!.setText(null)


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
    //private fun calculatePayAmount(payAmt: String): List<CreditInvoice> {

//        var payAmount:Double =payAmt.replace(",", "").toDouble()
//        val remainList = ArrayList<CreditInvoice>()
//        val tempCreditList = ArrayList<CreditInvoice>()
//        tempCreditList.addAll(creditInvoiceList)
//
//        for (creditInvoice in creditInvoiceList) {
//            val creditAmount = creditInvoice.amount - creditInvoice.pay_amount
//
//            if (payAmount != 0.0 && payAmount < creditAmount) {
//                creditInvoice.pay_amount=payAmount
//                payAmount = 0.0
//                remainList.add(creditInvoice)
//
//            } else if (payAmount != 0.0 && payAmount > creditAmount) {
//                payAmount -= creditAmount
//                creditInvoice.pay_amount=creditAmount
//                tempCreditList.remove(creditInvoice)
//                remainList.add(creditInvoice)
//
//            } else if (payAmount != 0.0 && payAmount == creditAmount) {
//                payAmount -= creditAmount
//                creditInvoice.pay_amount=creditAmount
//                tempCreditList.remove(creditInvoice)
//                remainList.add(creditInvoice)
//            }

//        }
//
//        Log.i("REMAIN -> ", payAmount.toString() + "")
//        Log.i("Remain item -> ", remainList.size.toString() + "")
//        Log.i("item remove -> ", tempCreditList.size.toString() + "")
//
//        return remainList
//    }

        //private fun insertIntoDB(invoiceList: List<Credit>) {

    }





