package com.aceplus.dms.ui.activities

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.VanIssueProductListAdapter
import com.aceplus.dms.ui.adapters.sale.ProductListAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.VanIssueViewModel
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale1.*
import kotlinx.android.synthetic.main.activity_van_issue.*
import kotlinx.android.synthetic.main.activity_van_issue.saleDateTextView
import kotlinx.android.synthetic.main.activity_van_issue.searchAutoCompleteTextView
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class VanIssueActivity: BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_van_issue

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, VanIssueActivity::class.java)
        }
    }

    private val vanIssueViewModel: VanIssueViewModel by viewModel()
    private val mProductListAdapter by lazy { ProductListAdapter(::onClickProductListItem) }
    private val mSearchProductAdapter by lazy { ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList<String>()) }
    private val mVanIssueProductAdapter by lazy { VanIssueProductListAdapter(this::onClickBtnQty, this::onLongClickSoldProductListItem) }

    private var locationCode: Int = 0
    private var invoiceNo: String? = null
    private var saleManID: String? = null
    private var totalIssueQuantity = 0
    private var routeID: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        initializeData()
        setupUI()
        catchEvent()

    }

    private fun initializeData(){

        vanIssueViewModel.loadProductList()
        locationCode = vanIssueViewModel.getLocationCode()
        saleManID = vanIssueViewModel.getSaleManID()
        routeID = vanIssueViewModel.getRouteID()

    }

    private fun setupUI(){

        saleDateTextView.text = Utils.getCurrentDate(false)
        setInvoiceNo()
        calculateTotalRemainingQty()

        searchAutoCompleteTextView.setAdapter(mSearchProductAdapter)
        searchAutoCompleteTextView.threshold = 1

        productsListView.adapter = mProductListAdapter
        productsListView.layoutManager = LinearLayoutManager(this)

        vanIssueProductList.adapter = mVanIssueProductAdapter
        vanIssueProductList.layoutManager = LinearLayoutManager(this)

    }

    private fun catchEvent(){

        vanIssueViewModel.productDataList.observe(this, Observer {
            it?.let {
                mProductListAdapter.setNewList(it.first as ArrayList<Product>)
                mSearchProductAdapter.clear()
                mSearchProductAdapter.addAll(it.second)
                mSearchProductAdapter.notifyDataSetChanged()
            }
                ?: Utils.commonDialog("No issued product", this, 2)
        })

        searchAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            checkDuplicateProductAndAdd(mProductListAdapter.getDataList()[position])
            searchAutoCompleteTextView.setText("")
        }

        vanIssueViewModel.errorMessage.observe(this, Observer {
            if (it != null){
                Utils.commonDialog(it, this, 1)
                vanIssueViewModel.errorMessage.value = null
            }
        })

        vanIssueViewModel.successState.observe(this, Observer {
            if (it != null){
                if (it == 1) finish()
                vanIssueViewModel.successState.value = null
            }
        })

        cancel_img.setOnClickListener { onBackPressed() }
        checkout_img.setOnClickListener { Utils.askConfirmationDialog("Save", "Do you want to confirm?", "save", this, this::onClickSaveButton) }

    }

    private fun onClickProductListItem(product: Product) {

        checkDuplicateProductAndAdd(product)

    }

    private fun onClickBtnQty(soldProduct: SoldProductInfo, position: Int){

        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null)

        val quantityEditText = view.findViewById(R.id.quantity) as EditText
        val messageTextView = view.findViewById(R.id.message) as TextView
        val availableQuantityLayout = view.findViewById(R.id.availableQuantityLayout) as LinearLayout

        val alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Quantity")
            .setPositiveButton("Confirm", null)
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.setOnShowListener {
            availableQuantityLayout.visibility = View.GONE
            val confirmButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            confirmButton.setOnClickListener {
                if (quantityEditText.text.toString().isBlank()) {
                    messageTextView.text = "You must specify quantity."
                    return@setOnClickListener
                } else{
                    val quantity = quantityEditText.text.toString().toInt()

                    if (soldProduct.quantity != 0 && soldProduct.quantity < quantity){
                        soldProduct.currentProductQty = soldProduct.quantity
                    }

                    soldProduct.quantity = quantity
                    soldProduct.totalAmt = quantity * (soldProduct.product.selling_price?.toDouble() ?: 0.0)

                    mVanIssueProductAdapter.notifyDataSetChanged()
                    calculateQuantity()
                    alertDialog.dismiss()
                }
            }
        }
        alertDialog.show()

    }

    private fun setInvoiceNo(){

        val invoiceCount = AppUtils.getIntFromShp(Constant.INVOICE_COUNT, this) ?: 0
        if (invoiceCount >= 0) AppUtils.saveIntToShp(Constant.INVOICE_COUNT, invoiceCount + 1, this)

        invoiceNo = vanIssueViewModel.getInvoiceNumber(saleManID!!, locationCode, Constant.FOR_VAN_ISSUE)
        tvInvoiceNo.text = invoiceNo

    }

    private fun calculateQuantity(){

        var totalIssueQuantity = 0

        for(soldProduct in mVanIssueProductAdapter.getDataList()){
            totalIssueQuantity += soldProduct.quantity
        }

        this.totalIssueQuantity = totalIssueQuantity
        mIssueQuantity.text = totalIssueQuantity.toString()

    }

    private fun calculateTotalRemainingQty(){

        var totalRemainingQuantity = 0
        var totalRequireQuantity = 0

        for(soldProduct in mVanIssueProductAdapter.getDataList()){
            if (soldProduct.product.remaining_quantity >= 0)
                totalRemainingQuantity += soldProduct.product.remaining_quantity
            else
                totalRequireQuantity -= soldProduct.product.remaining_quantity
        }

        mTotalQuantity.text = totalRemainingQuantity.toString()

    }

    private fun checkDuplicateProductAndAdd(tempProduct: Product){

        var isSameProduct = false

        for (selectedProduct in mVanIssueProductAdapter.getDataList()){
            if (selectedProduct.product.id == tempProduct.id){
                isSameProduct = true
                break
            }
        }

        if (isSameProduct)
            Utils.commonDialog("Already have this product", this, 2)
        else
            mVanIssueProductAdapter.addNewItem(SoldProductInfo(tempProduct, false))

    }

    private fun onLongClickSoldProductListItem(soldProduct: SoldProductInfo, position: Int){

        AlertDialog.Builder(this)
            .setTitle("Delete sold product")
            .setMessage("Are you sure you want to delete ${soldProduct.product.product_name}?")
            .setPositiveButton("Yes"){ arg0, arg1 ->

                val newList = mVanIssueProductAdapter.getDataList() as ArrayList
                newList.removeAt(position)
                mVanIssueProductAdapter.setNewList(newList)
                calculateQuantity()

            }
            .setNegativeButton("No", null)
            .show()

    }

    private fun onClickSaveButton(type: String){

        if (type == "save"){

            for (product in mVanIssueProductAdapter.getDataList()){

                if (product.quantity == 0){
                    AlertDialog.Builder(this)
                        .setTitle("Alert")
                        .setMessage("Quantity must not be zero.")
                        .setPositiveButton("OK", null)
                        .show()
                    return
                }

            }

            vanIssueViewModel.saveData(
                invoiceNo!!,
                Utils.getCurrentDate(false),
                routeID,
                "Testing",
                mVanIssueProductAdapter.getDataList() as ArrayList<SoldProductInfo>
            )

        }

    }

}
