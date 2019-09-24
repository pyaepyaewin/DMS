package com.example.dms.data.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.dms.network.ApiService
import com.example.dms.network.response.CustomerListResponse
import com.example.dms.util.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CustomerRepositoryImpl(private val context: Context,
                             private val mApiService: ApiService
):CustomerRepository {
    override fun loadCustomerList(param : String) {
        if (Utils.isOnline(context)) {
            customerData.postValue(mApiService.loadCustomerData(param))
        }

    }


    override var customerData: MutableLiveData<Observable<CustomerListResponse>> = MutableLiveData()


    }

