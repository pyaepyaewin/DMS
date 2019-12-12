package com.aceplus.data.repoimpl.creditcollectionrepoimpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.Location
import com.aceplus.domain.entity.cash.CashReceive
import com.aceplus.domain.entity.cash.CashReceiveItem
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionDataClass
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CreditCollectionRepoImpl(val database: MyDatabase):CreditCollectionRepo {
    override fun getCashReceiveCount(): Observable<Int> {
        return Observable.just(database.cashReceiveDao().getCashReceiveCount())

    }

    override fun getCreditCollectionList(): Observable<List<CreditCollectionDataClass>> {
        return Observable.just(database.creditDao().getCreditCollection())

    }
    override fun getTownShipName(customerID: Int): String {
        val township = database.townshipDao().townshipNameByID(customerID)
        return township ?: ""
    }


    override fun updatePayAmount(payAmt: Double, invoiceNo: String){
        return database.creditDao().update(payAmt,invoiceNo)

    }

    override fun saveCashReceiveDataIntoDatabase(
        creditData: MutableList<CashReceive>,
        creditItem: MutableList<CashReceiveItem>
    ) {
        Observable.fromCallable { database.cashReceiveDao().insertAll(creditData) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
        Observable.fromCallable { database.cashReceiveItemDao().insertAll(creditItem) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }




    override fun getLocation():String {
        return database.locationDao().getLocation()

    }

    override fun getCreditCollectionCheckOutList(customerId:String): Observable<List<Credit>> {
        return Observable.just(database.creditDao().getCreditCheckout(customerId))

    }
}

