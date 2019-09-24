package com.example.dms.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dms.R
import com.example.dms.network.request.saleInvoice
import com.example.dms.ui.adapters.PrintAdapter
import com.example.dms.viewmodels.Factory.print.PrintMainViewModel
import com.example.dms.viewmodels.Factory.print.PrintMainViewModelFactory
import kotlinx.android.synthetic.main.activity_print.*
import kotlinx.android.synthetic.main.activity_sale.*
import kotlinx.android.synthetic.main.print.*
import java.io.Serializable

class PrintActivity : AppCompatActivity() {
    private lateinit var checkoutList: MutableList<saleInvoice>

    private val printViewModel: PrintMainViewModel by lazy {
        ViewModelProvider(this, PrintMainViewModelFactory()).get(PrintMainViewModel::class.java)
    }

    private val printListAdapter: PrintAdapter by lazy {
        PrintAdapter(checkoutList)
    }

    companion object{
        fun getIntent(context: Context,
                      filteredSaleItemList: MutableList<saleInvoice>,
                      totalAmt: String,
                      discPercent: String,
                      discAmt: String,
                      netAmt: String,
                      receive: String): Intent{
            val intent =  Intent(context, PrintActivity::class.java)
            intent.putExtra("printList", filteredSaleItemList as Serializable)
            intent.putExtra("totalAmt", totalAmt)
            intent.putExtra("discPercent", discPercent)
            intent.putExtra("discAmt", discAmt)
            intent.putExtra("netAmt", netAmt)
            intent.putExtra("receive", receive)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)

        this.checkoutList = intent.getSerializableExtra("printList") as MutableList<saleInvoice>

//        btnclose.setOnClickListener { finish() }
//        btnCheck.setOnClickListener {
//            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
//        }

        rvPrint.adapter = printListAdapter
        rvPrint.layoutManager = LinearLayoutManager(this)

        totalAmount.text = intent.getStringExtra("totalAmt")
        netAmt.text = intent.getStringExtra("netAmt")
        receive.text = intent.getStringExtra("receive")
        dis.text = "${intent.getStringExtra("discAmt")} (${intent.getStringExtra("discPercent")}%)"

    }
}
