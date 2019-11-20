package com.aceplus.domain.repo.salecancelrepo

import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.model.sale.salecancel.SaleCancelDetailItem
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import com.aceplus.domain.vo.SoldProductInfo
import io.reactivex.Observable

interface SaleCancelRepo {
    fun getSaleCancelList(): Observable<List<SaleCancelItem>>
    fun getProductIdList(invoiceID:String):Observable<List<String>>
    fun getSoldProductList(productIdList:List<String>):Observable<List<SaleCancelDetailItem>>
    fun deleteInvoiceData(invoiceId:String)
    fun deleteInvoiceProduct(invoiceId: String)
    fun updateQuantity(invoiceId: String,productId: String,qty:Int)
    fun deleteInvoiceProductForLongClick(invoiceId: String,productIdList:List<Int>)
    fun getTaxPercent():Observable<List<CompanyInformation>>
    fun getInvoiceCountByID(invoiceId: String): Observable<Int>
    fun getAllInvoice(): Observable<List<Invoice>>
    fun getAllInvoiceProduct(): Observable<List<InvoiceProduct>>
    fun updateProductRemainingQty(soldProductInfo: SoldProductInfo)
    fun getSaleManData(): SaleMan
    fun insertInvoiceProduct(invoiceProductList:List<InvoiceProduct>)
    fun updateTotalQtyForInvoice(invoiceId: String,totalQty:Int)
    fun updateInvoice(invoice: Invoice)
    fun insertInvoice(invoice:Invoice)
    fun getSoldInvoice(invoiceId: String):Observable<List<Invoice>>




}