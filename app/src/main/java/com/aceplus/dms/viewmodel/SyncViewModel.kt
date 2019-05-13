package com.aceplus.dms.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.repo.SyncRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class SyncViewModel(private val syncRepo: SyncRepo) : BaseViewModel() {

    var successState = MutableLiveData<Pair<String, String>>()
    var errorState = MutableLiveData<Pair<String, String>>()

    fun downloadAllData() {
        val date = Date()
        val sdf = SimpleDateFormat("h:mm a")
        val startTime = sdf.format(date)
        syncRepo.saveStartTime(startTime)
        val saleMan = syncRepo.getSaleManData()
        val routeScheduleID = syncRepo.getRouteScheduleID()
        val customerIdList = syncRepo.getCustomerIdList()

        launch {
            val customerParam = Utils.createParamData(
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

                    val productParam = Utils.createDownloadProductParamData(
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

                    val classDiscountParam = Utils.createParamData(
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


                    val classDiscountForShowParam = Utils.createParamData(
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

                    val promotionParam = Utils.createParamData(
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

                    val volumeDiscountParam = Utils.createParamData(
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

                    val discountCategoryQuantityParam = Utils.createParamData(
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

                    val generalParam = Utils.createParamData(
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

                    val posmAndShopTypeParam = Utils.createParamData(
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
                    val deliveryParam = Utils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadDelivery(deliveryParam)
                }
                .flatMap {
                    //do something
                    val creditParam = Utils.createParamDataWithCustomerIDList(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        customerIdList
                    )
                    return@flatMap syncRepo.downloadCredit(creditParam)
                }
                .flatMap {
                    //do something
                    val customerVisitParam = Utils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        saleMan.id.toString()
                    )
                    return@flatMap syncRepo.downloadCustomerVisit(customerVisitParam)
                }
                .flatMap {
                    //do something
                    val companyParam = Utils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadCompanyInformation(companyParam)
                }
                .flatMap {
                    //do something
                    val saleTargetParam = Utils.createParamData(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID
                    )
                    return@flatMap syncRepo.downloadSaleTarget(saleTargetParam)
                }
                .flatMap {
                    //do something
                    val saleHistoryParam = Utils.createParamDataWithCustomerIDList(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        customerIdList
                    )
                    return@flatMap syncRepo.downloadSaleHistory(saleHistoryParam)
                }
                .flatMap {
                    //do something
                    val incentiveParam = Utils.createParamDataWithCustomerIDList(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        customerIdList
                    )
                    return@flatMap syncRepo.downloadIncentive(incentiveParam)
                }
                .flatMap {
                    //do something
                    val preOrderParam = Utils.createParamDataWithCustomerIDList(
                        saleMan.user_id,
                        saleMan.password!!,
                        routeScheduleID,
                        customerIdList
                    )
                    return@flatMap syncRepo.downloadPreOrderHistory(preOrderParam)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //do something
                    successState.postValue(Pair("Successfully Downloaded", "download"))
                }, {
                    errorState.postValue(Pair(it.localizedMessage, "download"))
                })
        }
    }

    fun uploadAllData() {

    }

    fun uploadSaleVisitRecord() {

    }

    fun deleteAllData() {

    }

    fun deleteProductData() {

    }

    fun downloadReissue() {

    }
}