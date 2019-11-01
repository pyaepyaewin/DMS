package com.aceplus.dms.ui.activities

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGattService
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.customer.CustomerActivity
import com.aceplus.dms.utils.Utils
import kotlinx.android.synthetic.main.activity_sale_print.*
import java.io.Serializable
import java.text.DecimalFormat

class PrintInvoiceActivity : AppCompatActivity() {



    companion object {
        fun getIntent(
            context: Context,
            customerName: String,
            address: String,
            receiveNo: String,
            collectPerson: String,
            date: String,
            totalAmount: String,
            discount: String,
            receive: String
        ): Intent {
            val printIntent = Intent(context, PrintInvoiceActivity::class.java)
            printIntent.putExtra("CustomerName", customerName)
            printIntent.putExtra("Address", address)
            printIntent.putExtra("ReceiveNo", receiveNo)
            printIntent.putExtra("CollectPerson", collectPerson)
            printIntent.putExtra("Date", date)
            printIntent.putExtra("TotalAmount", totalAmount)
            printIntent.putExtra("Discount", discount)
            printIntent.putExtra("Receive", receive)


            printIntent.putExtra("CREDIT_FLG", "CREDIT")
            printIntent.putExtra("PRINT_MODE", "C")
            return printIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_print)

        crditPrintHeaderLayout1.visibility=View.VISIBLE

    }
}


