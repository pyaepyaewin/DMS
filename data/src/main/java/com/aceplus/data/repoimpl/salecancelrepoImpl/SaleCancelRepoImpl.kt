package com.aceplus.data.repoimpl.salecancelrepoImpl

import android.content.SharedPreferences
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.utils.Constant
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.model.sale.salecancel.SaleCancelDetailItem
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import com.aceplus.domain.repo.salecancelrepo.SaleCancelRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.constants.AppUtils
import io.reactivex.Observable

class SaleCancelRepoImpl(val database: MyDatabase,val shf:SharedPreferences) : SaleCancelRepo {
    override fun deleteInvoiceProductForLongClick(invoiceId: String, productIdList: List<Int>) {
        return database.invoiceProductDao().deleteInvoiceProductForLongClick(invoiceId,productIdList)

    }

    override fun getSoldInvoice(invoiceId: String): Observable<List<Invoice>> {
        return Observable.just(database.invoiceDao().getSoldInvoice(invoiceId))

    }


    override fun insertInvoice(invoice: Invoice) {
        database.invoiceDao().insert(invoice)
    }

    override fun updateInvoice(invoice: Invoice) {
        database.invoiceDao().update(invoice)
    }


    override fun updateTotalQtyForInvoice(invoiceId: String, totalQty: Int) {
        database.invoiceDao()
            .updateTotalQtyForInvoice(invoiceId,totalQty)
    }

    override fun insertInvoiceProduct(invoiceProductList: List<InvoiceProduct>) {
        return database.invoiceProductDao().insertAll(invoiceProductList)
    }

        override fun getSaleManData(): SaleMan {
        val saleMan = SaleMan()
        saleMan.id = AppUtils.getStringFromShp(Constant.SALEMAN_ID, shf).toString()
        saleMan.user_id = AppUtils.getStringFromShp(Constant.SALEMAN_NO, shf).toString()
        saleMan.password = AppUtils.getStringFromShp(Constant.SALEMAN_PWD, shf)
        return saleMan
    }

    override fun updateProductRemainingQty(soldProductInfo: SoldProductInfo) {
        database.productDao()
            .updateProductRemainingQty(soldProductInfo.quantity, soldProductInfo.product.id)
    }
    override fun getAllInvoiceProduct(): Observable<List<InvoiceProduct>> {
        return Observable.just(database.invoiceProductDao().allData)

    }

    override fun getAllInvoice(): Observable<List<Invoice>> {
        return Observable.just(database.invoiceDao().allData)

    }

    override fun getInvoiceCountByID(invoiceId: String): Observable<Int> {
        return Observable.just(database.invoiceDao().getInvoiceCountByID(invoiceId))

    }

    override fun getTaxPercent(): Observable<List<CompanyInformation>> {
        return Observable.just(database.companyInformationDao().getTaxPercent())

    }


    override fun updateQuantity(invoiceId: String, productId: String, qty: Int) {
        return database.invoiceProductDao().updateQtyForInvoiceProduct(invoiceId,productId,qty)

    }





    override fun deleteInvoiceData(invoiceId: String) {
        return database.invoiceDao().deleteAll(invoiceId)

    }

    override fun deleteInvoiceProduct(invoiceId: String) {
        return database.invoiceProductDao().deleteAll(invoiceId)

    }

    override fun getSoldProductList(productIdList: List<String>): Observable<List<SaleCancelDetailItem>> {
        return Observable.just(database.productDao().allProductDataList(productIdList))

    }

    override fun getProductIdList(invoiceID: String): Observable<List<String>> {
        return Observable.just(database.invoiceProductDao().getProductIdList(invoiceID))

    }
    override fun getSaleCancelList(): Observable<List<SaleCancelItem>> {
        return Observable.just(database.invoiceDao().getSaleCancelList())
    }


}