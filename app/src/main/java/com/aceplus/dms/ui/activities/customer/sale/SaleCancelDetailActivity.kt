package com.aceplus.dms.ui.activities.customer.sale

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.sale.SaleCancelDetailAdapter
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.salecancelviewmodel.SaleCancelDetailViewModel
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import com.aceplus.domain.vo.SoldProductInfo
import kotlinx.android.synthetic.main.activity_sale.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class SaleCancelDetailActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    var soldProductList1 = mutableListOf<String>()
    var soldProductDataList = mutableListOf<Product>()
    private val duplicateProductList = mutableListOf<SoldProductDataClass>()

    companion object {
        fun getIntent(context: Context, invoiceID: String): Intent {
            val saleCancelIntent = Intent(context, SaleCancelDetailActivity::class.java)
            saleCancelIntent.putExtra("INVOICE_ID", invoiceID)
            return saleCancelIntent
        }
    }

    private val saleCancelDetailViewModel: SaleCancelDetailViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(SaleCancelDetailViewModel::class.java)
    }

    private val saleCancelDetailAdapter: SaleCancelDetailAdapter by lazy {
        SaleCancelDetailAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale)
        var invoiceId = intent.getStringExtra("INVOICE_ID")
        saleCancelDetailViewModel.productIdListSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {

                soldProductList1 = it as MutableList<String>

                saleCancelDetailViewModel.loadSoldProductList(soldProductList1)


            })

        saleCancelDetailViewModel.productIdListErrorState.observe(
            this,
            android.arch.lifecycle.Observer {

            })

        saleCancelDetailViewModel.loadSoldProductIdList(invoiceId)

        saleCancelDetailViewModel.soldProductListSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                soldProductDataList = it as MutableList<Product>
                var soldProductInfoList= ArrayList<SoldProductInfo>()
                soldProductDataList.map {
                    val soldProductInfo = SoldProductInfo()
                    soldProductInfo.product = it
                    soldProductInfoList.add(soldProductInfo)
                }

                saleCancelDetailAdapter.setNewList(soldProductInfoList)

            })



        saleCancelDetailViewModel.soldProductListErrorState.observe(
            this,
            android.arch.lifecycle.Observer {

            })


        soldProductList.apply {
            layoutManager = LinearLayoutManager(this@SaleCancelDetailActivity)
            adapter = saleCancelDetailAdapter
        }


    }
}

//    private fun onClickNoticeListItem(data: Product) {
//    }
    //private fun onLongClickSoldProductListItem(soldProduct: SoldProductDataClass, position: Int) {

//        AlertDialog.Builder(this@SaleCancelDetailActivity)
//            .setTitle("Delete sold product")
//            .setMessage("Are you sure you want to delete ${soldProduct.product_name}?")
//            .setPositiveButton("Yes") { arg0, arg1 ->

//                for (i in duplicateProductList.indices) {
//                    if (duplicateProductList[i].product_id == soldProduct.product_id) {
//                        duplicateProductList.removeAt(i)
//                        break
//                    }
//                }

//                if (soldProduct.quantity != 0){
//
//                    if(saleViewModel.totalQtyForGiftWithProduct1.containsKey(soldProduct.product.class_id)){
//                        val tempQty = saleViewModel.totalQtyForGiftWithProduct1[soldProduct.product.class_id]
//                        saleViewModel.totalQtyForGiftWithProduct1[soldProduct.product.class_id!!] = tempQty!! - soldProduct.quantity
//                        val amt = saleViewModel.totalAmtForGiftWithProduct1[soldProduct.product.class_id!!]
//                        saleViewModel.totalAmtForGiftWithProduct1[soldProduct.product.class_id!!] = amt!! - soldProduct.product.selling_price!!.toDouble()
//                        saleViewModel.totalQtyForGiftWithProduct1.remove(soldProduct.product.class_id!!)
//                        saleViewModel.totalAmtForGiftWithProduct1.remove(soldProduct.product.class_id!!)
//                    }
//
//                    if (saleViewModel.totalQtyForGiftWithProduct.containsKey(soldProduct.product.class_id)){
//                        val tempQty = saleViewModel.totalQtyForGiftWithProduct[soldProduct.product.class_id]
//                        saleViewModel.totalQtyForGiftWithProduct[soldProduct.product.class_id!!] = tempQty!! - soldProduct.quantity
//                    }
//
//                    if (saleViewModel.productItemForGift.contains(soldProduct.product.product_name)){
//                        saleViewModel.productItemForGift.remove(soldProduct.product.product_name)
//                    }
//
//                    if (promotionList.size > 0){
//                        try {
//                            for (promotion in promotionList){
//                                // ToDo - remove promotion if same class id is founded
//                                //ToDo - To check promoPlanID and classID not found in Promotion class
//                            }
//                        } catch (exception: ConcurrentModificationException){
//                            exception.printStackTrace()
//                        }
//                        updatePromotionProductList()
//                    }
//
//                }
//
//                val oldList = mSoldProductListAdapter.getDataList() as ArrayList
//                oldList.remove(soldProduct)
//                saleViewModel.calculateSoldProductData(oldList, this.promotionList)
//
//                if (oldList.isEmpty()){
//                    promotionList.clear()
//                    promotionGiftByClassDis.clear()
//                    saleViewModel.totalQtyForPercentWithProduct.clear()
//                    saleViewModel.totalQtyForPercentWithProduct1.clear()
//                    saleViewModel.totalAmtForPercentWithProduct1.clear()
//                    saleViewModel.totalQtyForGiftWithProduct.clear()
//                    saleViewModel.totalQtyForGiftWithProduct1.clear()
//                    saleViewModel.totalAmtForGiftWithProduct1.clear()
//                    giftCategoryClassId.clear()
//                    percentCategoryClassId.clear()
//                    saleViewModel.productItemForPercent.clear()
//                    saleViewModel.productItemForGift.clear()
//                    //countForGiftItem = 0
//                    saleViewModel.giftTotalCount.clear()
//                }
//
//            }
//                    .setNegativeButton("No", null)
//                    .show()
//            }
//    }
//    private fun onClickQtyButton(soldProduct: SoldProductDataClass, position: Int){
//
//        val layoutInflater = this@SaleCancelDetailActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null)
//
//        val remainingQtyTextView = view.findViewById(R.id.availableQuantity) as TextView
//        val quantityEditText = view.findViewById(R.id.quantity) as EditText
//        val messageTextView = view.findViewById(R.id.message) as TextView
//
//        val alertDialog = AlertDialog.Builder(this@SaleCancelDetailActivity)
//            .setView(view)
//            .setTitle("Sale Quantity")
//            .setPositiveButton("Confirm") { arg0, arg1 ->
//
////                if (quantityEditText.text.toString().isEmpty()) {
////                    messageTextView.text = "You must specify quantity."
////                } else{
////
////                    val quantity = quantityEditText.text.toString().toInt()
////
////                    if (soldProduct.quantity != 0 && soldProduct.quantity < quantity){
////                        /*updatedQtyForGift = true
////                        updatedQtyForPercent = true*/
////                        soldProduct.currentProductQty = soldProduct.quantity
////                    }
////
////                    soldProduct.quantity = quantity
////                    soldProduct.product.selling_price
////
////                    //saleViewModel.calculateSoldProductData(true, soldProduct, mSoldProductListAdapter.getDataList() as ArrayList, this.promotionList, position)
////
////                    val newList = mSoldProductListAdapter.getDataList() as ArrayList
////                    newList[position] = soldProduct
////                    saleViewModel.calculateSoldProductData(newList, this.promotionList)
////
////                }
//
//            }
//            .setNegativeButton("Cancel", null)
//            .create()
//
////        alertDialog.setOnShowListener {
////            if (isPreOrder) availableQuantityLayout.visibility = View.GONE
////            else remainingQtyTextView.text = soldProduct.product.remaining_quantity.toString()
////        }
//
//        alertDialog.show()
//
//    }
//
//    private fun onClickFocButton(soldProduct: SoldProductDataClass, position: Int){
//
//        val layoutInflater = this@SaleCancelDetailActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = layoutInflater.inflate(R.layout.dialog_box_sale_foc, null)
//
//        val radioFocPercent: RadioButton = view.findViewById(R.id.radioPrecent)
//        val radioFocAmount: RadioButton = view.findViewById(R.id.radioAmount)
//        val focVolume: EditText = view.findViewById(R.id.focPercent)
//
//        val alertDialog = AlertDialog.Builder(this@SaleCancelDetailActivity)
//            .setView(view)
//            .setTitle("FOC percent or Amount")
//            .setPositiveButton("Confirm") { arg0, arg1 ->
//
////                if (radioFocPercent.isChecked){
////                    soldProduct.focPercent = focVolume.text.toString().toDouble()
////                    soldProduct.setFocType(true)
////                } else{
////                    soldProduct.focAmount = focVolume.text.toString().toDouble()
////                    soldProduct.setFocType(false)
////                }
////
////                val newList = mSoldProductListAdapter.getDataList() as ArrayList
////                newList[position] = soldProduct
////                saleViewModel.calculateSoldProductData(newList, this.promotionList)
//
//                //saleViewModel.calculateSoldProductData(false, soldProduct, mSoldProductListAdapter.getDataList() as ArrayList, this.promotionList, position)
//
//            }
//            .setNegativeButton("Cancel", null)
//            .create()

//        alertDialog.setOnShowListener {
//
//            if (soldProduct.isFocTypePercent){
//                radioFocPercent.isChecked = true
//                focVolume.setText(soldProduct.focPercent.toString())
//
//            } else{
//                radioFocAmount.isChecked = true
//                focVolume.setText(soldProduct.focAmount.toString())
//            }
//            focVolume.selectAll()
//
//        }
//
//        alertDialog.show()

//    }
//
//
//}
