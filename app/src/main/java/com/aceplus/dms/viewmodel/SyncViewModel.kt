package com.aceplus.dms.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.aceplus.dms.utils.ParamUtils
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.repo.SyncRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.AndroidSchedulerProvider
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

class SyncViewModel(private val syncRepo: SyncRepo, private val schedulerProvider: SchedulerProvider) :
    BaseViewModel() {

    var successState = MutableLiveData<Pair<String, String>>()
    var errorState = MutableLiveData<Pair<String, String>>()

    fun downloadAllData() {
        val date = Date()
        val sdf = SimpleDateFormat("h:mm a")
        val startTime = sdf.format(date)
        syncRepo.saveStartTime(startTime)
        val saleMan = syncRepo.getSaleManData()
        val routeScheduleID = syncRepo.getRouteScheduleIDV2()
        val customerIdList = syncRepo.getCustomerIdList()

        launch {
            val customerParam = ParamUtils.createParamData(
                saleMan.user_id,
                saleMan.password!!,
                routeScheduleID
            )
            syncRepo.downloadCustomer(customerParam)
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val customerApiList = it.dataForCustomerList[0].customerList
                        if (customerApiList.count() > 0) {
                            syncRepo.saveCustomerData(customerApiList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val productParam = ParamUtils.createDownloadProductParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        "download"
                    )
                    return@flatMap syncRepo.downloadProduct(productParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val productApiList = it.dataForProductList[0].productList
                        if (productApiList.count() > 0) {
                            syncRepo.saveProductData(productApiList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val classDiscountParam = ParamUtils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadClassDiscount(classDiscountParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val classDiscountList = it.dataForClassDiscount[0].classDiscountPriceForApis
                        if (classDiscountList.count() > 0) {
                            syncRepo.saveClassDiscountData(classDiscountList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }


                    val classDiscountForShowParam = ParamUtils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadClassDiscountForShow(classDiscountForShowParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val classDiscountForShowList = it.dataForClassDiscount[0].classDiscountPriceForApis
                        if (classDiscountForShowList.count() > 0) {
                            syncRepo.saveClassDiscountForShowData(classDiscountForShowList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val promotionParam = ParamUtils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadPromotion(promotionParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val promotionList = it.promotionForApi
                        if (promotionList.count() > 0) {
                            syncRepo.savePromotionData(promotionList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val volumeDiscountParam = ParamUtils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadVolumeDiscount(volumeDiscountParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val volumeDiscountList = it.dataForVolumeDiscountList
                        if (volumeDiscountList.count() > 0) {
                            syncRepo.saveVolumeDiscountData(volumeDiscountList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val discountCategoryQuantityParam = ParamUtils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadDiscountCategoryQuantity(discountCategoryQuantityParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val discountCategoryQuantityList = it.data
                        if (discountCategoryQuantityList.count() > 0) {
                            syncRepo.saveDiscountCategoryQuantityData(discountCategoryQuantityList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val generalParam = ParamUtils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadGeneral(generalParam)
                }.flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val generalDataList = it.data
                        if (generalDataList.count() > 0) {
                            syncRepo.saveGeneralData(generalDataList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val posmAndShopTypeParam = ParamUtils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadPosmAndShopType(posmAndShopTypeParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val posmAndShopTypeList = it.posmShopTypeForApiList
                        if (posmAndShopTypeList.count() > 0) {
                            syncRepo.savePosmAndShopTypeData(posmAndShopTypeList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val deliveryParam = ParamUtils.createParamDataWithCustomerIDList(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        customerIdList
                    )
                    return@flatMap syncRepo.downloadDelivery(deliveryParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val deliveryList = it.dataForDeliveryList
                        if (deliveryList.count() > 0) {
                            syncRepo.saveDeliveryData(deliveryList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val creditParam = ParamUtils.createParamDataWithCustomerIDList(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        customerIdList
                    )
                    return@flatMap syncRepo.downloadCredit(creditParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val creditList = it.dataForCreditList
                        if (creditList.count() > 0) {
                            syncRepo.saveCreditData(creditList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val customerVisitParam = ParamUtils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        saleMan.id.toString()
                    )
                    return@flatMap syncRepo.downloadCustomerVisit(customerVisitParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val customerVisitList = it.customerVisitRequestDataList
                        if (customerVisitList.count() > 0) {
                            syncRepo.saveCustomerVisitData(customerVisitList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val companyParam = ParamUtils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadCompanyInformation(companyParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val companyInfoList = it.data
                        if (companyInfoList.count() > 0) {
                            syncRepo.saveCompanyData(companyInfoList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val saleTargetParam = ParamUtils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadSaleTarget(saleTargetParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val saleTargetList = it.dataForSaleTargetList
                        if (saleTargetList.count() > 0) {
                            syncRepo.saveSaleTargetData(saleTargetList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val saleHistoryParam = ParamUtils.createParamDataWithCustomerIDList(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        customerIdList
                    )
                    return@flatMap syncRepo.downloadSaleHistory(saleHistoryParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val saleHistoryList = it.dataForSaleHistoryList
                        if (saleHistoryList.count() > 0) {
                            syncRepo.saveSaleHistoryData(saleHistoryList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val incentiveParam = ParamUtils.createParamDataWithCustomerIDList(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        customerIdList
                    )
                    return@flatMap syncRepo.downloadIncentive(incentiveParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val incentiveList = it.dataForIncentive
                        if (incentiveList.count() > 0) {
                            syncRepo.saveIncentiveData(incentiveList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val preOrderParam = ParamUtils.createParamDataWithCustomerIDList(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        customerIdList
                    )
                    return@flatMap syncRepo.downloadPreOrderHistory(preOrderParam)
                }
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val preOrderList = it.dataForPreOrderList
                        if (preOrderList.count() > 0) {
                            syncRepo.savePreOrderData(preOrderList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }

                    val downloadConfirmParam = Utils.confirmRequestSuccessForProduct(saleMan.id, routeScheduleID)
                    return@flatMap syncRepo.downloadConfirmSuccess(downloadConfirmParam)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    if (it.aceplusStatusCode == 200) {
                        successState.postValue(Pair("Successfully Downloaded", "download"))
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download"))
                    }
                }, {
                    errorState.postValue(Pair(it.localizedMessage, "download"))
                })
        }
    }

    fun uploadAllData() {
        launch {
            val saleMan = syncRepo.getSaleManData()
            val routeScheduleID = syncRepo.getRouteScheduleIDV2()

            val customerList = syncRepo.getCustomerList()//todo check dao
            val existingCustomerList = syncRepo.getExistingCustomerList()//todo check dao

            val invoiceDataList = syncRepo.getInvoiceDataList()
            val invoicePresentList = syncRepo.getInvoicePresentList()

            val preOrderApiList = syncRepo.getPreOrderDataList()
            val preOrderPresentApiList = syncRepo.getPreOrderPresentDataList()

            val saleReturnApiList = syncRepo.getSaleReturnDataList()

            val posmByCustomerApiList = syncRepo.getPosmByCustomerDataList()

            val deliveryApiList = syncRepo.getDeliveryDataList()

            val cashReceiveApiList = syncRepo.getCashReceiveDataList()

            val displayAssessmentApiList = syncRepo.getDisplayAssessmentDataList()

            val customerVisitRequestApiList = syncRepo.getCustomerVisitDataList()

            val competitorSizeInStoreShareApiList = syncRepo.getCompetitorSizeInStoreShareDataList()

            val tSaleFeedbackApiList = syncRepo.getTSaleFeedbackDataList()

            val incentiveUploadDataApiList = syncRepo.getIncentiveUploadDataList()

            val saleCancelApiList = syncRepo.getSaleCancelDataList()

            val deviceIssueApiList = syncRepo.getDeviceIssueDataList(routeScheduleID)

            val competitorRequestApiList = syncRepo.getCompetitorRequestDataList()
            val customerParam =
                ParamUtils.createUploadCustomerParamData(
                    saleMan.user_id,
                    saleMan.password!!,
                    routeScheduleID,
                    customerList
                )

            syncRepo.uploadCustomer(customerParam)
                .flatMap {
                    if (it.aceplusStatusCode == 200) {

                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val existingCustomerParam = ParamUtils.createUploadExistingCustomerParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        existingCustomerList
                    )
                    return@flatMap syncRepo.uploadExistingCustomer(existingCustomerParam)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {

                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val saleInvoiceParam = ParamUtils.createUploadSaleInvoiceParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID, invoiceDataList, invoicePresentList
                    )
                    return@flatMap syncRepo.uploadSaleInvoice(saleInvoiceParam)//TODO check all of this point
                }
                .flatMap {
                    if (it.aceplusStatusCode == 200) {
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val preOrderParamData = ParamUtils.createUploadPreOrderParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID, preOrderApiList, preOrderPresentApiList
                    )
                    return@flatMap syncRepo.uploadPreOrder(preOrderParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                        //change all data into uploaded data
                        syncRepo.updateAllInactiveDataPreOrder()
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    //TODO Indirect Sale Table & data
                    return@flatMap syncRepo.uploadIndirectPreOrder("")
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val saleReturnParamData = ParamUtils.createUploadSaleReturnParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID, saleReturnApiList
                    )
                    return@flatMap syncRepo.uploadSaleReturn(saleReturnParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                        //change all data into uploaded data
                        syncRepo.updateAllInactiveDataSaleReturn()
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val posmByCustomerParamData = ParamUtils.createUploadPosmByCustomer(
                        saleMan.user_id,
                        saleMan.password!!, posmByCustomerApiList
                    )
                    return@flatMap syncRepo.uploadPosmByCustomer(posmByCustomerParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                        //change all data into uploaded data
                        syncRepo.updateAllInactiveDataPosmByCustomer()
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val deliveryParamData = ParamUtils.createUploadDeliveryParamData(
                        saleMan.user_id,
                        saleMan.password!!, deliveryApiList
                    )
                    return@flatMap syncRepo.uploadDelivery(deliveryParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val cashReceiveParam = ParamUtils.createUploadCashReceiveParamData(
                        saleMan.user_id,
                        saleMan.password!!, cashReceiveApiList
                    )
                    return@flatMap syncRepo.uploadCashReceive(cashReceiveParam)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val displayAssessmentParamData = ParamUtils.createUploadDisplayAssessmentParamData(
                        saleMan.user_id,
                        saleMan.password!!, displayAssessmentApiList
                    )
                    return@flatMap syncRepo.uploadDisplayAssessment(displayAssessmentParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                        //change all data into uploaded data
                        syncRepo.updateAllInactiveDataDisplayAssessment()
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val competitorSizeInStoreShareParamData =
                        ParamUtils.createUploadCompetitorSizeInStoreShareParamData(
                            saleMan.user_id,
                            saleMan.password!!, competitorSizeInStoreShareApiList
                        )
                    return@flatMap syncRepo.uploadCompetitorSizeInStoreShare(competitorSizeInStoreShareParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                        //change all data into uploaded data
                        syncRepo.updateAllInactiveDataCompetitorSizeInStoreShare()
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val customerVisitParamData = ParamUtils.createUploadCustomerVisitParamData(
                        saleMan.user_id,
                        saleMan.password!!, customerVisitRequestApiList
                    )
                    return@flatMap syncRepo.uploadCustomerVisit(customerVisitParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val unsellReasonParamData = ParamUtils.createUploadUnsellReasonParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID, tSaleFeedbackApiList
                    )
                    return@flatMap syncRepo.uploadUnsellReason(unsellReasonParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val incentiveParamData = ParamUtils.createUploadIncentiveParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID, incentiveUploadDataApiList
                    )
                    return@flatMap syncRepo.uploadIncentive(incentiveParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val saleCancelParamData = ParamUtils.createUploadSaleCancelParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID, saleCancelApiList
                    )
                    return@flatMap syncRepo.uploadSaleCancel(saleCancelParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val vanIssueParamData = ParamUtils.createUploadVanIssueParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID, deviceIssueApiList
                    )
                    return@flatMap syncRepo.uploadVanIssue(vanIssueParamData)
                }.flatMap {
                    if (it.aceplusStatusCode == 200) {
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                    val competitorParamData = ParamUtils.createUploadCompetitorParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID, competitorRequestApiList
                    )
                    return@flatMap syncRepo.uploadCompetitor(competitorParamData)
                }.subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    if (it.aceplusStatusCode == 200) {
                        syncRepo.deleteAllData()
                        successState.postValue(Pair("Successfully Uploaded", "upload"))
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "upload"))
                    }
                }, {
                    errorState.postValue(Pair(it.localizedMessage, "upload"))
                })
        }
    }

    fun uploadSaleVisitRecord() {
        val saleManRouteDataList = syncRepo.getSaleManRouteDataList()
        if (saleManRouteDataList.isNotEmpty()) {
            val saleMan = syncRepo.getSaleManData()
            val saleManRouteParamData = ParamUtils.createUploadSaleManRouteParamData(
                saleMan.user_id,
                saleMan.password!!, saleManRouteDataList
            )
            launch {
                syncRepo.uploadSaleManRoute(saleManRouteParamData)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.mainThread())
                    .subscribe({
                        if (it.aceplusStatusCode == 200) {
                            successState.postValue(
                                Pair(
                                    "Sale Man Route has successfully uploaded",
                                    "upload_sale_visit_record"
                                )
                            )
                        } else {
                            errorState.postValue(Pair(it.aceplusStatusMessage, "upload_sale_visit_record"))
                        }
                    }, {
                        errorState.postValue(Pair(it.localizedMessage, "upload_sale_visit_record"))
                    })
            }
        }
    }

    fun deleteAllData() {
        syncRepo.deleteAllData()
    }

    fun deleteProductData() {
        syncRepo.deleteProductData()
    }

    fun downloadReissue() {
        val saleMan = syncRepo.getSaleManData()
        val routeScheduleID = syncRepo.getRouteScheduleIDV2()
        val reissueParamData =
            ParamUtils.createDownloadProductParamData(saleMan.user_id, saleMan.password!!, routeScheduleID, "reissue")
        launch {
            syncRepo.downloadProduct(reissueParamData)
                .flatMap {
                    //save data
                    if (it.aceplusStatusCode == 200) {
                        val productApiList = it.dataForProductList[0].productList
                        if (productApiList.count() > 0) {
                            syncRepo.saveProductData(productApiList)
                        }
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download_reissue"))
                    }

                    val downloadConfirmParam = Utils.confirmRequestSuccessForProduct(saleMan.user_id, routeScheduleID)
                    return@flatMap syncRepo.downloadConfirmSuccess(downloadConfirmParam)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    if (it.aceplusStatusCode == 200) {
                        successState.postValue(Pair("Successfully Downloaded", "download_reissue"))
                    } else {
                        errorState.postValue(Pair(it.aceplusStatusMessage, "download_reissue"))
                    }
                }, {
                    errorState.postValue(Pair(it.localizedMessage, "download_reissue"))
                })
        }
    }
}