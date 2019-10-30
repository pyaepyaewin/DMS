package com.aceplus.data.repoimpl.prospectcustomet

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.ShopType
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.predefine.District
import com.aceplus.domain.entity.predefine.StateDivision
import com.aceplus.domain.entity.predefine.Street
import com.aceplus.domain.entity.predefine.Township
import com.aceplus.domain.repo.prospectcustomer.ProspectCustomerRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ProspectCustomerRepoImpl(private val db: MyDatabase) : ProspectCustomerRepo {

    //add new customer for prospect customer
    override fun saveCustomer(customer: Customer) {
        Observable.fromCallable { db.customerDao().insertCustomerData(customer) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }


    override fun streetData(): Observable<List<Street>> {
        return Observable.just(db.streetDao().allData)
    }

    override fun townshipData(): Observable<List<Township>> {
        return Observable.just(db.townshipDao().allData)
    }

    override fun shopData(): Observable<List<ShopType>> {
        return Observable.just(db.shopTypeDao().allData)
    }

    override fun distinctData(): Observable<List<District>> {
        return Observable.just(db.districtDao().allData)
    }

    override fun stateDivisionData(): Observable<List<StateDivision>> {
        return Observable.just(db.stateDivisionDao().allData)
    }
}