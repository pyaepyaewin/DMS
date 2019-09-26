package com.example.dms.data.repositories

import android.content.Context
import com.example.dms.data.database.MyDatabase
import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.Date
import com.example.dms.network.ApiService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CheckOutRepositoryImpl (private val context: Context,private val database: MyDatabase
):CheckOutRepository {
    override fun saveCheckOutIntoDatabase(checkOutList: List<CheckOut>) {
        Observable.fromCallable{database.checkOutDao().insertAll(checkOutList)}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun saveDataIntoDatabase(dateList: List<Date>) {
        Observable.fromCallable{database.dateDao().insertAll(dateList)}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }


}