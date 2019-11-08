package com.aceplus.dms.viewmodel.customer.delivery

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.entity.delivery.DeliveryItem
import com.aceplus.domain.entity.delivery.DeliveryPresent
import com.aceplus.domain.model.Product
import com.aceplus.domain.model.customer.Customer
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.model.delivery.DeliverItem
import com.aceplus.domain.repo.deliveryrepo.DeliveryRepo
import com.aceplus.domain.vo.customer.DeliveryVO
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class DeliveryViewModel(
    private val deliveryRepo: DeliveryRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var deliveryDataList = MutableLiveData<List<DeliveryVO>>()
    var deliveryItemDataList = MutableLiveData<List<DeliveryItem>>()
    var deliveryPresentDataList = MutableLiveData<List<DeliveryPresent>>()
    var deliveryProductDataList = MutableLiveData<List<com.aceplus.domain.entity.product.Product>>()
    var deliveryCustomerDataList = MutableLiveData<com.aceplus.domain.entity.customer.Customer>()

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
    fun loadDeliveryItemList(deliveryId:Int) {
        launch {
            deliveryRepo.deliveryItemDataList(deliveryId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryItemDataList.postValue(it)
                }
        }
    }
    fun loadDeliveryPresentList(deliveryId:Int) {
        launch {
            deliveryRepo.deliveryPresentDataList(deliveryId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryPresentDataList.postValue(it)
                }
        }
    }
    fun loadProductDeliveryList(stockId:String) {
        launch {
            deliveryRepo.deliveryProductList(stockId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryProductDataList.postValue(it)
                }
        }
    }
    fun loadCustomerDeliveryList(customerId:Int) {
        launch {
            deliveryRepo.deliveryCustomerList(customerId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryCustomerDataList.postValue(it)
                }
        }
    }
}