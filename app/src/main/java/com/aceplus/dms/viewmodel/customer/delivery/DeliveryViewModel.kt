package com.aceplus.dms.viewmodel.customer.delivery

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.delivery.*
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.route.RouteScheduleV2
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.model.forApi.delivery.DeliveryItemApi
import com.aceplus.domain.repo.deliveryrepo.DeliveryRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.domain.vo.customer.DeliveryVO
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import kotlin.math.roundToInt

class DeliveryViewModel(
    private val deliveryRepo: DeliveryRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var deliveryDataList = MutableLiveData<List<DeliveryVO>>()
    var deliveryAllItemDataList = MutableLiveData<Pair<List<SoldProductInfo>, Customer>>()

    fun loadDeliveryList() {
        launch {
            deliveryRepo.deliveryDataList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryDataList.postValue(it)
                }
        }
    }

    fun loadAllDeliveryItemList(deliveryId: String, customerId: Int) {
        var deliveryItemDataList = mutableListOf<DeliveryItem>()
        var deliveryPresentDataList = mutableListOf<DeliveryPresent>()
        var deliveryCustomerDataList: Customer
        var deliveryProductDataList = listOf<SoldProductInfo>()

        launch {
            val innerSoldProductList = ArrayList<SoldProductInfo>()
            deliveryRepo.deliveryItemDataList(deliveryId)
                .flatMap {
                    deliveryItemDataList = it as MutableList<DeliveryItem>
                    val stockIdList = mutableListOf<String>()
                    for (i in deliveryItemDataList) {
                        stockIdList.add(i.stock_id!!)
                    }
                    return@flatMap deliveryRepo.deliveryProductList(stockIdList as List<String>)
                }
                .flatMap {
                    for (product in it) {
                        for (data in deliveryItemDataList) {
                            if (data.stock_id == product.id.toString()) {
                                val soldProduct = SoldProductInfo(product, false)
                                val um = product.um
                                soldProduct.product.um = um
                                soldProduct.quantity = data.order_quantity!!.toDouble().roundToInt()
                                soldProduct.orderedQuantity = data.order_quantity!!.toDouble().roundToInt()
                                soldProduct.product.product_id = data.stock_id!!
                                innerSoldProductList.add(soldProduct)
                            }
                        }
                    }
                    return@flatMap deliveryRepo.deliveryPresentDataList(deliveryId)
                }
                .flatMap {
                    deliveryPresentDataList = it as MutableList<DeliveryPresent>
                    val stockIdList = mutableListOf<String>()
                    for (i in it) {
                        stockIdList.add(i.stock_id!!)
                    }
                    return@flatMap deliveryRepo.deliveryProductList(stockIdList as List<String>)
                }
                .flatMap {
                    for (product in it) {
                        val productItem = Product()
                        for (data in deliveryPresentDataList) {
                            if (data.stock_id == product.id.toString()) {
                                productItem.id = product.id
                                productItem.product_name = product.product_name
                                productItem.selling_price = "0"
                                productItem.purchase_price = product.purchase_price
                                productItem.discount_type = product.discount_type
                                productItem.remaining_quantity = product.remaining_quantity
                                val soldProduct = SoldProductInfo(productItem, false)
                                val um = product.um
                                soldProduct.product.um = um
                                soldProduct.quantity = data.quantity!!.toDouble().roundToInt()
                                soldProduct.orderedQuantity = data.quantity!!.toDouble().roundToInt()
                                soldProduct.product.product_id = data.stock_id!!
                                soldProduct.isFocIsChecked = true
                                innerSoldProductList.add(soldProduct)
                            }
                        }
                    }
                    deliveryProductDataList = innerSoldProductList

                    return@flatMap deliveryRepo.deliveryCustomerList(customerId)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryCustomerDataList = it
                    deliveryAllItemDataList.postValue(Pair(deliveryProductDataList, deliveryCustomerDataList))
                }
        }
    }

    val routeDataList = MutableLiveData<RouteScheduleV2>()
    fun loadRouteId(saleManId: String) {
        launch {
            deliveryRepo.getRouteId(saleManId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    routeDataList.postValue(it)
                }
        }
    }

    fun insertDeliveryDataItemToDatabase(soldProductList: ArrayList<SoldProductInfo>, invoiceId: String): Int {
        var totalQtyForInvoice = 0
        val cvInvoiceProduct = InvoiceProduct()
        for (soldProduct in soldProductList) {
            cvInvoiceProduct.invoice_product_id = invoiceId
            cvInvoiceProduct.product_id = soldProduct.product.product_id
            cvInvoiceProduct.sale_quantity = soldProduct.quantity.toString()
            val discount = soldProduct.product.selling_price!!.toDouble() * soldProduct.discount / 100
            cvInvoiceProduct.discount_amount = discount.toString()
            cvInvoiceProduct.total_amount = soldProduct.totalAmount
            cvInvoiceProduct.discount_percent = soldProduct.discount
            cvInvoiceProduct.extra_discount = soldProduct.extraDiscount
            cvInvoiceProduct.s_price = soldProduct.product.selling_price!!.toDouble()
            cvInvoiceProduct.p_price = soldProduct.product.purchase_price!!.toDouble()

            var promoPrice = soldProduct.promotionPrice
            if (promoPrice == 0.0) {
                promoPrice = soldProduct.product.selling_price!!.toDouble()
            }
            //updated by Thu Thu ----- Calculation by focPercent -----
            if (soldProduct.focPercent.toInt() != 0 || soldProduct.focAmount != 0.0 || soldProduct.priceByClassDiscount.toDouble() != 0.0)
                promoPrice = soldProduct.promoPriceByDiscount

            cvInvoiceProduct.promotion_price = promoPrice
            totalQtyForInvoice += soldProduct.quantity

            val deliveryItemApi = DeliveryItemApi()
            deliveryItemApi.deliveryId = invoiceId
            deliveryItemApi.stockId = soldProduct.product.product_id!!.toInt()
            deliveryItemApi.deliveryQty = soldProduct.quantity
            deliveryItemApi.foc = (java.lang.Short.parseShort(if (soldProduct.isFocStatus) "1" else "0"))
            insertDeliveryItemUpload(deliveryItemApi)

            deliveryRepo.updateRelatedProduct(soldProduct.quantity, soldProduct.product.id)
            deliveryRepo.updateRelatedDeliveryItem(soldProduct.quantity.toDouble(),soldProduct.product.product_id)
            // if (soldProduct.product.selling_price!!.toDouble() == 0.0) {
                deliveryRepo.updateRelatedDeliveryPresent(soldProduct.product.product_id)
           // }
            deliveryRepo.updateCheckDeliveryItem(soldProduct.product.product_id)

        }
        deliveryRepo.saveDeliveryDataItem(cvInvoiceProduct)
        return totalQtyForInvoice
    }

    private fun insertDeliveryItemUpload(deliveryItemApi: DeliveryItemApi) {
        val cvDeliveryUploadItem = DeliveryItemUpload()
        if (deliveryItemApi.deliveryId == "") {
            cvDeliveryUploadItem.delivery_id = "0"
        } else {
            cvDeliveryUploadItem.delivery_id = deliveryItemApi.deliveryId
        }
        cvDeliveryUploadItem.stock_id = deliveryItemApi.stockId
        cvDeliveryUploadItem.quantity = deliveryItemApi.deliveryQty.toString()
        cvDeliveryUploadItem.foc = deliveryItemApi.foc.toString()
        deliveryRepo.saveDeliveryItemUpload(cvDeliveryUploadItem)
    }

    val userNameDataList = MutableLiveData<List<SaleMan>>()
     fun loadOrderPerson(saleManId: Int){
         launch {
             deliveryRepo.getSaleManName(saleManId)
                 .subscribeOn(schedulerProvider.io())
                 .observeOn(schedulerProvider.mainThread())
                 .subscribe {
                     userNameDataList.postValue(it)
                 }
         }
     }

    fun saveDeliveryUpload(cvDeliveryUpload: DeliveryUpload){
        deliveryRepo.saveDeliveryUpload(cvDeliveryUpload)
    }
    fun saveInvoiceData(invoice1: Invoice){
        deliveryRepo.saveInvoiceData(invoice1)
    }

}