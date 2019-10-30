package com.aceplus.dms.viewmodel.customer.prospectcustomer

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.entity.ShopType
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.predefine.District
import com.aceplus.domain.entity.predefine.StateDivision
import com.aceplus.domain.entity.predefine.Street
import com.aceplus.domain.entity.predefine.Township
import com.aceplus.domain.model.customer.prospectcustomer.NewCustomer
import com.aceplus.domain.repo.prospectcustomer.ProspectCustomerRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class ProspectCustomerViewModel(
    private val prospectCustomerRepo: ProspectCustomerRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    //add new customer for prospect customer
    fun loadAddNewCustomerList(newCustomer:NewCustomer) {
        val customer = Customer()
        customer.customer_name = newCustomer.customerName
        customer.contact_person = newCustomer.contact_person
        customer.phone = newCustomer.phone
        customer.address = newCustomer.address
        customer.latitude = newCustomer.latitude.toString()
        customer.longitude = newCustomer.longitude.toString()
        customer.township_number = newCustomer.township_number.toString()
        customer.district_id = newCustomer.district_id
        customer.state_division_id = newCustomer.state_division_id
        customer.shop_type_id = newCustomer.shopTypeId
        prospectCustomerRepo.saveCustomer(customer)

    }
    //spinner data
    var streetDataList = MutableLiveData<List<Street>>()
    var townshipDataList = MutableLiveData<List<Township>>()
    var shopTypeDataList = MutableLiveData<List<ShopType>>()
    var distinctDataList = MutableLiveData<List<District>>()
    var stateDivisionDataList = MutableLiveData<List<StateDivision>>()

    fun loadStreetList() {
        launch {
            prospectCustomerRepo.streetData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    streetDataList.postValue(it)
                }
        }
    }
    fun loadTownshipList() {
        launch {
            prospectCustomerRepo.townshipData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    townshipDataList.postValue(it)
                }
        }
    }
    fun loadShopTypeList() {
        launch {
            prospectCustomerRepo.shopData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    shopTypeDataList.postValue(it)
                }
        }
    }
    fun loadDistinctList() {
        launch {
            prospectCustomerRepo.distinctData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    distinctDataList.postValue(it)
                }
        }
    }
    fun loadStateDivisionList() {
        launch {
            prospectCustomerRepo.stateDivisionData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    stateDivisionDataList.postValue(it)
                }
        }
    }
}