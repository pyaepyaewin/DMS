package com.aceplus.dms.ui.activities.customer.sale

import android.app.Activity
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
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale1.*
import kotlinx.android.synthetic.main.dialog_box_sale_quantity.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.util.*
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

        getIntentData()
        setupUI()
        catchEvents()

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
        //tableHeaderDiscount.visibility = if (this.isPreOrder) View.GONE else View.VISIBLE

    }

    private fun catchEvents() {

        searchAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            onSelectAtMostTwoSameProduct(mProductListAdapter.getDataList()[position])
            searchAutoCompleteTextView.setText("")
        }

        cancelImg.setOnClickListener { onBackPressed() }
        checkoutImg.setOnClickListener { checkoutSale() }

        saleViewModel.productDataList.observe(this, Observer {
            it?.let {
                mProductListAdapter.setNewList(it.first as ArrayList<Product>)
                mSearchProductAdapter.clear()
                mSearchProductAdapter.addAll(it.second)
                mSearchProductAdapter.notifyDataSetChanged()
            }
                ?: Utils.commonDialog("No issued product", this, 2)
        })

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

        saleViewModel.calculatedSoldProductList.observe(this, Observer {
            if (it != null){
                mSoldProductListAdapter.setNewList(it.first)
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

        val newList = mSoldProductListAdapter.getDataList() as ArrayList
        newList[position] = data
        saleViewModel.calculateSoldProductData(newList, this.promotionList)

        //saleViewModel.calculateSoldProductData(false, data, mSoldProductListAdapter.getDataList() as ArrayList, this.promotionList, position)

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

                if(saleViewModel.totalQtyForGiftWithProduct1.containsKey(soldProduct.product.class_id)){
                    val tempQty = saleViewModel.totalQtyForGiftWithProduct1[soldProduct.product.class_id]
                    saleViewModel.totalQtyForGiftWithProduct1[soldProduct.product.class_id!!] = tempQty!! - soldProduct.quantity
                    val amt = saleViewModel.totalAmtForGiftWithProduct1[soldProduct.product.class_id!!]
                    saleViewModel.totalAmtForGiftWithProduct1[soldProduct.product.class_id!!] = amt!! - soldProduct.product.selling_price!!.toDouble()
                    saleViewModel.totalQtyForGiftWithProduct1.remove(soldProduct.product.class_id!!)
                    saleViewModel.totalAmtForGiftWithProduct1.remove(soldProduct.product.class_id!!)
                }

                if (saleViewModel.totalQtyForGiftWithProduct.containsKey(soldProduct.product.class_id)){
                    val tempQty = saleViewModel.totalQtyForGiftWithProduct[soldProduct.product.class_id]
                    saleViewModel.totalQtyForGiftWithProduct[soldProduct.product.class_id!!] = tempQty!! - soldProduct.quantity
                }

                if (saleViewModel.productItemForGift.contains(soldProduct.product.product_name)){
                    saleViewModel.productItemForGift.remove(soldProduct.product.product_name)
                }

                if (promotionList.size > 0){
                    try {
                        for (promotion in promotionList){
                            // ToDo - remove promotion if same class id is founded
                            //ToDo - To check promoPlanID and classID not found in Promotion class
                        }
                    } catch (exception: ConcurrentModificationException){
                        exception.printStackTrace()
                    }
                    updatePromotionProductList()
                }

            }

                val oldList = mSoldProductListAdapter.getDataList() as ArrayList
                oldList.remove(soldProduct)
                saleViewModel.calculateSoldProductData(oldList, this.promotionList)

                if (oldList.isEmpty()){
                    promotionList.clear()
                    promotionGiftByClassDis.clear()
                    saleViewModel.totalQtyForPercentWithProduct.clear()
                    saleViewModel.totalQtyForPercentWithProduct1.clear()
                    saleViewModel.totalAmtForPercentWithProduct1.clear()
                    saleViewModel.totalQtyForGiftWithProduct.clear()
                    saleViewModel.totalQtyForGiftWithProduct1.clear()
                    saleViewModel.totalAmtForGiftWithProduct1.clear()
                    giftCategoryClassId.clear()
                    percentCategoryClassId.clear()
                    saleViewModel.productItemForPercent.clear()
                    saleViewModel.productItemForGift.clear()
                    //countForGiftItem = 0
                    saleViewModel.giftTotalCount.clear()
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
        val availableQuantityLayout = view.findViewById(R.id.availableQuantityLayout) as LinearLayout
        val alertDialog = AlertDialog.Builder(this@SaleActivity)
            .setView(view)
            .setTitle("Sale Quantity")
            .setPositiveButton("Confirm") { arg0, arg1 ->

                if (quantityEditText.text.toString().isBlank()) {
                    messageTextView.text = "You must specify quantity."
                } else{

                    val quantity = quantityEditText.text.toString().toInt()

                    if (soldProduct.quantity != 0 && soldProduct.quantity < quantity){
                        /*updatedQtyForGift = true
                        updatedQtyForPercent = true*/
                        soldProduct.currentProductQty = soldProduct.quantity
                    }

                    soldProduct.quantity = quantity
                    soldProduct.product.selling_price

                    //saleViewModel.calculateSoldProductData(true, soldProduct, mSoldProductListAdapter.getDataList() as ArrayList, this.promotionList, position)

                    val newList = mSoldProductListAdapter.getDataList() as ArrayList
                    newList[position] = soldProduct
                    saleViewModel.calculateSoldProductData(newList, this.promotionList)

                }

            }
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.setOnShowListener {
            if (isPreOrder) availableQuantityLayout.visibility = View.GONE
            else remainingQtyTextView.text = soldProduct.product.remaining_quantity.toString()
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
                    soldProduct.focPercent = if (focVolume.text.isNotBlank()) focVolume.text.toString().toDouble() else 0.0
                    soldProduct.setFocType(true)
                } else{
                    soldProduct.focAmount = if (focVolume.text.isNotBlank()) focVolume.text.toString().toDouble() else 0.0
                    soldProduct.setFocType(false)
                }

                val newList = mSoldProductListAdapter.getDataList() as ArrayList
                newList[position] = soldProduct
                saleViewModel.calculateSoldProductData(newList, this.promotionList)

                //saleViewModel.calculateSoldProductData(false, soldProduct, mSoldProductListAdapter.getDataList() as ArrayList, this.promotionList, position)

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

    private fun checkoutSale() {

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

        val isFocPass = toEnableFocSameProduct()

        if (isFocPass){

            val intent = SaleCheckoutActivity.newIntentFromSale(this, customer!!, mSoldProductListAdapter.getDataList() as ArrayList, this.promotionList)
            startActivityForResult(intent, Utils.RQ_BACK_TO_CUSTOMER)

        }

    }

    private fun toEnableFocSameProduct(): Boolean{

        for (product in duplicateProductList){

            val arrList = ArrayList<String>()
            for (soldProduct in mSoldProductListAdapter.getDataList()){

                if (soldProduct.quantity == 0){
                    AlertDialog.Builder(this@SaleActivity)
                        .setTitle("Alert")
                        .setMessage("Quantity must not be zero.")
                        .setPositiveButton("OK", null)
                        .show()
                    return false
                } else{
                    val stockId = soldProduct.product.id // Need to check stock id or ID
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
                AlertDialog.Builder(this@SaleActivity)
                    .setTitle("Alert")
                    .setMessage("Need to enable one of FOC in the same product: ${product.product_name}")
                    .setPositiveButton("OK", null)
                    .show()
                return false
            }

        }
        return true
    }

    private fun getIntentData() {
        if (intent.getParcelableExtra<Customer>(IE_CUSTOMER_DATA) != null) customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        // ToDo - get intent data
    }

    private fun updatePromotionProductList(){
        mPromotionGiftPresentListAdapter.setNewList(promotionList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Utils.RQ_BACK_TO_CUSTOMER)
            if (resultCode == Activity.RESULT_OK){
                setResult(Activity.RESULT_OK)
                finish()
            }
    }

}