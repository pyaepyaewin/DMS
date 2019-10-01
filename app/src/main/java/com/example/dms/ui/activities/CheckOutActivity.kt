package com.example.dms.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dms.R
import com.example.dms.data.database.table.*
import com.example.dms.di.injection
import com.example.dms.network.response.Customer
import com.example.dms.ui.adapters.CheckOutAdapter
import com.example.dms.util.Utils
import com.example.dms.viewmodels.Factory.checkout.CheckOutMainViewModel
import com.example.dms.viewmodels.Factory.checkout.CheckOutMainViewModelFactory
import kotlinx.android.synthetic.main.activity_check_out.*
import java.io.Serializable

class CheckOutActivity : AppCompatActivity() {
    private lateinit var checkoutList: MutableList<InvoiceItem>
    private val checkoutAdp: CheckOutAdapter by lazy {
        CheckOutAdapter(checkoutList)
    }

    private val checkoutViewModel: CheckOutMainViewModel by lazy {
        ViewModelProvider(
            this,
            CheckOutMainViewModelFactory(injection.provideCheckOutRepository(this), this)
        )
            .get(CheckOutMainViewModel::class.java)

    }

    companion object {
        fun getIntent(
            context: Context,
            filteredList: MutableList<InvoiceItem>,
            customer: Customer
        ): Intent {
            val intent = Intent(context, CheckOutActivity::class.java)
            intent.putExtra("CheckoutList", filteredList as Serializable)
            intent.putExtra("Customer", customer)
            return intent
        }
//
//        fun getCustomerIntent(context: Context, customer: Customer): Intent {
//            val intent1 = Intent(context, CheckOutActivity::class.java)
//            intent1.putExtra("Customer", customer)
//            return intent1
//        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        val customer = intent.getSerializableExtra("Customer") as Customer
        val saleCheckoutList =
            intent.getSerializableExtra("CheckoutList") as MutableList<InvoiceItem>

        this.checkoutList = saleCheckoutList

        txtSalesDateCheckout.text = Utils.getCurrentDate()
        txtInvoiceIDCheckout.text = Utils.getInvoiceId()
        txtTotalAmountCheckout.text = checkoutViewModel.calculateTotal(checkoutList).toString()

        for (item in checkoutList){
            item.invoiceId = txtInvoiceIDCheckout.text.toString()
        }
        //txtNetAmountCheckout.text=amt.text
        imgbtnCheckoutCancel.setOnClickListener { finish() }
        imgbtnCheckoutSC.setOnClickListener {


            if (checkoutList.isNotEmpty()) {
                checkoutViewModel.saveData(
                    txtInvoiceIDCheckout.text.toString(),
                    customer.CUSTOMER_ID,
                    txtSalesDateCheckout.text.toString(),
                    txtNetAmountCheckout.text.toString(),
                    edtDiscPercentCheckout.text.toString(),
                    edtDiscAmtCheckout.text.toString(),
                    checkoutList
                )
                Toast.makeText(this,"checkout save",Toast.LENGTH_SHORT).show()
//                startActivity(
//                    PrintActivity.getIntent(
//                        this,checkoutList
//                    )
//                )
//                val intent=Intent(this,PrintActivity::class.java)
//                startActivity(intent)

            } else {
                Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show()
            }
        }

        checkoutViewModel.errorState.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        checkoutViewModel.discAmount.observe(this, Observer {
            edtDiscAmtCheckout.setText(it.toString())
            checkoutViewModel.calculateNetAmount(edtDiscAmtCheckout.text.toString())
        })



        checkoutViewModel.netAmount.observe(this, Observer {
            txtNetAmountCheckout.text = it.toString()
            if (!edtPayAmountCheckout.text.isNullOrEmpty()) {
                checkoutViewModel.calculateRefund(
                    edtPayAmountCheckout.text.toString().toInt(),
                    txtNetAmountCheckout.text.toString().toInt()
                )
            } else {
                txtRefundCheckout.text = "1111"
            }
            checkoutViewModel.calculateTax(txtNetAmountCheckout.text.toString().toInt())
        })

        checkoutViewModel.refund.observe(this, Observer {
            txtRefundCheckout.text = it.toString()

        })

        checkoutViewModel.tax.observe(this, Observer {
            txtTaxCheckout.text = it.toString()
        })

        rvCheckoutList.adapter = checkoutAdp
        rvCheckoutList.layoutManager = LinearLayoutManager(this)


        checkoutViewModel.calculateNetAmount(edtDiscAmtCheckout.text.toString())
        checkoutViewModel.calculateTax(txtNetAmountCheckout.text.toString().toInt())


    }
}
