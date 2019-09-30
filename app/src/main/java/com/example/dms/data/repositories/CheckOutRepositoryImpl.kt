package com.example.dms.data.repositories

import android.content.Context
import com.example.dms.data.database.MyDatabase
import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.Invoice
import com.example.dms.data.database.table.InvoiceItem
import com.example.dms.data.database.table.InvoiceReport
import com.example.dms.network.ApiService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CheckOutRepositoryImpl(
    private val context: Context,
    private val apiService : ApiService,
    private val database: MyDatabase
): CheckOutRepository {
    override fun saveDataIntoDatabase(invoice: Invoice, invoiceItems: MutableList<InvoiceItem>) {

        Observable.fromCallable { database.invoiceDao().insert(invoice) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()

        Observable.fromCallable { database.invoiceItemDao().insertAll(invoiceItems) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()

    }

}

