package com.aceplus.dms.ui.activities

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
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.creditcollectionadapters.CreditCollectionCheckOutAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.creditcollection.CreditCollectionCheckOutViewModel
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.domain.entity.credit.Credit
import kotlinx.android.synthetic.main.activity_credit_collection.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class CreditCollectionCheckoutActivity : AppCompatActivity(), KodeinAware {
    private var invoiceNo: String? = null
    private var date: String? = null
    var calculateList = mutableListOf<Credit>()
    override val kodein: Kodein by kodein()
    private val creditCollectionCheckOutAdapter: CreditCollectionCheckOutAdapter by lazy {
        CreditCollectionCheckOutAdapter(this::onClickNoticeListItem)
    }
    private val creditCollectionCheckOutViewModel: CreditCollectionCheckOutViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(CreditCollectionCheckOutViewModel::class.java)
    }

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
                        s.toString().replace(",", "").toDouble()
                    tempNetAmount =
                        invno_total_amount_txt.text.toString().replace(
                            ",",
                            ""
                        ).toDouble()

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

        payment_amount_edit!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var tempPayAmount = 0.0
                var tempNetAmount = 0.0
                if (s.toString() != "" && invno_total_amount_txt.text.toString() != "" && !s.toString().endsWith(
                        "."
                    )
                ) {
                    tempPayAmount =
                        s.toString().replace(",", "").toDouble()
                    tempNetAmount =
                        invno_total_amount_txt.text.toString().replace(
                            ",",
                            ""
                        ).toDouble()

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
                    if (payment_amount_edit.text.toString() != convertedString && convertedString.isNotEmpty()) {
                        payment_amount_edit.setText(convertedString)
                        payment_amount_edit.setSelection(payment_amount_edit.text.length)
                    }
                }
            }
        })


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


                    }
                }
            }
        }
                    creditCollectionCheckOutViewModel.creditCollectionCheckOutSuccessState.observe(
                        this,
                        android.arch.lifecycle.Observer {
                            creditCollectionCheckOutAdapter.setNewList(it as ArrayList<Credit>)
                            var totalUnpaid = 0.0
                            var total = 0.0
                            var totalPaid = 0.0

                            for (i in it) {
                                var amt = i.amount
                                var paid = i.pay_amount
                                var unpaid = amt - paid
                                total += amt
                                totalPaid += paid
                                totalUnpaid += unpaid
                           //creditCollectionCheckOutViewModel.updatePayAmount(payment_amount_edit.text.toString().toDouble(),i.invoice_no!!)


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
            }












