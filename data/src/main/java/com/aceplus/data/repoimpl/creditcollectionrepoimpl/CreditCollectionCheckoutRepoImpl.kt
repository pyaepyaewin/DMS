package com.aceplus.data.repoimpl.creditcollectionrepoimpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.Location
import com.aceplus.domain.entity.cash.CashReceive
import com.aceplus.domain.entity.cash.CashReceiveItem
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.model.forApi.other.Township
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionCheckOutRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CreditCollectionCheckoutRepoImpl(val database:MyDatabase):CreditCollectionCheckOutRepo {
    override fun getTownShipName(customerID: Int): String {
        val township = database.townshipDao().townshipNameByID(customerID)
        return township?.data ?: ""
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




    override fun getLocation(): Observable<List<Location>> {
        return Observable.just(database.locationDao().getLocation())

    }

    override fun getCreditCollectionCheckOutList(customerId:String): Observable<List<Credit>> {
        return Observable.just(database.creditDao().getCreditCheckout(customerId))

    }
}