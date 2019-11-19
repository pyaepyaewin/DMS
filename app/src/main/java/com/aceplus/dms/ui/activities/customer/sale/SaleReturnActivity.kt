package com.aceplus.dms.ui.activities.customer.sale

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.sale.ProductListAdapter
import com.aceplus.dms.ui.adapters.sale.SaleReturnSelectedProductAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.sale.SalesReturnViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale_return.*
import kotlinx.android.synthetic.main.activity_sale_return.searchAutoCompleteTextView
import kotlinx.android.synthetic.main.activity_sale_return.tvTitle
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class SaleReturnActivity : BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale_return

    companion object {

        private const val IE_SALE_EXCHANGE = "IE_SALE_EXCHANGE"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

        fun newIntentFromCustomer(context: Context, isSaleExchange: Boolean, customer: Customer): Intent {
            val intent = Intent(context, SaleReturnActivity::class.java)
            intent.putExtra(IE_SALE_EXCHANGE, isSaleExchange)
            intent.putExtra(IE_CUSTOMER_DATA, customer)
            return intent
        }

    }

    private val salesReturnViewModel: SalesReturnViewModel by viewModel()
    private val mSearchProductAdapter by lazy { ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList<String>()) }
    private val mProductListAdapter by lazy { ProductListAdapter(::onClickProductListItem) }
    private val mReturnProductListAdapter by lazy { SaleReturnSelectedProductAdapter() }

    private var isSaleExchange: Boolean = false
    private var customer: Customer? = null
    private var salePersonID: String? = null
    private var saleReturnID: String? = null
    private var locationCode: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN) // Hide keyboard on startup.

        getIntentData()
        initializeData()
        setupUI()
        catchEvents()

    }

    private fun getIntentData(){

        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        isSaleExchange = intent.getBooleanExtra(IE_SALE_EXCHANGE, false)

    }

    private fun initializeData(){

        salePersonID = salesReturnViewModel.getSaleManID()
        locationCode = salesReturnViewModel.getRouteID() // Check point - route id or location id - main thread

        saleReturnID = if (isSaleExchange)
            Utils.getInvoiceNo(salePersonID!!, locationCode.toString(), Constant.FOR_SALE_RETURN_EXCHANGE, salesReturnViewModel.getLastCountForInvoiceNumber(Constant.FOR_SALE_RETURN_EXCHANGE))
        else
            Utils.getInvoiceNo(salePersonID!!, locationCode.toString(), Constant.FOR_SALE_RETURN, salesReturnViewModel.getLastCountForInvoiceNumber(Constant.FOR_SALE_RETURN))

        salesReturnViewModel.loadProductList()
        salesReturnViewModel.getTaxInfo()

    }

    private fun setupUI(){

        if (isSaleExchange) tvTitle.text = resources.getString(R.string.sale_exchange)

        searchAutoCompleteTextView.setAdapter(mSearchProductAdapter)
        searchAutoCompleteTextView.threshold = 1

        rvProductListSalesReturn.adapter = mProductListAdapter
        rvProductListSalesReturn.layoutManager = LinearLayoutManager(this)

        selectedProductList.adapter = mReturnProductListAdapter
        selectedProductList.layoutManager = LinearLayoutManager(this)

    }

    private fun catchEvents(){

        cancel_img.setOnClickListener { onBackPressed() }
        confirm_img.setOnClickListener { "ToDo" }

        salesReturnViewModel.productDataList.observe(this, Observer {
            if(it != null){
                mProductListAdapter.setNewList(it.first as ArrayList<Product>)
                mSearchProductAdapter.clear()
                mSearchProductAdapter.addAll(it.second)
                mSearchProductAdapter.notifyDataSetChanged()
            }
        })

        salesReturnViewModel.taxType.observe(this, Observer {
            if (it != null){
                if (it.equals("E", true)){
                    txtTaxAmt.text = "Tax (Exclude) : "
                } else{
                    txtTaxAmt.text = "Tax (Include) : "
                }
            }
        })

        searchAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            checkDuplicateProductAndAdd(mProductListAdapter.getDataList()[position])
            searchAutoCompleteTextView.setText("")
        }

    }

    private fun onClickProductListItem(product: Product) = checkDuplicateProductAndAdd(product)

    private fun checkDuplicateProductAndAdd(tempProduct: Product){

        var isSameProduct = false

        for (selectedProduct in mReturnProductListAdapter.getDataList()){
            if (selectedProduct.product.id == tempProduct.id){
                isSameProduct = true
                break
            }
        }

        if (isSameProduct)
            Utils.commonDialog("Already have this product", this, 2)
        else
            mReturnProductListAdapter.addNewItem(SoldProductInfo(tempProduct, false))

    }

    private fun onClickQtyButton(soldProduct: SoldProductInfo, position: Int){

        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null)

        val quantityEditText = view.findViewById(R.id.quantity) as EditText
        val messageTextView = view.findViewById(R.id.message) as TextView
        val availableQuantityLayout = view.findViewById(R.id.availableQuantityLayout) as LinearLayout

        val alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Quantity")
            .setPositiveButton("Confirm") { arg0, arg1 ->

                if (quantityEditText.text.toString().isBlank()) {
                    messageTextView.text = "You must specify quantity."
                    return@setPositiveButton
                } else{
                    val quantity = quantityEditText.text.toString().toInt()

                    if (soldProduct.quantity != 0 && soldProduct.quantity < quantity){
                        /*updatedQtyForGift = true
                        updatedQtyForPercent = true*/
                        soldProduct.currentProductQty = soldProduct.quantity
                    }

                    soldProduct.quantity = quantity

                    val newList = mReturnProductListAdapter.getDataList() as ArrayList
                    newList[position] = soldProduct
                    //saleViewModel.calculateSoldProductData(newList, this.promotionList)
                }

            }
            .setNegativeButton("Cancel", null)
            .create()

    }

    private fun onLongClickSoldProductListItem(soldProduct: SoldProductInfo, position: Int){

    }

}
