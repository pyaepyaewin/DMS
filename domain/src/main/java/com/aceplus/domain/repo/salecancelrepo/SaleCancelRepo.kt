package com.aceplus.domain.repo.salecancelrepo

import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import io.reactivex.Observable

interface SaleCancelRepo {
    fun getSaleCancelList(): Observable<List<SaleCancelItem>>
    fun getProductIdList(invoiceID:String):Observable<List<String>>
    fun getSoldProductList(productIdList:List<String>):Observable<List<Product>>

}