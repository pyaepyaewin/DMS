package com.aceplus.data.repoimpl

import android.content.ContentValues
import android.content.SharedPreferences
import android.util.Log
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.utils.Constant
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.domain.entity.customer.DidCustomerFeedback
import com.aceplus.domain.entity.predefine.Township
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.route.TempForSaleManRoute
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.model.roomdb.StringObject
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.shared.utils.GPSTracker
import com.aceplussolutions.rms.constants.AppUtils
import io.reactivex.Observable

class CustomerVisitRepoImpl(
    private val db: MyDatabase, private val shf: SharedPreferences
) : CustomerVisitRepo {

    override fun getLocationCode(): Int {
        val locationDataList = db.locationDao().allData
        if (locationDataList.isNotEmpty()) {
            return locationDataList.first().location_id.toInt()
        }
        return 0
    }

    override fun getSaleManData(): SaleMan {
        val saleMan = SaleMan()
        saleMan.id = AppUtils.getStringFromShp(Constant.SALEMAN_ID, shf).toString()
        saleMan.user_id = AppUtils.getStringFromShp(Constant.SALEMAN_NO, shf).toString()
        saleMan.password = AppUtils.getStringFromShp(Constant.SALEMAN_PWD, shf)
        return saleMan
    }

    override fun getRouteScheduleIDV2(): Int {
        val saleManId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, shf)
        val routeSchedule = db.routeScheduleV2Dao().dataBySaleManId(saleManId!!)
        val routeScheduleItems = db.routeScheduleItemV2Dao().allDataByRouteScheduleId(routeSchedule.id.toString())
        return if (routeScheduleItems.count() > 0) routeScheduleItems[0].route_schedule_id
        else 0
    }

    override fun getLastCountForInvoiceNumber(mode: String): Int {

        var next = 0
        if (mode == Constant.FOR_SALE) {
//            next += context.getSharedPreferences("PREFERENCE",
//                Activity.MODE_PRIVATE).getInt("INVOICE_COUNT", -1)
            next += AppUtils.getIntFromShp("INVOICE_COUNT", shf)//todo check here to sure
        } else if (mode == Constant.FOR_OTHERS || mode == Constant.FOR_PACKAGE_SALE) {
            next += db.didCustomerFeedbackDao().dataCount//todo check here to sure
        } else if (mode == Constant.FOR_PRE_ORDER_SALE) {
            next += db.preOrderDao().dataCount + 1
        } else if (mode == Constant.FOR_DELIVERY) {
            next += db.deliveryUploadDao().dataCount + 1
        } else if (mode == Constant.FOR_SALE_RETURN) {
            next += db.saleReturnDao().dataCount + 1
        } else if (mode == Constant.FOR_SALE_RETURN_EXCHANGE) {
            next += db.saleReturnDao().dataCountForSaleReturnExchange + 1
        } else if (mode == Constant.FOR_SALE_EXCHANGE) {
            next += db.invoiceDao().dataCountForSaleExchange + 1
        } else if (mode == Constant.FOR_DISPLAY_ASSESSMENT) {
            next += db.outletVisibilityDao().dataCount + 1
        } else if (mode == Constant.FOR_OUTLET_STOCK_AVAILABILITY) {
            next += db.outletStockAvailabilityDao().dataCount + 1
        } else if (mode == Constant.FOR_SIZE_IN_STORE_SHARE) {
            next += db.sizeInStoreShareDao().dataCount + 1
        } else if (mode == Constant.FOR_COMPETITORACTIVITY) {
            next += db.competitorActivityDao().dataCount + 1
        } else if (mode == Constant.FOR_VAN_ISSUE) {
            next += db.deviceIssueRequestDao().dataCount + 1
        }
        return next
    }

    override fun getAllCustomerData(): Observable<List<Customer>> {
        return Observable.just(db.customerDao().allCustomerData())
    }

    override fun getAllDidFeedback(): Observable<List<String>> {
        val idList = db.didCustomerFeedbackDao().getAllCustomerIdList().map { it.data }
        return Observable.just(idList)
    }

    override fun getAllDefaultFeedback(): Observable<List<CustomerFeedback>> {
        return Observable.just(db.customerFeedbackDao().allObservableData)
    }

    override fun getAllProductData(): Observable<List<Product>> {
        return Observable.just(db.productDao().allProductData)
    }

    override fun saveDataForTempSaleManRoute(selectedCustomer: Customer, currentDate: String) {
        val saleManId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, shf)
        if (db.tempForSaleManRouteDao().dataById(saleManId ?: "0", selectedCustomer.customer_id!!).isEmpty()) {
            val tempForSaleManRoute = TempForSaleManRoute()
            tempForSaleManRoute.sale_man_id = saleManId?.toInt() ?: 0
            tempForSaleManRoute.customer_id = selectedCustomer.id
            tempForSaleManRoute.latitude = selectedCustomer.latitude
            tempForSaleManRoute.longitude = selectedCustomer.longitude
            tempForSaleManRoute.arrival_time = currentDate
            tempForSaleManRoute.departure_time = currentDate
            tempForSaleManRoute.route_id = getRouteScheduleIDV2()
            db.tempForSaleManRouteDao().insertData(tempForSaleManRoute)
        } else {
            db.tempForSaleManRouteDao().updateArrivalAndDepartureTime(
                saleManId ?: "0",
                selectedCustomer.id,
                currentDate
            )
        }
    }

    override fun saveCustomerFeedback(didCustomerFeedbackEntity: DidCustomerFeedback) {
        db.didCustomerFeedbackDao().insertData(didCustomerFeedbackEntity)
    }

    override fun saveSaleVisitRecord(selectedCustomer: Customer, gpsTracker: GPSTracker) {
        if (isSameCustomer(selectedCustomer.id, gpsTracker)) {
            db.saleVisitRecordUploadDao().deleteData(selectedCustomer)
            db.saleVisitRecordUploadDao().saveData(selectedCustomer)
        }
    }

    override fun updateDepartureTimeForSaleManRoute(saleManId: String, customerId: Int, currentDate: String) {
        db.tempForSaleManRouteDao().updateDepartureTime(saleManId, customerId, currentDate)
    }

    private fun isSameCustomer(customerId: Int, gpsTracker: GPSTracker): Boolean {
        val customer = db.customerDao().dataById(customerId = customerId)
        val latiString: String? = customer.latitude ?: ""
        val longiString: String? = customer.longitude ?: ""
        var latitude: Double? = 0.0
        var longitude: Double? = 0.0
        var latiDouble: Double? = 0.0
        var longDouble: Double? = 0.0

        if (latiString != null && longiString != null && latiString != "" && longiString != "" && latiString != "0" && longiString != "0" && latiString.length > 6 && longiString.length > 6) {
            latiDouble = java.lang.Double.parseDouble(latiString.substring(0, 7))
            longDouble = java.lang.Double.parseDouble(longiString.substring(0, 7))
        }

        if (gpsTracker.canGetLocation()) {
            val lat = gpsTracker.getLatitude().toString()
            val lon = gpsTracker.getLongitude().toString()

            if (lat.length > 6 && lon.length > 6) {
                latitude = java.lang.Double.parseDouble(lat.substring(0, 7))
                longitude = java.lang.Double.parseDouble(lon.substring(0, 7))
            }
        } else {
            gpsTracker.showSettingsAlert()
        }

        var flag1 = false
        var flag2 = false
        if (latiDouble != null && longDouble != null && latitude != null && longitude != null) {
            val lati1 = latiDouble - 0.002
            val lati2 = latiDouble + 0.002

            if (latitude in lati1..lati2) {
                flag1 = true
            } else if (latitude == latiDouble) {
                flag1 = true
            }

            val longi1 = longDouble - 0.002
            val longi2 = longDouble + 0.002

            if (longitude in longi1..longi2) {
                flag2 = true
            } else if (longitude == longDouble) {
                flag2 = true
            }

            if (flag1 || flag2) {
                return true
            }
        }

        return false
    }

}