package com.aceplus.domain.repo

import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.domain.entity.customer.DidCustomerFeedback
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.model.roomdb.StringObject
import com.aceplus.shared.utils.GPSTracker
import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableLift
import java.util.*

interface CustomerVisitRepo {

    fun getLocationCode(): Int
    fun getSaleManData(): SaleMan
    fun getRouteScheduleIDV2(): Int
    fun getLastCountForInvoiceNumber(mode: String): Int

    fun getAllCustomerData(): Observable<List<Customer>>
    fun getAllDidFeedback(): Observable<List<String>>
    fun getAllDefaultFeedback(): Observable<List<CustomerFeedback>>

    fun getAllProductData(): Observable<List<Product>>

    fun saveDataForTempSaleManRoute(selectedCustomer: Customer, currentDate: String)
    fun saveCustomerFeedback(didCustomerFeedbackEntity: DidCustomerFeedback)
    fun saveSaleVisitRecord(selectedCustomer: Customer, gpsTracker: GPSTracker)

    fun updateDepartureTimeForSaleManRoute(saleManId: String, customerId: Int, currentDate: String)
}