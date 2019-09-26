package com.example.dms.data.repositories

class ApiRepositoryImpl(
) {}

//    override var customerData: MutableLiveData<Observable<CustomerListResponse>> = MutableLiveData()
//
//    override fun loadCustomerList(param: String) {
//        if (Utils.isOnline(context)) {
//            customerData.postValue(mApiService.loadCustomerData(param))
//      }
//        else {
//            val localCustomerDataList = database.customerDao().allCustomerData
//            val disposable = CompositeDisposable()
//            disposable.add(
//                localCustomerDataList
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(Schedulers.io())
//                    .subscribe {
//                        disposable.clear()
//                        val responseData=CustomerListResponse()
//
//
//                    }
//            )
//        }

    //}


//    override var saleData: MutableLiveData<Observable<SaleListResponse>> = MutableLiveData()
//
//
//
//    override fun loadSaleList(param: String) {
//        if (Utils.isOnline(context)) {
//         saleData.postValue(mApiService.loadSaleData(param))
//                  }
//    }
//    override fun saveCustomerDataIntoDatabase(customerList: List<Customer>) {
//        Observable.fromCallable{database.customerDao().insertAll(customerList)}
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe()
//    }
//
//
//
//    override fun saveSaleDataIntoDatabase(saleList: List<Product>) {
//        Observable.fromCallable{database.productDao().insertAll(saleList)}
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe()
//    }
//
//}