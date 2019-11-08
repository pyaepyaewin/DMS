package com.aceplus.dms.ui.activities.customer.saleorder

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.customer.sale.SaleCheckoutActivity
import com.aceplus.dms.ui.adapters.sale.ProductListAdapter
import com.aceplus.dms.ui.adapters.saleorder.OrderedProductListAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.sale.SaleViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale.*
import kotlinx.android.synthetic.main.activity_sale.saleDateTextView
import kotlinx.android.synthetic.main.activity_sale.tableHeaderQty
import kotlinx.android.synthetic.main.activity_sale1.*
import kotlinx.android.synthetic.main.dialog_box_sale_quantity.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_sale.cancelImg as cancelImg1
import kotlinx.android.synthetic.main.activity_sale.checkoutImg as checkoutImg1
import kotlinx.android.synthetic.main.activity_sale.headerDiscount as headerDiscount1
import kotlinx.android.synthetic.main.activity_sale.rvSoldProductList as rvSoldProductList1
import kotlinx.android.synthetic.main.activity_sale.searchAutoCompleteTextView as searchAutoCompleteTextView1
import kotlinx.android.synthetic.main.activity_sale.tvNetAmount as tvNetAmount1
import kotlinx.android.synthetic.main.activity_sale1.rvProductList as rvProductList1
import kotlinx.android.synthetic.main.activity_sale1.searchAndSelectProductsLayout as searchAndSelectProductsLayout1

class SaleOrderActivity : BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale

    companion object {

        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

        fun newIntentFromCustomer(context: Context, customerData: Customer): Intent {
            val intent = Intent(context, SaleOrderActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            return intent
        }

        fun newIntentFromSaleExchange(context: Context, customerId: String): Intent {
            val intent = Intent(context, SaleOrderActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customerId)
            return intent
        }
    }

    private val saleViewModel: SaleViewModel by viewModel()
    private val mProductListAdapter by lazy { ProductListAdapter(this::onClickProductListItem) }
    private val mOrderedProductListAdapter: OrderedProductListAdapter by lazy { OrderedProductListAdapter(this::onLongClickOrderedProductListItem, this::onFocCheckChange, this::onClickQtyButton, this.isDelivery) }
    private val mSearchProductAdapter by lazy { ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList<String>()) }

    private val duplicateProductList = mutableListOf<Product>()
    private var promotionList: ArrayList<Promotion> = ArrayList()
    private var isDelivery: Boolean = false
    private var customer: Customer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        getIntentData()
        setupUI()
        catchEvents()

        saleViewModel.loadProductList()

    }

    private fun getIntentData(){
        if (intent.getParcelableExtra<Customer>(IE_CUSTOMER_DATA) != null) customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
    }

    private fun setupUI(){

        headerDiscount.visibility = View.GONE
        tableHeaderQty.visibility = View.GONE
        saleDateTextView.text = Utils.getCurrentDate(false)

        if (isDelivery){
            tableHeaderQty.visibility = View.VISIBLE
            searchAndSelectProductsLayout.visibility = View.GONE
        }

        rvProductList.adapter = mProductListAdapter
        rvProductList.layoutManager = GridLayoutManager(applicationContext, 1)

        searchAutoCompleteTextView.setAdapter(mSearchProductAdapter)
        searchAutoCompleteTextView.threshold = 1

        rvSoldProductList.adapter = mOrderedProductListAdapter
        rvSoldProductList.layoutManager = GridLayoutManager(applicationContext, 1)

    }

    private fun catchEvents(){

        searchAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            onSelectAtMostTwoSameProduct(mProductListAdapter.getDataList()[position])
            searchAutoCompleteTextView.setText("")
        }

        cancelImg.setOnClickListener { onBackPressed() }
        checkoutImg.setOnClickListener { checkoutOrder() }

        saleViewModel.productDataList.observe(this, Observer {
            it?.let {
                mProductListAdapter.setNewList(it.first as ArrayList<Product>)
                mSearchProductAdapter.clear()
                mSearchProductAdapter.addAll(it.second)
                mSearchProductAdapter.notifyDataSetChanged()
            }
                ?: Utils.commonDialog("No issued product", this, 2)
        })

        saleViewModel.calculatedSoldProductList.observe(this, Observer {
            if (it != null){
                mOrderedProductListAdapter.setNewList(it.first)
                tvNetAmount.text = Utils.formatAmount(it.second)
                saleViewModel.calculatedSoldProductList.value = null
            }
        })

    }

    private fun onClickProductListItem(product: Product) {
        onSelectAtMostTwoSameProduct(tempProduct = product)
    }

    private fun onSelectAtMostTwoSameProduct(tempProduct: Product){

        var sameProduct = false

        for (tempSoldProduct in mOrderedProductListAdapter.getDataList()) {
            if (tempSoldProduct.product.product_id === tempProduct.product_id) {
                sameProduct = true
                if (duplicateProductList.contains(tempProduct))
                    Constant.PRODUCT_COUNT = 2
                else
                    Constant.PRODUCT_COUNT++
                break
            } else {
                if (!duplicateProductList.contains(tempProduct)) {
                    Constant.PRODUCT_COUNT = 0
                }
            }
        }

        val newSoldProduct = SoldProductInfo(tempProduct, false)
        newSoldProduct.promoPriceByDiscount = tempProduct.selling_price!!.toDouble()

        if (!sameProduct) {
            mOrderedProductListAdapter.addNewItem(newSoldProduct)
        } else {
            if (Constant.PRODUCT_COUNT < 2 && !duplicateProductList.contains(tempProduct)) {
                if (Constant.PRODUCT_COUNT == 1) {
                    Constant.PRODUCT_COUNT = 0
                    duplicateProductList.add(tempProduct)
                    mOrderedProductListAdapter.addNewItem(newSoldProduct)
                }
            } else {
                Constant.PRODUCT_COUNT = 0
                Utils.commonDialog("Already have this product", this, 2)
            }
        }

    }

    private fun onLongClickOrderedProductListItem(soldProduct: SoldProductInfo, position: Int){

        AlertDialog.Builder(this)
            .setTitle("Delete sold product")
            .setMessage("Are you sure you want to delete ${soldProduct.product.product_name}?")
            .setPositiveButton("Yes") { arg0, arg1 ->

                for (i in duplicateProductList.indices) {
                    if (duplicateProductList[i].product_id == soldProduct.product.product_id) {
                        duplicateProductList.removeAt(i)
                        break
                    }
                }

                val oldList = mOrderedProductListAdapter.getDataList() as ArrayList
                oldList.remove(soldProduct)
                saleViewModel.calculateSoldProductData(oldList, this.promotionList)
            }
            .setNegativeButton("Cancel", null)
            .show()

    }

    private fun onClickQtyButton(soldProduct: SoldProductInfo, position: Int){

        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null)

        val quantityEditText = view.findViewById(R.id.quantity) as EditText
        val messageTextView = view.findViewById(R.id.message) as TextView
        val availableQuantityLayout = view.findViewById(R.id.availableQuantityLayout) as LinearLayout

        val alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Sale Quantity")
            .setPositiveButton("Confirm") { arg0, arg1 ->

                if (quantityEditText.text.toString().isBlank()) {
                    messageTextView.text = "You must specify quantity."
                    return@setPositiveButton
                }

                val quantity = quantityEditText.text.toString().toInt()

                if (isDelivery){
                    if (quantity != soldProduct.orderedQuantity){
                        messageTextView.text = "Your quantity must be equal to order quantity."
                        return@setPositiveButton
                    }
                }

                soldProduct.quantity = quantity

                // ToDo - calculate promotion if it's not delivery

                val newList = mOrderedProductListAdapter.getDataList() as ArrayList
                newList[position] = soldProduct
                saleViewModel.calculateSoldProductData(newList, this.promotionList)

            }
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.setOnShowListener {
            availableQuantityLayout.visibility = View.GONE
        }

        alertDialog.show()

    }

    private fun onFocCheckChange(data: SoldProductInfo, isChecked: Boolean, position: Int){

        data.isFocIsChecked = isChecked
        val newList = mOrderedProductListAdapter.getDataList() as ArrayList
        newList[position] = data
        saleViewModel.calculateSoldProductData(newList, this.promotionList)

    }

    private fun checkoutOrder(){

        if (mOrderedProductListAdapter.getDataList().isEmpty()) {

            AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("You must specify at least one product.")
                .setPositiveButton("OK", null)
                .show()

            return
        }

        if (tvNetAmount.text == "0" || tvNetAmount.text == "0.0") {

            AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Net amount should be more than zero.")
                .setPositiveButton("OK", null)
                .show()

            return
        }

        val isFocPass = toEnableFocSameProduct()

        if (isFocPass){
            val intent = SaleOrderCheckoutActivity.getIntentFromSaleOrder(this, customer!!, mOrderedProductListAdapter.getDataList() as ArrayList, this.promotionList)
            startActivity(intent)
        }

    }

    private fun toEnableFocSameProduct(): Boolean{

        for (product in duplicateProductList){

            val arrList = ArrayList<String>()
            for (soldProduct in mOrderedProductListAdapter.getDataList()){

                if (soldProduct.quantity == 0){
                    AlertDialog.Builder(this)
                        .setTitle("Alert")
                        .setMessage("Quantity must not be zero.")
                        .setPositiveButton("OK", null)
                        .show()
                    return false
                } else{
                    val stockId = soldProduct.product.id
                    val tempStockId = product.id
                    if (stockId == tempStockId){
                        val isFoc = soldProduct.isFocIsChecked
                        if (isFoc) arrList.add("T")
                        else arrList.add("F")
                    }
                }

            }

            val freq = Collections.frequency(arrList, "T")
            if (freq == 2 || freq == 0){
                AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage("Need to enable one of FOC in the same product: ${product.product_name}")
                    .setPositiveButton("OK", null)
                    .show()
                return false
            }

        }
        return true
    }

}
