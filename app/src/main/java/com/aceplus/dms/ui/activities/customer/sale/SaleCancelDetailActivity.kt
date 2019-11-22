package com.aceplus.dms.ui.activities.customer.sale

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import com.aceplus.dms.ui.adapters.sale.SaleCancelDetailAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.salecancelviewmodel.SaleCancelDetailViewModel
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.sale.salecancel.SaleCancelDetailItem
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import com.aceplus.domain.vo.SoldProductInfo
import kotlinx.android.synthetic.main.activity_sale.*
import kotlinx.android.synthetic.main.dialog_box_sale_quantity.*
import kotlinx.android.synthetic.main.dialog_box_sale_quantity.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R
import android.content.ContentValues
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceCancel
import com.aceplus.domain.entity.invoice.InvoiceCancelProduct
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.forApi.invoice.InvoiceDetail
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import io.fabric.sdk.android.services.common.CommonUtils.isNullOrEmpty


class SaleCancelDetailActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    var soldProductList1 = mutableListOf<String>()
    val invoiceCancelProductList: ArrayList<InvoiceCancelProduct> = ArrayList()
    var invoiceCancelList = ArrayList<InvoiceCancel>()
    private var invoiceList: ArrayList<InvoiceCancel> = ArrayList()
    var invoice = Invoice()
    var indexList = arrayListOf<Int>()
    var soldInvoiceDataList = mutableListOf<Invoice>()

    //    val customerId = intent.getStringExtra("CUSTOMER_ID")
    var soldProductDataList = mutableListOf<SaleCancelDetailItem>()
    private var isPreOrder: Boolean = false
    private var duplicateProductList = mutableListOf<SoldProductInfo>()


    lateinit var alertDialog1: AlertDialog

    companion object {
        fun getIntent(
            context: Context,
            invoiceID: String,
            date: String,
            customerId: String,
            customerName: String
        ): Intent {
            val saleCancelIntent = Intent(context, SaleCancelDetailActivity::class.java)
            saleCancelIntent.putExtra("INVOICE_ID", invoiceID)
            saleCancelIntent.putExtra("INVOICE_DATE", date)
            saleCancelIntent.putExtra("CUSTOMER_ID", customerId)
            saleCancelIntent.putExtra("CUSTOMER_NAME", customerName)
            return saleCancelIntent
        }
    }

    private val saleCancelDetailViewModel: SaleCancelDetailViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(SaleCancelDetailViewModel::class.java)
    }

    private val saleCancelDetailAdapter: SaleCancelDetailAdapter by lazy {
        SaleCancelDetailAdapter(this::onClickQtyButton, this::onLongClickSoldProductListItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.aceplus.dms.R.layout.activity_sale)
        tvTitle.text = "SALES CANCEL"
        tableHeaderOrderedQty.visibility = View.GONE
        headerFoc.visibility = View.GONE
        headerDiscount.visibility = View.GONE
        alertDialogWithRadioButtons()
        var invoiceid = intent.getStringExtra("INVOICE_ID")
        var invoicedate = intent.getStringExtra("INVOICE_DATE")
        var customerId = intent.getStringExtra("CUSTOMER_ID")
        var customerName = intent.getStringExtra("CUSTOMER_NAME")

        cancelImg.setOnClickListener {
            onBackPressed()
            true
        }
        checkoutImg.setOnClickListener {
            startActivity(
                SaleCancelCheckoutActivity.getSaleCancelDetailIntent(
                    this,
                    saleCancelDetailAdapter.getDataList() as ArrayList,
                    invoiceid,
                    invoicedate,
                    customerId,
                    customerName,
                    indexList
                )
            )
        }
        saleCancelDetailViewModel.productIdListSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {

                soldProductList1 = it as MutableList<String>

                saleCancelDetailViewModel.loadSoldProductList(it)


            })

        saleCancelDetailViewModel.productIdListErrorState.observe(
            this,
            android.arch.lifecycle.Observer {

            })

        saleCancelDetailViewModel.loadSoldProductIdList(invoiceid)
        saleCancelDetailViewModel.invoiceCancelSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                it?.let {
                    if (it != null) {
                        invoice = it!!
                        saleCancelDetailViewModel.saveDeleteInvoice(
                            saleCancelDetailAdapter.getDataList() as ArrayList<SoldProductInfo>,
                            invoice,
                            invoiceid
                        )
                    }
                }


            })

        saleCancelDetailViewModel.invoiceCancelErrorState.observe(
            this,
            android.arch.lifecycle.Observer {

            })

        saleCancelDetailViewModel.calculatedSoldProductList.observe(this, Observer {
            if (it != null) {
                saleCancelDetailAdapter.setNewList(it.first)
                tvNetAmount.text = Utils.formatAmount(it.second)
                saleCancelDetailViewModel.calculatedSoldProductList.value = null
            }
        })

        saleCancelDetailViewModel.soldProductListSuccessState.observe(
            this,
            android.arch.lifecycle.Observer { it ->
                if (it!!.isNotEmpty()) {
                    soldProductDataList = it as MutableList<SaleCancelDetailItem>
                    var soldProductInfoList = ArrayList<SoldProductInfo>()
                    soldProductDataList.map {
                        val soldProductInfo = SoldProductInfo()
                        soldProductInfo.product = Product()
                        soldProductInfo.product.id = it.id
                        soldProductInfo.product.product_id = it.product_id
                        soldProductInfo.product.product_name = it.product_name
                        soldProductInfo.product.um = it.um
                        soldProductInfo.product.selling_price = it.selling_price
                        soldProductInfo.product.category_id = it.category_id
                        soldProductInfo.product.class_id = it.class_id
                        soldProductInfo.product.delivery_quantity = it.delivery_quantity
                        soldProductInfo.product.device_issue_status = it.device_issue_status
                        soldProductInfo.product.discount_type = it.discount_type
                        soldProductInfo.product.exchange_quantity = it.exchange_quantity
                        soldProductInfo.product.group_id = it.group_id
                        soldProductInfo.product.order_quantity = it.order_quantity
                        soldProductInfo.product.present_quantity = it.present_quantity
                        soldProductInfo.product.purchase_price = it.purchase_price
                        soldProductInfo.product.remaining_quantity = it.remaining_quantity
                        soldProductInfo.product.return_quantity = it.return_quantity
                        soldProductInfo.product.sold_quantity = it.sold_quantity
                        soldProductInfo.product.total_quantity = it.total_quantity
                        soldProductInfo.totalAmt = it.total_amount
                        soldProductInfo.promotionPrice = it.promotion_price
                        soldProductInfo.quantity = it.sale_quantity.toInt()
                        soldProductInfo.discountAmount = it.discount_amount
                        soldProductInfo.discountPercent = it.discount_percent
                        // soldProductInfo.exclude = it.exclude?.toInt()
                        soldProductInfo.promotionPlanId = it.promotion_plan_id.toString()

                        soldProductInfoList.add(soldProductInfo)

//                        var totalAmt: Double = 0.00
//                        for (i in soldProductInfoList) {
//
//                            val amount = i.totalAmt
//                            totalAmt += amount
//                            tvNetAmount.text = totalAmt.toString()
//                        }
//

                        invoiceId.text = invoiceid
                        saleDateTextView.text = Utils.getCurrentDate(false)

                    }

                    saleCancelDetailAdapter.setNewList(soldProductInfoList)
                    saleCancelDetailAdapter.notifyDataSetChanged()
                }
            })



        saleCancelDetailViewModel.soldProductListErrorState.observe(
            this,
            android.arch.lifecycle.Observer {

            })

        rvSoldProductList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = saleCancelDetailAdapter
        }


    }


    fun alertDialogWithRadioButtons() {
        val invoiceID = intent.getStringExtra("INVOICE_ID")
        var invoiceList = intent.getSerializableExtra("INVOICE_LIST")
        val values = arrayOf<String>("Cancel the whole invoice", "Cancel only quantity")
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Select Your Choice")
        builder.setSingleChoiceItems(
            values, -1
        ) { dialog, item ->

            if (item == 0) {

                insertSaleCancelToDb()
                startActivity(SaleCancelActivity.newIntentFromCustomer(this))
                finish()

            } else {
                alertDialog1.dismiss()
            }
        }
        alertDialog1 = builder.create()
        alertDialog1.show()
        alertDialog1.setCanceledOnTouchOutside(false)

    }

    private fun insertSaleCancelToDb() {
        val invoiceID = intent.getStringExtra("INVOICE_ID")
        saleCancelDetailViewModel.loadInvoiceCancel(invoiceID)

    }


    private fun onClickQtyButton(soldProduct: SoldProductInfo, position: Int) {

        val layoutInflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(com.aceplus.dms.R.layout.dialog_box_sale_quantity, null)
        val alertDialog = android.app.AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Sale Quantity")
            .setPositiveButton("Confirm") { arg0, arg1 ->

                if (view.quantity.text.isBlank()) {
                    message.text = "You must specify quantity."
                    Log.i("qty", "qqqqqqqqqqqqqqqqqqq")
                } else {
                    val quantity = view.quantity.text.toString().toInt()
                    if (soldProduct.quantity != 0 && soldProduct.quantity < quantity) {
                        soldProduct.currentProductQty = soldProduct.quantity
                    }

                    soldProduct.quantity = quantity
                    // saleCancelDetailViewModel.updateQty(quantity,soldProduct.product.product_id!!)
                    val newList = saleCancelDetailAdapter.getDataList() as ArrayList
                    newList[position] = soldProduct
                    saleCancelDetailViewModel.calculateSoldProductData(newList)

//                    var totalAmt: Double = 0.00
//                    for (i in newList) {
//
//                        val amt = i.quantity.toDouble() * i.product.selling_price!!.toDouble()
//                        totalAmt += amt
//                        tvNetAmount.text = totalAmt.toString()
//                    }

                    saleCancelDetailAdapter.notifyItemChanged(position)

                }

            }
            .setNegativeButton("Cancel", null)
            .create()
        alertDialog.setOnShowListener {
            if (isPreOrder) view.availableQuantityLayout.visibility = View.GONE
            else
                view.availableQuantity.text = soldProduct.product.remaining_quantity.toString()

        }
        alertDialog.show()

    }

    private fun onLongClickSoldProductListItem(soldProduct: SoldProductInfo, position: Int) {

        android.app.AlertDialog.Builder(this)
            .setTitle("Delete sold product")
            .setMessage("Are you sure you want to delete ${soldProduct.product.product_name}?")

            .setPositiveButton("Yes") { arg0, arg1 ->

                var oldList = saleCancelDetailAdapter.getDataList() as ArrayList
                oldList.removeAt(position)
                indexList.add(soldProduct.product.id)
                saleCancelDetailAdapter.notifyDataSetChanged()
                saleCancelDetailViewModel.calculateSoldProductData(oldList)

            }
            .setNegativeButton("No", null)
            .show()
    }
}


//        val invoiceCancel = InvoiceCancel()
//        var invoiceId = intent.getStringExtra("INVOICE_ID")
//        var customerId = intent.getStringExtra("CUSTOMER_ID")
//        val saleDate = Utils.getCurrentDate(true)
//
//
//        for (soldProduct in soldProductDataList) {
//
//            val invoiceDetail = InvoiceDetail()
//            invoiceDetail.tsaleId = invoiceId
//            invoiceDetail.productId = soldProduct.id
//            invoiceDetail.qty = soldProduct.sale_quantity.toInt()
//            invoiceDetail.discountAmt = soldProduct.discount_amount
//            invoiceDetail.amt = soldProduct.total_amount
//            invoiceDetail.discountPercent = soldProduct.discount_percent
//            invoiceDetail.s_price = soldProduct.selling_price!!.toDouble()
//            invoiceDetail.p_price = soldProduct.purchase_price!!.toDouble()
//            invoiceDetail.promotionPrice = soldProduct.promotion_price
//            invoiceDetail.exclude = soldProduct.exclude?.toInt()
//            invoiceDetail.itemDiscountPercent = soldProduct.discount_percent
//            invoiceDetail.itemDiscountAmount = soldProduct.discount_amount
//            if (!soldProduct.promotion_plan_id.toString().isNullOrEmpty())
//                invoiceDetail.promotion_plan_id = soldProduct.promotion_plan_id
//
//            invoiceDetailList.add(invoiceDetail)
//            val invoiceCancelProduct = InvoiceCancelProduct()
//            invoiceCancelProduct.invoice_product_id = invoiceId.toInt()
//            invoiceCancelProduct.product_id = soldProduct.id
//            invoiceCancelProduct.sale_quantity = soldProduct.sale_quantity.toDouble()
//            invoiceCancelProduct.discount_amount = soldProduct.discount_amount
//            invoiceCancelProduct.total_amount = soldProduct.total_amount
//            invoiceCancelProduct.discount_percent = soldProduct.discount_percent
//            invoiceCancelProduct.s_price = soldProduct.selling_price!!.toDouble()
//            invoiceCancelProduct.p_price = soldProduct.purchase_price!!.toDouble()
//            invoiceCancelProduct.promotion_price =
//                soldProduct.promotion_price // Check promo price or promo price by disc
//            invoiceCancelProduct.volume_discount_percent = soldProduct.discount_percent
//            invoiceCancelProduct.exclude = "${soldProduct.exclude}"
//
//            if (!soldProduct.promotion_plan_id.toString().isNullOrEmpty())
//                invoiceCancelProduct.promotion_plan_id = soldProduct.promotion_plan_id!!
//
//
//            invoiceCancelProductList.add(invoiceCancelProduct)
//
//
//            invoiceCancel.invoice_id = invoiceId.toInt()
//            invoiceCancel.customer_id = customerId.toInt()
//            invoiceCancel.sale_date = saleDate
//            invoiceCancel.total_amount = totalAmount
//            invoiceCancel.total_discount_amount = totalDiscountAmount // Need to check
//            invoiceCancel.pay_amount = payAmount
//            invoiceCancel.refund_amount = refundAmount.toString()
//            invoiceCancel.receipt_person_name = receiptPerson
//            invoiceCancel.sale_person_id = salePersonId
//            invoiceCancel.due_date = dueDate
//            invoiceCancel.cash_or_credit = cashOrLoanOrBank
//            invoiceCancel.location_code = "" // Need to add - route id
//            invoiceCancel.device_id = deviceId
//            invoiceCancel.invoice_time = invoiceTime
//            invoiceCancel.package_invoice_number = 0 // Need to add
//            invoiceCancel.package_status = 0 // Need to check
//            invoiceCancel.volume_amount = 0.0 // Need to check
//            invoiceCancel.package_grade = "" // Need to check
//            invoiceCancel.invoice_product_id = 0 // Need to check
//            invoiceCancel.total_quantity = totalQtyForInvoice.toDouble() // Check int or double
//            invoiceCancel.invoice_status = cashOrLoanOrBank
//            invoiceCancel.total_discount_percent =
//                totalVolumeDiscountPercent.toString()  // Need to check
//            invoiceCancel.rate = "1"
//            invoiceCancel.tax_amount = taxAmt
//            invoiceCancel.bank_name = bank
//            invoiceCancel.bank_account_no = acc
//            invoiceCancel.sale_flag = 0 // Need to check
//
//            customerVisitRepo.insertNewInvoice(invoice)
//            this.invoice.postValue(invoice)
//
//
//            ///  saleCancelDetailViewModel.updateProductRemainingQty(soldProduct) // Need to remind !!!
//        }
//
//
//    }
//
//    customerVisitRepo.insertAllInvoiceProduct(invoiceProductList)
//
//
//    saleCancelDetailViewModel.deleteInvoice(invoiceId)
//    saleCancelDetailViewModel.deleteInvoiceProduct(invoiceId)
//
//}
//saleCancelDetailViewModel.insertInvoiceCancelList()
//for (soldProduct in soldProductDataList) {
////
////            val invoiceProductList = InvoiceCancelProduct()
////            invoiceProductList.add("INVOICE_PRODUCT_ID", invoiceId)
////            invoiceProductList.put("PRODUCT_ID", soldProduct.product_id)
////            invoiceProductList.put("SALE_QUANTITY", soldProduct.sale_quantity)
////            invoiceProductList.put("DISCOUNT_AMOUNT", soldProduct.discount_amount)
////            //to change netAmt
////            invoiceProductList.put("TOTAL_AMOUNT", soldProduct.total_amount)
////            invoiceProductList.put("DISCOUNT_PERCENT", soldProduct.discount_percent)
////            invoiceProductList.put("S_PRICE", soldProduct.selling_price)
////            invoiceProductList.put("P_PRICE", soldProduct.purchase_price)
////
////            var promoPrice = soldProduct.promotion_price
////            if (promoPrice == 0.0) {
////                promoPrice = soldProduct.selling_price.toDouble()
////            }
////
////            invoiceProductList.put("PROMOTION_PRICE", promoPrice)
//    saleCancelDetailViewModel.insertInvoiceCancelProduct(invoiceProductList)
//
////            val query =
////                ("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY + " + soldProduct.getQuantity()
////                        + ", SOLD_QTY = SOLD_QTY - " + soldProduct.getQuantity() + " WHERE ID = \'" + soldProduct.getProduct().getId() + "\'")
////            sqLiteDatabase.execSQL(query)
//
//}
