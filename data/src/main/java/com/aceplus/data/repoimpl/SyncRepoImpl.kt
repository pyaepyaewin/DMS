package com.aceplus.data.repoimpl

import android.content.SharedPreferences
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.remote.DownloadApiService
import com.aceplus.data.remote.UploadApiService
import com.aceplus.data.utils.Constant
import com.aceplus.domain.entity.*
import com.aceplus.domain.entity.Currency
import com.aceplus.domain.entity.classdiscount.*
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerBalance
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.entity.delivery.DeliveryItem
import com.aceplus.domain.entity.delivery.DeliveryPresent
import com.aceplus.domain.entity.incentive.Incentive
import com.aceplus.domain.entity.incentive.IncentiveItem
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.posm.POSM
import com.aceplus.domain.entity.predefine.District
import com.aceplus.domain.entity.predefine.StateDivision
import com.aceplus.domain.entity.predefine.Street
import com.aceplus.domain.entity.predefine.Township
import com.aceplus.domain.entity.preorder.PreOrder
import com.aceplus.domain.entity.preorder.PreOrderProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.product.ProductCategory
import com.aceplus.domain.entity.product.ProductType
import com.aceplus.domain.entity.promotion.PromotionDate
import com.aceplus.domain.entity.promotion.PromotionGift
import com.aceplus.domain.entity.promotion.PromotionGiftItem
import com.aceplus.domain.entity.promotion.PromotionPrice
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import com.aceplus.domain.entity.sale.salevisit.SaleVisitRecordDownload
import com.aceplus.domain.entity.tdiscount.TDiscountByCategoryQuantity
import com.aceplus.domain.entity.tdiscount.TDiscountByCategoryQuantityItem
import com.aceplus.domain.entity.volumediscount.VolumeDiscount
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilter
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilterItem
import com.aceplus.domain.entity.volumediscount.VolumeDiscountItem
import com.aceplus.domain.model.forApi.DataForDiscountCategoryQuantity
import com.aceplus.domain.model.forApi.DiscountCategoryQuantityResponse
import com.aceplus.domain.model.forApi.ERouteReport
import com.aceplus.domain.model.forApi.cashreceive.CashReceiveApi
import com.aceplus.domain.model.forApi.cashreceive.CashReceiveItemApi
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountDataForShow
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountForShowResponse
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountPriceForApi
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountResponse
import com.aceplus.domain.model.forApi.company.CompanyInformationResponse
import com.aceplus.domain.model.forApi.company.CompanyInfromationData
import com.aceplus.domain.model.forApi.competitor.CompetitorRequestData
import com.aceplus.domain.model.forApi.competitor.CompetitorSizeinstoreshareData
import com.aceplus.domain.model.forApi.competitor.Competitor_Activity
import com.aceplus.domain.model.forApi.credit.CreditResponse
import com.aceplus.domain.model.forApi.credit.DataForCredit
import com.aceplus.domain.model.forApi.customer.*
import com.aceplus.domain.model.forApi.delivery.DataForDelivery
import com.aceplus.domain.model.forApi.delivery.DeliveryApi
import com.aceplus.domain.model.forApi.delivery.DeliveryItemApi
import com.aceplus.domain.model.forApi.delivery.DeliveryResponse
import com.aceplus.domain.model.forApi.deviceissue.DeviceIssueItem
import com.aceplus.domain.model.forApi.deviceissue.DeviceIssueItem_Request
import com.aceplus.domain.model.forApi.displayassessment.DisplayAssessment
import com.aceplus.domain.model.forApi.incentive.DataForIncentive
import com.aceplus.domain.model.forApi.incentive.IncentivePaidUploadData
import com.aceplus.domain.model.forApi.incentive.IncentiveResponse
import com.aceplus.domain.model.forApi.incentive.IncentiveUploadData
import com.aceplus.domain.model.forApi.invoice.InvoiceDetail
import com.aceplus.domain.model.forApi.invoice.InvoicePresent
import com.aceplus.domain.model.forApi.invoice.InvoiceResponse
import com.aceplus.domain.model.forApi.other.GeneralData
import com.aceplus.domain.model.forApi.other.GeneralResponse
import com.aceplus.domain.model.forApi.posm.PosmByCustomerApi
import com.aceplus.domain.model.forApi.posm.PosmShopTypeForApi
import com.aceplus.domain.model.forApi.posm.PosmShopTypeResponse
import com.aceplus.domain.model.forApi.preorder.*
import com.aceplus.domain.model.forApi.product.ProductForApi
import com.aceplus.domain.model.forApi.product.ProductResponse
import com.aceplus.domain.model.forApi.promotion.PromotionForApi
import com.aceplus.domain.model.forApi.promotion.PromotionResponse
import com.aceplus.domain.model.forApi.sale.SaleVisitRecord
import com.aceplus.domain.model.forApi.sale.salehistory.DataForSaleHistory
import com.aceplus.domain.model.forApi.sale.salehistory.SaleHistoryResponse
import com.aceplus.domain.model.forApi.sale.salereturn.SaleReturnApi
import com.aceplus.domain.model.forApi.sale.salereturn.SaleReturnItem
import com.aceplus.domain.model.forApi.sale.saletarget.DataForSaleTarget
import com.aceplus.domain.model.forApi.sale.saletarget.SaleTargetResponse
import com.aceplus.domain.model.forApi.sizeinstore.SizeInStoreShare
import com.aceplus.domain.model.forApi.tsale.TSaleFeedback
import com.aceplus.domain.model.forApi.tsale.TSaleFeedbackData
import com.aceplus.domain.model.forApi.volumediscount.DataForVolumeDiscount
import com.aceplus.domain.model.forApi.volumediscount.VolumeDiscountResponse
import com.aceplus.domain.repo.SyncRepo
import com.aceplussolutions.rms.constants.AppUtils
import io.reactivex.Observable
import java.util.*

class SyncRepoImpl(
    private val downloadApiService: DownloadApiService,
    private val uploadApiService: UploadApiService,
    private val db: MyDatabase,
    private val shf: SharedPreferences
) : SyncRepo {

    override fun saveStartTime(time: String) {
        AppUtils.saveStringToShp(Constant.START_TIME, time, shf)
    }

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
        val routeScheduleItems =
            db.routeScheduleItemV2Dao().allDataByRouteScheduleId(routeSchedule.id.toString())
        return if (routeScheduleItems.count() > 0)
            routeScheduleItems[0].route_schedule_id
        else
            0
    }

    override fun getCustomerIdList(): List<Int> {
        return db.customerDao().allID.map { it.data!!.toInt() }
    }

    override fun getCustomerList(): List<CustomerForApi> {
//        return db.customerDao().customerList
        val list = mutableListOf<CustomerForApi>()
        return list
    }

    override fun getExistingCustomerList(): List<ExistingCustomerForApi> {
//        return db.customerDao().existingCustomerList
        val list = mutableListOf<ExistingCustomerForApi>()
        return list
    }

    override fun getInvoiceDataList(): List<com.aceplus.domain.model.forApi.invoice.Invoice> {
        val mmCurrencyId = db.currencyDao().mmCurrencyId
        val invoiceForApiList = mutableListOf<com.aceplus.domain.model.forApi.invoice.Invoice>()
        db.invoiceDao().activeData.map { it ->
            val invoiceForApi = com.aceplus.domain.model.forApi.invoice.Invoice()
            invoiceForApi.id = it.invoice_id
            invoiceForApi.customerId = it.customer_id
            invoiceForApi.date = it.sale_date
            invoiceForApi.totalAmt = it.total_amount?.toDouble() ?: 0.0
            invoiceForApi.totalQty = it.total_quantity.toInt()
            invoiceForApi.totalDiscountAmt = it.total_discount_amount
            invoiceForApi.totalPayAmt = it.pay_amount?.toDouble() ?: 0.0
            invoiceForApi.totalRefundAmt = it.refund_amount?.toDouble() ?: 0.0
            invoiceForApi.receiptPerson = it.receipt_person_name
            invoiceForApi.salepersonId = it.sale_person_id?.toInt() ?: 0
            invoiceForApi.locationCode = it.location_code?.toInt() ?: 0
            invoiceForApi.deviceId = it.device_id
            invoiceForApi.invoiceTime = it.invoice_time
            invoiceForApi.currencyId = mmCurrencyId
            invoiceForApi.invoiceStatus = it.invoice_status
            invoiceForApi.discountPercent = it.total_discount_percent?.toDouble() ?: 0.0
            invoiceForApi.rate = it.rate?.toDouble() ?: 0.0
            invoiceForApi.taxAmount = it.tax_amount
            invoiceForApi.dueDate = it.due_date
            invoiceForApi.bankName = it.bank_name
            invoiceForApi.bankAccountNo = it.bank_account_no

            invoiceForApi.productDiscountPercent =
                "promotionDiscountPercent"//todo change table column
            invoiceForApi.productDiscountAmount =
                "promotionDiscountAmount"//todo change table column
            invoiceForApi.deliveryDiscountPercent =
                "deliveryDiscountPercent"//todo change table column
            invoiceForApi.deliveryDiscountAmount =
                "deliveryDiscountAmount"//todo change table column

            val invoiceDetailForApiList = ArrayList<InvoiceDetail>()
            val invoiceDetailList = db.invoiceProductDao().allDataById(it.invoice_id)
            invoiceDetailList.map {
                val invoiceDetail = InvoiceDetail()
                invoiceDetail.tsaleId = it.invoice_product_id
                invoiceDetail.productId = it.product_id?.toInt() ?: 0
                invoiceDetail.qty = it.sale_quantity?.toInt() ?: 0
                invoiceDetail.discountAmt = it.discount_amount?.toDouble() ?: 0.0
                invoiceDetail.amt = it.total_amount
                invoiceDetail.discountPercent = it.discount_percent
                invoiceDetail.s_price = it.s_price
                invoiceDetail.p_price = it.p_price
                invoiceDetail.promotionPrice = it.promotion_price
                invoiceDetail.promotion_plan_id = it.promotion_plan_id
                invoiceDetail.exclude = it.exclude?.toInt() ?: 0
                invoiceDetail.itemDiscountAmount = it.item_discount_amount
                invoiceDetail.itemDiscountPercent = it.item_discount_percent

                invoiceDetailForApiList.add(invoiceDetail)
            }

            invoiceForApi.invoiceDetail = invoiceDetailForApiList

            invoiceForApiList.add(invoiceForApi)
        }
        return invoiceForApiList
    }

    override fun getInvoicePresentList(): List<InvoicePresent> {
        val invoicePresentForApiList = mutableListOf<InvoicePresent>()
        db.invoicePresentDao().allData.map {
            val invoicePresent = InvoicePresent()
            invoicePresent.tsaleId = it.tsale_id.toString()
            invoicePresent.stockId = it.stock_id
            invoicePresent.quantity = it.quantity.toInt()
            invoicePresent.pcAddress = it.pc_address
            invoicePresent.locationId = it.location_id.toString()
            invoicePresent.price = it.price
            invoicePresent.currencyId = it.currency_id
            invoicePresent.rate = it.rate?.toDouble() ?: 0.0
            invoicePresent.p_price = it.p_price
            invoicePresentForApiList.add(invoicePresent)
        }
        return invoicePresentForApiList
    }

    override fun getPreOrderDataList(): List<PreOrderApi> {
        val preOrderApiList = mutableListOf<PreOrderApi>()
        val preOrderList = db.preOrderDao().allActiveData
        preOrderList.map { it ->
            val preOrderApi = PreOrderApi()
            preOrderApi.id = it.invoice_id
            preOrderApi.customerId = it.customer_id?.toInt() ?: 0
            preOrderApi.saleManId = it.sale_man_id
            preOrderApi.deviceId = it.dev_id
            preOrderApi.saleOrderDate = it.pre_order_date
            preOrderApi.expectedDeliveredDate = it.expected_delivery_date
            preOrderApi.advancedPaymentAmt = it.advance_payment_amount?.toDouble() ?: 0.0
            preOrderApi.netAmt = it.net_amount?.toDouble() ?: 0.0
            preOrderApi.locationId = it.location_id?.toInt() ?: 0
            preOrderApi.discount = it.discount?.toDouble() ?: 0.0
            preOrderApi.discountPer = it.discount_percent?.toDouble() ?: 0.0
            preOrderApi.taxAmount = it.tax_amount?.toDouble() ?: 0.0
            preOrderApi.remark = it.remark
            preOrderApi.bankName = it.bank_name
            preOrderApi.bankAccountNo = it.bank_account_no
            preOrderApi.productDiscountPercent =
                "preOrder.getProductDiscountPercent()"//todo change table
            preOrderApi.productDiscountAmount =
                "preOrder.getProductDiscountAmount()"//todo change table
            preOrderApi.deliveryDiscountPercent =
                "preOrder.getDeliveryDiscountPercent()"//todo change table
            preOrderApi.deliveryDiscountAmount =
                "preOrder.getDeliveryDiscountAmount()"//todo change table

            val preOrderProductList = db.preOrderProductDao().allDataById(it.invoice_id)

            val preOrderDetailApiList = ArrayList<PreOrderDetailApi>()
            preOrderProductList.map {
                val preOrderDetailApi = PreOrderDetailApi()
                preOrderDetailApi.saleOrderId = it.sale_order_id
                preOrderDetailApi.productId = it.product_id?.toInt() ?: 0
                preOrderDetailApi.qty = it.order_quantity?.toDouble() ?: 0.0
                preOrderDetailApi.promotionPrice = it.promotion_price?.toDouble() ?: 0.0
                preOrderDetailApi.volumeDiscount = it.volume_discount?.toDouble() ?: 0.0
                preOrderDetailApi.volumeDiscountPer = it.volume_discount_percent?.toDouble() ?: 0.0
                preOrderDetailApi.exclude = it.exclude?.toInt() ?: 0
                preOrderDetailApi.s_Price = it.price?.toDouble() ?: 0.0
                preOrderDetailApi.promotionPlanId = it.promotion_plan_id?.toInt() ?: 0
                preOrderDetailApiList.add(preOrderDetailApi)
            }

            preOrderApi.preOrderDetailList = preOrderDetailApiList

            preOrderApiList.add(preOrderApi)
        }

        return preOrderApiList
    }

    override fun getPreOrderPresentDataList(): List<PreOrderPresentApi> {
        val preOrderPresentApiList = mutableListOf<PreOrderPresentApi>()
        val preOrderPresentList = db.preOrderPresentDao().allActiveData
        preOrderPresentList.map {
            val preOrderPresentApi = PreOrderPresentApi()
            preOrderPresentApi.saleOrderId = it.pre_order_id.toString()
            preOrderPresentApi.productId = it.stock_id
            preOrderPresentApi.quantity = it.quantity.toInt()
//            preOrderPresentApi.setStatus(cursorPreOrderPresent.getString(cursorPreOrderPresent.getColumnIndex("status")));
            preOrderPresentApi.status = "Y"
            preOrderPresentApiList.add(preOrderPresentApi)
        }
        return preOrderPresentApiList
    }

    override fun getSaleReturnDataList(): List<SaleReturnApi> {
        val mmCurrencyId = db.currencyDao().mmCurrencyId
        val saleReturnList = db.saleReturnDao().allActiveData
        val saleReturnApiList = ArrayList<SaleReturnApi>()
        saleReturnList.map { it ->
            val saleReturnApi = SaleReturnApi()
            saleReturnApi.invoiceNo = it.sale_return_id.toString()
            saleReturnApi.customerId = it.customer_id
            saleReturnApi.invoiceDate = it.return_date
            saleReturnApi.locationId = it.location_id
            saleReturnApi.pcAddress = it.pc_address
            saleReturnApi.amount = it.amount
            saleReturnApi.payAmount = it.pay_amount
            saleReturnApi.disAmount = it.discount
            saleReturnApi.taxAmount = it.tax
            saleReturnApi.taxPercent = it.tax_percent
            saleReturnApi.currencyId = mmCurrencyId
            saleReturnApi.rate = 1.0
            saleReturnApi.invoiceStatus = it.invoice_status
            saleReturnApi.saleManId = it.sale_man_id
            saleReturnApi.saleId = it.sale_id.toString()

            val saleReturnDetailList = db.saleReturnDetailDao().allActiveDataById(it.sale_return_id!!)

            val saleReturnDetailApiList = ArrayList<SaleReturnItem>()
            saleReturnDetailList.map {
                val saleReturnItem = SaleReturnItem()
                saleReturnItem.invoiceNo = it.sale_return_id.toString()
                saleReturnItem.stockId = it.product_id.toString()
                saleReturnItem.price = it.price
                saleReturnItem.quantity = it.quantity.toInt()
                saleReturnDetailApiList.add(saleReturnItem)
            }

            saleReturnApi.saleReturnItemList = saleReturnDetailApiList
            saleReturnApiList.add(saleReturnApi)
        }
        return saleReturnApiList
    }

    override fun getPosmByCustomerDataList(): List<PosmByCustomerApi> {
        val posmByCustomerApiList = mutableListOf<PosmByCustomerApi>()
        val posmByCustomerList = db.posmByCustomerDao().allActiveData
        posmByCustomerList.map {
            val posmByCustomerApi = PosmByCustomerApi()
            posmByCustomerApi.invoiceNo = it.invoice_no
            posmByCustomerApi.invoiceDate = it.invoice_date
            posmByCustomerApi.customerId = it.customer_id
            posmByCustomerApi.stockId = it.stock_id
            posmByCustomerApi.shopTypeId = it.shop_type_id
            posmByCustomerApi.saleManId = it.sale_man_id.toString()
            posmByCustomerApi.quantity = it.quantity.toInt()
            posmByCustomerApi.price = it.price
            posmByCustomerApiList.add(posmByCustomerApi)
        }
        return posmByCustomerApiList
    }

    override fun getDeliveryDataList(): List<DeliveryApi> {
        val locationCode = this.getLocationCode()
        val salemanId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, shf).toString()

        val deliveryApiList = ArrayList<DeliveryApi>()
        val deliveryDataList = db.deliveryUploadDao().allData
        deliveryDataList.map { it ->
            val deliveryApi = DeliveryApi()
            deliveryApi.invoiceNo = it.invoice_no.toString()
            deliveryApi.invoiceDate = it.invoice_date
            deliveryApi.saleId = it.sale_id.toString()
            deliveryApi.remark = it.remark
            deliveryApi.customerId = it.customer_id
            deliveryApi.locationId = locationCode
            deliveryApi.saleManId = salemanId.toInt()
            val deliveryItemDataList = db.deliveryItemUpload().allDataById(it.invoice_no)
            val deliveryItemApiList = ArrayList<DeliveryItemApi>()
            deliveryItemDataList.map {
                val deliveryItemApi = DeliveryItemApi()
                deliveryItemApi.deliveryId = it.delivery_id.toString()
                deliveryItemApi.stockId = it.stock_id
                deliveryItemApi.deliveryQty = it.quantity?.toInt() ?: 0
                deliveryItemApi.foc = it.foc?.toShort() ?: 0
                deliveryItemApiList.add(deliveryItemApi)
            }
            deliveryApi.deliveryItemApi = deliveryItemApiList
            deliveryApiList.add(deliveryApi)
        }
        return deliveryApiList
    }

    override fun getCashReceiveDataList(): List<CashReceiveApi> {
        val cashReceiveApiList = ArrayList<CashReceiveApi>()
        val cashReceiveDataList = db.cashReceiveDao().allData
        cashReceiveDataList.map {
            val cashReceiveApi = CashReceiveApi()
            cashReceiveApi.receiveNo = it.receive_no
            cashReceiveApi.receiveDate = it.receive_date
            cashReceiveApi.customerId = it.customer_id
            cashReceiveApi.amount = it.amount
            cashReceiveApi.currencyId = it.currency_id
            cashReceiveApi.status = it.status
            cashReceiveApi.locationId = it.location_id
            cashReceiveApi.paymentType = it.payment_type
            cashReceiveApi.cashReceiveType = it.cash_receive_type
            cashReceiveApi.saleId = it.sale_id
            cashReceiveApi.saleManId = it.sale_man_id

            val cashReceiveItemApiList = ArrayList<CashReceiveItemApi>()
            val cashReceiveItemDataList = db.cashReceiveItemDao().allDataById(it.receive_no)
            cashReceiveItemDataList.map {
                val cashReceiveItemApi = CashReceiveItemApi()
                cashReceiveItemApi.receiveNo = it.receive_no
                cashReceiveItemApi.saleId = it.sale_id?.toInt() ?: 0
                cashReceiveItemApiList.add(cashReceiveItemApi)
            }
            cashReceiveApi.cashReceiveItem = cashReceiveItemApiList

            cashReceiveApiList.add(cashReceiveApi)
        }
        return cashReceiveApiList
    }

    override fun getDisplayAssessmentDataList(): List<DisplayAssessment> {
        val displayAssessmentList = ArrayList<DisplayAssessment>()

        val displayAssessmentDataList = db.outletVisibilityDao().allActiveData
        displayAssessmentDataList.map {
            val displayAssessment = DisplayAssessment()
            displayAssessment.invoiceNo = it.invoice_no
            displayAssessment.invoiceDate = it.invoice_date
            displayAssessment.customerId = it.customer_id
            displayAssessment.saleManId = it.sale_man_id
            displayAssessment.image = it.image
            displayAssessment.imageNo = it.image_no
            displayAssessment.imageName = it.image_name
            displayAssessment.dateAndTime = it.date_and_time
            displayAssessment.remark = it.remark

            displayAssessmentList.add(displayAssessment)
        }
        return displayAssessmentList
    }

    override fun getCompetitorSizeInStoreShareDataList(): List<CompetitorSizeinstoreshareData> {
        val competitorSizeInStoreShareDataList = ArrayList<CompetitorSizeinstoreshareData>()
        val competitorSizeInStoreShareData = CompetitorSizeinstoreshareData()

        val sizeInStoreShareList = ArrayList<SizeInStoreShare>()

        val sizeInStoreShareEntitiyDataList = db.sizeInStoreShareDao().allActiveData
        sizeInStoreShareEntitiyDataList.map {

            val sizeInStoreShare = SizeInStoreShare()
            sizeInStoreShare.sizeInStoreShareNo = it.size_in_store_share_id.toString()
            sizeInStoreShare.customerId = it.customer_id
            sizeInStoreShare.date = it.invoice_date
            sizeInStoreShare.stockId = it.stock_id
            sizeInStoreShare.quantity = it.quantity
            sizeInStoreShare.status = it.status
            sizeInStoreShare.remark = it.remark
            sizeInStoreShare.salemanId = it.sale_man_id

            sizeInStoreShareList.add(sizeInStoreShare)
        }

        competitorSizeInStoreShareData.sizeInStoreShare = sizeInStoreShareList
        competitorSizeInStoreShareDataList.add(competitorSizeInStoreShareData)

        return competitorSizeInStoreShareDataList
    }

    override fun getCustomerVisitDataList(): List<CustomerVisitRequestData> {
        val customerVisitRequestDataList = ArrayList<CustomerVisitRequestData>()
        val customerVisitRequestData = CustomerVisitRequestData()

        val saleVisitRecordList = ArrayList<SaleVisitRecord>()

        val saleVisitRecordUploadDataList = db.saleVisitRecordUploadDao().allData
        saleVisitRecordUploadDataList.map {
            val saleVisitRecord = SaleVisitRecord()

            saleVisitRecord.id = it.id
            saleVisitRecord.customerId = it.customer_id
            saleVisitRecord.latitude = it.latitude
            saleVisitRecord.longitude = it.longitude
            saleVisitRecord.salemanId = it.sale_man_id
            saleVisitRecord.saleFlg = it.sale_flag?.toShort() ?: 0
            saleVisitRecord.visitFlg = it.visit_flag?.toShort() ?: 0
            saleVisitRecord.recordDate = it.record_date
            saleVisitRecordList.add(saleVisitRecord)
        }
        customerVisitRequestData.saleVisitRecordList = saleVisitRecordList

        customerVisitRequestDataList.add(customerVisitRequestData)
        return customerVisitRequestDataList
    }

    override fun getTSaleFeedbackDataList(): List<TSaleFeedbackData> {
        val feedbackList = ArrayList<TSaleFeedback>()

        val tSaleFeedbackDataList = db.didCustomerFeedbackDao().allData
        tSaleFeedbackDataList.map {
            val customerFeedback = TSaleFeedback()
            customerFeedback.id = it.invoice_no.toString()
            customerFeedback.invoiceDate = it.invoice_date
            customerFeedback.customerId = it.customer_no.toString()
            customerFeedback.saleManId = it.sale_man_id
            customerFeedback.customerFeedbackId = it.feedback_no
            customerFeedback.description = it.description
            customerFeedback.remark = it.remark
            customerFeedback.route_id = it.route_id
            feedbackList.add(customerFeedback)
        }

        val feedbackDataList = ArrayList<TSaleFeedbackData>()
        val tSaleFeedbackData = TSaleFeedbackData()
        tSaleFeedbackData.feedBackList = feedbackList
        feedbackDataList.add(tSaleFeedbackData)
        return feedbackDataList
    }

    override fun getIncentiveUploadDataList(): List<IncentiveUploadData> {
        val incentiveUploadDataList = ArrayList<IncentiveUploadData>()
        val incentivePaidUploadDataList = ArrayList<IncentivePaidUploadData>()

        val incentivePaidDataList = db.incentivePaidDao().allActiveData
        incentivePaidDataList.map {
            val incentivePaidUploadData = IncentivePaidUploadData()
            incentivePaidUploadData.invoiceNo = it.invoice_no.toString()
            incentivePaidUploadData.invoiceDate = it.invoice_date
            incentivePaidUploadData.customerId = it.customer_id
            incentivePaidUploadData.stockId = it.stock_id
            incentivePaidUploadData.quantity = it.quantity
            incentivePaidUploadData.paidQuantity = it.paid_quantity
            incentivePaidUploadData.saleManId = it.sale_man_id
            incentivePaidUploadDataList.add(incentivePaidUploadData)
        }
        val incentiveUploadData = IncentiveUploadData()
        incentiveUploadData.incentivePaidUploadDataList = incentivePaidUploadDataList
        incentiveUploadDataList.add(incentiveUploadData)
        return incentiveUploadDataList
    }

    override fun getSaleCancelDataList(): List<com.aceplus.domain.model.forApi.invoice.Invoice> {
        val mmCurrencyId = db.currencyDao().mmCurrencyId
        val invoiceList = ArrayList<com.aceplus.domain.model.forApi.invoice.Invoice>()
        val invoiceCancelList = db.invoiceCancelDao().allActiveData
        invoiceCancelList.map { it ->
            val invoice = com.aceplus.domain.model.forApi.invoice.Invoice()

            invoice.id = it.invoice_id.toString()
            invoice.customerId = it.customer_id.toString()
            invoice.date = it.sale_date
            invoice.totalAmt = it.total_amount
            invoice.totalQty = it.total_quantity
            invoice.totalDiscountAmt = it.total_discount_amount
            invoice.totalPayAmt = it.pay_amount
            invoice.totalRefundAmt = it.refund_amount
            invoice.receiptPerson = it.receipt_person_name
            invoice.salepersonId = it.sale_person_id
            invoice.locationCode = it.location_code?.toInt() ?: 0
            invoice.deviceId = it.device_id
            invoice.invoiceTime = it.invoice_time
            invoice.currencyId = mmCurrencyId
            invoice.invoiceStatus = it.invoice_status
            invoice.discountPercent = it.total_discount_percent?.toDouble() ?: 0.0
            invoice.rate = it.rate?.toDouble() ?: 0.0
            invoice.taxAmount = it.tax_amount
            invoice.dueDate = it.due_date
            invoice.bankName = it.bank_name
            invoice.bankAccountNo = it.bank_account_no

            val invoiceDetailList = ArrayList<InvoiceDetail>()
            val invoiceCancelProductList = db.invoiceCancelProductDao().allDataById(invoice.id)
            invoiceCancelProductList.map {
                val invoiceDetail = InvoiceDetail()
                invoiceDetail.tsaleId = it.invoice_product_id.toString()
                invoiceDetail.productId = it.product_id
                invoiceDetail.qty = it.sale_quantity.toInt()
                invoiceDetail.discountAmt = it.discount_amount
                invoiceDetail.amt = it.total_amount
                invoiceDetail.discountPercent = it.discount_percent
                invoiceDetail.s_price = it.s_price
                invoiceDetail.p_price = it.p_price
                invoiceDetail.promotionPrice = it.promotion_price
                invoiceDetail.promotion_plan_id = it.promotion_plan_id
                invoiceDetail.exclude = it.exclude?.toInt() ?: 0

                invoiceDetailList.add(invoiceDetail)
            }
            invoice.invoiceDetail = invoiceDetailList
            invoiceList.add(invoice)
        }


        return invoiceList
    }

    override fun getDeviceIssueDataList(routeScheduleID: Int): List<DeviceIssueItem_Request> {
        val request = DeviceIssueItem_Request()
        val requestList = ArrayList<DeviceIssueItem_Request>()

        val deviceIssueRequestDataList = db.deviceIssueRequestDao().allData

        deviceIssueRequestDataList.map { it ->
            request.invoiceNo = it.invoice_no.toString()
            request.date = it.date
            request.remark = it.remark
            if (it.route_id != 0)
                request.routeId = it.route_id.toString()
            else
                request.routeId = routeScheduleID.toString()

            val itemLists = ArrayList<DeviceIssueItem>()
            val deviceIssueRequestItemDataList =
                db.deviceIssueRequestItemDao().allDataById(it.invoice_no)

            deviceIssueRequestItemDataList.map {
                val deviceIssueItem = DeviceIssueItem()
                deviceIssueItem.stockId = it.stock_id.toString()
                deviceIssueItem.quantity = it.quantity
                deviceIssueItem.invoiceNo = it.invoice_no.toString()
                itemLists.add(deviceIssueItem)
            }
            request.deviceIssueItemList = itemLists
            requestList.add(request)
        }

        return requestList
    }

    override fun getCompetitorRequestDataList(): List<CompetitorRequestData> {
        val competitorRequestDataList = ArrayList<CompetitorRequestData>()
        val competitorActivities = ArrayList<Competitor_Activity>()

        val competitorActivityDataList = db.competitorActivityDao().allData
        competitorActivityDataList.map {
            val competitorActivity = Competitor_Activity()
            competitorActivity.competitorActivitiesNo = it.id.toString()
            competitorActivity.customerId = it.customer_id
            competitorActivity.competitorName = it.competitor_name
            competitorActivity.activities = it.activity
            competitorActivity.invoiceDate = it.invoice_date
            competitorActivity.saleManId = it.sale_man_id?.toInt() ?: 0
            competitorActivities.add(competitorActivity)
        }
        val competitorRequestData = CompetitorRequestData()
        competitorRequestData.competitorActivityList = competitorActivities
        competitorRequestDataList.add(competitorRequestData)
        return competitorRequestDataList
    }

    override fun getSaleManRouteDataList(): List<ERouteReport> {
        val eRouteReportArrayList = ArrayList<ERouteReport>()
        val saleManRouteDataList = db.tempForSaleManRouteDao().allData
        saleManRouteDataList.map {
            val eRouteReport = ERouteReport()
            eRouteReport.customerId = it.customer_id
            eRouteReport.saleManId = it.sale_man_id // Changed Int to String by YLA
            eRouteReport.routeId = it.route_id
            eRouteReport.arrivalTime = it.arrival_time
            eRouteReport.departureTime = it.departure_time
            eRouteReport.latitude = it.latitude?.toDouble() ?: 0.0
            eRouteReport.longitude = it.longitude?.toDouble() ?: 0.0

            eRouteReportArrayList.add(eRouteReport)
        }
        return eRouteReportArrayList
    }

    override fun downloadCustomer(paramData: String): Observable<CustomerResponse> {
        return downloadApiService.getCustomer(paramData)
    }

    override fun downloadProduct(paramData: String): Observable<ProductResponse> {
        return downloadApiService.getProduct(paramData)
    }

    override fun downloadClassDiscount(paramData: String): Observable<ClassDiscountResponse> {
        return downloadApiService.getClassDiscount(paramData)
    }

    override fun downloadClassDiscountForShow(paramData: String): Observable<ClassDiscountForShowResponse> {
        return downloadApiService.getClassDiscountForShow(paramData)
    }

    override fun downloadPromotion(paramData: String): Observable<PromotionResponse> {
        return downloadApiService.getPromotion(paramData)
    }

    override fun downloadVolumeDiscount(paramData: String): Observable<VolumeDiscountResponse> {
        return downloadApiService.getVolumeDiscount(paramData)
    }

    override fun downloadDiscountCategoryQuantity(paramData: String): Observable<DiscountCategoryQuantityResponse> {
        return downloadApiService.getDiscountCategoryQuantity(paramData)
    }

    override fun downloadGeneral(paramData: String): Observable<GeneralResponse> {
        return downloadApiService.getGeneral(paramData)
    }

    override fun downloadPosmAndShopType(paramData: String): Observable<PosmShopTypeResponse> {
        return downloadApiService.getPosmAndShopType(paramData)
    }

    override fun downloadDelivery(paramData: String): Observable<DeliveryResponse> {
        return downloadApiService.getDeliveryFromApi(paramData)
    }

    override fun downloadCredit(paramData: String): Observable<CreditResponse> {
        return downloadApiService.getCreditFromApi(paramData)
    }

    override fun downloadCustomerVisit(paramData: String): Observable<CustomerVisitResponse> {
        return downloadApiService.getCustomerVisitFromApi(paramData)
    }

    override fun downloadCompanyInformation(paramData: String): Observable<CompanyInformationResponse> {
        return downloadApiService.getCompanyInformationFromApi(paramData)
    }

    override fun downloadSaleTarget(paramData: String): Observable<SaleTargetResponse> {
        return downloadApiService.getSaleTargetFromApi(paramData)
    }

    override fun downloadSaleHistory(paramData: String): Observable<SaleHistoryResponse> {
        return downloadApiService.getSaleHistoryFromApi(paramData)
    }

    override fun downloadIncentive(paramData: String): Observable<IncentiveResponse> {
        return downloadApiService.getIncentiveFromApi(paramData)
    }

    override fun downloadPreOrderHistory(paramData: String): Observable<PreOrderHistoryResponse> {
        return downloadApiService.getPreOrderHistoryi(paramData)
    }

    override fun downloadConfirmSuccess(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadConfirmDownloadSuccess(paramData)
    }

    override fun saveCustomerData(customerList: List<CustomerForApi>) {
        val customerEntityList = mutableListOf<Customer>()
        customerList.map {
            val customer = Customer()
            customer.id = it.id
            customer.customer_id = it.customerId!!
            customer.customer_name = it.customerName
            customer.customer_type_id = it.customerTypeId
            customer.customer_type_name = it.customerTypeName
            customer.address = it.address
            customer.phone = it.pH
            customer.township_number = it.townshipNumber
            customer.credit_term = it.creditTerm
            customer.credit_limit = it.creditLimit
            customer.credit_amount = it.creditAmt
            customer.due_amount = it.dueAmount
            customer.prepaid_amount = it.prepaidAmount
            customer.payment_type = it.paymentType
            customer.in_route = it.isInRoute
            customer.latitude = it.latitude
            customer.longitude = it.longitude
            customer.visit_record = it.visitRecord
            customer.district_id = it.districtId
            customer.state_division_id = it.stateDivisionId
            customer.contact_person = it.contactPerson
            customer.customer_category_no = it.customerCategoryNo
            customer.shop_type_id = it.shopTypeId
            customer.street_id = it.streetId
            customer.fax = it.fax
            customer.route_schedule_status = it.routeScheduleStatus
            customer.created_user_id = it.createdUserId
            customer.created_date = it.createdDate
            customerEntityList.add(customer)
        }
        db.customerDao().deleteAll()
        db.customerDao().insertAll(customerEntityList)
    }

    override fun saveProductData(productApiList: List<ProductForApi>) {
        val productEntityList = mutableListOf<Product>()
        productApiList.map {
            val product = Product()
            product.id = it.id.toInt()
            product.product_id = it.productId
            product.category_id = it.categoryId
            product.group_id = it.groupId
            product.product_name = it.productName
            product.total_quantity = it.total_Qty
            product.remaining_quantity = it.total_Qty
            product.selling_price = it.sellingPrice
            product.purchase_price = it.purchasePrice
            product.discount_type = it.productTypeId
            product.class_id = it.classId
            product.um = it.umId
            product.device_issue_status = it.deviceIssueStatus.toString()
            productEntityList.add(product)
        }

        db.productDao().deleteAll()
        db.productDao().insertAll(productEntityList)
    }

    override fun saveClassDiscountData(classDiscountList: List<ClassDiscountPriceForApi>) {
        val classDiscountByPriceEntityList = mutableListOf<ClassDiscountByPrice>()
        val classDiscountByPriceItemEntityList = mutableListOf<ClassDiscountByPriceItem>()
        val classDiscountByPriceGiftEntityList = mutableListOf<ClassDiscountByPriceGift>()

        classDiscountList.map {
            val classDiscountByPrice = ClassDiscountByPrice()
            classDiscountByPrice.id = it.id.toInt()
            classDiscountByPrice.class_discount_no = it.classDiscountNo
            classDiscountByPrice.date = it.date
            classDiscountByPrice.start_date = it.startDate
            classDiscountByPrice.end_date = it.endDate
            classDiscountByPrice.sunday = it.sunday
            classDiscountByPrice.monday = it.monday
            classDiscountByPrice.tuesday = it.tuesday
            classDiscountByPrice.wednesday = it.wednesday
            classDiscountByPrice.thursday = it.thursday
            classDiscountByPrice.friday = it.friday
            classDiscountByPrice.saturday = it.saturday
            classDiscountByPrice.s_hour = it.sHour
            classDiscountByPrice.s_minute = it.sMinute
            classDiscountByPrice.s_shift = it.sShif
            classDiscountByPrice.e_hour = it.eHour
            classDiscountByPrice.e_minute = it.eMinute
            classDiscountByPrice.e_shift = it.eShif
            classDiscountByPrice.discount_type = it.discountType
            classDiscountByPrice.active = it.active
            classDiscountByPrice.created_date = it.createdDate
            classDiscountByPrice.created_user_id = it.createdUserID
            classDiscountByPrice.updated_date = it.updatedDate
            classDiscountByPrice.updated_user_id = it.updatedUserID
            classDiscountByPrice.timestamp = it.ts

            classDiscountByPriceEntityList.add(classDiscountByPrice)

            it.classDiscountByPriceItems.map { items ->
                val classDiscountByPriceItem = ClassDiscountByPriceItem()
                classDiscountByPriceItem.id = it.id.toInt()
                classDiscountByPriceItem.class_discount_id = items.classDiscountId.toInt()
                classDiscountByPriceItem.class_id = items.classId
                classDiscountByPriceItem.from_quantity = items.fromQuantity
                classDiscountByPriceItem.to_quantity = items.toQuantity
                classDiscountByPriceItem.from_amount = items.fromAmount
                classDiscountByPriceItem.to_amount = items.toAmount
                classDiscountByPriceItem.discount_percent = items.discountPercent
                classDiscountByPriceItem.active = items.active
                classDiscountByPriceItem.created_date = items.createdDate
                classDiscountByPriceItem.created_user_id = items.createdUserID
                classDiscountByPriceItem.updated_date = items.updatedDate
                classDiscountByPriceItem.updated_user_id = items.updatedUserID
                classDiscountByPriceItem.timestamp = items.ts
                classDiscountByPriceItemEntityList.add(classDiscountByPriceItem)
            }

            it.classDiscountByPriceGifts.map { gifts ->
                val classDiscountByPriceGift = ClassDiscountByPriceGift()
                classDiscountByPriceGift.id = gifts.id.toInt()
                classDiscountByPriceGift.class_discount_id = gifts.classDiscountId
                classDiscountByPriceGift.stock_id = gifts.stockId
                classDiscountByPriceGift.quantity = gifts.quantity
                classDiscountByPriceGift.active = gifts.active
                classDiscountByPriceGift.created_date = gifts.createdDate
                classDiscountByPriceGift.created_user_id = gifts.createdUserID
                classDiscountByPriceGift.updated_date = gifts.updatedDate
                classDiscountByPriceGift.updated_user_id = gifts.updatedUserID
                classDiscountByPriceGift.timestamp = gifts.ts

                classDiscountByPriceGiftEntityList.add(classDiscountByPriceGift)
            }
        }


        db.classDiscountByPriceDao().deleteAll()
        db.classDiscountByPriceItemDao().deleteAll()
        db.classDiscountByPriceGiftDao().deleteAll()

        db.classDiscountByPriceDao().insertAll(classDiscountByPriceEntityList)
        db.classDiscountByPriceItemDao().insertAll(classDiscountByPriceItemEntityList)
        db.classDiscountByPriceGiftDao().insertAll(classDiscountByPriceGiftEntityList)

    }

    override fun saveClassDiscountForShowData(classDiscountForShowList: List<ClassDiscountDataForShow>) {
        val classDiscountForShowEntityList = mutableListOf<ClassDiscountForShow>()
        val classDiscountForShowItemEntityList = mutableListOf<ClassDiscountForShowItem>()
        val classDiscountForShowGiftEntityList = mutableListOf<ClassDiscountForShowGift>()

        classDiscountForShowList.map {
            val classDiscountForShow = ClassDiscountForShow()
            classDiscountForShow.id = it.id.toInt()
            classDiscountForShow.class_discount_no = it.classDiscountNo
            classDiscountForShow.date = it.date
            classDiscountForShow.start_date = it.startDate
            classDiscountForShow.end_date = it.endDate
            classDiscountForShow.sunday = it.sunday
            classDiscountForShow.monday = it.monday
            classDiscountForShow.thursday = it.thursday
            classDiscountForShow.wednesday = it.wednesday
            classDiscountForShow.thursday = it.thursday
            classDiscountForShow.friday = it.friday
            classDiscountForShow.saturday = it.saturday
            classDiscountForShow.s_hour = it.sHour
            classDiscountForShow.s_minute = it.sMinute
            classDiscountForShow.s_shift = it.sShif
            classDiscountForShow.e_hour = it.eHour
            classDiscountForShow.e_minute = it.eMinute
            classDiscountForShow.e_shift = it.eShif
            classDiscountForShow.discount_type = it.discountType
            classDiscountForShow.active = it.active
            classDiscountForShow.created_date = it.createdDate
            classDiscountForShow.created_user_id = it.createdUserID
            classDiscountForShow.updated_date = it.updatedDate
            classDiscountForShow.updated_user_id = it.updatedUserID
            classDiscountForShow.timestamp = it.ts

            classDiscountForShowEntityList.add(classDiscountForShow)

            it.classDiscountByPriceItems.map { items ->
                val classDiscountForShowItem = ClassDiscountForShowItem()
                classDiscountForShowItem.id = items.id.toInt()
                classDiscountForShowItem.class_discount_id = items.classDiscountId
                classDiscountForShowItem.class_id = items.classId
                classDiscountForShowItem.from_quantity = items.fromQuantity
                classDiscountForShowItem.to_quantity = items.toQuantity
                classDiscountForShowItem.from_amount = items.fromAmount
                classDiscountForShowItem.to_amount = items.toAmount
                classDiscountForShowItem.discount_percent = items.discountPercent
                classDiscountForShowItem.active = items.active
                classDiscountForShowItem.created_date = items.createdDate
                classDiscountForShowItem.created_user_id = items.createdUserID
                classDiscountForShowItem.updated_date = items.updatedDate
                classDiscountForShowItem.updated_user_id = items.updatedUserID
                classDiscountForShowItem.timestamp = items.ts
                classDiscountForShowItemEntityList.add(classDiscountForShowItem)
            }

            it.classDiscountByPriceGifts.map { gifts ->
                val classDiscountForShowGift = ClassDiscountForShowGift()
                classDiscountForShowGift.id = gifts.id.toInt()
                classDiscountForShowGift.class_discount_id = gifts.classDiscountId
                classDiscountForShowGift.class_id = gifts.classId
                classDiscountForShowGift.quantity = gifts.quantity
                classDiscountForShowGift.active = gifts.active
                classDiscountForShowGift.created_date = gifts.createdDate
                classDiscountForShowGift.created_user_id = gifts.createdUserID
                classDiscountForShowGift.updated_date = gifts.updatedDate
                classDiscountForShowGift.updated_user_id = gifts.updatedUserID
                classDiscountForShowGift.timestamp = gifts.ts

                classDiscountForShowGiftEntityList.add(classDiscountForShowGift)
            }

        }

        db.classDiscountForShowDao().deleteAll()
        db.classDiscountForShowItemDao().deleteAll()
        db.classDiscountForShowGiftDao().deleteAll()

        db.classDiscountForShowDao().insertAll(classDiscountForShowEntityList)
        db.classDiscountForShowItemDao().insertAll(classDiscountForShowItemEntityList)
        db.classDiscountForShowGiftDao().insertAll(classDiscountForShowGiftEntityList)
    }

    override fun savePromotionData(promotionList: List<PromotionForApi>) {
        val promotionDateEntityList = mutableListOf<PromotionDate>()
        val promotionPriceEntityList = mutableListOf<PromotionPrice>()
        val promotionGiftEntityList = mutableListOf<PromotionGift>()
        val promotionGiftItemEntityList = mutableListOf<PromotionGiftItem>()

        promotionList.map { promotion ->
            promotion.promotionDateList.map {
                val promotionDate = PromotionDate()
                promotionDate.id = it.id.toInt()
                promotionDate.promotion_plan_id = it.promotionPlanId
                promotionDate.date = it.date
                promotionDate.promotion_date = it.promotionDate
                promotionDate.process_status = it.processStatus

                promotionDateEntityList.add(promotionDate)
            }

            promotion.promotionPriceList.map {
                val promotionPrice = PromotionPrice()
                promotionPrice.id = it.id.toInt()
                promotionPrice.promotion_plan_id = it.promotionPlanId
                promotionPrice.stock_id = it.stockId
                promotionPrice.from_quantity = it.fromQuantity
                promotionPrice.to_quantity = it.toQuantity
                promotionPrice.promotion_price = it.promotionPrice

                promotionPriceEntityList.add(promotionPrice)
            }

            promotion.promotionGiftList.map {
                val promotionGift = PromotionGift()
                promotionGift.id = it.id.toInt()
                promotionGift.promotion_plan_id = it.promotionPlanId
                promotionGift.stock_id = it.stockId
                promotionGift.from_quantity = it.fromQuantity
                promotionGift.to_quantity = it.toQuantity

                promotionGiftEntityList.add(promotionGift)
            }

            promotion.promotionGiftItemList.map {
                val promotionGiftItem = PromotionGiftItem()
                promotionGiftItem.id = it.id.toInt()
                promotionGiftItem.promotion_plan_id = it.promotionPlanId
                promotionGiftItem.stock_id = it.stockId
                promotionGiftItem.quantity = it.quantity

                promotionGiftItemEntityList.add(promotionGiftItem)
            }


        }


        db.promotionDateDao().deleteAll()
        db.promotionPriceDao().deleteAll()
        db.promotionGiftDao().deleteAll()
        db.promotionGiftItemDao().deleteAll()

        db.promotionDateDao().insertAll(promotionDateEntityList)
        db.promotionPriceDao().insertAll(promotionPriceEntityList)
        db.promotionGiftDao().insertAll(promotionGiftEntityList)
        db.promotionGiftItemDao().insertAll(promotionGiftItemEntityList)
    }

    override fun saveVolumeDiscountData(volumeDiscountList: List<DataForVolumeDiscount>) {
        val volumeDiscountEntityList = mutableListOf<VolumeDiscount>()
        val volumeDiscountItemEntityList = mutableListOf<VolumeDiscountItem>()
        val volumeDiscountFilterEntityList = mutableListOf<VolumeDiscountFilter>()
        val volumeDiscountFilterItemEntityList = mutableListOf<VolumeDiscountFilterItem>()

        volumeDiscountList.map { discount ->
            discount.volumeDiscount.map {
                val volumeDiscount = VolumeDiscount()
                volumeDiscount.id = it.id.toInt()
                volumeDiscount.discount_plan_no = it.discountPlanNo
                volumeDiscount.date = it.date
                volumeDiscount.start_date = it.startDate
                volumeDiscount.end_date = it.endDate
                volumeDiscount.exclude = it.exclude

                volumeDiscountEntityList.add(volumeDiscount)

                it.volumeDiscountItem.map { item ->
                    val volumeDiscountItem = VolumeDiscountItem()
                    volumeDiscountItem.id = item.id.toInt()
                    volumeDiscountItem.volume_discount_id = item.volumeDiscountId
                    volumeDiscountItem.from_sale_amount = item.fromSaleAmt
                    volumeDiscountItem.to_sale_amount = item.toSaleAmt
                    volumeDiscountItem.discount_percent = item.discountPercent
                    volumeDiscountItem.discount_amount = item.discountAmount
                    volumeDiscountItem.discount_price = item.discountPrice

                    volumeDiscountItemEntityList.add(volumeDiscountItem)
                }

            }

            discount.volumeDiscountFilter.map {
                val volumeDiscountFilter = VolumeDiscountFilter()
                volumeDiscountFilter.id = it.id.toInt()
                volumeDiscountFilter.discount_plan_no = it.discountPlanNo
                volumeDiscountFilter.date = it.date
                volumeDiscountFilter.start_date = it.startDate
                volumeDiscountFilter.end_date = it.endDate
                volumeDiscountFilter.exclude = it.exclude

                volumeDiscountFilterEntityList.add(volumeDiscountFilter)

                it.volumediscountfilterItem.map { item ->
                    val volumeDiscountFilterItem = VolumeDiscountFilterItem()
                    volumeDiscountFilterItem.id = item.id.toInt()
                    volumeDiscountFilterItem.volume_discount_id = item.volumeDiscountId
                    volumeDiscountFilterItem.category_id = item.categoryId
                    volumeDiscountFilterItem.group_code_id = item.groupCodeId
                    volumeDiscountFilterItem.from_sale_amount = item.fromSaleAmount
                    volumeDiscountFilterItem.to_sale_amount = item.toSaleAmount
                    volumeDiscountFilterItem.discount_percent = item.discountPercent
                    volumeDiscountFilterItem.discount_amount = item.discountAmount
                    volumeDiscountFilterItem.discount_price = item.discountPrice

                    volumeDiscountFilterItemEntityList.add(volumeDiscountFilterItem)
                }
            }

        }
        db.volumeDiscountDao().deleteAll()
        db.volumeDiscountItemDao().deleteAll()
        db.volumeDiscountFilterDao().deleteAll()
        db.volumeDiscountFilterItemDao().deleteAll()

        db.volumeDiscountDao().insertAll(volumeDiscountEntityList)
        db.volumeDiscountItemDao().insertAll(volumeDiscountItemEntityList)
        db.volumeDiscountFilterDao().insertAll(volumeDiscountFilterEntityList)
        db.volumeDiscountFilterItemDao().insertAll(volumeDiscountFilterItemEntityList)
    }

    override fun saveDiscountCategoryQuantityData(discountCategoryQuantityList: List<DataForDiscountCategoryQuantity>) {
        val tDiscountByCategoryQuantityEntityList = mutableListOf<TDiscountByCategoryQuantity>()
        val tDiscountByCategoryQuantityItemEntityList =
            mutableListOf<TDiscountByCategoryQuantityItem>()
        discountCategoryQuantityList.map { data ->
            data.tDiscountByCategoryQuantity.map {
                val tDiscountByCategoryQuantity = TDiscountByCategoryQuantity()
                tDiscountByCategoryQuantity.id = it.id.toInt()
                tDiscountByCategoryQuantity.promotion_plan_no = it.promotionPlanNo
                tDiscountByCategoryQuantity.date = it.date
                tDiscountByCategoryQuantity.start_date = it.startDate
                tDiscountByCategoryQuantity.end_date = it.endDate
                tDiscountByCategoryQuantity.category_id = it.categoryId

                tDiscountByCategoryQuantityEntityList.add(tDiscountByCategoryQuantity)

                it.tDiscountByCategoryQuantityItem.map { item ->
                    val tDiscountByCategoryQuantityItem = TDiscountByCategoryQuantityItem()
                    tDiscountByCategoryQuantityItem.id = item.id.toInt()
                    tDiscountByCategoryQuantityItem.t_promotion_plan_id = item.tPromotionPlanId
                    tDiscountByCategoryQuantityItem.stock_id = item.stockId
                    tDiscountByCategoryQuantityItem.from_quantity = item.fromQuantity
                    tDiscountByCategoryQuantityItem.to_quantity = item.toQuantity
                    tDiscountByCategoryQuantityItem.discount_percent = item.discountPercent
                    tDiscountByCategoryQuantityItem.discount_amount = item.discountAmount

                    tDiscountByCategoryQuantityItemEntityList.add(tDiscountByCategoryQuantityItem)
                }
            }
        }

        db.tDiscountByCategoryQuantityDao().deleteAll()
        db.tDiscountByCategoryQuantityItemDao().deleteAll()

        db.tDiscountByCategoryQuantityDao().insertAll(tDiscountByCategoryQuantityEntityList)
        db.tDiscountByCategoryQuantityItemDao().insertAll(tDiscountByCategoryQuantityItemEntityList)
    }

    override fun saveGeneralData(generalDataList: List<GeneralData>) {
        val classOfProductEnitiyList = mutableListOf<Class>()
        val districtEnitiyList = mutableListOf<District>()
        val groupCodeEnityList = mutableListOf<GroupCode>()
        val productTypeEnitiyList = mutableListOf<ProductType>()
        val stateDivisionEnitiyList = mutableListOf<StateDivision>()
        val townshipEntityList = mutableListOf<Township>()
        val streetEntityList = mutableListOf<Street>()
        val umEntityList = mutableListOf<UM>()
        val locationEntityList = mutableListOf<Location>()
        val customerFeedbackEntityList = mutableListOf<CustomerFeedback>()
        val currencyEntityList = mutableListOf<Currency>()
        val productCategoryEntityList = mutableListOf<ProductCategory>()

        generalDataList.map { data ->
            data.class_.map {
                val classEntity = Class()
                classEntity.id = it.id
                classEntity.name = it.name
                classEntity.class_id = it.classId

                classOfProductEnitiyList.add(classEntity)
            }
            data.district.map {
                val districtEntity = District()
                districtEntity.id = it.id.toInt()

                districtEntity.name = it.name

                districtEnitiyList.add(districtEntity)
            }
            data.groupCode.map {
                val groupCodeEntity = GroupCode()
                groupCodeEntity.id = it.id.toInt()
                groupCodeEntity.name = it.name
                groupCodeEntity.group_no = it.groupNo

                groupCodeEnityList.add(groupCodeEntity)
            }
            data.productType.map {
                val productTypeEntity = ProductType()
                productTypeEntity.id = it.id
                productTypeEntity.description = it.description

                productTypeEnitiyList.add(productTypeEntity)
            }
            data.stateDivision.map {
                val stateDivisionEntity = StateDivision()
                stateDivisionEntity.id = it.id.toInt()
                stateDivisionEntity.name = it.name

                stateDivisionEnitiyList.add(stateDivisionEntity)
            }
            data.township.map {
                val townshipEntity = Township()
                townshipEntity.id = it.townshipId.toInt()
                townshipEntity.township_name = it.townshipName

                townshipEntityList.add(townshipEntity)
            }
            data.streets.map {
                val streetEntity = Street()
                streetEntity.id = it.id.toInt()
                streetEntity.street_id = it.streetId
                streetEntity.street_name = it.streetName

                streetEntityList.add(streetEntity)
            }

            data.um.map {
                val umEntity = UM()
                umEntity.id = it.id.toInt()
                umEntity.name = it.name
                umEntity.code = it.code

                umEntityList.add(umEntity)
            }
            data.location.map {
                val locationEntity = Location()
                locationEntity.location_id = it.locationId
                locationEntity.location_no = it.locationNo
                locationEntity.location_name = it.locationName
                locationEntity.branch_id = it.branchId
                locationEntity.branch_name = it.branchNo

                locationEntityList.add(locationEntity)
            }
            data.customerFeedbacks.map {
                val customerFeedbackEntity = CustomerFeedback()
                customerFeedbackEntity.id = it.id.toInt()
                customerFeedbackEntity.invoice_no = it.invoiceNo
                customerFeedbackEntity.invoice_date = it.invoiceDate
                customerFeedbackEntity.remark = it.remark

                customerFeedbackEntityList.add(customerFeedbackEntity)
            }
            data.currencyList.map {
                val currencyEntity = Currency()
                currencyEntity.id = it.id
                currencyEntity.currency = it.currency
                currencyEntity.description = it.description
                currencyEntity.coupon_status = it.cuponStatus

                currencyEntityList.add(currencyEntity)
            }
            data.productCategory.map {
                val productCategoryEntity = ProductCategory()
                productCategoryEntity.category_id = it.category_Id
                productCategoryEntity.category_no = it.category_no
                productCategoryEntity.category_name = it.category_Name

                productCategoryEntityList.add(productCategoryEntity)
            }
        }

        db.classDao().deleteAll()
        db.districtDao().deleteAll()
        db.groupCodeDao().deleteAll()
        db.productTypeDao().deleteAll()
        db.stateDivisionDao().deleteAll()
        db.townshipDao().deleteAll()
        db.streetDao().deleteAll()
        db.umDao().deleteAll()
        db.locationDao().deleteAll()
        db.customerFeedbackDao().deleteAll()
        db.currencyDao().deleteAll()
        db.productCategoryDao().deleteAll()

        db.classDao().insertAll(classOfProductEnitiyList)
        db.districtDao().insertAll(districtEnitiyList)
        db.groupCodeDao().insertAll(groupCodeEnityList)
        db.productTypeDao().insertAll(productTypeEnitiyList)
        db.stateDivisionDao().insertAll(stateDivisionEnitiyList)
        db.townshipDao().insertAll(townshipEntityList)
        db.streetDao().insertAll(streetEntityList)
        db.umDao().insertAll(umEntityList)
        db.locationDao().insertAll(locationEntityList)
        db.customerFeedbackDao().insertAll(customerFeedbackEntityList)
        db.currencyDao().insertAll(currencyEntityList)
        db.productCategoryDao().insertAll(productCategoryEntityList)

    }

    override fun savePosmAndShopTypeData(posmAndShopTypeList: List<PosmShopTypeForApi>) {
        val posmEntityList = mutableListOf<POSM>()
        val shopTypeEntityList = mutableListOf<ShopType>()

        posmAndShopTypeList.map { posmAndShopType ->
            posmAndShopType.posmForApiList.map {
                val posmEntity = POSM()
                posmEntity.id = it.id.toInt()
                posmEntity.invoice_no = it.invoiceNo
                posmEntity.invoice_date = it.invoiceDate
                posmEntity.shop_type_id = it.shopTypeId
                posmEntity.stock_id = it.stockId

                posmEntityList.add(posmEntity)
            }

            posmAndShopType.shopTypeForApiList.map {
                val shopTypeEntity = ShopType()
                shopTypeEntity.id = it.id.toInt()
                shopTypeEntity.shop_type_no = it.shopTypeNo
                shopTypeEntity.shop_type_name = it.shopTypeName

                shopTypeEntityList.add(shopTypeEntity)
            }
        }

        db.posmDao().deleteAll()
        db.shopTypeDao().deleteAll()

        db.posmDao().insertAll(posmEntityList)
        db.shopTypeDao().insertAll(shopTypeEntityList)
    }

    override fun saveDeliveryData(deliveryList: List<DataForDelivery>) {
        val deliveryEntityList = mutableListOf<Delivery>()
        val deliveryItemEntityList = mutableListOf<DeliveryItem>()
        val deliveryPresentEntityList = mutableListOf<DeliveryPresent>()
        deliveryList.map { data ->
            data.deliveryForApiList.map { delivery ->

                val deliveryEntity = Delivery()
                deliveryEntity.id = delivery.id.toInt()
                deliveryEntity.invoice_no = delivery.invoiceNo
                deliveryEntity.invoice_date = delivery.invoiceDate
                deliveryEntity.customer_id = delivery.customerId
                deliveryEntity.amount = delivery.amount
                deliveryEntity.paid_amount = delivery.paidAmount
                deliveryEntity.expire_date = delivery.expDate
                deliveryEntity.sale_man_id = delivery.saleManId
                deliveryEntity.discount = delivery.discount
                deliveryEntity.discount_percent = delivery.discountPercent
                deliveryEntity.remark = delivery.remark

                deliveryEntityList.add(deliveryEntity)

                delivery.deliveryItemForApiList.map {
                    val deliveryItemEntity = DeliveryItem()
                    deliveryItemEntity.id = it.id.toInt()
                    deliveryItemEntity.delivery_id = it.saleOrderId
                    deliveryItemEntity.stock_id = it.stockNo
                    deliveryItemEntity.order_quantity = it.orderQty
                    deliveryItemEntity.received_quantity = it.receiveQty
                    deliveryItemEntity.s_price = it.sPrice
                    deliveryItemEntity.foc_status = it.focStatus

                    deliveryItemEntityList.add(deliveryItemEntity)
                }

                delivery.deliveryPresentForApiList.map {
                    val deliveryPresentEntity = DeliveryPresent()
                    deliveryPresentEntity.id = it.id.toInt()
                    deliveryPresentEntity.sale_order_id = it.saleOrderId
                    deliveryPresentEntity.stock_id = it.stockId
                    deliveryPresentEntity.quantity = it.quantity

                    deliveryPresentEntityList.add(deliveryPresentEntity)
                }

            }
        }

        db.deliveryDao().deleteAll()
        db.deliveryItemDao().deleteAll()
        db.deliveryPresentDao().deleteAll()

        db.deliveryDao().insertAll(deliveryEntityList)
        db.deliveryItemDao().insertAll(deliveryItemEntityList)
        db.deliveryPresentDao().insertAll(deliveryPresentEntityList)
    }

    override fun saveCreditData(creditList: List<DataForCredit>) {
        val creditEntityList = mutableListOf<Credit>()

        val customerBalanceEntityList = mutableListOf<CustomerBalance>()

        creditList.map { credit ->
            credit.creditForApiList.map {
                val creditEntity = Credit()
                creditEntity.id = it.id
                creditEntity.invoice_no = it.invoiceNo
                creditEntity.invoice_date = it.invoiceDate
                creditEntity.customer_id = it.customerId
                creditEntity.amount = it.amount
                creditEntity.pay_amount = it.payAmount
                creditEntity.first_pay_amount = it.firstPayAmount
                creditEntity.extra_amount = it.extraAmount
                creditEntity.refund = it.refund
                creditEntity.sale_status = it.saleStatus
                creditEntity.invoice_status = it.invoiceStatus
                creditEntity.sale_man_id = it.saleManId

                creditEntityList.add(creditEntity)
            }

            credit.customerBalanceList.map {
                val customerBalanceEntity = CustomerBalance()
                customerBalanceEntity.id = it.id
                customerBalanceEntity.customer_id = it.customerId
                customerBalanceEntity.currency_id = it.currencyId
                customerBalanceEntity.opening_balance = it.openingBalance
                customerBalanceEntity.balance = it.balance

                customerBalanceEntityList.add(customerBalanceEntity)
            }
        }


        db.creditDao().deleteAll()
        db.customerBalanceDao().deleteAll()

        db.creditDao().insertAll(creditEntityList)
        db.customerBalanceDao().insertAll(customerBalanceEntityList)
    }

    override fun saveCustomerVisitData(customerVisitList: List<CustomerVisitRequestData>) {
        val saleVisitEntityList = mutableListOf<SaleVisitRecordDownload>()

        customerVisitList.map { data ->
            data.saleVisitRecordList.map {
                val saleVisitEntity = SaleVisitRecordDownload()
                saleVisitEntity.customer_id = it.customerId
                saleVisitEntity.sale_man_id = it.salemanId
                saleVisitEntity.latitude = it.latitude
                saleVisitEntity.longitude = it.longitude
                saleVisitEntity.visit_flag = it.visitFlg.toInt()
                saleVisitEntity.sale_flag = it.saleFlg.toInt()
                saleVisitEntity.record_date = it.recordDate

                saleVisitEntityList.add(saleVisitEntity)
            }
        }
        db.saleVisitRecordDownloadDao().deleteAll()
        db.saleVisitRecordDownloadDao().insertAll(saleVisitEntityList)
    }

    override fun saveCompanyData(companyInfoList: List<CompanyInfromationData>) {
        val companyInfoEntityList = mutableListOf<CompanyInformation>()

        companyInfoList.map { data ->
            data.companyInformation.map {
                val companyInformationEntity = CompanyInformation()
                companyInformationEntity.id = it.id
                companyInformationEntity.description = it.description
                companyInformationEntity.main_db_name = it.mainDBName
                companyInformationEntity.home_currency_id = it.homeCurrencyId
                companyInformationEntity.multi_currency = it.multiCurrency
                companyInformationEntity.start_date = it.startDate
                companyInformationEntity.end_date = it.endDate
                companyInformationEntity.auto_generate = it.autoGenerate
                companyInformationEntity.company_name = it.companyName
                companyInformationEntity.short_name = it.shortName
                companyInformationEntity.contact_person = it.contactPerson
                companyInformationEntity.address = it.address
                companyInformationEntity.email = it.email
                companyInformationEntity.website = it.website
                companyInformationEntity.serial_number = it.serialNumber
                companyInformationEntity.phone_number = it.phoneNumber
                companyInformationEntity.separator = it.isSeparator
                companyInformationEntity.amount_format = it.amountFormat
                companyInformationEntity.price_format = it.priceFormat
                companyInformationEntity.quantity_format = it.quantityFormat
                companyInformationEntity.rate_format = it.rateFormat
                companyInformationEntity.valuation_method = it.valuationMethod
                companyInformationEntity.font = it.font
                companyInformationEntity.report_font = it.reportFont
                companyInformationEntity.receipt_voucher = it.receiptVoucher
                companyInformationEntity.print_port = it.prnPort
                companyInformationEntity.pos_voucher_footer1 = it.posVoucherFooter1
                companyInformationEntity.pos_voucher_footer2 = it.posVoucherFooter2
                companyInformationEntity.stock_auto_generate = it.isStockAutoGenerate
                companyInformationEntity.pc_count = it.pcCount
                companyInformationEntity.expired_month = it.expiredMonth
                companyInformationEntity.paid_status = it.paidstatus
                companyInformationEntity.h1 = it.h1
                companyInformationEntity.h2 = it.h2
                companyInformationEntity.h3 = it.h3
                companyInformationEntity.h4 = it.h4
                companyInformationEntity.f1 = it.f1
                companyInformationEntity.f2 = it.f2
                companyInformationEntity.f3 = it.f3
                companyInformationEntity.f4 = it.f4
                companyInformationEntity.tax = it.tax
                companyInformationEntity.branch_code = it.branchCode
                companyInformationEntity.branch_name = it.branchName
                companyInformationEntity.hb_code = it.hbCode
                companyInformationEntity.credit_sale = it.creditSale
                companyInformationEntity.use_combo = it.useCombo
                companyInformationEntity.last_day_close_date = it.lastDayCloseDate
                companyInformationEntity.last_update_invoice_date = it.lastUpdateInvoiceDate
                companyInformationEntity.company_type = it.companyType
                companyInformationEntity.company_code = it.companyCode
                companyInformationEntity.start_time = it.startTime
                companyInformationEntity.end_time = it.endTime
                companyInformationEntity.branch_id = it.branchId
                companyInformationEntity.print_copy = it.printCopy
                companyInformationEntity.tax_type = it.taxType
                companyInformationEntity.balance_control = it.balanceControl
                companyInformationEntity.transaction_auto_generate = it.transactionAutoGenerate
                companyInformationEntity.company_tax_reg_no = it.commonTaxRegNo

                companyInfoEntityList.add(companyInformationEntity)
            }
        }

        db.companyInformationDao().deleteAll()
        db.companyInformationDao().insertAll(companyInfoEntityList)

    }

    override fun saveSaleTargetData(saleTargetList: List<DataForSaleTarget>) {
        val saleTargetForSaleManEntityList = mutableListOf<SaleTargetSaleMan>()
        val saleTargetForCustomerEntityList = mutableListOf<SaleTargetCustomer>()

        saleTargetList.map { saleTarget ->
            saleTarget.saleTargetForSaleManList.map {
                val saleTargetSaleMan = SaleTargetSaleMan()
                saleTargetSaleMan.id = it.id.toInt()
                saleTargetSaleMan.from_date = it.fromDate
                saleTargetSaleMan.to_date = it.toDate
                saleTargetSaleMan.sale_man_id = it.saleManId
                saleTargetSaleMan.category_id = it.categoryId
                saleTargetSaleMan.group_code_id = it.groupCodeId
                saleTargetSaleMan.stock_id = it.stockId
                saleTargetSaleMan.target_amount = it.targetAmount
                saleTargetSaleMan.date = it.date
                saleTargetSaleMan.day = it.day
                saleTargetSaleMan.date_status = it.dateStatus
                saleTargetSaleMan.invoice_no = it.invoiceNo

                saleTargetForSaleManEntityList.add(saleTargetSaleMan)
            }

            saleTarget.saleTargetForCustomerList.map {
                val saleTargetCustomer = SaleTargetCustomer()
                saleTargetCustomer.id = it.id.toInt()
                saleTargetCustomer.from_date = it.fromDate
                saleTargetCustomer.to_date = it.toDate
                saleTargetCustomer.customer_id = it.customerId
                saleTargetCustomer.sale_man_id = it.saleManId
                saleTargetCustomer.category_id = it.categoryId
                saleTargetCustomer.group_code_id = it.groupCodeId
                saleTargetCustomer.stock_id = it.stockId
                saleTargetCustomer.target_amount = it.targetAmount
                saleTargetCustomer.date = it.date
                saleTargetCustomer.day = it.day
                saleTargetCustomer.date_status = it.dateStatus
                saleTargetCustomer.invoice_no = it.invoiceNo

                saleTargetForCustomerEntityList.add(saleTargetCustomer)
            }
        }


        db.saleTargetSaleManDao().deleteAll()
        db.saleTargetCustomerDao().deleteAll()

        db.saleTargetSaleManDao().insertAll(saleTargetForSaleManEntityList)
        db.saleTargetCustomerDao().insertAll(saleTargetForCustomerEntityList)
    }

    override fun saveSaleHistoryData(saleHistoryList: List<DataForSaleHistory>) {
        val saleHistoryEntityList = mutableListOf<Invoice>()
        val saleHistoryDetailEntityList = mutableListOf<InvoiceProduct>()

        saleHistoryList.map { data ->
            data.saleHistoryList.map { saleHistory ->
                val invoice = Invoice()
                invoice.invoice_id = saleHistory.id
                invoice.sale_date = saleHistory.date
                invoice.customer_id = saleHistory.customerId
                invoice.total_amount = saleHistory.totalAmt
                invoice.pay_amount = saleHistory.totalPayAmt
                invoice.refund_amount = saleHistory.totalRefundAmt
                invoice.sale_person_id = saleHistory.salepersonId
                invoice.location_code = saleHistory.locationCode
                invoice.cash_or_credit = saleHistory.invoiceStatus
                invoice.device_id = saleHistory.deviceId
                invoice.sale_flag = 1

                saleHistoryEntityList.add(invoice)

                saleHistory.saleHistoryDetailList.map {
                    val invoiceProduct = InvoiceProduct()
                    invoiceProduct.invoice_product_id = it.gettSaleId()
                    invoiceProduct.product_id = it.productId
                    invoiceProduct.sale_quantity = it.qty
                    invoiceProduct.discount_amount = it.discountAmt

                    saleHistoryDetailEntityList.add(invoiceProduct)
                }
            }
        }

        db.invoiceDao().deleteAll()
        db.invoiceProductDao().deleteAll()
        db.invoiceDao().insertAll(saleHistoryEntityList)
        db.invoiceProductDao().insertAll(saleHistoryDetailEntityList)
    }

    override fun saveIncentiveData(incentiveList: List<DataForIncentive>) {
        val incentiveEntityList = mutableListOf<Incentive>()
        val incentiveItemEntityList = mutableListOf<IncentiveItem>()

        incentiveList.map { data ->
            data.incentiveList.map {
                val incentive = Incentive()
                incentive.id = it.id
                incentive.invoice_no = it.invoiceNo
                incentive.invoice_date = it.invoiceDate
                incentive.standard_external_check_id = it.standardExternalCheckId
                incentive.outlet_visibility_id = it.outletVisibilityId
                incentive.customer_id = it.customerId
                incentive.sale_man_id = it.saleManId
                incentive.incentive_program_name = it.incentiveProgramName

                incentiveEntityList.add(incentive)

                it.displayProgramItem.map { item ->
                    val incentiveItem = IncentiveItem()
                    incentiveItem.id = item.id
                    incentiveItem.display_program_id = item.displayProgramId
                    incentiveItem.incentive_id = item.incentiveId
                    incentiveItem.stock_id = item.stockId
                    incentiveItem.quantity = item.quantity
                    incentiveItem.price = item.price

                    incentiveItemEntityList.add(incentiveItem)
                }
            }
        }
        db.incentiveDao().deleteAll()
        db.incentiveItemDao().deleteAll()

        db.incentiveDao().insertAll(incentiveEntityList)
        db.incentiveItemDao().insertAll(incentiveItemEntityList)
    }

    override fun savePreOrderData(preOrderList: List<DataForPreOrderHistory>) {
        val preOrderEntityList = mutableListOf<PreOrder>()
        val preOrderItemEntityList = mutableListOf<PreOrderProduct>()

        preOrderList.map { data ->
            data.preOrderHistoryForApiList.map {
                val preOrder = PreOrder()
                preOrder.invoice_id = it.invoiceNo
                preOrder.customer_id = it.customerId
                preOrder.sale_man_id = it.saleManId
                preOrder.dev_id = ""
                preOrder.pre_order_date = it.invoiceDate
                preOrder.expected_delivery_date = it.deliveryDate
                preOrder.advance_payment_amount = it.paidAmount
                preOrder.net_amount = it.amount
                preOrder.location_id = it.locationId
                preOrder.discount = it.discount
                preOrder.discount_percent = it.discountPer
                preOrder.tax_amount = it.taxAmount
                preOrder.remark = it.remark
                preOrder.bank_name = it.cardCodeId
                preOrder.bank_account_no = it.cardNo
                preOrder.delete_flag = 1
                preOrder.sale_flag = 1
                //todo if you want to do samparoo

                preOrderEntityList.add(preOrder)

                it.itemHistoryList.map { product ->
                    val preOrderProduct = PreOrderProduct()
                    preOrderProduct.sale_order_id = product.saleOrderId
                    preOrderProduct.product_id = product.stockId
                    preOrderProduct.order_quantity = product.orderQty
                    preOrderProduct.price = product.getsPrice()
                    preOrderProduct.total_amount = 0.0
                    preOrderProduct.promotion_price = product.promotionPrice
                    preOrderProduct.volume_discount = product.volumeDiscount
                    preOrderProduct.volume_discount_percent = product.volumeDiscountPer
                    preOrderProduct.delete_flag = 1
                    preOrderProduct.exclude = product.exclude
                    preOrderProduct.promotion_plan_id = product.promotionPlanId

                    preOrderItemEntityList.add(preOrderProduct)
                }
            }
        }

        db.preOrderDao().deleteAll()
        db.preOrderProductDao().deleteAll()
        db.preOrderPresentDao().deleteAll()

        db.preOrderDao().insertAll(preOrderEntityList)
        db.preOrderProductDao().insertAll(preOrderItemEntityList)
    }


    override fun deleteAllData() {
        db.clearAllTables()//todo check clear table ( CLASS & PRODUCT )
    }

    override fun deleteProductData() {
        db.productDao().deleteAll()
    }

    override fun uploadCustomer(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadCustomer(paramData)
    }

    override fun uploadExistingCustomer(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadExistingCustomer(paramData)
    }

    override fun uploadSaleInvoice(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadSaleInvoice(paramData)
    }

    override fun uploadPreOrder(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadPreOrderData(paramData)
    }

    override fun uploadIndirectPreOrder(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadIndirectPreOrderData(paramData)
    }

    override fun uploadSaleReturn(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadSaleReturn(paramData)
    }

    override fun uploadPosmByCustomer(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadPosmByCustomer(paramData)
    }

    override fun uploadDelivery(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadDelivery(paramData)
    }

    override fun uploadCashReceive(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadCashReceive(paramData)
    }

    override fun uploadDisplayAssessment(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadDisplayAssessment(paramData)
    }

    override fun uploadCompetitorSizeInStoreShare(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadCompetitorSizeInStore(paramData)
    }

    override fun uploadCustomerVisit(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadCustomerVisit(paramData)
    }

    override fun uploadUnsellReason(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadUnsellReason(paramData)
    }

    override fun uploadIncentive(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadIncentive(paramData)
    }

    override fun uploadSaleCancel(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadSaleCancel(paramData)
    }

    override fun uploadVanIssue(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadVanIssue(paramData)
    }

    override fun uploadCompetitor(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadCompetitor(paramData)
    }

    override fun uploadSaleManRoute(paramData: String): Observable<InvoiceResponse> {
        return uploadApiService.uploadSaleManRoute(paramData)
    }

    override fun updateAllInactiveDataPreOrder() {
        db.preOrderDao().updateAllInactiveData()
        db.preOrderProductDao().updateAllInactiveData()
        db.preOrderPresentDao().updateAllInactiveData()
    }

    override fun updateAllInactiveDataSaleReturn() {
        db.saleReturnDao().updateAllInactiveData()
        db.saleReturnDetailDao().updateAllInactiveData()
    }

    override fun updateAllInactiveDataPosmByCustomer() {
        db.posmByCustomerDao().updateAllInactiveData()
    }

    override fun updateAllInactiveDataDisplayAssessment() {
        db.outletVisibilityDao().updateAllInactiveData()
    }

    override fun updateAllInactiveDataCompetitorSizeInStoreShare() {
        db.sizeInStoreShareDao().updateAllInactiveData()
    }
}