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
import com.aceplus.dms.R
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

class SaleCancelDetailActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    var soldProductList1 = mutableListOf<String>()
    var soldProductDataList = mutableListOf<SaleCancelDetailItem>()
    private var isPreOrder: Boolean = false
    private val duplicateProductList = mutableListOf<SoldProductInfo>()


    lateinit var alertDialog1: AlertDialog

    companion object {
        fun getIntent(context: Context, invoiceID: String, date: String): Intent {
            val saleCancelIntent = Intent(context, SaleCancelDetailActivity::class.java)
            saleCancelIntent.putExtra("INVOICE_ID", invoiceID)
            saleCancelIntent.putExtra("INVOICE_DATE", date)

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
        setContentView(R.layout.activity_sale)
        tvTitle.text = "SALES CANCEL"
        tableHeaderOrderedQty.visibility = View.GONE
        headerFoc.visibility = View.GONE
        headerDiscount.visibility = View.GONE
        alertDialogWithRadioButtons()
        var invoiceid = intent.getStringExtra("INVOICE_ID")
        var invoicedate = intent.getStringExtra("INVOICE_DATE")
        cancelImg.setOnClickListener {
            onBackPressed()
            true
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

        saleCancelDetailViewModel.soldProductListSuccessState.observe(
            this,
            android.arch.lifecycle.Observer { it ->
                if (it!!.isNotEmpty()) {
                    soldProductDataList = it as MutableList<SaleCancelDetailItem>
                    var soldProductInfoList = ArrayList<SoldProductInfo>()
                    soldProductDataList.map {
                        val soldProductInfo = SoldProductInfo()
                        soldProductInfo.product = Product()
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
                        soldProductInfo.promotionPrice = it.promotion_price
                        soldProductInfo.quantity = it.sale_quantity.toInt()

                        soldProductInfoList.add(soldProductInfo)

                        var totalAmt: Double = 0.00
                        for (i in soldProductInfoList) {

                            val amt = i.quantity.toDouble() * i.product.selling_price!!.toDouble()
                            totalAmt += amt
                            tvNetAmount.text = totalAmt.toString()
                        }


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
        val values = arrayOf<String>("Cancel the whole invoice", "Cancel only quantity")
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Select Your Choice")
        builder.setSingleChoiceItems(
            values, -1
        ) { dialog, item ->
            val invoice = intent.getStringExtra("INVOICE_ID")

            if (item == 0) {
                saleCancelDetailViewModel.deleteInvoice(invoice)
                saleCancelDetailViewModel.deleteInvoiceProduct(invoice)
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

    private fun onClickQtyButton(soldProduct: SoldProductInfo, position: Int) {

        val layoutInflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null)
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

                    saleCancelDetailAdapter.notifyItemRemoved(position)

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
            .setMessage("Are you sure you want to delete ${soldProduct.product.product_id}?")

            .setPositiveButton("Yes") { arg0, arg1 ->

                for (i in duplicateProductList.indices) {
                    if (duplicateProductList[i].product.product_id == soldProduct.product.product_id) {
                        duplicateProductList.removeAt(i)
                        break
                    }
                }
                duplicateProductList.remove(soldProduct)
               // duplicateProductList.removeAt(position)
                saleCancelDetailAdapter.setNewList(duplicateProductList as ArrayList<SoldProductInfo>)


                val oldList = saleCancelDetailAdapter.getDataList() as ArrayList
                oldList.remove(soldProduct)
            }
                    .setNegativeButton("No", null)
                    .show()
            }
    }




