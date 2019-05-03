package com.aceplus.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.aceplus.data.database.dao.*
import com.aceplus.data.database.dao.cash.CashReceiveDao
import com.aceplus.data.database.dao.cash.CashReceiveItemDao
import com.aceplus.data.database.dao.classdiscount.*
import com.aceplus.data.database.dao.competitor.CompetitorActivitiesDao
import com.aceplus.data.database.dao.competitor.CompetitorActivitiesDetailDao
import com.aceplus.data.database.dao.competitor.CompetitorActivityDao
import com.aceplus.data.database.dao.competitor.CompetitorDao
import com.aceplus.data.database.dao.credit.CreditDao
import com.aceplus.data.database.dao.credit.CreditItemDao
import com.aceplus.data.database.dao.customer.*
import com.aceplus.data.database.dao.delivery.*
import com.aceplus.data.database.dao.deviceissue.DeviceIssueRequestDao
import com.aceplus.data.database.dao.deviceissue.DeviceIssueRequestItemDao
import com.aceplus.data.database.dao.incentive.IncentiveDao
import com.aceplus.data.database.dao.incentive.IncentiveItemDao
import com.aceplus.data.database.dao.incentive.IncentivePaidDao
import com.aceplus.data.database.dao.invoice.*
import com.aceplus.data.database.dao.outlet.*
import com.aceplus.data.database.dao.posm.POSMByCustomerDao
import com.aceplus.data.database.dao.posm.POSMDao
import com.aceplus.data.database.dao.predefine.DistrictDao
import com.aceplus.data.database.dao.predefine.StateDivisionDao
import com.aceplus.data.database.dao.predefine.StreetDao
import com.aceplus.data.database.dao.predefine.TownshipDao
import com.aceplus.data.database.dao.preorder.PreOrderDao
import com.aceplus.data.database.dao.preorder.PreOrderPresentDao
import com.aceplus.data.database.dao.preorder.PreOrderProductDao
import com.aceplus.data.database.dao.product.ProductCategoryDao
import com.aceplus.data.database.dao.product.ProductDao
import com.aceplus.data.database.dao.product.ProductGroupDao
import com.aceplus.data.database.dao.product.ProductTypeDao
import com.aceplus.data.database.dao.promotion.*
import com.aceplus.data.database.dao.route.*
import com.aceplus.data.database.dao.sale.SaleChannelDao
import com.aceplus.data.database.dao.sale.SaleManDao
import com.aceplus.data.database.dao.sale.saleexchange.SaleExchangeDao
import com.aceplus.data.database.dao.sale.saleexchange.SaleExchangeDetailDao
import com.aceplus.data.database.dao.sale.salereturn.SaleReturnDao
import com.aceplus.data.database.dao.sale.salereturn.SaleReturnDetailDao
import com.aceplus.data.database.dao.sale.saletarget.SaleTargetCustomerDao
import com.aceplus.data.database.dao.sale.saletarget.SaleTargetSaleManDao
import com.aceplus.data.database.dao.sale.salevisit.SaleVisitRecordDownloadDao
import com.aceplus.data.database.dao.sale.salevisit.SaleVisitRecordUploadDao
import com.aceplus.data.database.dao.sizeinstoreshare.SizeInStoreShareDao
import com.aceplus.data.database.dao.sizeinstoreshare.SizeInStoreShareDetailDao
import com.aceplus.data.database.dao.tdiscount.TDiscountByCategoryQuantityDao
import com.aceplus.data.database.dao.tdiscount.TDiscountByCategoryQuantityItemDao
import com.aceplus.data.database.dao.volumediscount.VolumeDiscountDao
import com.aceplus.data.database.dao.volumediscount.VolumeDiscountFilterDao
import com.aceplus.data.database.dao.volumediscount.VolumeDiscountFilterItemDao
import com.aceplus.data.database.dao.volumediscount.VolumeDiscountItemDao
import com.aceplus.domain.entity.*
import com.aceplus.domain.entity.cash.CashReceive
import com.aceplus.domain.entity.classdiscount.*
import com.aceplus.domain.entity.competitor.Competitor
import com.aceplus.domain.entity.competitor.CompetitorActivities
import com.aceplus.domain.entity.competitor.CompetitorActivitiesDetail
import com.aceplus.domain.entity.competitor.CompetitorActivity
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.entity.credit.CreditItem
import com.aceplus.domain.entity.customer.*
import com.aceplus.domain.entity.delivery.*
import com.aceplus.domain.entity.deviceissue.DeviceIssueRequest
import com.aceplus.domain.entity.deviceissue.DeviceIssueRequestItem
import com.aceplus.domain.entity.incentive.Incentive
import com.aceplus.domain.entity.incentive.IncentiveItem
import com.aceplus.domain.entity.incentive.IncentivePaid
import com.aceplus.domain.entity.invoice.*
import com.aceplus.domain.entity.outlet.*
import com.aceplus.domain.entity.posm.POSM
import com.aceplus.domain.entity.posm.POSMByCustomer
import com.aceplus.domain.entity.predefine.District
import com.aceplus.domain.entity.predefine.StateDivision
import com.aceplus.domain.entity.predefine.Street
import com.aceplus.domain.entity.predefine.Township
import com.aceplus.domain.entity.preorder.PreOrder
import com.aceplus.domain.entity.preorder.PreOrderPresent
import com.aceplus.domain.entity.preorder.PreOrderProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.product.ProductCategory
import com.aceplus.domain.entity.product.ProductGroup
import com.aceplus.domain.entity.product.ProductType
import com.aceplus.domain.entity.promotion.*
import com.aceplus.domain.entity.route.*
import com.aceplus.domain.entity.sale.SaleChannel
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.entity.sale.saleexchange.SaleExchange
import com.aceplus.domain.entity.sale.saleexchange.SaleExchangeDetail
import com.aceplus.domain.entity.sale.salereturn.SaleReturn
import com.aceplus.domain.entity.sale.salereturn.SaleReturnDetail
import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import com.aceplus.domain.entity.sale.salevisit.SaleVisitRecordDownload
import com.aceplus.domain.entity.sale.salevisit.SaleVisitRecordUpload
import com.aceplus.domain.entity.sizeinstoreshare.SizeInStoreShare
import com.aceplus.domain.entity.sizeinstoreshare.SizeInStoreShareDetail
import com.aceplus.domain.entity.tdiscount.TDiscountByCategoryQuantity
import com.aceplus.domain.entity.tdiscount.TDiscountByCategoryQuantityItem
import com.aceplus.domain.entity.volumediscount.VolumeDiscount
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilter
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilterItem
import com.aceplus.domain.entity.volumediscount.VolumeDiscountItem

@Database(
    entities = arrayOf(
        //cash
        CashReceive::class,
        CashReceive::class,

        //class discount
        ClassDiscountByPrice::class,
        ClassDiscountByPriceGift::class,
        ClassDiscountByPriceItem::class,
        ClassDiscountForShow::class,
        ClassDiscountForShowGift::class,
        ClassDiscountForShowItem::class,

        //competitor
        CompetitorActivities::class,
        CompetitorActivitiesDetail::class,
        CompetitorActivity::class,
        Competitor::class,

        //credit
        Credit::class,
        CreditItem::class,

        //customer
        CustomerBalance::class,
        CustomerCategory::class,
        Customer::class,
        CustomerFeedback::class,
        CustomerVisitRecordReport::class,
        DidCustomerFeedback::class,

        //delivery
        Delivery::class,
        DeliveryItem::class,
        DeliveryItemUpload::class,
        DeliveryPresent::class,
        DeliveryUpload::class,

        //device issue
        DeviceIssueRequest::class,
        DeviceIssueRequestItem::class,

        //incentive
        Incentive::class,
        IncentiveItem::class,
        IncentivePaid::class,

        //invoice
        InvoiceCancel::class,
        InvoiceCancelProduct::class,
        Invoice::class,
        InvoicePresent::class,
        InvoiceProduct::class,

        //outlet
        OutletExternalCheck::class,
        OutletExternalCheckDetail::class,
        OutletStockAvailability::class,
        OutletStockAvailabilityDetail::class,
        OutletVisibility::class,

        //posm
        POSMByCustomer::class,
        POSM::class,

        //predefine
        District::class,
        StateDivision::class,
        Street::class,
        Township::class,

        //preorder
        PreOrder::class,
        PreOrderPresent::class,
        PreOrderProduct::class,

        //product
        ProductCategory::class,
        Product::class,
        ProductGroup::class,
        ProductType::class,

        //promotion
        PromotionAmount::class,
        Promotion::class,
        PromotionDate::class,
        PromotionGift::class,
        PromotionGiftItem::class,
        PromotionInvoice::class,
        PromotionPrice::class,

        //route
        RouteAssign::class,
        Route::class,
        RouteSchedule::class,
        RouteScheduleItem::class,
        RouteScheduleItemV2::class,
        RouteScheduleV2::class,
        TempForSaleManRoute::class,

        //sale
        //sale exchange
        SaleExchange::class,
        SaleExchangeDetail::class,
        //sale return
        SaleReturn::class,
        SaleReturnDetail::class,
        //sale target
        SaleTargetCustomer::class,
        SaleTargetSaleMan::class,
        //sale visit
        SaleVisitRecordDownload::class,
        SaleVisitRecordUpload::class,

        SaleChannel::class,
        SaleMan::class,

        //size in store share
        SizeInStoreShare::class,
        SizeInStoreShareDetail::class,

        //t discount
        TDiscountByCategoryQuantity::class,
        TDiscountByCategoryQuantityItem::class,

        //volume discount
        VolumeDiscount::class,
        VolumeDiscountFilter::class,
        VolumeDiscountFilterItem::class,
        VolumeDiscountFilterItem::class,
        VolumeDiscountItem::class,

        Class::class,
        CompanyInformation::class,
        Currency::class,
        GroupCode::class,
        Location::class,
        ShopType::class,
        SMSRecord::class,
        UM::class


    ), version = 1, exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    //    abstract fun annualRevenueDao(): AnnualRevenueDao
//cash
    abstract fun cashReceiveDao(): CashReceiveDao
    abstract fun cashReceiveItemDao(): CashReceiveItemDao

    //class discount
    abstract fun classDiscountByPriceDao(): ClassDiscountByPriceDao
    abstract fun classDiscountByPriceGiftDao(): ClassDiscountByPriceGiftDao
    abstract fun classDiscountByPriceItemDao(): ClassDiscountByPriceItemDao
    abstract fun classDiscountForShowDao(): ClassDiscountForShowDao
    abstract fun classDiscountForShowGiftDao(): ClassDiscountForShowGiftDao
    abstract fun classDiscountForShowItemDao():   ClassDiscountForShowItemDao

    //competitor
    abstract fun competitorActivitiesDao(): CompetitorActivitiesDao
    abstract fun competitorActivitiesDetailDao(): CompetitorActivitiesDetailDao
    abstract fun competitorActivityDao(): CompetitorActivityDao
    abstract fun competitorDao(): CompetitorDao

    //credit
    abstract fun creditDao(): CreditDao
    abstract fun creditItemDao(): CreditItemDao

    //customer
    abstract fun customerBalanceDao(): CustomerBalanceDao
    abstract fun customerCategoryDao(): CustomerCategoryDao
    abstract fun customerDao(): CustomerDao
    abstract fun customerFeedbackDao(): CustomerFeedbackDao
    abstract fun customerVisitRecordReportDao(): CustomerVisitRecordReportDao
    abstract fun didCustomerFeedbackDao():DidCustomerFeedbackDao

    //delivery
    abstract fun deliveryDao(): DeliveryDao
    abstract fun deliveryItemDao(): DeliveryItemDao
    abstract fun deliveryItemUpload(): DeliveryItemUploadDao
    abstract fun deliveryPresentDao(): DeliveryPresentDao
    abstract fun deliveryUploadDao(): DeliveryUploadDao

    //device issue
    abstract fun deviceIssueRequestDao(): DeviceIssueRequestDao
    abstract fun deviceIssueRequestItemDao(): DeviceIssueRequestItemDao

    //incentive
    abstract fun incentiveDao(): IncentiveDao
    abstract fun incentiveItemDao(): IncentiveItemDao
    abstract fun incentivePaidDao(): IncentivePaidDao

    //invoice
    abstract fun invoiceCancelDao(): InvoiceCancelDao
    abstract fun invoiceCancelProductDao(): InvoiceCancelProductDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun invoicePresentDao(): InvoicePresentDao
    abstract fun invoiceProductDao(): InvoiceProductDao

    //outlet
    abstract fun outletExternalCheckDao(): OutletExternalCheckDao
    abstract fun outletExternalCheckDetailDao(): OutletExternalCheckDetailDao
    abstract fun outletStockAvailabilityDao(): OutletStockAvailabilityDao
    abstract fun outletStockAvailabilityDetailDao(): OutletStockAvailabilityDetailDao
    abstract fun outletVisibilityDao(): OutletVisibilityDao

    //posm
    abstract fun posmByCustomerDao(): POSMByCustomerDao
    abstract fun posmDao(): POSMDao

    //predefine
    abstract fun districtDao(): DistrictDao
    abstract fun stateDivisionDao(): StateDivisionDao
    abstract fun streetDao(): StreetDao
    abstract fun townshipDao(): TownshipDao

    //preorder
    abstract fun preOrderDao(): PreOrderDao
    abstract fun preOrderPresentDao(): PreOrderPresentDao
    abstract fun preOrderProductDao(): PreOrderProductDao

    //product
    abstract fun productCategoryDao(): ProductCategoryDao
    abstract fun productDao(): ProductDao
    abstract fun productGroupDao(): ProductGroupDao
    abstract fun productTypeDao(): ProductTypeDao

    //promotion
    abstract fun promotionAmountDao(): PromotionAmountDao
    abstract fun promotionDao(): PromotionDao
    abstract fun promotionDateDao(): PromotionDateDao
    abstract fun promotionGiftDao(): PromotionGiftDao
    abstract fun promotionGiftItemDao(): PromotionGiftItemDao
    abstract fun promotionInvoiceDao():PromotionInvoiceDao
    abstract fun promotionPriceDao():PromotionPriceDao

    //route
    abstract fun routeAssignDao(): RouteAssignDao
    abstract fun routeDao(): RouteDao
    abstract fun routeScheduleDao(): RouteScheduleDao
    abstract fun routeScheduleItemDao(): RouteScheduleItemDao
    abstract fun routeScheduleItemV2Dao(): RouteScheduleItemV2Dao
    abstract fun routeScheduleV2Dao():RouteScheduleV2Dao
    abstract fun tempForSaleManRouteDao():TempForSaleManRouteDao

    //sale
    //sale exchange
    abstract fun saleExchangeDao(): SaleExchangeDao
    abstract fun saleExchangeDetailDao(): SaleExchangeDetailDao
    //sale return
    abstract fun saleReturnDao(): SaleReturnDao
    abstract fun saleReturnDetailDao(): SaleReturnDetailDao
    //sale target
    abstract fun saleTargetCustomerDao(): SaleTargetCustomerDao
    abstract fun saleTargetSaleManDao(): SaleTargetSaleManDao
    //sale visit
    abstract fun saleVisitRecordDownloadDao(): SaleVisitRecordDownloadDao
    abstract fun saleVisitRecordUploadDao(): SaleVisitRecordUploadDao

    abstract fun saleChannelDao(): SaleChannelDao
    abstract fun saleManDao(): SaleManDao

    //size in store share
    abstract fun sizeInStoreShareDao(): SizeInStoreShareDao
    abstract fun sizeInStoreShareDetailDao(): SizeInStoreShareDetailDao

    //t discount
    abstract fun tDiscountByCategoryQuantityDao(): TDiscountByCategoryQuantityDao
    abstract fun tDiscountByCategoryQuantityItemDao(): TDiscountByCategoryQuantityItemDao

    //volume discount
    abstract fun volumeDiscountDao(): VolumeDiscountDao
    abstract fun volumeDiscountFilterDao(): VolumeDiscountFilterDao
    abstract fun volumeDiscountFilterItemDao(): VolumeDiscountFilterItemDao
    abstract fun volumeDiscountItemDao(): VolumeDiscountItemDao

    abstract fun classDao(): ClassDao
    abstract fun companyInformationDao(): CompanyInformationDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun groupCodeDao(): GroupCodeDao
    abstract fun locationDao():LocationDao
    abstract fun shopTypeDao():ShopTypeDao
    abstract fun smsRecordDao():SMSRecordDao
    abstract fun umDao(): UMDao
    companion object {
        private var INSTANCE: MyDatabase? = null
        private val DB_NAME = "myeadb.db"

        fun getInstance(context: Context): MyDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, MyDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}
