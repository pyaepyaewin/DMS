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
                                    val locationNumber = customerVisitRepo.getLocationCode()
                                    val feedbackNumber =
                                        Integer.parseInt(customerFeedbackList[descriptionPosition].invoice_no!!)
                                    val feedbackDate = customerFeedbackList[descriptionPosition].invoice_date
                                    val serialNumber = ""
                                    val description = customerFeedbackList[descriptionPosition].remark
                                    val routeId = customerVisitRepo.getRouteScheduleIDV2()

                                    val invoiceNumber = Utils.getInvoiceNo(
                                        saleManId,
                                        locationNumber.toString(),
                                        Constant.FOR_OTHERS,
                                        customerVisitRepo.getLastCountForInvoiceNumber(Constant.FOR_OTHERS).toString()
                                    )

                                    val didCustomerFeedbackEntity = DidCustomerFeedback()
                                    didCustomerFeedbackEntity.sale_man_id = saleManId.toInt()
                                    didCustomerFeedbackEntity.dev_id = deviceId.toInt()
                                    didCustomerFeedbackEntity.invoice_no = invoiceNumber
                                    didCustomerFeedbackEntity.invoice_date = invoiceDate
                                    didCustomerFeedbackEntity.customer_no = customerNumber
                                    didCustomerFeedbackEntity.location_no = locationNumber
                                    didCustomerFeedbackEntity.feedback_no = feedbackNumber
                                    didCustomerFeedbackEntity.feedback_date = feedbackDate
                                    didCustomerFeedbackEntity.serial_no = serialNumber.toInt()
                                    didCustomerFeedbackEntity.description = description
                                    didCustomerFeedbackEntity.remark = remark
                                    didCustomerFeedbackEntity.route_id = routeId

                                    customerVisitRepo.saveCustomerFeedback(didCustomerFeedbackEntity)

                                    customerVisitRepo.saveSaleVisitRecord(selectedCustomer, gpsTracker)

                                    customerVisitRepo.updateDepartureTimeForSaleManRoute(
                                        saleManId,
                                        selectedCustomer.id,
                                        Utils.getCurrentDate(true)
                                    )

                                }
                            }
                    }
                }
        }
    }

    fun insertDataForTempSaleManRoute(selectedCustomer: Customer, currentDate: String) {
        customerVisitRepo.saveDataForTempSaleManRoute(selectedCustomer, currentDate)
    }

}