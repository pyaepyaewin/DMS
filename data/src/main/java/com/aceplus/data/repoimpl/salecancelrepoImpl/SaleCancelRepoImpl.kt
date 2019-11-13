package com.aceplus.data.repoimpl.salecancelrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.sale.salecancel.SaleCancelDetailItem
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import com.aceplus.domain.repo.salecancelrepo.SaleCancelRepo
import com.aceplus.domain.vo.SoldProductInfo
import io.reactivex.Observable

class SaleCancelRepoImpl(val database: MyDatabase) : SaleCancelRepo {
    override fun updateQuantity(qty: Int, productName: String) {
        return database.invoiceProductDao().update(qty,productName)

    }

    override fun deleteInvoiceData(invoiceId: String) {
        return database.invoiceDao().deleteAll(invoiceId)

    }

    override fun deleteInvoiceProduct(invoiceId: String) {
        return database.invoiceProductDao().deleteAll(invoiceId)

    }

    override fun getSoldProductList(productIdList: List<String>): Observable<List<SaleCancelDetailItem>> {
        return Observable.just(database.productDao().allProductData(productIdList))

    }

    override fun getProductIdList(invoiceID: String): Observable<List<String>> {
        return Observable.just(database.invoiceProductDao().getProductIdList(invoiceID))

    }
    override fun getSaleCancelList(): Observable<List<SaleCancelItem>> {
        return Observable.just(database.invoiceDao().getSaleCancelList())
    }


}