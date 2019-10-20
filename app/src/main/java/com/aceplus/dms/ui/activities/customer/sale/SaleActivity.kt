package com.aceplus.dms.ui.activities.customer.sale

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
import com.aceplus.dms.ui.adapters.sale.ProductListAdapter
import com.aceplus.dms.ui.adapters.sale.PromotionPlanGiftListAdapter
import com.aceplus.dms.ui.adapters.sale.SoldProductListAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.sale.SaleViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.VO.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale1.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SaleActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale1

    companion object {
        private const val IE_SALE_EXCHANGE = "IE_SALE_EXCHANGE"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"
        private const val IE_IS_PRE_ORDER = "IS_PRE_ORDER"
        private const val IE_IS_DELIVERY = "IS_DELIVERY"
        private const val IE_REMAINING_AMOUNT_KEY = "REMAINING_AMOUNT_KEY"
        private const val IE_USER_INFO_KEY = "USER_INFO_KEY"
        private const val IE_CUSTOMER_INFO_KEY = "CUSTOMER_INFO_KEY"
        private const val IE_SOLD_PRODUCT_LIST_KEY = "SOLD_PRODUCT_LIST_KEY"
        private const val IE_ORDERED_INVOICE_KEY = "ORDERED_INVOICE_KEY"
        private const val IE_SALE_RETURN_INVOICE_ID_KEY = "SALE_RETURN_INVOICE_ID_KEY"

        fun newIntentFromCustomer(context: Context, isSaleExchange: String, customerData: Customer): Intent {
            val intent = Intent(context, SaleActivity::class.java)
            intent.putExtra(IE_SALE_EXCHANGE, isSaleExchange)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            return intent
        }

        fun newIntentFromSaleExchange(context: Context, isSaleExchange: String, customerId: String): Intent {
            val intent = Intent(context, SaleActivity::class.java)
            intent.putExtra(IE_SALE_EXCHANGE, isSaleExchange)
            intent.putExtra(IE_CUSTOMER_DATA, customerId)
            return intent
        }
    }

    private val saleViewModel: SaleViewModel by viewModel()
    private val mProductListAdapter by lazy { ProductListAdapter(::onClickProductListItem) }
    private val mSoldProductListAdapter by lazy { SoldProductListAdapter(this::onLongClickSoldProductListItem, this::onFocCheckChange, this::onClickQtyButton, this::onClickFocButton, this.isDelivery) }
    private val mPromotionGiftPresentListAdapter by lazy { PromotionPlanGiftListAdapter() }
    /*private val mPromotionItemListAdapter by lazy { PromotionPlanItemListAdapter() }*/
    private val mSearchProductAdapter by lazy { ArrayAdapter<String>(this@SaleActivity, android.R.layout.simple_list_item_1, ArrayList<String>()) }

    private val duplicateProductList = mutableListOf<Product>()
    private var customer: Customer? = null
    private var promotionList: ArrayList<Promotion> = ArrayList()
    private val promotionGiftByClassDis: ArrayList<Int> = ArrayList()
    private val percentTotalCount: ArrayList<String> = ArrayList()
    private val giftTotalCount: ArrayList<String> = ArrayList()
    private val percentCategoryClassId: HashMap<String, Integer> = HashMap()
    private val giftCategoryClassId: HashMap<String, Integer> = HashMap()
    private val percentAmount: HashMap<String, Double> = HashMap()
    private val giftAmount: HashMap<String, Double> = HashMap()

    private var isDelivery: Boolean = false // Need to update by getIntentData
    private var isPreOrder: Boolean = false // Need to update by getIntentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        onClearArrayList()
        getIntentData()
        setupUI()
        catchEvents()

        saleViewModel.productDataList.observe(this, Observer {
            it?.let {
                mProductListAdapter.setNewList(it.first as ArrayList<Product>)
                mSearchProductAdapter.clear()
                mSearchProductAdapter.addAll(it.second)
                mSearchProductAdapter.notifyDataSetChanged()
            }
                ?: Utils.commonDialog("No issued product", this, 2)
        })

        saleViewModel.loadProductList()
        saleViewModel.calculateClassDiscountByPrice()
        updatePromotionProductList()

    }

    private fun setupUI() {

        val check = intent.getStringExtra(IE_SALE_EXCHANGE)
        if (check.equals("yes", ignoreCase = true)) tvTitle.text = getString(R.string.sale_exchange)

        saleDateTextView.text = Utils.getCurrentDate(false)

        rvProductList.adapter = mProductListAdapter
        rvProductList.layoutManager = GridLayoutManager(applicationContext, 1)

        rvSoldProductList.adapter = mSoldProductListAdapter
        rvSoldProductList.layoutManager = GridLayoutManager(applicationContext, 1)

        rvPromotionPlanGiftList.adapter = mPromotionGiftPresentListAdapter
        rvPromotionPlanGiftList.layoutManager = GridLayoutManager(applicationContext, 1)

        /*rvPromotionPlanItemList.adapter = mPromotionItemPresentListAdapter
        rvPromotionPlanItemList.layoutManager = GridLayoutManager(applicationContext, 1)*/

        searchAutoCompleteTextView.setAdapter(mSearchProductAdapter)
        searchAutoCompleteTextView.threshold = 1

        searchAndSelectProductsLayout.visibility = if (this.isDelivery) View.GONE else View.VISIBLE
        tableHeaderOrderedQty.visibility = if (this.isDelivery) View.VISIBLE else View.GONE
        tableHeaderDiscount.visibility = if (this.isPreOrder) View.GONE else View.VISIBLE

    }

    private fun catchEvents() {

        searchAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            onSelectAtMostTwoSameProduct(mProductListAdapter.getDataList()[position])
            searchAutoCompleteTextView.setText("")
        }

        cancelImg.setOnClickListener { onBackPressed() }
        checkoutImg.setOnClickListener { saveSaleData() }

        saleViewModel.updatedSoldProduct.observe(this, Observer {
            if (it != null){
                mSoldProductListAdapter.updateList(it.first, it.second)
            }
        })

        saleViewModel.promotionList.observe(this, Observer {
            if (it != null){
                this.promotionList = it
                updatePromotionProductList()
            }
        })

    }

    private fun onClickProductListItem(product: Product) {
        onSelectAtMostTwoSameProduct(tempProduct = product)
    }

    private fun onSelectAtMostTwoSameProduct(tempProduct: Product){

        var sameProduct = false

        for (tempSoldProduct in mSoldProductListAdapter.getDataList()) {
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
            mSoldProductListAdapter.addNewItem(newSoldProduct)
        } else {
            if (Constant.PRODUCT_COUNT < 2 && !duplicateProductList.contains(tempProduct)) {
                if (Constant.PRODUCT_COUNT == 1) {
                    Constant.PRODUCT_COUNT = 0
                    duplicateProductList.add(tempProduct)
                    mSoldProductListAdapter.addNewItem(newSoldProduct)
                }
            } else {
                Constant.PRODUCT_COUNT = 0
                Utils.commonDialog("Already have this product", this@SaleActivity, 2)
            }
        }

    }

    private fun onFocCheckChange(data: SoldProductInfo, isChecked: Boolean, position: Int){

        data.isFocIsChecked = isChecked
        saleViewModel.calculateSoldProductData(false, data, mSoldProductListAdapter.getDataList() as ArrayList, this.promotionList, position)

    }

    private fun onLongClickSoldProductListItem(soldProduct: SoldProductInfo, position: Int) {

        AlertDialog.Builder(this@SaleActivity)
            .setTitle("Delete sold product")
            .setMessage("Are you sure you want to delete ${soldProduct.product.product_name}?")
            .setPositiveButton("Yes") { arg0, arg1 ->

                for (i in duplicateProductList.indices){
                    if (duplicateProductList[i].product_id == soldProduct.product.product_id){
                        duplicateProductList.removeAt(i)
                        break
                    }
                }

                if (soldProduct.quantity != 0){
                    // ToDo change or remove promotion list
                }

                mSoldProductListAdapter.removeItem(position)

                if (mSoldProductListAdapter.getDataList().isNotEmpty()){
                    // ToDo calculate Promotion Price And Gift
                } else{
                    // ToDo clear lists and update promotion list
                }

            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun onClickQtyButton(soldProduct: SoldProductInfo, position: Int){

        val layoutInflater = this@SaleActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null)

        val remainingQtyTextView = view.findViewById(R.id.availableQuantity) as TextView
        val quantityEditText = view.findViewById(R.id.quantity) as EditText
        val messageTextView = view.findViewById(R.id.message) as TextView

        val alertDialog = AlertDialog.Builder(this@SaleActivity)
            .setView(view)
            .setTitle("Sale Quantity")
            .setPositiveButton("Confirm") { arg0, arg1 ->

                if (quantityEditText.text.toString().isEmpty()) {
                    messageTextView.text = "You must specify quantity."
                } else{

                    val quantity = quantityEditText.text.toString().toInt()

                    if (soldProduct.quantity != 0 && soldProduct.quantity < quantity){
                        /*updatedQtyForGift = true
                        updatedQtyForPercent = true*/
                        soldProduct.currentProductQty = soldProduct.quantity
                    }

                    soldProduct.quantity = quantity
                    saleViewModel.calculateSoldProductData(true, soldProduct, mSoldProductListAdapter.getDataList() as ArrayList, this.promotionList, position)

                }

            }
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.setOnShowListener {
            //view.findViewById<LinearLayout>(R.id.availableQuantityLayout).visibility = View.GONE // If it's a pre-order activity
            remainingQtyTextView.text = soldProduct.product.remaining_quantity.toString() // If it's not a pre-order activity
        }

        alertDialog.show()

    }

    private fun onClickFocButton(soldProduct: SoldProductInfo, position: Int){

        val layoutInflater = this@SaleActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_box_sale_foc, null)

        val radioFocPercent: RadioButton = view.findViewById(R.id.radioPrecent)
        val radioFocAmount: RadioButton = view.findViewById(R.id.radioAmount)
        val focVolume: EditText = view.findViewById(R.id.focPercent)

        val alertDialog = AlertDialog.Builder(this@SaleActivity)
            .setView(view)
            .setTitle("FOC percent or Amount")
            .setPositiveButton("Confirm") { arg0, arg1 ->

                if (radioFocPercent.isChecked){
                    soldProduct.focPercent = focVolume.text.toString().toDouble()
                    soldProduct.setFocType(true)
                } else{
                    soldProduct.focAmount = focVolume.text.toString().toDouble()
                    soldProduct.setFocType(false)
                }

                saleViewModel.calculateSoldProductData(false, soldProduct, mSoldProductListAdapter.getDataList() as ArrayList, this.promotionList, position)

            }
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.setOnShowListener {

            if (soldProduct.isFocTypePercent){
                radioFocPercent.isChecked = true
                focVolume.setText(soldProduct.focPercent.toString())

            } else{
                radioFocAmount.isChecked = true
                focVolume.setText(soldProduct.focAmount.toString())
            }
            focVolume.selectAll()

        }

        alertDialog.show()

    }

    private fun saveSaleData() {

        if (mSoldProductListAdapter.getDataList().isEmpty()) {
            AlertDialog.Builder(this@SaleActivity)
                .setTitle("Alert")
                .setMessage("You must specify at least one product.")
                .setPositiveButton("OK", null)
                .show()
            return
        }

        if (tvNetAmount.text == "0" || tvNetAmount.text == "0.0") {
            AlertDialog.Builder(this@SaleActivity)
                .setTitle("Alert")
                .setMessage("Net amount should be more than zero.")
                .setPositiveButton("OK", null)
                .show()

            return
        }

        // ToDo - Go to checkout page if one of the two same products is selected for foc

    }

    private fun getIntentData() {

        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)

//        if (intent.getSerializableExtra(SOLD_PROUDCT_LIST_KEY) != null) {
//
//            soldProductList = intent.getSerializableExtra(SOLD_PROUDCT_LIST_KEY) as ArrayList<SoldProduct>
//        }
//
//        if (intent.getSerializableExtra(SaleCheckoutActivity.PRESENT_PROUDCT_LIST_KEY) != null) {
//            promotionArrayList =
//                    intent.getSerializableExtra(SaleCheckoutActivity.PRESENT_PROUDCT_LIST_KEY) as ArrayList<Promotion>
//        }
//
//        if (intent.getSerializableExtra(SaleCheckoutActivity.DUPLICATE_PRODUCT_LIST_KEY) != null) {
//            duplicateProductList =
//                    intent.getSerializableExtra(SaleCheckoutActivity.DUPLICATE_PRODUCT_LIST_KEY) as ArrayList<Product>
//        }
//
//        if (intent.getSerializableExtra(SaleCheckoutActivity.DUPLICATE_PROMOTION_LIST_KEY) != null) {
//            promotionGiftByClassDis =
//                    intent.getSerializableExtra(SaleCheckoutActivity.DUPLICATE_PROMOTION_LIST_KEY) as ArrayList<Int>
//        }
//
//        if (intent.getSerializableExtra(SaleCheckoutActivity.PERCENT_TOTAL_COUNT) != null) {
//            PercentTotalCount =
//                    intent.getSerializableExtra(SaleCheckoutActivity.PERCENT_TOTAL_COUNT) as ArrayList<String>
//        }
//        if (intent.getSerializableExtra(SaleCheckoutActivity.GIFT_TOTAL_COUNT) != null) {
//            giftTotalCount = intent.getSerializableExtra(SaleCheckoutActivity.GIFT_TOTAL_COUNT) as ArrayList<String>
//        }
//
//        if (intent.getSerializableExtra(SaleCheckoutActivity.PERCENT_CLASS_CAT_ID) != null) {
//            PercentCategoryClassId =
//                    intent.getSerializableExtra(SaleCheckoutActivity.PERCENT_CLASS_CAT_ID) as HashMap<String, Int>
//        }
//        if (intent.getSerializableExtra(SaleCheckoutActivity.GIFT_CLASS_CAT_ID) != null) {
//            giftCategoryClassId =
//                    intent.getSerializableExtra(SaleCheckoutActivity.GIFT_CLASS_CAT_ID) as HashMap<String, Int>
//        }
//
//        if (intent.getSerializableExtra(SaleCheckoutActivity.PERCENT_AMT) != null) {
//            PercentAmount = intent.getSerializableExtra(SaleCheckoutActivity.PERCENT_AMT) as HashMap<String, Double>
//        }
//        if (intent.getSerializableExtra(SaleCheckoutActivity.GIFT_AMT) != null) {
//            giftAmount = intent.getSerializableExtra(SaleCheckoutActivity.GIFT_AMT) as HashMap<String, Double>
//        }
    }

    private fun onClearArrayList(){
        // ToDo - To clear all the list
    }

    private fun updatePromotionProductList(){
        mPromotionGiftPresentListAdapter.setNewList(promotionList)
    }

}