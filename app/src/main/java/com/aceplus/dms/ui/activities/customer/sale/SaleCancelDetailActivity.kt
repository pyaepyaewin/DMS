package com.aceplus.dms.ui.activities.customer.sale

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.aceplus.dms.ui.adapters.sale.SaleCancelDetailAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.salecancelviewmodel.SaleCancelViewModel
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.sale.salecancel.SaleCancelDetailItem
import com.aceplus.domain.vo.SoldProductInfo
import kotlinx.android.synthetic.main.activity_sale.*
import kotlinx.android.synthetic.main.dialog_box_sale_quantity.*
import kotlinx.android.synthetic.main.dialog_box_sale_quantity.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import kotlin.collections.ArrayList
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceCancel
import com.aceplus.domain.entity.invoice.InvoiceCancelProduct
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplussolutions.rms.ui.activities.BaseActivity


class SaleCancelDetailActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    override val layoutId: Int
        get() = com.aceplus.dms.R.layout.activity_sale

    var soldProductList1 = mutableListOf<String>()
    val invoiceCancelProductList: ArrayList<InvoiceCancelProduct> = ArrayList()
    var invoiceCancelList = ArrayList<InvoiceCancel>()
    private var invoiceList: ArrayList<InvoiceCancel> = ArrayList()
    var invoice = Invoice()
    var deletedProductList = arrayListOf<SoldProductInfo>()
    var soldInvoiceDataList = mutableListOf<Invoice>()

    //    val customerId = intent.getStringExtra("CUSTOMER_ID")
    var soldProductDataList = mutableListOf<SaleCancelDetailItem>()
    private var isPreOrder: Boolean = false
    private var duplicateProductList = mutableListOf<SoldProductInfo>()
    private var promotionArrayList = ArrayList<Promotion>()


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

    private val saleCancelViewModel: SaleCancelViewModel by viewModel()

    private val saleCancelDetailAdapter: SaleCancelDetailAdapter by lazy {
        SaleCancelDetailAdapter(this::onClickQtyButton, this::onLongClickSoldProductListItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text = "SALES CANCEL"
        tableHeaderOrderedQty.visibility = View.GONE
        headerFoc.visibility = View.GONE
        headerDiscount.visibility = View.GONE
//        if (intent.getSerializableExtra(SaleCheckoutActivity.PRESENT_PROUDCT_LIST_KEY) != null) {
//
//            promotionArrayList =
//                intent.getSerializableExtra(SaleCheckoutActivity.PRESENT_PROUDCT_LIST_KEY) as ArrayList<Promotion>
//        }
        if (soldProductDataList.size == 0) {
            Utils.commonDialog("No issued product", this, 2)

        }
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
            startActivityForResult(
                SaleCancelCheckoutActivity.getSaleCancelDetailIntent(
                    this,
                    saleCancelDetailAdapter.getDataList() as ArrayList,
                    invoiceid,
                    invoicedate,
                    customerId,
                    customerName,
                    deletedProductList
                ),
                Utils.RQ_BACK_TO_CUSTOMER
            )
        }
        saleCancelViewModel.productIdListSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                    soldProductList1 = it as MutableList<String>
                    //saleCancelViewModel.soldProductListSuccessState.value=null

                    saleCancelViewModel.loadSoldProductList(it, invoiceid)




            })

        saleCancelViewModel.productIdListErrorState.observe(
            this,
            android.arch.lifecycle.Observer {

            })

        saleCancelViewModel.loadSoldProductIdList(invoiceid)
        saleCancelViewModel.invoiceCancelSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                it?.let {
                    if (it != null) {
                        invoice = it
                        saleCancelViewModel.saveDeleteInvoice(
                            saleCancelDetailAdapter.getDataList() as ArrayList<SoldProductInfo>,
                            invoice,
                            invoiceid
                        )
                    }
                }


            })

        saleCancelViewModel.invoiceCancelErrorState.observe(
            this,
            android.arch.lifecycle.Observer {

            })

        saleCancelViewModel.calculatedSoldProductList.observe(this, Observer {
            if (it != null) {
                saleCancelDetailAdapter.setNewList(it.first)
                tvNetAmount.text = Utils.formatAmount(it.second)
                saleCancelViewModel.calculatedSoldProductList.value = null
            }
        })

        saleCancelViewModel.soldProductListSuccessState.observe(
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
                        soldProductInfo.exclude = it.exclude
                        soldProductInfo.promotionPlanId = it.promotion_plan_id.toString()
                        soldProductInfo.currentProductQty=it.sale_quantity.toInt()
                        soldProductInfoList.add(soldProductInfo)
                        saleCancelViewModel.calculateSoldProductData(soldProductInfoList)

                        invoiceId.text = invoiceid
                        saleDateTextView.text = Utils.getCurrentDate(false)

                    }

                    saleCancelDetailAdapter.setNewList(soldProductInfoList)
                    saleCancelDetailAdapter.notifyDataSetChanged()
                }
            })



        saleCancelViewModel.soldProductListErrorState.observe(
            this,
            android.arch.lifecycle.Observer {

            })

        rvSoldProductList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = saleCancelDetailAdapter
        }
        saleCancelViewModel.promotionDateSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                it?.let {
                    if (it != null) {
                        val promotionPlanId = it[0].promotion_plan_id
                        saleCancelViewModel.loadPromotionPriceList(
                            promotionPlanId!!,
                            soldProductDataList[0].sale_quantity.toInt(),
                            soldProductDataList[0].product_id
                        )

                    }

                }
            })

        saleCancelViewModel.promotionDateErrorState.observe(
            this,
            android.arch.lifecycle.Observer {

            })
        saleCancelViewModel.promotionPriceSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                it?.let {


                }
            })

        saleCancelViewModel.promotionPriceErrorState.observe(
            this,
            android.arch.lifecycle.Observer {

            })


    }


    fun alertDialogWithRadioButtons() {
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
        saleCancelViewModel.loadInvoiceCancel(invoiceID)

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
                    view.message.text = "You must specify quantity."
                    Log.i("qty", "qqqqqqqqqqqqqqqqqqq")
                } else {
                    val quantity = view.quantity.text.toString().toInt()
                    if (soldProduct.quantity != 0 && soldProduct.quantity < quantity){

                        soldProduct.currentProductQty = soldProduct.quantity
                    }

                    soldProduct.quantity = quantity

                    val soldProductList = saleCancelDetailAdapter.getDataList() as ArrayList
                    soldProductList[position] = soldProduct
                    saleCancelViewModel.calculateSoldProductData(soldProductList)
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

                var oldSoldProductList = saleCancelDetailAdapter.getDataList() as ArrayList
                oldSoldProductList.removeAt(position)
                deletedProductList.add(soldProduct)
                saleCancelDetailAdapter.notifyDataSetChanged()
                saleCancelViewModel.calculateSoldProductData(oldSoldProductList)
                if (oldSoldProductList.size != 0) {
//                    //calculatePromotinPriceAndGift(
//                        oldSoldProductList[oldSoldProductList.size - 1]
//                    )
                } else {
                    promotionArrayList.clear()
                    // updatePromotionProductList()

                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Utils.RQ_BACK_TO_CUSTOMER)
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK)
                finish()
            }
    }



}



