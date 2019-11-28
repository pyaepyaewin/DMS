package com.aceplus.dms.viewmodel.customer

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.data.utils.Constant
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.domain.entity.customer.DidCustomerFeedback
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.shared.utils.GPSTracker
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import java.text.SimpleDateFormat
import java.util.*

class CustomerViewModel(
    private val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    var customerDataList = MutableLiveData<List<Customer>>()
    var dialogStatus = MutableLiveData<String>()

    fun loadCustomer() {
        launch {
            customerVisitRepo.getAllCustomerData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    customerDataList.postValue(it)
                }
        }
    }

    fun loadDidCustomerFeedback(
        selectedCustomer: Customer,
        didFeedbackAction: () -> Unit,
        newFeedbackAction: (feedbackList: List<CustomerFeedback>, makeFeedbackAction: (deviceId: String, descriptionPosition: Int, remark: String, gpsTracker: GPSTracker) -> Unit) -> Unit
    ) {
        launch {
            customerVisitRepo.getAllDidFeedback()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe { it ->
                    if (it.contains(selectedCustomer.id.toString())) {
                        didFeedbackAction()
                    } else {
                        customerVisitRepo.getAllDefaultFeedback()
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe {
                                newFeedbackAction(it) { deviceId: String, descriptionPosition: Int, remark: String, gpsTracker: GPSTracker ->
                                    val saleManData = customerVisitRepo.getSaleManData()
                                    val saleManId = saleManData.id
                                    val customerFeedbackList = it

                                    val invoiceDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss").format(Date())
                                    val customerNumber = selectedCustomer.id
                                    val locationNumber = customerVisitRepo.getLocationCode() // To Check
                                    val feedbackNumber = customerFeedbackList[descriptionPosition].id //To Check..Invoice_No or ID // Modified
                                    val feedbackDate = customerFeedbackList[descriptionPosition].invoice_date
                                    val serialNumber = ""
                                    val description = customerFeedbackList[descriptionPosition].remark
                                    val routeId = customerVisitRepo.getRouteScheduleIDV2() // Main Thread

                                    val invoiceNumber = Utils.getInvoiceNo(
                                        saleManId,
                                        locationNumber.toString(),
                                        Constant.FOR_OTHERS,
                                        customerVisitRepo.getLastCountForInvoiceNumber(Constant.FOR_OTHERS)
                                    )

                                    val didCustomerFeedbackEntity = DidCustomerFeedback()
                                    didCustomerFeedbackEntity.sale_man_id = saleManId.toInt()
                                    didCustomerFeedbackEntity.dev_id = deviceId
                                    didCustomerFeedbackEntity.invoice_no = invoiceNumber
                                    didCustomerFeedbackEntity.invoice_date = invoiceDate
                                    didCustomerFeedbackEntity.customer_no = customerNumber
                                    didCustomerFeedbackEntity.location_no = locationNumber
                                    didCustomerFeedbackEntity.feedback_no = feedbackNumber
                                    didCustomerFeedbackEntity.feedback_date = feedbackDate
                                    didCustomerFeedbackEntity.serial_no = serialNumber
                                    didCustomerFeedbackEntity.description = description
                                    didCustomerFeedbackEntity.remark = remark
                                    didCustomerFeedbackEntity.route_id = routeId

                                    customerVisitRepo.saveCustomerFeedback(didCustomerFeedbackEntity)

                                    val arrivalStatus = if (isSameCustomer(selectedCustomer,gpsTracker)) 1 else 0

                                    customerVisitRepo.saveSaleVisitRecord(selectedCustomer, arrivalStatus)
                                    customerVisitRepo.updateDepartureTimeForSaleManRoute(
                                        saleManId,
                                        selectedCustomer.customer_id!!,
                                        Utils.getCurrentDate(true)
                                    )

                                }
                            }
                    }
                }
        }
    }

    fun insertDataForTempSaleManRouteAndSaleVisitRecord(selectedCustomer: Customer, currentDate: String, gpsTracker: GPSTracker) {
        val arrivalStatus = if (isSameCustomer(selectedCustomer,gpsTracker)) 1 else 0
        customerVisitRepo.saveDataForTempSaleManRoute(selectedCustomer, currentDate, arrivalStatus)
        customerVisitRepo.saveSaleVisitRecord(selectedCustomer, arrivalStatus)
    }

    // Can it move to utils?
    private fun isSameCustomer(customer: Customer, gpsTracker: GPSTracker): Boolean {
        var arrivalStatus = false
        if (gpsTracker.canGetLocation()) {
            val customerLatitude = customer.latitude?.toDouble() ?: 0.0
            val customerLongitude = customer.longitude?.toDouble() ?: 0.0

            val saleManLatitude = gpsTracker.getLatitude()
            val saleManLongitude = gpsTracker.getLongitude()

            val startPoint = android.location.Location("locationA")
            startPoint.latitude = customerLatitude
            startPoint.longitude = customerLongitude

            val endPoint = android.location.Location("locationB")
            endPoint.latitude = saleManLatitude
            endPoint.longitude = saleManLongitude

            val distance = startPoint.distanceTo(endPoint).toDouble()
            if (customerLatitude != 0.0 && customerLongitude != 0.0) {
                if (distance < 50) {
                    arrivalStatus = true
                } else {
                    dialogStatus.postValue("You are not near customer's shop")
                    //Utils.commonDialog("You are not near customer's shop", this@CustomerActivity, 2)//will show this alert?
                }
            } else {
                dialogStatus.postValue("Customer's location is not detected")
                //Utils.commonDialog("Customer's location is not detected", this@CustomerActivity, 2)//will show this alert?
            }
        } else {
            gpsTracker.showSettingsAlert()
        }
        return arrivalStatus
    }


    fun updateCustomerLocation(customer: Customer){
        customerVisitRepo.updateCustomerData(customer)
    }

    fun saveOrUpdateSaleManRoute(customer: Customer){
        customerVisitRepo.saveDataForTempSaleManRoute(
            customer,
            Utils.getCurrentDate(true),
            1
        ) //Is this need arrival check? Now it's solid data!
    }

}