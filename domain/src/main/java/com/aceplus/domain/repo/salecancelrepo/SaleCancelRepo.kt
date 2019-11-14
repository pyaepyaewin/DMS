package com.aceplus.domain.repo.salecancelrepo

import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.sale.salecancel.SaleCancelDetailItem
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import io.reactivex.Observable

interface SaleCancelRepo {
    fun getSaleCancelList(): Observable<List<SaleCancelItem>>
    fun getProductIdList(invoiceID:String):Observable<List<String>>
    fun getSoldProductList(productIdList:List<String>):Observable<List<SaleCancelDetailItem>>
    fun deleteInvoiceData(invoiceId:String)
    fun deleteInvoiceProduct(invoiceId: String)
    fun updateQuantity(invoiceId: String,productId: String,qty:Int)
    fun deleteInvoiceProductForLongClick(productId:String)


}