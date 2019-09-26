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
import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.Customer
import com.example.dms.data.database.table.Date
import com.example.dms.data.database.table.Product
import com.example.dms.data.repositories.CheckOutRepository
import com.example.dms.di.injection
import com.example.dms.network.request.saleInvoice
import com.example.dms.ui.adapters.CheckOutAdapter
import com.example.dms.util.Utils
import com.example.dms.viewmodels.Factory.checkout.CheckOutMainViewModel
import com.example.dms.viewmodels.Factory.checkout.CheckOutMainViewModelFactory
import kotlinx.android.synthetic.main.activity_check_out.*
import java.io.Serializable

class CheckOutActivity : AppCompatActivity() {
    private lateinit var checkoutList: MutableList<saleInvoice>
    private lateinit var checkOutData: List<Product>
    private lateinit var invoiceData: List<Customer>
    private lateinit var checkOutRepo: CheckOutRepository
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
        fun getIntent(context: Context, filteredSaleItemList: MutableList<saleInvoice>): Intent {
            val intent = Intent(context, CheckOutActivity::class.java)
            intent.putExtra("CheckoutList", filteredSaleItemList as Serializable)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        val saleCheckoutList =
            intent.getSerializableExtra("CheckoutList") as MutableList<saleInvoice>
        this.checkoutList = saleCheckoutList
        //txtNetAmountCheckout.text=amt.text
        imgbtnCheckoutCancel.setOnClickListener { finish() }
        imgbtnCheckoutSC.setOnClickListener {

            //  val filteredList = checkoutViewModel.nullCheckingSalesItem()
            if (checkoutList.isNotEmpty()) {
                startActivity(
                    PrintActivity.getIntent(
                        this,
                        checkoutList,
                        txtTotalAmountCheckout.text.toString(),
                        edtDiscPercentCheckout.text.toString(),
                        edtDiscAmtCheckout.text.toString(),
                        txtNetAmountCheckout.text.toString(),
                        edtPayAmountCheckout.text.toString()
                    )
                )
                val amountPrice = txtNetAmountCheckout.text.toString()

                val checkoutId = Utils.getInvoiceId()
                val productId = checkOutData[0].Product_id
                var qty = checkOutData[0].total_qty
                val um = checkOutData[0].um_id

                var checkOutData =
                    CheckOut(checkoutId, productId, qty, um, amountPrice, "0", amountPrice)
                val checkOutDataList = mutableListOf<CheckOut>()
                checkOutDataList.add(checkOutData)
                checkOutRepo.saveCheckOutIntoDatabase(checkOutDataList)
                val customerId = invoiceData[0].CUSTOMER_ID
                var invoiceData = Date(checkoutId, customerId, amountPrice, "0", "0")
                val invoiceDataList = mutableListOf<Date>()
                invoiceDataList.add(invoiceData)

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
                txtRefundCheckout.text = "0000"
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


        txtTotalAmountCheckout.text = checkoutViewModel.calculateTotal(checkoutList).toString()

        checkoutViewModel.calculateNetAmount(edtDiscAmtCheckout.text.toString())
        checkoutViewModel.calculateTax(txtNetAmountCheckout.text.toString().toInt())
//
//        edtPayAmountCheckout.addTextChangedListener(object : TextWatcher{
//            override fun afterTextChanged(s: Editable?) {}
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (!s.isNullOrEmpty()){
//                    checkoutViewModel.calculateRefund(s?.toString().toInt(), txtNetAmountCheckout.text.toString().toInt())
//                } else{
//                    txtRefundCheckout.text = "0000"
//                }
//            }
//        })

    }
}
