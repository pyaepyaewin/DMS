package com.aceplus.data.repoimpl

import android.content.SharedPreferences
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.remote.DownloadApiService
import com.aceplus.data.utils.Constant
import com.aceplus.domain.entity.*
import com.aceplus.domain.entity.classdiscount.*
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.domain.entity.predefine.District
import com.aceplus.domain.entity.predefine.StateDivision
import com.aceplus.domain.entity.predefine.Street
import com.aceplus.domain.entity.predefine.Township
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.product.ProductCategory
import com.aceplus.domain.entity.product.ProductType
import com.aceplus.domain.entity.promotion.PromotionDate
import com.aceplus.domain.entity.promotion.PromotionGift
import com.aceplus.domain.entity.promotion.PromotionGiftItem
import com.aceplus.domain.entity.promotion.PromotionPrice
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.entity.tdiscount.TDiscountByCategoryQuantity
import com.aceplus.domain.entity.tdiscount.TDiscountByCategoryQuantityItem
import com.aceplus.domain.entity.volumediscount.VolumeDiscount
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilter
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilterItem
import com.aceplus.domain.entity.volumediscount.VolumeDiscountItem
import com.aceplus.domain.model.forApi.DataForDiscountCategoryQuantity
import com.aceplus.domain.model.forApi.DiscountCategoryQuantityResponse
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountDataForShow
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountForShowResponse
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountPriceForApi
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountResponse
import com.aceplus.domain.model.forApi.company.CompanyInformationResponse
import com.aceplus.domain.model.forApi.credit.CreditResponse
import com.aceplus.domain.model.forApi.customer.CustomerForApi
import com.aceplus.domain.model.forApi.customer.CustomerResponse
import com.aceplus.domain.model.forApi.customer.CustomerVisitResponse
import com.aceplus.domain.model.forApi.delivery.DeliveryResponse
import com.aceplus.domain.model.forApi.incentive.IncentiveResponse
import com.aceplus.domain.model.forApi.other.GeneralData
import com.aceplus.domain.model.forApi.other.GeneralResponse
import com.aceplus.domain.model.forApi.posm.PosmShopTypeForApi
import com.aceplus.domain.model.forApi.posm.PosmShopTypeResponse
import com.aceplus.domain.model.forApi.preorder.PreOrderHistoryResponse
import com.aceplus.domain.model.forApi.product.ProductForApi
import com.aceplus.domain.model.forApi.product.ProductResponse
import com.aceplus.domain.model.forApi.promotion.PromotionForApi
import com.aceplus.domain.model.forApi.promotion.PromotionResponse
import com.aceplus.domain.model.forApi.sale.salehistory.SaleHistoryResponse
import com.aceplus.domain.model.forApi.sale.saletarget.SaleTargetResponse
import com.aceplus.domain.model.forApi.volumediscount.DataForVolumeDiscount
import com.aceplus.domain.model.forApi.volumediscount.VolumeDiscountResponse
import com.aceplus.domain.repo.SyncRepo
import com.aceplussolutions.rms.constants.AppUtils
import io.reactivex.Observable

class SyncRepoImpl(
        private val downloadApiService: DownloadApiService, private val db: MyDatabase,
        private val shf: SharedPreferences
) : SyncRepo {

    override fun saveStartTime(time: String) {
        AppUtils.saveStringToShp(Constant.START_TIME, time, shf)
    }

    override fun getSaleManData(): SaleMan {
        val saleMan = SaleMan()
        saleMan.id = AppUtils.getIntFromShp(Constant.SALEMAN_ID, shf)
        saleMan.user_id = AppUtils.getStringFromShp(Constant.SALEMAN_NO, shf).toString()
        saleMan.password = AppUtils.getStringFromShp(Constant.SALEMAN_PWD, shf)
        return saleMan
    }

    override fun getRouteScheduleID(): Int {
        val saleManId = AppUtils.getIntFromShp(Constant.SALEMAN_ID, shf).toString()
        val routeSchedule = db.routeScheduleV2Dao().dataBySaleManId(saleManId)
        val routeScheduleItems = db.routeScheduleItemV2Dao().allDataByRouteScheduleId(routeSchedule.id.toString())
        return if (routeScheduleItems.count() > 0)
            routeScheduleItems[0].route_schedule_id
        else
            0
    }

    override fun getCustomerIdList(): List<Int> {
        return db.customerDao().allID
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
                classDiscountByPriceItem.class_discount_id = items.classDiscountId
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
        val tDiscountByCategoryQuantityItemEntityList = mutableListOf<TDiscountByCategoryQuantityItem>()
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
                classEntity.id = it.id.toInt()
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
                productTypeEntity.id = it.id.toInt()
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
//        val posmEntityList = mutableListOf<>()
    }
}