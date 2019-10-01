package com.aceplus.dms.ui.activities.customer.sale

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.sale.ProductListAdapter
import com.aceplus.dms.ui.adapters.sale.PromotionPlanGiftListAdapter
import com.aceplus.dms.ui.adapters.sale.SoldProductListAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.sale.SaleViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale1.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.util.*

class SaleActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale1

    companion object {
        private const val IE_SALE_EXCHANGE = "IE_SALE_EXCHANGE"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

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

    private val mProductListAdapter by lazy {
        ProductListAdapter(::onClickProductListItem)
    }
    private val mSoldProductListAdapter by lazy {
        SoldProductListAdapter(::onLongClickSoldProductListItem)
    }
    private val mPromotionGiftPresentListAdapter by lazy {
        PromotionPlanGiftListAdapter()
    }
    private val mPromotionItemListAdapter by lazy {
        //        PromotionPlanItemListAdapter()
    }
    private val mSearchProductAdapter by lazy {
        ArrayAdapter<String>(
            this@SaleActivity, android.R.layout.simple_list_item_1, ArrayList<String>()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setupUI()

        catchEvents()

        saleViewModel.productDataList.observe(this, Observer { it ->
            it?.let {
                mProductListAdapter.setNewList(it.first as java.util.ArrayList<Product>)
                mSearchProductAdapter.clear()
                mSearchProductAdapter.addAll(it.second)
                mSearchProductAdapter.notifyDataSetChanged()
            }
                ?: Utils.commonDialog("No issued product", this, 2)
        })
        saleViewModel.soldProductList.observe(this, Observer {
            mSoldProductListAdapter.setNewList(it as ArrayList<Product>)
        })

        saleViewModel.loadProductList()

        //todo
//        products = getProducts("")
//        calculateClassDiscountByPrice()

        getIntentData()
    }

    private fun setupUI() {
        val check = intent.getStringExtra(IE_SALE_EXCHANGE)
        if (check.equals("yes", ignoreCase = true)) {
            tvTitle.text = getString(R.string.sale_exchange)
        }

        saleDateTextView.text = Utils.getCurrentDate(false)

        //todo
//        searchAndSelectProductsLayout.visibility = if (this.isDelivery) View.GONE else View.VISIBLE
//        tableHeaderOrderedQty.visibility = if (this.isDelivery) View.VISIBLE else View.GONE
//        tableHeaderDiscount.visibility = if (this.isPreOrder) View.GONE else View.VISIBLE

    }

    private fun catchEvents() {
        rvProductList.adapter = mProductListAdapter
        rvProductList.layoutManager = GridLayoutManager(applicationContext, 1)

        rvSoldProductList.adapter = mSoldProductListAdapter
        rvSoldProductList.layoutManager = GridLayoutManager(applicationContext, 1)

        rvPromotionPlanGiftList.adapter = mPromotionGiftPresentListAdapter
        rvPromotionPlanGiftList.layoutManager = GridLayoutManager(applicationContext, 1)

//        rvPromotionPlanItemList.adapter = mPromotionItemPresentListAdapter
//        rvPromotionPlanItemList.layoutManager = GridLayoutManager(applicationContext, 1)

        searchAutoCompleteTextView.setAdapter(mSearchProductAdapter)
        searchAutoCompleteTextView.threshold = 1

        searchAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            //            var tempProduct: Product? = null
            //
            //            for (product in products) {
            //
            //                if (product.getName().equals(parent.getItemAtPosition(position).toString())) {
            //
            //                    tempProduct = product
            //                }
            //            }
            //
            //            onSelectAtMostTwoSameProduct(tempProduct)
            searchAutoCompleteTextView.setText("")
        }

        cancelImg.setOnClickListener { onBackPressed() }
        checkoutImg.setOnClickListener { saveSaleData() }

    }

    private fun onSelectAtMostTwoSameProduct(tempProduct: Product){
        val productDuplicateCheck = mutableListOf<Product>()
            var sameProduct = false
            for (tempSoldProduct in mSoldProductListAdapter.getDataList()) {
                if (tempSoldProduct.product_id === tempProduct.product_id) {
                    sameProduct = true
                    if (productDuplicateCheck.contains(tempProduct))
                        Constant.PRODUCT_COUNT = 2
                    else
                        Constant.PRODUCT_COUNT++
                    break
                } else {
                    if (!productDuplicateCheck.contains(tempProduct)) {
                        Constant.PRODUCT_COUNT = 0
                    }
                }
            }

            if (!sameProduct) {
//                soldProductList.add(SoldProduct(tempProduct, false))
                mSoldProductListAdapter.addNewItem(tempProduct)
            } else {
                if (Constant.PRODUCT_COUNT < 2 && !productDuplicateCheck.contains(tempProduct)) {
                    //                    if (tempProduct.getRemainingQty() != 1) {

//                    soldProductList.add(SoldProduct(tempProduct, false))
//                    soldProductListRowAdapter.notifyDataSetChanged()

                    mSoldProductListAdapter.addNewItem(tempProduct)

                    if (Constant.PRODUCT_COUNT == 1) {
                        Constant.PRODUCT_COUNT = 0
                        productDuplicateCheck.add(tempProduct)
//                        soldProductsCheck.add(SoldProduct(tempProduct, false))
                    }
                    //                    } else {
                    //                        Constant.PRODUCT_COUNT = 0;
                    //                        Utils.commonDialog("Your Quantity is just only 1", SaleActivity.this, 2);
                    //                    }
                } else {
                    Constant.PRODUCT_COUNT = 0
                    Utils.commonDialog("Already have this product", this@SaleActivity, 2)
                }
        }
    }
    private fun onClickProductListItem(product: Product) {
        val newSoldProductList = mSoldProductListAdapter.getDataList() as MutableList
        newSoldProductList.add(product)
        saleViewModel.soldProductList.postValue(newSoldProductList)

//        lpbreak = false
//            var tempProduct: Product? = null
//            for (product in products) {
//                if (product.getName().equals(parent.getItemAtPosition(position).toString())) {
//                    tempProduct = product
//                }
//            }
//            onSelectAtMostTwoSameProduct(tempProduct)
    }

    private fun onLongClickSoldProductListItem(product: Product) {
        AlertDialog.Builder(this@SaleActivity)
            .setTitle("Delete sold product")
            .setMessage("Are you sure you want to delete " + product.product_name + "?")
            .setPositiveButton("Yes") { arg0, arg1 ->
                //                val quantity = soldProductList.get(position).getQuantity()
//                val product = soldProductList.get(position).getProduct()
//                for (i in productDuplicateCheck.indices) {
//                    val tempProduct = productDuplicateCheck.get(i)
//                    if (tempProduct.getStockId() === product.getStockId()) {
//                        productDuplicateCheck.remove(tempProduct)
//                    }
//                }
//
//                if (quantity != 0) {
//                    if (totalQtyForGiftWithProudct1.containsKey(String.valueOf(product.getClassId()))) {
//                        val tempQty = totalQtyForGiftWithProudct1.get(String.valueOf(product.getClassId()))
//                        totalQtyForGiftWithProudct1.put(String.valueOf(product.getClassId()), (tempQty!! - quantity))
//                        val amt = totalAmtForGiftWithProudct1.get(String.valueOf(product.getClassId()))
//                        totalAmtForGiftWithProudct1.put(
//                            String.valueOf(product.getClassId()),
//                            amt!! - product.getPrice()
//                        )
//                        totalQtyForGiftWithProudct1.remove(String.valueOf(product.getClassId()))
//                        totalAmtForGiftWithProudct1.remove(String.valueOf(product.getClassId()))
//
//                    }
//                    if (totalQtyForGiftWithProudct.containsKey(String.valueOf(product.getClassId()))) {
//                        val tempQty = totalQtyForGiftWithProudct.get(String.valueOf(product.getClassId()))
//                        totalQtyForGiftWithProudct.put(String.valueOf(product.getClassId()), (tempQty!! - quantity))
//                        deletedItem = true
//                    }
//
//                    if (productItemForGift.contains(product.getName())) {
//                        productItemForGift.remove(product.getName())
//                    }
//
//                    if (promotionArrayList.size > 0) {
//                        try {
//                            for (promo in promotionArrayList) {
//                                if (promo.getClassId().equalsIgnoreCase(String.valueOf(product.getClassId()))) {
//                                    promotionGiftByClassDis.remove(Integer.valueOf(promo.getPromotionProductId()))
//                                    promotionArrayList.remove(promo)
//
//                                }
//                                //                                                promotionGiftByClassDis.clear();
//                            }
//                        } catch (exception: ConcurrentModificationException) {
//                            exception.printStackTrace()
//                        }
//
//                        updatePromotionProductList()
//
//                    }
//                }
//
//                soldProductList.removeAt(position)
//                soldProductListRowAdapter.notifyDataSetChanged()
//                if (soldProductList.size != 0) {
//                    calculatePromotinPriceAndGift(soldProductList.get(soldProductList.size - 1))
//                } else {
//                    promotionArrayList.clear()
//                    promotionGiftByClassDis.clear()
//                    totalQtyForGiftWithProudct.clear()
//                    totalQtyForGiftWithProudct1.clear()
//                    totalAmtForGiftWithProudct1.clear()
//                    totalQtyForPercentWithProudct1.clear()
//                    totalAmtForPercentWithProudct1.clear()
//                    totalQtyForPercentWithProudct.clear()
//                    giftCategoryClassId.clear()
//                    PercentCategoryClassId.clear()
//                    productItemForGift.clear()
//                    productItemForPercent.clear()
//                    countForGiftItem = 0
//                    giftTotalCount.clear()
//                    //tempPromoListByClassId.clear();
//
//                    updatePromotionProductList()
//                }
            }
            .setNegativeButton("No", null)
            .show()
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
                .setMessage("Net Amount should be more than zero.")
                .setPositiveButton("OK", null)
                .show()

            return
        }

//        val boo = toEnableFocSameProduct()
//        if (boo) {
//            val intent = Intent(this@SaleActivity, SaleCheckoutActivity::class.java)
//            intent.putExtra(SaleCheckoutActivity.REMAINING_AMOUNT_KEY, this@SaleActivity.remainingAmount)/*
//                intent.putExtra(SaleCheckoutActivity.USER_INFO_KEY
//                        , SaleActivity.this.salemanInfo.toString());*/
//            intent.putExtra(SaleCheckoutActivity.CUSTOMER_INFO_KEY, this@SaleActivity.customer)
//            intent.putExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY, this@SaleActivity.soldProductList)
//            intent.putExtra(SaleCheckoutActivity.PRESENT_PROUDCT_LIST_KEY, this@SaleActivity.promotionArrayList)
//            intent.putExtra(SaleCheckoutActivity.DUPLICATE_PRODUCT_LIST_KEY, this@SaleActivity.productDuplicateCheck)
//            intent.putExtra(
//                SaleCheckoutActivity.DUPLICATE_PROMOTION_LIST_KEY,
//                this@SaleActivity.promotionGiftByClassDis
//            )
//            intent.putExtra(SaleCheckoutActivity.PERCENT_TOTAL_COUNT, this@SaleActivity.PercentTotalCount)
//            intent.putExtra(SaleCheckoutActivity.GIFT_TOTAL_COUNT, this@SaleActivity.giftTotalCount)
//            intent.putExtra(SaleCheckoutActivity.PERCENT_CLASS_CAT_ID, this@SaleActivity.PercentCategoryClassId)
//            intent.putExtra(SaleCheckoutActivity.GIFT_CLASS_CAT_ID, this@SaleActivity.giftCategoryClassId)
//            intent.putExtra(SaleCheckoutActivity.PERCENT_AMT, this@SaleActivity.PercentAmount)
//            intent.putExtra(SaleCheckoutActivity.GIFT_AMT, this@SaleActivity.giftAmount)
//            if (this@SaleActivity.orderedInvoice != null) {
//                intent.putExtra(SaleCheckoutActivity.ORDERED_INVOICE_KEY, this@SaleActivity.orderedInvoice.toString())
//            }
//
//            if (check.equals("yes", ignoreCase = true)) {
//                intent.putExtra("SaleExchange", "yes")
//                intent.putExtra(SALE_RETURN_INVOICEID_KEY, getIntent().getStringExtra(SALE_RETURN_INVOICEID_KEY))
//                intent.putExtra(
//                    Constant.KEY_SALE_RETURN_AMOUNT,
//                    getIntent().getDoubleExtra(Constant.KEY_SALE_RETURN_AMOUNT, 0.0)
//                )
//            } else {
//                intent.putExtra("SaleExchange", "no")
//            }
//            startActivity(intent)
//        }
    }

    private fun getIntentData() {
//        customer = intent.getSerializableExtra(CUSTOMER_INFO_KEY) as Customer
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
//            productDuplicateCheck =
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
}