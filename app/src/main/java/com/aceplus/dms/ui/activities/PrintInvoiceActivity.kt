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
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mService: BluetoothGattService? = null
    private val requestConnectDevice = 1
    private val REQUEST_ENABLE_BT = 2
    companion object
    {
        fun getIntent(context: Context,customerName:String,address:String,receiveNo:String,
                      saleInvoice:String,collectPerson:String,date:String,totalAmount:String,discount:String,
                      netAmount:String,
                      receive:String,position:Int):Intent
        {
            val printIntent = Intent(context, PrintInvoiceActivity::class.java)
            printIntent.putExtra("CustomerName", customerName)
            printIntent.putExtra("Address",address)
            printIntent.putExtra("ReceiveNo",receiveNo)
            printIntent.putExtra("SaleInvoice",saleInvoice)
            printIntent.putExtra("CollectPerson",collectPerson)
            printIntent.putExtra("Date",date)
            printIntent.putExtra("TotalAmount",totalAmount)
            printIntent.putExtra("Discount",discount)
            printIntent.putExtra("NetAmount",netAmount)
            printIntent.putExtra("Receive",receive)


            printIntent.putExtra("CUR_POS", position)
            printIntent.putExtra("CREDIT_FLG", "CREDIT")
            printIntent.putExtra("PRINT_MODE", "C")
            return printIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_sale_print)

        print_img.setOnClickListener {
            val serverIntent = Intent(
                this,
                DeviceListActivity::class.java
            )
            startActivityForResult(serverIntent, requestConnectDevice) }

        cancel_img.setOnClickListener {
            if (printMode.equals("RP", ignoreCase = true)) {
                val intent=Intent(this,CustomerActivity::class.java)
                startActivity(intent)

                finish()
            } else
                onBackPressed()
        }
    }

        var customerName = intent.getSerializableExtra("CustomerName") as String
        var address = intent.getSerializableExtra("Address") as String
        var receiveNo=intent.getSerializableExtra("ReceiveNo") as String
        var saleInvoice = intent.getSerializableExtra("SaleInvoice") as String
        var collectPerson = intent.getSerializableExtra("CollectPerson") as String
        var date=intent.getSerializableExtra("Date") as String
        var totalAmount = intent.getSerializableExtra("TotalAmount") as Double
        var discount = intent.getSerializableExtra("Discount") as Double
        var netAmount=intent.getSerializableExtra("NetAmount") as String
        var receive=intent.getSerializableExtra("Receive") as String
        var position = intent.getSerializableExtra("CUR_POS") as Int
        var flag=intent.getSerializableExtra("CREDIT_FLG") as String
        var printMode=intent.getSerializableExtra("PRINT_MODE") as String


//        if (printMode == "S" || printMode == "RP") {
//            saleDate.text = date.substring(0, 10)
//            invoiceId.text = saleInvoice
//            saleMan.text = collectPerson
//            branch.text = address
//
//            val totalAmtShow = totalAmount
//            print_totalAmount.text = Utils.formatAmount(totalAmtShow)
//            if (discount !== 0.0)
//                print_totalDiscount.setText(Utils.formatAmount(discount))
//            if (taxType.equals("E", ignoreCase = true)) {
//                if (totalAmtShow != null && invoie.getTotalDiscountAmt() != null) {
//                    netAmountTxtView.setText(Utils.formatAmount(totalAmtShow!! - invoie.getTotalDiscountAmt() + invoie.getTaxAmount()))
//                }
//            } else {
//                if (invoie.getTotalAmt() != null && invoie.getTotalDiscountAmt() != null) {
//                    netAmountTxtView.setText(Utils.formatAmount(totalAmtShow!! - invoie.getTotalDiscountAmt()))
//                }
//            }
//            prepaidAmountTxtView.setText(Utils.formatAmount(invoie.getTotalPayAmt()))
//            print_discountAmountTxtView.setText(
//                Utils.formatAmount(invoie.getTotalDiscountAmt()) + " (" + DecimalFormat(
//                    "#0.00"
//                ).format(invoie.getDiscountPercent()) + "%)"
//            )
//        } else if (printMode == "C") {
//            saleLayout.setVisibility(View.GONE)
//            salePrintHeaderLayout1.visibility = View.GONE
//            salePrintHeaderLayout2.visibility = View.GONE
//            creditPrintHeaderLayout.setVisibility(View.VISIBLE)
//            creditDate.setText(creditList.get(pos).getInvoiceDate().substring(0, 10))
//            creditInvoiceNo.setText(creditList.get(pos).getInvoiceNo())
//            creditCollectPerson.setText(getSaleManName(creditList.get(pos).getSaleManId()))
//            creditCustomerName.setText(getCustomerData(creditList.get(pos).getCustomerId()).getCustomerName())
//            creditTownshipName.setText(getCustomerTownshipName(creditList.get(pos).getCustomerId()))
//            creditRecievieNo.setText(creditList.get(pos).getInvoiceNo())
//
//            crediTotalAmount.setTextSize(30f)
//            crediDiscount.setTextSize(30f)
//            crediNetAmount.setTextSize(30f)
//            creditReceiveAmount.setTextSize(30f)
//
//            crediTotalAmount.setText(Utils.formatAmount(creditList.get(pos).getAmt()))
//            crediNetAmount.setText(Utils.formatAmount(creditList.get(pos).getAmt()))
//            creditReceiveAmount.setText(Utils.formatAmount(creditList.get(pos).getPayAmt()))
//            crediDiscount.setText("0.0 (0%)")
//        } else if (printMode == "D") {
//            salePrintHeaderLayout1.visibility = View.GONE
//            salePrintHeaderLayout2.visibility = View.GONE
//            deliveryHeaderLayout1.setVisibility(View.VISIBLE)
//            llPrepaid.setVisibility(View.VISIBLE)
//            val cusId = Integer.parseInt(orderedInvoice.getCustomerId())
//
//            deliveryCustomerNameTxt.setText(getCustomerData(cusId).getCustomerName())
//            deliveryTownshipNameTxt.setText(getCustomerTownshipName(cusId))
//            deliveryOrderNoTxt.setText(orderedInvoice.getInvoiceNo())
//            deliveryOrderPersonTxt.setText(getSaleManName(orderedInvoice.getSaleManId()))
//            deliveryInvoiceNoTxt.setText(invoie.getId())
//            deliveryPersonTxt.setText(getSaleManName(invoie.getSalepersonId()))
//            deliveryDateTxt.setText(invoie.getDate().substring(0, 10))
//
//            soldProductListView.setAdapter(SoldProductListRowAdapter(this))
//            setPromotionProductListView()
//            totalAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt()))
//            //            totalDiscountTxtView.setText(Utils.formatAmount(total_dis_Amt));
//            totalDiscountTxtView.setText(Utils.formatAmount(orderedInvoice.getDiscount()))
//            print_prepaidAmountShow.setText(Utils.formatAmount(orderedInvoice.getPaidAmount()))
//            if (taxType.equals("E", ignoreCase = true)) {
//                netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt() + invoie.getTaxAmount() - orderedInvoice.getPaidAmount()))
//            } else {
//                netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt() - orderedInvoice.getPaidAmount()))
//            }
//            prepaidAmountTxtView.setText(Utils.formatAmount(invoie.getTotalPayAmt()))
//            print_discountAmountTxtView.setText(
//                Utils.formatAmount(invoie.getTotalDiscountAmt()) + " (" + DecimalFormat(
//                    "#0.00"
//                ).format(invoie.getDiscountPercent()) + "%)"
//            )
//        } else if (printMode == "SR") {
//            txtSaleDate.setText(invoie.getDate().substring(0, 10))
//            txtInvoiceNo.setText(invoie.getId())
//            txtSaleMan.setText(getSaleManName(invoie.getSalepersonId()))
//            txtBranch.setText(branchCode)
//            soldProductListView.setAdapter(
//                PrintInvoiceActivityWithHSPOS.SoldProductListRowAdapter(
//                    this
//                )
//            )
//            setPromotionProductListView()
//
//            if (invoie.getTotalAmt() != null) {
//                totalAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt()))
//                totalDiscountTxtView.setText(Utils.formatAmount(total_dis_Amt))
//            }
//            if (invoie.getTotalDiscountAmt() !== 0.0)
//                totalDiscountTxtView.setText(Utils.formatAmount(invoie.getTotalDiscountAmt()))
//            if (taxType.equals("E", ignoreCase = true)) {
//                if (invoie.getTotalAmt() != null && invoie.getTotalDiscountAmt() != null) {
//                    netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt() + invoie.getTaxAmount()))
//                }
//            } else {
//                if (invoie.getTotalAmt() != null && invoie.getTotalDiscountAmt() != null) {
//                    netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt()))
//                }
//            }
//
//            if (invoie.getTotalPayAmt() != null) {
//                prepaidAmountTxtView.setText(Utils.formatAmount(invoie.getTotalPayAmt()))
//            }
//
//            if (invoie.getTotalDiscountAmt() != null) {
//                print_discountAmountTxtView.setText(
//                    Utils.formatAmount(invoie.getTotalDiscountAmt()) + " (" + DecimalFormat(
//                        "#0.00"
//                    ).format(invoie.getDiscountPercent()) + "%)"
//                )
//            }
//        }


    }

