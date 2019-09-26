package com.example.dms.viewmodels.Factory.api

import com.example.dms.data.repositories.ApiRepository

class ApiMainViewModel (
    private val repo: ApiRepository
) : ApiBaseViewModel()
{
//    var errorState = MutableLiveData<String>()
//    var successCustomerState = MutableLiveData<List<Customer>>()
//    var successSaleState = MutableLiveData<List<Product>>()
//
//    fun loadCustomerList(customerReqParam : String) {
//        repo.customerData
//            .observeForever {
//                launch {
//                    it
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({ response ->
//                            repo.customerData = MutableLiveData()
//                            successCustomerState.postValue(response.data[0].Customer)
//                        }, { error ->
//                            errorState.postValue(error.localizedMessage)
//                        })
//                }
//            }
//
//
//        repo.loadCustomerList(customerReqParam)
//    }
//    fun loadSaleList(saleRequestParm: String) {
//        repo.saleData
//            .observeForever {
//                launch {
//                    it
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({ response ->
//                            repo.saleData = MutableLiveData()
//                            successSaleState.postValue(response.data[0].Product)
//                        }, { error ->
//                            errorState.postValue(error.localizedMessage)
//                        })
//                }
//            }
//       repo.loadSaleList(saleRequestParm)
//
//    }
}