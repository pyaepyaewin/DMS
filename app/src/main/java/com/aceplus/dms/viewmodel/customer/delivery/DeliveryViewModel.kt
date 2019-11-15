package com.aceplus.dms.viewmodel.customer.delivery

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.entity.delivery.DeliveryItem
import com.aceplus.domain.entity.delivery.DeliveryPresent
import com.aceplus.domain.repo.deliveryrepo.DeliveryRepo
import com.aceplus.domain.vo.customer.DeliveryVO
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class DeliveryViewModel(
    private val deliveryRepo: DeliveryRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var deliveryDataList = MutableLiveData<List<DeliveryVO>>()
    var deliveryAllDataList = MutableLiveData<List<Delivery>>()
    var deliveryAllItemDataList =  MutableLiveData<Triple<List<DeliveryItem>, List<DeliveryPresent>,Customer>>()
    var deliveryProductDataList = MutableLiveData<List<com.aceplus.domain.entity.product.Product>>()

    //Testing
    fun loadAllDeliveryList() {
        launch {
            deliveryRepo.allData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryAllDataList.postValue(it)
                }
        }
    }
    fun loadDeliveryList() {
        launch {
            deliveryRepo.deliveryDataList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryDataList.postValue(it)
                    //Log.d("Delivery View Model","${it.size}")
                }
        }
    }
    fun loadAllDeliveryItemList(deliveryId:Int,customerId:Int) {
        var deliveryItemDataList =  listOf<DeliveryItem>()
        var deliveryPresentDataList = listOf<DeliveryPresent>()
        var deliveryCustomerDataList :Customer

        launch {
            deliveryRepo.deliveryItemDataList(deliveryId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryItemDataList = it
                }
            deliveryRepo.deliveryPresentDataList(deliveryId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryPresentDataList = it
                }
            deliveryRepo.deliveryCustomerList(customerId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    deliveryCustomerDataList = it
                    Log.d("Delivery Customer Id","${it.id}")
                    Log.d("Delivery Customer Name","${it.customer_name}")

                    deliveryAllItemDataList.postValue(
                        Triple(
                        deliveryItemDataList,deliveryPresentDataList,deliveryCustomerDataList
                    )
                    )
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
}