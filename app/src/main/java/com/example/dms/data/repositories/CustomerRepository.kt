package com.example.dms.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.dms.network.response.Customer
import com.example.dms.network.response.CustomerListResponse
import io.reactivex.Observable

interface CustomerRepository {
 var customerData: MutableLiveData<Observable<CustomerListResponse>>
  fun loadCustomerList(param : String)
 fun saveDataIntoDatabase(movieList:List<Customer>)
}