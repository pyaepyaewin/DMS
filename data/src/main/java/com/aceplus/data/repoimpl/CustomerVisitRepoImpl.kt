package com.aceplus.data.repoimpl

import android.content.SharedPreferences
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.remote.UploadApiService
import com.aceplus.data.utils.Constant
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.Location
import com.aceplus.domain.entity.SMSRecord
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.domain.entity.classdiscount.ClassDiscountByPrice
import com.aceplus.domain.entity.classdiscount.ClassDiscountByPriceItem
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.domain.entity.customer.DidCustomerFeedback
import com.aceplus.domain.entity.deviceissue.DeviceIssueRequest
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.preorder.PreOrder
import com.aceplus.domain.entity.preorder.PreOrderPresent
import com.aceplus.domain.entity.preorder.PreOrderProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.PromotionDate
import com.aceplus.domain.entity.promotion.PromotionGift
import com.aceplus.domain.entity.promotion.PromotionPrice
import com.aceplus.domain.entity.route.RouteScheduleV2
import com.aceplus.domain.entity.route.TempForSaleManRoute
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.entity.sale.salereturn.SaleReturn
import com.aceplus.domain.entity.sale.salereturn.SaleReturnDetail
import com.aceplus.domain.entity.sale.salevisit.SaleVisitRecordUpload
import com.aceplus.domain.entity.volumediscount.VolumeDiscount
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilter
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilterItem
import com.aceplus.domain.model.forApi.invoice.InvoiceResponse
import com.aceplus.domain.model.forApi.preorder.PreOrderPresentApi
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.SaleExchangeProductInfo
import com.aceplus.shared.utils.GPSTracker
import com.aceplussolutions.rms.constants.AppUtils
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomerVisitRepoImpl(
    private val db: MyDatabase, private val shf: SharedPreferences, private val upLoadApi: UploadApiService
): CustomerVisitRepo {

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

    override fun getSaleManName(saleManId: String?): Observable<String?> {
        return Observable.just(db.saleManDao().getSaleManNameByID(saleManId))
    }

    override fun getRouteID(saleManId: String): Observable<List<String>> {
        return Observable.just(db.routeScheduleV2Dao().getRouteId(saleManId))
    }

    override fun getRouteScheduleByID(saleManId: String): Observable<RouteScheduleV2> {
        return Observable.just(db.routeScheduleV2Dao().dataBySaleManId(saleManId))
    }

    override fun getRouteNameByID(routeID: Int): Observable<String?> {
        return Observable.just(db.routeDao().getRouteNameByID(routeID))
    }

    override fun getRouteScheduleIDV2(): Int {
        val saleManId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, shf)
        val routeSchedule = db.routeScheduleV2Dao().dataBySaleManId(saleManId ?: "")
        val routeScheduleItems = db.routeScheduleItemV2Dao().allDataByRouteScheduleId(routeSchedule.id.toString())
        return if (routeScheduleItems.count() > 0) routeScheduleItems[0].route_schedule_id else 0
        // Route Schedule Item id was updated for auto generation - by YLA
    }

    override fun getLastCountForInvoiceNumber(mode: String): Int {

        var next = 0
        if (mode == Constant.FOR_SALE) {
//            next += context.getSharedPreferences("PREFERENCE",
//                Activity.MODE_PRIVATE).getInt("INVOICE_COUNT", -1)
            next += AppUtils.getIntFromShp(Constant.INVOICE_COUNT, shf)//todo check here to sure
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

    override fun getCustomerByID(customerID: Int): Observable<Customer> {
        return Observable.just(db.customerDao().dataById(customerID))
    }

    override fun getCustomerTownshipName(customerID: Int): Observable<String> {
        return Observable.just(db.townshipDao().townshipNameByID(customerID))
    }

    override fun updateCustomerData(customer: Customer) {
        db.customerDao().updateCustomerLocation(customer.id, customer.latitude, customer.longitude)
    }

    override fun getAllDidFeedback(): Observable<List<String>> {
        val idList = db.didCustomerFeedbackDao().getAllCustomerIdList().map { it.data!! }
        return Observable.just(idList)
    }

    override fun getAllDefaultFeedback(): Observable<List<CustomerFeedback>> {
        return Observable.just(db.customerFeedbackDao().allObservableData)
    }

    override fun getAllProductData(): Observable<List<Product>> {
        return Observable.just(db.productDao().allProductData)
    }

    override fun getProductByID(productID: Int): Observable<List<Product>> {
        return Observable.just(db.productDao().getProductByID(productID))
    }

    override fun updateProductRemainingQty(soldProductInfo: SoldProductInfo) {
        db.productDao().updateProductRemainingQty(soldProductInfo.quantity, soldProductInfo.product.id)
    }

    override fun updateRemainingQtyWithExchangeOrReturn(
        isSaleExchange: Boolean,
        qty: Int,
        productID: Int
    ) {
        if (isSaleExchange)
            db.productDao().updateProductRemainingQtyWithSaleExchange(qty, productID)
        else
            db.productDao().updateProductRemainingQtyWithSaleReturn(qty, productID)
    }

    override fun updateOrderQty(soldProductInfo: SoldProductInfo) {
        db.productDao().updateOrderQty(soldProductInfo.quantity, soldProductInfo.product.id)
    }

    override fun saveDataForTempSaleManRoute(selectedCustomer: Customer, currentDate: String, arrivalStatus: Int) {

        val saleManId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, shf)

        if (db.tempForSaleManRouteDao().dataById(saleManId ?: "0", selectedCustomer.customer_id!!).isEmpty()) {
            val tempForSaleManRoute = TempForSaleManRoute()
            tempForSaleManRoute.sale_man_id = saleManId?.toInt() ?: 0
            tempForSaleManRoute.customer_id = selectedCustomer.id.toString() //Updated - customer_id to id
            tempForSaleManRoute.latitude = selectedCustomer.latitude
            tempForSaleManRoute.longitude = selectedCustomer.longitude
            tempForSaleManRoute.arrival_time = currentDate
            tempForSaleManRoute.departure_time = currentDate
            tempForSaleManRoute.route_id = getRouteScheduleIDV2()
            db.tempForSaleManRouteDao().insertData(tempForSaleManRoute)
        } else {
            db.tempForSaleManRouteDao().updateArrivalAndDepartureTime(
                saleManId ?: "0",
                selectedCustomer.customer_id!!,
                currentDate
            )
        }

    }

    override fun saveCustomerFeedback(didCustomerFeedbackEntity: DidCustomerFeedback) {
        db.didCustomerFeedbackDao().insertData(didCustomerFeedbackEntity)
    }

    override fun saveSaleVisitRecord(selectedCustomer: Customer, arrivalStatus: Int) {
        val saleVisitRecordUpload = SaleVisitRecordUpload()
        selectedCustomer.let {
            saleVisitRecordUpload.customer_id = it.id
            val saleManId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, shf)
            saleVisitRecordUpload.sale_man_id = saleManId?.toInt() ?: 0
            saleVisitRecordUpload.latitude = it.latitude
            saleVisitRecordUpload.longitude = it.longitude
            saleVisitRecordUpload.visit_flag = "1"
            saleVisitRecordUpload.sale_flag = "0"

            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
            val currentDate = sdf.format(Date())
            saleVisitRecordUpload.record_date = currentDate
            //saleVisitRecordUpload.routeId = getRouteScheduleIDV2() //todo (to work with BI2 new API)
        }
        db.saleVisitRecordUploadDao().deleteData(saleVisitRecordUpload)
        db.saleVisitRecordUploadDao().saveData(saleVisitRecordUpload)
    }

    override fun updateSaleVisitRecord(customerId: Int, visitFlag: String, saleFlag: String) {
        db.saleVisitRecordUploadDao().updateSaleVisitRecord(customerId, visitFlag, saleFlag)
    }

    override fun updateDepartureTimeForSaleManRoute(
        saleManId: String,
        customerId: String,
        currentDate: String
    ) {
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

    override fun getClassDiscountByPrice(currentDate: String): Observable<List<ClassDiscountByPrice>> {
        return Observable.just(db.classDiscountByPriceDao().getClassDiscountByPrice(currentDate))
    }

    override fun getClassDiscountByPriceOnClassID(currentClassId: String): Observable<List<ClassDiscountByPrice>> {
        return Observable.just(
            db.classDiscountByPriceDao().getClassDiscountByPriceOnClassID(
                currentClassId
            )
        )
    }

    override fun getClassDiscountByPriceItem(classDiscountId: Int): Observable<List<ClassDiscountByPriceItem>> {
        return Observable.just(
            db.classDiscountByPriceItemDao().getClassDiscountByPriceItem(
                classDiscountId
            )
        )
    }

    override fun getClassDiscountByPriceItemCountOnClassID(classID: String): Observable<Int> {
        return Observable.just(
            db.classDiscountByPriceItemDao().getClassDiscountByPriceItemCountOnClassID(
                classID
            )
        )
    }

    override fun getCurrentDatePromotion(currentDate: String): Observable<List<PromotionDate>> {
        return Observable.just(db.promotionDateDao().getCurrentDatePromotion(currentDate))
    }

    override fun getPromotionPriceByID(
        promotionPlanId: String,
        buy_qty: Int,
        stockID: String
    ): Observable<List<PromotionPrice>> {
        return Observable.just(
            db.promotionPriceDao().getPromotionPriceByID(
                promotionPlanId,
                buy_qty,
                stockID
            )
        )
    }

    // Testing for promotion exist or not
    override fun getAllPromoPrice(): Observable<List<PromotionPrice>> {
        return Observable.just(db.promotionPriceDao().allData)
    }

    override fun getPromotionGiftByPlanID(promotionPlanId: String): Observable<List<PromotionGift>> {
        return Observable.just(db.promotionGiftDao().getPromotionGiftByPlanID(promotionPlanId))
    }

    override fun getPromotionToBuyProduct(
        promotionPlanId: String,
        soldProductInfo: SoldProductInfo
    ): Observable<List<PromotionGift>> {
        return Observable.just(
            db.promotionGiftDao().getPromotionToBuyProduct(
                promotionPlanId,
                soldProductInfo.product.sold_quantity,
                soldProductInfo.product.id.toString()
            )
        )
    }

    override fun getInvoiceCountByID(invoiceId: String): Observable<Int> {
        return Observable.just(db.invoiceDao().getInvoiceCountByID(invoiceId))
    }

    override fun insertNewInvoice(invoice: Invoice) {
        db.invoiceDao().insert(invoice)
    }

    override fun getAllInvoice(): Observable<List<Invoice>> {
        return Observable.just(db.invoiceDao().allData)
    }

    override fun insertInvoiceProduct(invoiceProduct: InvoiceProduct) {
        db.invoiceProductDao().insert(invoiceProduct)
    }

    override fun getAllInvoiceProduct(): Observable<List<InvoiceProduct>> {
        return Observable.just(db.invoiceProductDao().allData)
    }

    override fun insertAllInvoiceProduct(invoiceProductList: ArrayList<InvoiceProduct>) {
        db.invoiceProductDao().insertAll(invoiceProductList)
    }

    override fun getAllLocation(): Observable<List<Location>> {
        return Observable.just(db.locationDao().allData)
    }

    override fun getCompanyInfo(): Observable<List<CompanyInformation>> {
        return Observable.just(db.companyInformationDao().allData)
    }

    override fun getVolumeDiscountFilterByDate(currentDate: String): Observable<List<VolumeDiscountFilter>> {
        return Observable.just(
            db.volumeDiscountFilterDao().getVolumeDiscountFilterByDate(
                currentDate
            )
        )
    }

    override fun getVolumeDiscountFilterItem(volDisFilterId: Int): Observable<List<VolumeDiscountFilterItem>> {
        return Observable.just(db.volumeDiscountFilterItemDao().getDataByID(volDisFilterId.toString()))
    }

    override fun getDiscountPercentFromVolumeDiscountFilterItem(
        volDisFilterId: Int,
        buyAmt: Double
    ): Observable<List<VolumeDiscountFilterItem>> {
        return Observable.just(
            db.volumeDiscountFilterItemDao().getDiscountPercent(
                volDisFilterId.toString(),
                buyAmt
            )
        )
    }

    override fun getVolumeDiscountByDate(currentDate: String): Observable<List<VolumeDiscount>> {
        return Observable.just(db.volumeDiscountDao().getVolumeDiscountByDate(currentDate))
    }

    override fun getOrderInvoiceCountByID(invoiceId: String): Observable<Int> {
        return Observable.just(db.preOrderDao().getOrderInvoiceCountByID(invoiceId))
    }

    override fun insertAllPreOrderProduct(preOrderProductList: ArrayList<PreOrderProduct>) {
        db.preOrderProductDao().insertAll(preOrderProductList)
    }

    override fun insertPreOrder(preOrder: PreOrder) {
        db.preOrderDao().insert(preOrder)
    }

    override fun getAllPreOrder(): Observable<List<PreOrder>> {
        return Observable.just(db.preOrderDao().allData)
    }

    override fun getAllPreOrderProduct(): Observable<List<PreOrderProduct>> {
        return Observable.just(db.preOrderProductDao().allData)
    }

    override fun getActivePreOrderByIDWithName(invoiceId: String): Observable<List<PreOrder>> {
        return Observable.just(db.preOrderDao().getActivePreOrderByIDWithName(invoiceId))
    }

    override fun getActivePreOrderProductByInvoiceIDWithName(invoiceId: String): Observable<List<PreOrderProduct>> {
        return Observable.just(db.preOrderProductDao().getActivePreOrderProductByInvoiceIDWithName(invoiceId))
    }

    override fun getActivePreOrderProductByInvoiceIDList(invoiceIdList: List<String>): Observable<List<PreOrderProduct>> {
        return Observable.just(db.preOrderProductDao().getActivePreOrderProductByInvoiceIDList(invoiceIdList))
    }

    override fun insertSmsRecord(smsRecord: SMSRecord) {
        db.smsRecordDao().insert(smsRecord)
    }

    override fun getActivePreOrderPresentByInvoiceIDList(invoiceIdList: List<String>): Observable<List<PreOrderPresent>> {
        return Observable.just(db.preOrderPresentDao().getActivePreOrderPresentByInvoiceIDList(invoiceIdList))
    }

    override fun uploadPreOrderToServer(paramData: String): Observable<InvoiceResponse> {
        return upLoadApi.uploadPreOrderData(paramData)
    }

    override fun getAllActivePreOrder(): Observable<List<PreOrder>> {
        return Observable.just(db.preOrderDao().allActiveData)
    }

    override fun updateInactivePreOrderAndPreOrderProductByID(id: String) {
        db.preOrderDao().updateInactivePreOrderByID(id)
        db.preOrderProductDao().updateInactivePreOrderProductByID(id)
    }

    override fun updateInactivePreOrderPresentByID(id: String) {
        db.preOrderPresentDao().updateInactivePreOrderPresentByID(id)
    }

    override fun insertSaleReturn(saleReturn: SaleReturn) {
        db.saleReturnDao().insert(saleReturn)
    }

    override fun getAllSaleReturn(): Observable<List<SaleReturn>> {
        return Observable.just(db.saleReturnDao().allData)
    }

    override fun getSaleReturnCountByID(id: String): Observable<Int> {
        return Observable.just(db.saleReturnDao().getSaleReturnCountByID(id))
    }

    override fun insertAllSaleReturnDetail(list: List<SaleReturnDetail>) {
        db.saleReturnDetailDao().insertAll(list)
    }

    override fun updateSaleIdInSaleReturn(saleReturnInvoiceNo: String, saleID: String) {
        db.saleReturnDao().updateSaleIdInSaleReturn(saleReturnInvoiceNo, saleID)
    }

    override fun getSaleReturnProductInfo(saleReturnInvoiceNo: String): Observable<List<SaleExchangeProductInfo>> {
        return Observable.just(db.saleReturnDetailDao().getSaleReturnProductInfo(saleReturnInvoiceNo))
    }

    override fun getSaleReturnInfo(saleReturnInvoiceNo: String?): Observable<SaleReturn?> {
        return Observable.just(db.saleReturnDao().getDataByID(saleReturnInvoiceNo))
    }

    override fun getDeviceIssueRequestByID(invoiceNo: String): Observable<List<DeviceIssueRequest>> {
        return Observable.just(db.deviceIssueRequestDao().allData)
    }

}