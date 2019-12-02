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
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.delivery.DeliveryItem
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_delivery_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class FragmentDeliveryReport : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private var deliveryReportsArrayList = ArrayList<Deliver>()
    private val deliveryActivityAdapter: DeliveryActivityAdapter by lazy {
        DeliveryActivityAdapter(
            this::onClickItem
        )
    }
    private var soldProductList = ArrayList<SoldProductInfo>()
    private var customer: Customer? = null
    private var delivery:Deliver? = null
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
            if (list != null) {
                soldProductList = list.first as ArrayList<SoldProductInfo>
                customer = list.second
//                for (product in soldProductList) {
//                    if (product.orderedQuantity == 0) {
//                        soldProductList.remove(product)
//                    }
//                }
                if (soldProductList.isEmpty()) {
                    AlertDialog.Builder(activity)
                        .setTitle("Delivery")
                        .setMessage("No products to deliver for this invoice.")
                        .setPositiveButton("OK", null)
                        .setIcon(R.drawable.info)
                        .show()
                } else if (customer != null && soldProductList.isNotEmpty()){
                    val intent = SaleOrderActivity.newIntentFromDelivery(
                        activity!!,
                        customer!!,
                        soldProductList,
                        delivery!!
                    )
                    startActivity(intent)
                    activity!!.finish()
                }
                fragmentDeliveryViewModel.deliveryAllItemDataList.value = null
            }
        })
    }

    private fun onClickItem(deliver: Deliver) {
        delivery = deliver
        fragmentDeliveryViewModel.loadAllDeliveryItemList(deliver.deliverId.toString(), deliver.customerId.toInt())
    }
}