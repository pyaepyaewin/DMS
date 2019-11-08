package com.aceplus.dms.ui.activities.customer

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.CustomerVisitActivity
import com.aceplus.dms.ui.fragments.customer.FragmentDeliveryReport
import com.aceplus.dms.viewmodel.customer.delivery.DeliveryViewModel
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.model.delivery.DeliverItem
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.util.*

class DeliveryActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    override val layoutId: Int
        get() = R.layout.activity_report
    private val fragmentDeliveryViewModel: DeliveryViewModel by viewModel()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, DeliveryActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reports.visibility = View.GONE
        reportTitle.text = "DELIVERY"
        val transaction = supportFragmentManager.beginTransaction()
        val deliveryReportFragment = FragmentDeliveryReport()
        deliveryReportFragment.deliveryReportsArrayList = getCustomerAndDelivery()
        deliveryReportFragment.isDelivery = true
        transaction.replace(R.id.fragment_report, FragmentDeliveryReport())
        transaction.addToBackStack(null)
        transaction.commit()


        cancel_img.setOnClickListener {
            val intent = Intent(this@DeliveryActivity, CustomerVisitActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getCustomerAndDelivery(): List<Deliver> {
        val deliveryList = ArrayList<Deliver>()
        val deliver = Deliver()
        val deliverItemList = ArrayList<DeliverItem>()
        fragmentDeliveryViewModel.deliveryDataList.observe(this, Observer {
            for (i in it!!) {
                deliver.deliverId = i.dId
                deliver.customerId = i.cId.toString()
                deliver.customerName = i.customerName
                deliver.invoiceNo = i.invoiceNo
                deliver.amount = i.amount!!.toDouble()
                deliver.paidAmount = i.paidAmount!!.toDouble()
                deliver.saleManId = i.saleManId!!.toInt()
                deliver.discount = i.discount!!.toDouble()
                deliver.discountPercent = i.discountPercent!!.toDouble()
                deliver.customerAddres = i.address
                deliver.remark = i.remark
            }

            fragmentDeliveryViewModel.deliveryItemDataList.observe(this, Observer { list ->
                val deliverItem = DeliverItem()
                for (i in list!!) {
                    deliverItem.deliverId = i.delivery_id!!.toInt()
                    deliverItem.stockNo = i.stock_id
                    deliverItem.orderQty = i.order_quantity!!.toInt()
                    deliverItem.sPrice = i.s_price!!.toDouble()
                    deliverItem.receivedQty = i.received_quantity!!.toInt()
                }
                if (deliverItem.deliverId == deliver.deliverId) {
                    deliverItemList.add(deliverItem)
                }
            })

            fragmentDeliveryViewModel.deliveryPresentDataList.observe(this, Observer {
                val deliverItem = DeliverItem()
                for (i in it!!) {
                    deliverItem.deliverId = i.sale_order_id!!.toInt()
                    deliverItem.orderQty = i.quantity!!.toInt()
                    deliverItem.stockNo = i.stock_id
                    deliverItemList.add(deliverItem)
                }
            })

            deliver.deliverItemList = deliverItemList
            deliveryList.add(deliver)
        })

        fragmentDeliveryViewModel.loadDeliveryList()
        fragmentDeliveryViewModel.loadDeliveryItemList(deliver.deliverId)
        fragmentDeliveryViewModel.loadDeliveryPresentList(deliver.deliverId)


        return deliveryList
    }

    override fun onBackPressed() {
        finish()
    }
}
