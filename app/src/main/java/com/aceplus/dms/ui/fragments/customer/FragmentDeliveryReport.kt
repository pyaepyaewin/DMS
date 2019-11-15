package com.aceplus.dms.ui.fragments.customer

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.customer.saleorder.SaleOrderActivity
import com.aceplus.dms.ui.adapters.customer.DeliveryActivityAdapter
import com.aceplus.dms.viewmodel.customer.delivery.DeliveryViewModel
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.model.delivery.DeliverItem
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_delivery_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import kotlin.math.roundToInt

class FragmentDeliveryReport : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private var deliveryReportsArrayList = ArrayList<Deliver>()
    private val deliveryActivityAdapter: DeliveryActivityAdapter by lazy {
        DeliveryActivityAdapter(
            this::onClickItem
        )
    }
    private var deliver1: Deliver? = null
    private val fragmentDeliveryViewModel: DeliveryViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentDeliveryViewModel.deliveryDataList.observe(this, Observer {
            val deliveryList = ArrayList<Deliver>()
            for (i in it!!) {
                val deliver = Deliver()
                deliver.deliverId = i.dId
                deliver.customerId = i.cId.toString()
                deliver.customerName = i.customerName
                deliver.invoiceNo = i.invoiceNo
                deliver.amount = i.amount!!.toDouble()
                deliver.paidAmount = i.paidAmount!!.toDouble()
                deliver.saleManId = i.saleManId!!.toInt()
                deliver.discount = i.discount!!
                deliver.discountPercent = i.discountPercent!!
                deliver.customerAddres = i.address
                deliver.remark = i.remark
                deliveryList.add(deliver)
            }
            deliveryActivityAdapter.setNewList(deliveryList)
            deliveryReportsArrayList = deliveryList
        })
        deliveryReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = deliveryActivityAdapter
        }
        fragmentDeliveryViewModel.loadDeliveryList()
        //Delivery Item List...........
        fragmentDeliveryViewModel.deliveryAllItemDataList.observe(this, Observer { list ->
            val deliverItem = DeliverItem()
            val deliverItemList = ArrayList<DeliverItem>()
            for (i in list!!.first) {
                deliverItem.deliverId = i.delivery_id!!.toInt()
                deliverItem.stockNo = i.stock_id
                deliverItem.orderQty = i.order_quantity!!.toDouble().roundToInt()
                deliverItem.sPrice = i.s_price!!.toDouble()
                deliverItem.receivedQty = i.received_quantity!!.toDouble().roundToInt()
                deliverItemList.add(deliverItem)
            }
            for (i in list!!.second) {
                deliverItem.deliverId = i.sale_order_id!!.toInt()
                deliverItem.orderQty = i.quantity!!.toInt()
                deliverItem.stockNo = i.stock_id
                deliverItemList.add(deliverItem)
            }
            //Sold Product Info List
            var soldProductList = ArrayList<SoldProductInfo>()
            for (i in deliverItemList) {
                fragmentDeliveryViewModel.deliveryProductDataList.observe(this, Observer {
                    val innerSoldProductList = ArrayList<SoldProductInfo>()
                    for (product in it!!) {
                        val productItem = Product()
                        productItem.id = product.id
                        productItem.product_name = product.product_name
                        productItem.selling_price = product.selling_price
                        productItem.purchase_price = product.purchase_price
                        productItem.discount_type = product.discount_type
                        productItem.remaining_quantity = product.remaining_quantity
                        val soldProduct = SoldProductInfo(productItem, false)
                        val um = product.um
                        soldProduct.product.um = um
                        soldProduct.orderedQuantity = i.orderQty
                        soldProduct.quantity = soldProduct.orderedQuantity
                        soldProduct.product.product_id = i.stockNo

                        if (i.sPrice == 0.0) {
                            soldProduct.product.selling_price = 0.0.toString()
                        }
                        innerSoldProductList.add(soldProduct)
                        soldProductList = innerSoldProductList
                    }
                })
                fragmentDeliveryViewModel.loadProductDeliveryList(i.stockNo)
            }

            val copySoldProductList = ArrayList(soldProductList)
            for (soldProduct in soldProductList) {
                if (soldProduct.orderedQuantity == 0) {
                    copySoldProductList.remove(soldProduct)
                }
            }
            soldProductList = copySoldProductList
            if (soldProductList.size == 0) run {
                AlertDialog.Builder(activity)
                    .setTitle("Delivery")
                    .setMessage("No products to deliver for this invoice.")
                    .setPositiveButton("OK", null)
                    .setIcon(R.drawable.info)
                    .show()
            } else if (list.third != null && soldProductList.size != 0) {
                val intent = SaleOrderActivity.newIntentFromDelivery(
                    activity!!,
                    true,
                    list.third!!,
                    soldProductList,
                    deliver1!!
                )
                startActivity(intent)
                activity!!.finish()
            }


        })

    }

    private fun onClickItem(deliver: Deliver) {
        deliver1 = deliver
        fragmentDeliveryViewModel.loadAllDeliveryItemList(deliver.deliverId, deliver.customerId.toInt())
    }
}