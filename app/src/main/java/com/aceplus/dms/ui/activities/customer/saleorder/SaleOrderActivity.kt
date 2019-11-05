package com.aceplus.dms.ui.activities.customer.saleorder

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.sale.ProductListAdapter
import com.aceplus.dms.ui.adapters.saleorder.OrderedProductListAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.sale.SaleViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale.*
import kotlinx.android.synthetic.main.activity_sale.saleDateTextView
import kotlinx.android.synthetic.main.activity_sale.tableHeaderQty
import kotlinx.android.synthetic.main.activity_sale1.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import kotlinx.android.synthetic.main.activity_sale.cancelImg as cancelImg1
import kotlinx.android.synthetic.main.activity_sale.checkoutImg as checkoutImg1
import kotlinx.android.synthetic.main.activity_sale.headerDiscount as headerDiscount1
import kotlinx.android.synthetic.main.activity_sale.rvSoldProductList as rvSoldProductList1
import kotlinx.android.synthetic.main.activity_sale.searchAutoCompleteTextView as searchAutoCompleteTextView1
import kotlinx.android.synthetic.main.activity_sale1.rvProductList as rvProductList1
import kotlinx.android.synthetic.main.activity_sale1.searchAndSelectProductsLayout as searchAndSelectProductsLayout1

class SaleOrderActivity : BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale

    companion object {

        private const val IE_PRE_ORDER = "IE_PRE_ORDER"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

        fun newIntentFromCustomer(context: Context, isPreOrder: Boolean, customerData: Customer): Intent {
            val intent = Intent(context, SaleOrderActivity::class.java)
            intent.putExtra(IE_PRE_ORDER, isPreOrder)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            return intent
        }

        fun newIntentFromSaleExchange(context: Context, isPreOrder: String, customerId: String): Intent {
            val intent = Intent(context, SaleOrderActivity::class.java)
            intent.putExtra(IE_PRE_ORDER, isPreOrder)
            intent.putExtra(IE_CUSTOMER_DATA, customerId)
            return intent
        }
    }

    private val saleViewModel: SaleViewModel by viewModel()
    private val mProductListAdapter by lazy { ProductListAdapter(this::onClickProductListItem) }
    private val mOrderedProductListAdapter: OrderedProductListAdapter by lazy { OrderedProductListAdapter(this::onLongClickOrderedProductListItem, this::onFocCheckChange, this::onClickQtyButton, this.isDelivery) }
    private val mSearchProductAdapter by lazy { ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList<String>()) }

    private val duplicateProductList = mutableListOf<Product>()
    private var isPreOrder: Boolean = false
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
        isPreOrder = intent.getBooleanExtra(IE_PRE_ORDER, false)
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
        checkoutImg.setOnClickListener { onBackPressed() }

        saleViewModel.productDataList.observe(this, Observer {
            it?.let {
                mProductListAdapter.setNewList(it.first as ArrayList<Product>)
                mSearchProductAdapter.clear()
                mSearchProductAdapter.addAll(it.second)
                mSearchProductAdapter.notifyDataSetChanged()
            }
                ?: Utils.commonDialog("No issued product", this, 2)
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

    }

    private fun onClickQtyButton(soldProduct: SoldProductInfo, position: Int){

    }

    private fun onFocCheckChange(data: SoldProductInfo, isChecked: Boolean, position: Int){}

}
