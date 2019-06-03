package com.aceplus.dms.utils

import android.util.Log
import com.aceplus.data.utils.Constant
import com.aceplus.domain.entity.preorder.PreOrder
import com.aceplus.domain.model.forApi.ERouteReport
import com.aceplus.domain.model.forApi.VanIssueUploadRequest
import com.aceplus.domain.model.forApi.cashreceive.CashReceiveApi
import com.aceplus.domain.model.forApi.cashreceive.CashReceiveRequest
import com.aceplus.domain.model.forApi.cashreceive.CashReceiveRequestData
import com.aceplus.domain.model.forApi.competitor.CompetitorRequest
import com.aceplus.domain.model.forApi.competitor.CompetitorRequestData
import com.aceplus.domain.model.forApi.competitor.CompetitorSizeinstoreshareData
import com.aceplus.domain.model.forApi.competitor.CompetitorSizeinstoreshareRequest
import com.aceplus.domain.model.forApi.credit.CreditApi
import com.aceplus.domain.model.forApi.customer.*
import com.aceplus.domain.model.forApi.delivery.DeliveryApi
import com.aceplus.domain.model.forApi.delivery.DeliveryRequest
import com.aceplus.domain.model.forApi.delivery.DeliveryRequestData
import com.aceplus.domain.model.forApi.deviceissue.DeviceIssueItemList
import com.aceplus.domain.model.forApi.deviceissue.DeviceIssueItem_Request
import com.aceplus.domain.model.forApi.displayassessment.DisplayAssessment
import com.aceplus.domain.model.forApi.displayassessment.DisplayAssessmentData
import com.aceplus.domain.model.forApi.displayassessment.DisplayAssessmentRequest
import com.aceplus.domain.model.forApi.incentive.IncentiveUpload
import com.aceplus.domain.model.forApi.incentive.IncentiveUploadData
import com.aceplus.domain.model.forApi.invoice.Invoice
import com.aceplus.domain.model.forApi.invoice.InvoicePresent
import com.aceplus.domain.model.forApi.login.LoginCreditRequest
import com.aceplus.domain.model.forApi.login.LoginRequest
import com.aceplus.domain.model.forApi.posm.PosmByCustomerApi
import com.aceplus.domain.model.forApi.posm.PosmByCustomerRequest
import com.aceplus.domain.model.forApi.posm.PosmByCustomerRequestData
import com.aceplus.domain.model.forApi.preorder.*
import com.aceplus.domain.model.forApi.sale.DataForSaleCancelUpload
import com.aceplus.domain.model.forApi.sale.DataforSaleUpload
import com.aceplus.domain.model.forApi.sale.saleman.DataForSaleManRoute
import com.aceplus.domain.model.forApi.sale.saleman.SaleManRouteRequest
import com.aceplus.domain.model.forApi.sale.salereturn.SaleReturnApi
import com.aceplus.domain.model.forApi.sale.salereturn.SaleReturnItem
import com.aceplus.domain.model.forApi.sale.salereturn.SaleReturnRequest
import com.aceplus.domain.model.forApi.sale.salereturn.SaleReturnRequestData
import com.aceplus.domain.model.forApi.tsale.TSaleCancelRequest
import com.aceplus.domain.model.forApi.tsale.TSaleFeedbackData
import com.aceplus.domain.model.forApi.tsale.TsaleRequest
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

object ParamUtils {

    fun getJsonFromObject(`object`: Any): String {
        val gson = GsonBuilder().serializeNulls().create()
        return gson.toJson(`object`)
    }

    //DOWNLOAD create param data start
    fun createParamData(user_no: String, password: String, routeId: Int): String {
        var paramData = ""
        val loginRequest = LoginRequest()
        loginRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        loginRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        loginRequest.userId = user_no
        loginRequest.password = password
        loginRequest.date = Utils.getCurrentDate(false)
        loginRequest.route = routeId
        val objectList = ArrayList<Any>()
        loginRequest.data = objectList

        val jsonObject = JSONObject()
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY)
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY)
            jsonObject.put("user_id", loginRequest.userId)
            jsonObject.put("password", loginRequest.password)
            jsonObject.put("route", loginRequest.route)
            jsonObject.put("date", loginRequest.date)
            jsonObject.put("data", loginRequest.data)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.i("param_data>>>", jsonObject.toString())

        paramData = jsonObject.toString()
        return paramData
    }

    fun createParamData(user_no: String, password: String, routeId: Int, saleman_id: String): String {
        var paramData = ""
        val loginRequest = LoginRequest()
        loginRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        loginRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        loginRequest.userId = user_no
        loginRequest.saleManId = saleman_id
        loginRequest.password = password
        loginRequest.date = Utils.getCurrentDate(false)
        loginRequest.route = routeId
        val objectList = ArrayList<Any>()
        loginRequest.data = objectList

        val jsonObject = JSONObject()
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY)
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY)
            jsonObject.put("user_id", loginRequest.userId)
            jsonObject.put("password", loginRequest.password)
            jsonObject.put("route", loginRequest.route)
            jsonObject.put("date", loginRequest.date)
            jsonObject.put("data", loginRequest.data)
            jsonObject.put("saleman_id", loginRequest.saleManId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.i("param_data>>>", jsonObject.toString())

        paramData = jsonObject.toString()
        return paramData
    }

    fun createLoginParamData(user_no: String, password: String, routeId: Int, tabletKey: String): String {
        var paramData = ""
        val loginRequest = LoginRequest()
        loginRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        loginRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        loginRequest.userId = user_no
        loginRequest.password = password
        loginRequest.date = Utils.getCurrentDate(false)
        loginRequest.route = routeId
        loginRequest.tabletKey = tabletKey
        val objectList = ArrayList<Any>()
        loginRequest.data = objectList

        val jsonObject = JSONObject()
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY)
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY)
            jsonObject.put("user_id", loginRequest.getUserId())
            jsonObject.put("password", loginRequest.getPassword())
            jsonObject.put("route", loginRequest.getRoute())
            jsonObject.put("tablet_key", loginRequest.getTabletKey())
            jsonObject.put("date", loginRequest.getDate())
            jsonObject.put("data", loginRequest.getData())
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.i("param_data>>>", jsonObject.toString())

        paramData = jsonObject.toString()
        return paramData
    }

    fun createDownloadProductParamData(user_no: String, password: String, routeId: Int, status: String): String {
        var paramData = ""
        val loginRequest = LoginRequest()
        loginRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        loginRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        loginRequest.userId = user_no
        loginRequest.password = password
        loginRequest.date = Utils.getCurrentDate(false)
        loginRequest.route = routeId
        val objectList = ArrayList<Any>()
        loginRequest.data = objectList

        val jsonObject = JSONObject()
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY)
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY)
            jsonObject.put("user_id", loginRequest.userId)
            jsonObject.put("password", loginRequest.password)
            jsonObject.put("route", loginRequest.route)
            jsonObject.put("date", loginRequest.date)
            jsonObject.put("data", loginRequest.data)
            jsonObject.put("status", status)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.i("param_data>>>", jsonObject.toString())

        paramData = jsonObject.toString()
        return paramData
    }

    fun createParamDataWithCustomerIDList(
        saleManNo: String,
        password: String,
        routeId: Int,
        customerIdList: List<Int>
    ): String {
        val loginRequest = LoginCreditRequest()
        loginRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        loginRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        loginRequest.userId = saleManNo
        loginRequest.password = password
        loginRequest.date = Utils.getCurrentDate(false)
        loginRequest.route = routeId

        val creditApiList = ArrayList<CreditApi>()
        val creditApi = CreditApi()
        creditApi.idList = customerIdList
        creditApiList.add(creditApi)
        loginRequest.data = creditApiList
        return getJsonFromObject(loginRequest)
    }

    //DOWNLOAD create param data end


    //UPLOAD create param data start
    fun createUploadCustomerParamData(
        userId: String,
        password: String,
        routeId: Int,
        customerForApiList: List<CustomerForApi>
    ): String {

        val customerData = CustomerData()
        val customerDataList = ArrayList<CustomerData>()

        customerData.customerForApiList = customerForApiList
        customerDataList.add(customerData)

        val addNewCustomerRequest = AddnewCustomerRequest()

        addNewCustomerRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        addNewCustomerRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        addNewCustomerRequest.userId = userId
        addNewCustomerRequest.password =
                password//it is empty string bcoz json format using gson cannot accept encrypted
        addNewCustomerRequest.route = routeId.toString() //customer downloaded by route ID
        addNewCustomerRequest.data = customerDataList

        return getJsonFromObject(addNewCustomerRequest)
    }

    fun createUploadExistingCustomerParamData(
        userId: String,
        password: String,
        routeId: Int,
        existingCustomerForApiList: List<ExistingCustomerForApi>
    ): String {
        val customerData = ExistingCustomerData()
        val customerDataList = ArrayList<ExistingCustomerData>()

        customerData.customerForApiList = existingCustomerForApiList
        customerDataList.add(customerData)

        val addExistingCustomerRequest = AddExistingCustomerRequest()

        addExistingCustomerRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        addExistingCustomerRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        addExistingCustomerRequest.userId = userId
        addExistingCustomerRequest.password =
                password//it is empty string bcoz json format using gson cannot accept encrypted
        addExistingCustomerRequest.route = routeId.toString()
        addExistingCustomerRequest.data = customerDataList
        return getJsonFromObject(addExistingCustomerRequest)
    }

    fun createUploadSaleInvoiceParamData(
        userId: String,
        password: String,
        routeId: Int, invoiceDataList: List<Invoice>, invoicePresentList: List<InvoicePresent>
    ): String {
        val dataforSaleUpload = DataforSaleUpload()
        val dataforSaleUploads = ArrayList<DataforSaleUpload>()

        dataforSaleUpload.invoice = invoiceDataList
        dataforSaleUpload.invoicePresent = invoicePresentList

        dataforSaleUploads.add(dataforSaleUpload)

        val tsaleRequest = TsaleRequest()
        tsaleRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        tsaleRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        tsaleRequest.userId = userId
        tsaleRequest.password = password//it is empty string bcoz json format using gson cannot accept encrypted
        tsaleRequest.route = routeId.toString()
        tsaleRequest.data = dataforSaleUploads

        return getJsonFromObject(tsaleRequest)
    }

    fun createUploadPreOrderParamData(
        userId: String,
        password: String,
        routeId: Int, preOrderApiList: List<PreOrderApi>, preOrderPresentApiList: List<PreOrderPresentApi>
    ): String {

        val preOrderRequestDataList = ArrayList<PreOrderRequestData>()
        val preOrderRequestData = PreOrderRequestData()
        preOrderRequestData.data = preOrderApiList
        preOrderRequestData.preorderPresent = preOrderPresentApiList
        preOrderRequestDataList.add(preOrderRequestData)

        val preOrderRequest = PreOrderRequest()
        preOrderRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        preOrderRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        preOrderRequest.userId = userId
        preOrderRequest.salemanId = userId
        preOrderRequest.password = password
        preOrderRequest.route = routeId.toString()
        preOrderRequest.data = preOrderRequestDataList

        return getJsonFromObject(preOrderRequest)
    }


    fun createUploadSaleReturnParamData(
        userId: String,
        password: String,
        routeId: Int, saleReturnApiList: List<SaleReturnApi>
    ): String {
        val saleReturnRequestDataList = ArrayList<SaleReturnRequestData>()

        val saleReturnRequestData = SaleReturnRequestData()
        saleReturnRequestData.data = saleReturnApiList
        saleReturnRequestDataList.add(saleReturnRequestData)

        val saleReturnRequest = SaleReturnRequest()
        saleReturnRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        saleReturnRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        saleReturnRequest.userId = userId
        saleReturnRequest.salemanId = userId.toInt()
        saleReturnRequest.password = password
        saleReturnRequest.route = routeId.toString()
        saleReturnRequest.data = saleReturnRequestDataList

        return getJsonFromObject(saleReturnRequest);
    }

    fun createUploadPosmByCustomer(
        userId: String,
        password: String,
        posmByCustomerApiList: List<PosmByCustomerApi>
    ): String {

        val posmByCustomerRequestDataList = ArrayList<PosmByCustomerRequestData>()

        val posmByCustomerRequestData = PosmByCustomerRequestData()
        posmByCustomerRequestData.posmByCustomerApiList = posmByCustomerApiList

        posmByCustomerRequestDataList.add(posmByCustomerRequestData)

        val posmByCustomerRequest = PosmByCustomerRequest()
        posmByCustomerRequest.data = posmByCustomerRequestDataList
        posmByCustomerRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        posmByCustomerRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        posmByCustomerRequest.userId = userId
        posmByCustomerRequest.password = password

        return getJsonFromObject(posmByCustomerRequest)
    }

    fun createUploadDeliveryParamData(
        userId: String,
        password: String, deliveryApiList: List<DeliveryApi>
    ): String {
        val deliveryRequestDataList = ArrayList<DeliveryRequestData>()

        val deliveryRequestData = DeliveryRequestData()
        deliveryRequestData.deliveryApiList = deliveryApiList
        deliveryRequestDataList.add(deliveryRequestData)

        val deliveryRequest = DeliveryRequest()
        deliveryRequest.data = deliveryRequestDataList
        deliveryRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        deliveryRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        deliveryRequest.userId = userId
        deliveryRequest.password = password
        return getJsonFromObject(deliveryRequest)
    }

    fun createUploadCashReceiveParamData(
        userId: String,
        password: String, cashReceiveApiList: List<CashReceiveApi>
    ): String {
        val cashReceiveRequestDataList = ArrayList<CashReceiveRequestData>()

        val cashReceiveRequestData = CashReceiveRequestData()
        cashReceiveRequestData.cashReceiveApiList = cashReceiveApiList
        cashReceiveRequestDataList.add(cashReceiveRequestData)

        val cashReceiveRequest = CashReceiveRequest()
        cashReceiveRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        cashReceiveRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        cashReceiveRequest.userId = userId
        cashReceiveRequest.password = password
        cashReceiveRequest.data = cashReceiveRequestDataList

        return getJsonFromObject(cashReceiveRequest)
    }

    fun createUploadDisplayAssessmentParamData(
        userId: String,
        password: String, displayAssessmentApiList: List<DisplayAssessment>
    ): String {
        val displayAssessmentDataList = ArrayList<DisplayAssessmentData>()

        val displayAssessmentData = DisplayAssessmentData()
        displayAssessmentData.displayAssessment = displayAssessmentApiList

        displayAssessmentDataList.add(displayAssessmentData)

        val displayAssessmentRequest = DisplayAssessmentRequest()
        displayAssessmentRequest.data = displayAssessmentDataList
        displayAssessmentRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        displayAssessmentRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        displayAssessmentRequest.userId = userId
        displayAssessmentRequest.password = password

        return getJsonFromObject(displayAssessmentRequest)
    }

    fun createUploadCompetitorSizeInStoreShareParamData(
        userId: String,
        password: String, competitorSizeInStoreShareApiList: List<CompetitorSizeinstoreshareData>
    ): String {
        val competitorSizeInStoreShareRequest = CompetitorSizeinstoreshareRequest()

        competitorSizeInStoreShareRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        competitorSizeInStoreShareRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        competitorSizeInStoreShareRequest.userId = userId
        competitorSizeInStoreShareRequest.password = password
        competitorSizeInStoreShareRequest.data = competitorSizeInStoreShareApiList

        return getJsonFromObject(competitorSizeInStoreShareRequest)
    }

    fun createUploadCustomerVisitParamData(
        userId: String,
        password: String, customerVisitRequestApiList: List<CustomerVisitRequestData>
    ): String {
        val customerVisitRequest = CustomerVisitRequest()

        customerVisitRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        customerVisitRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        customerVisitRequest.userId = userId
        customerVisitRequest.password = password//it is empty string bcoz json format using gson cannot accept encrypted
        customerVisitRequest.data = customerVisitRequestApiList

        return getJsonFromObject(customerVisitRequest)
    }

    fun createUploadUnsellReasonParamData(
        userId: String,
        password: String,
        routeId: Int, tSaleFeedbackApiList: List<TSaleFeedbackData>
    ): String {
        val feedbackRequest = CustomerFeedbackRequest()
        feedbackRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        feedbackRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        feedbackRequest.userId = userId
        feedbackRequest.password = password//it is empty string bcoz json format using gson cannot accept encrypted
        feedbackRequest.route = routeId.toString()
        /**
         * setting the data of invoice_feedback data
         */
        feedbackRequest.setData(tSaleFeedbackApiList)

        return getJsonFromObject(feedbackRequest)
    }

    fun createUploadIncentiveParamData(
        userId: String,
        password: String,
        routeId: Int, incentiveUploadDataApiList: List<IncentiveUploadData>
    ): String {
        val incentiveUpload = IncentiveUpload()
        incentiveUpload.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        incentiveUpload.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        incentiveUpload.userId = userId
        incentiveUpload.password = password//it is empty string bcoz json format using gson cannot accept encrypted
        incentiveUpload.route = routeId.toString()
        incentiveUpload.incentiveUploadList = incentiveUploadDataApiList

        return getJsonFromObject(incentiveUpload)
    }

    fun createUploadSaleCancelParamData(
        userId: String,
        password: String,
        routeId: Int, saleCancelApiList: List<Invoice>
    ): String {
        val dataForSaleCancelUpload = DataForSaleCancelUpload()
        val dataForSaleCancelUploadList = ArrayList<DataForSaleCancelUpload>()

        dataForSaleCancelUpload.invoice = saleCancelApiList
        dataForSaleCancelUploadList.add(dataForSaleCancelUpload)

        val tSaleCancelRequest = TSaleCancelRequest()
        tSaleCancelRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        tSaleCancelRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        tSaleCancelRequest.userId = userId
        tSaleCancelRequest.password = password //t is empty string bcoz json format using gson cannot accept encrypted
        tSaleCancelRequest.route = routeId.toString()
        tSaleCancelRequest.data = dataForSaleCancelUploadList

        return getJsonFromObject(tSaleCancelRequest)
    }

    fun createUploadVanIssueParamData(
        userId: String,
        password: String,
        routeId: Int, deviceIssueApiList: List<DeviceIssueItem_Request>
    ): String {
        val deviceIssueItem = DeviceIssueItemList()
        val deviceIssueItemList = ArrayList<DeviceIssueItemList>()

        deviceIssueItem.deviceIssueItem_requests = deviceIssueApiList
        deviceIssueItemList.add(deviceIssueItem)


        val vanIssueUploadRequest = VanIssueUploadRequest()
        vanIssueUploadRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        vanIssueUploadRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        vanIssueUploadRequest.userId = userId
        vanIssueUploadRequest.password =
                password//it is empty string bcoz json format using gson cannot accept encrypted
        vanIssueUploadRequest.route = routeId.toString()
        vanIssueUploadRequest.data = deviceIssueItemList

        return getJsonFromObject(vanIssueUploadRequest)
    }

    fun createUploadCompetitorParamData(
        userId: String,
        password: String,
        routeId: Int,
        competitorRequestApiList: List<CompetitorRequestData>
    ): String {
        val competitorRequest = CompetitorRequest()
        competitorRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        competitorRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        competitorRequest.userId = userId
        competitorRequest.password = password//it is empty string bcoz json format using gson cannot accept encrypted
        competitorRequest.route = routeId.toString()
        competitorRequest.competitorRequestList = competitorRequestApiList

        return getJsonFromObject(competitorRequest)

    }

    fun createUploadSaleManRouteParamData(
        userId: String,
        password: String, eRouteReportList: List<ERouteReport>
    ): String {
        val saleManRouteRequest = SaleManRouteRequest()
        saleManRouteRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        saleManRouteRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        saleManRouteRequest.userId = userId
        saleManRouteRequest.password = password//it is empty string bcoz json format using gson cannot accept encrypted
        val dataForSaleManRouteList = ArrayList<DataForSaleManRoute>()
        val dataForSaleManRoute = DataForSaleManRoute()
        dataForSaleManRoute.eRouteReport = eRouteReportList
        dataForSaleManRouteList.add(dataForSaleManRoute)
        saleManRouteRequest.data = dataForSaleManRouteList

        return getJsonFromObject(saleManRouteRequest)
    }
    //UPLOAD create param data end

}