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
import com.aceplus.dms.ui.activities.customer.SaleOrderActivity
import com.aceplus.dms.ui.adapters.customer.DeliveryActivityAdapter
import com.aceplus.dms.viewmodel.customer.delivery.DeliveryViewModel
import com.aceplus.domain.model.Product
import com.aceplus.domain.model.SoldProduct
import com.aceplus.domain.model.customer.Customer
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.model.delivery.DeliverItem
import com.aceplus.domain.vo.customer.DeliveryVO
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_delivery_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class FragmentDeliveryReport : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    var deliveryReportsArrayList: List<Deliver> = ArrayList()
    var isDelivery: Boolean = false
    private val deliveryActivityAdapter: DeliveryActivityAdapter by lazy {
        DeliveryActivityAdapter(
            this::onClickItem
        )
    }

    private val fragmentDeliveryViewModel: DeliveryViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        deliveryActivityAdapter.setNewList(deliveryReportsArrayList as ArrayList<DeliveryVO>)
        Log.d("Delivery List is:","${deliveryReportsArrayList.size}")
        deliveryReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = deliveryActivityAdapter
        }

        fragmentDeliveryViewModel.loadDeliveryList()
    }

    private fun onClickItem(deliver: DeliveryVO) {
        if (this@FragmentDeliveryReport.isDelivery) {
            val deliveryItemList =
                this@FragmentDeliveryReport.deliveryReportsArrayList[deliver.dId].deliverItemList
            var soldProductList = this@FragmentDeliveryReport.getProductList(deliveryItemList)
            val copySoldProductList = ArrayList(soldProductList)
            for (soldProduct in soldProductList) {

                if (soldProduct.orderedQuantity == 0) {
                    copySoldProductList.remove(soldProduct)
                }
            }
            soldProductList = copySoldProductList
            val customer =
                getCustomerFromDB(deliveryReportsArrayList[deliver.dId].customerId.toInt())
            if (soldProductList.size == 0) run {
                AlertDialog.Builder(activity)
                    .setTitle("Delivery")
                    .setMessage("No products to deliver for this invoice.")
                    .setPositiveButton("OK", null)
                    .setIcon(R.drawable.info)
                    .show()

                return
            } else if (customer != null && soldProductList.size != 0) {
                val intent = SaleOrderActivity.newIntentFromDelivery(
                    activity!!,
                    true,
                    customer,
                    soldProductList,
                    deliveryReportsArrayList[deliver.dId]
                )
                startActivity(intent)
                activity!!.finish()
            }

        }
    }

    private fun getProductList(deliverItemList: List<DeliverItem>): ArrayList<SoldProduct> {
        val soldProductList = ArrayList<SoldProduct>()
        var deliverItem = DeliverItem()
        for (i in deliverItemList) {
            deliverItem = i
        }
        fragmentDeliveryViewModel.deliveryProductDataList.observe(this, Observer {
            for (product in it!!) {
                val soldProduct = SoldProduct(
                    Product(
                        product.id.toString(),
                        product.product_name,
                        product.selling_price!!.toDouble(),
                        product.purchase_price!!.toDouble(),
                        product.discount_type,
                        product.remaining_quantity
                    ), false
                )
                val um = product.um
                soldProduct.product.um = um
                soldProduct.orderedQuantity = deliverItem.orderQty
                soldProduct.quantity = soldProduct.orderedQuantity
                soldProduct.product.stockId = Integer.parseInt(deliverItem.stockNo)

                if (deliverItem.sPrice === 0.0) {
                    soldProduct.product.price = 0.0
                }
                soldProductList.add(soldProduct)
            }
        })

        fragmentDeliveryViewModel.loadProductDeliveryList(deliverItem.stockNo)
        return soldProductList
    }

    private fun getCustomerFromDB(customerId: Int): Customer {
        var customer: Customer? = null
        fragmentDeliveryViewModel.deliveryCustomerDataList.observe(this, Observer { it ->
            if (it != null) {
                customer = Customer(it.customer_id, it.customer_name, it.customer_type_id, it.customer_type_name, it.address, it.phone, it.township, it.credit_term, it.credit_limit!!.toDouble(), it.credit_amount!!.toDouble(), it.due_amount!!.toDouble(), it.prepaid_amount!!.toDouble(), it.payment_type, "false", it.latitude!!.toDouble(), it.longitude!!.toDouble(), it.visit_record!!.toInt())
            }
            customer!!.shopTypeId = it!!.shop_type_id
            customer!!.id = it!!.id
        })
        fragmentDeliveryViewModel.loadCustomerDeliveryList(customerId = customerId)
        return customer!!
    }
}