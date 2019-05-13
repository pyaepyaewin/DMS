package com.aceplus.domain.model.forApi.customer

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by haker on 2/9/17.
 */

class CustomerForApi {
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("township_number")
    @Expose
    var townshipNumber: String? = null

    @SerializedName("contact_person")
    @Expose
    var contactPerson: String? = null

    @SerializedName("CUSTOMER_ID")
    @Expose
    var customerId: String? = null

    @SerializedName("CUSTOMER_NAME")
    @Expose
    var customerName: String? = null

    @SerializedName("CUSTOMER_TYPE_ID")
    @Expose
    var customerTypeId: String? = null

    @SerializedName("CUSTOMER_TYPE_NAME")
    @Expose
    var customerTypeName: String? = null

    @SerializedName("ADDRESS")
    @Expose
    var address: String? = null

    @SerializedName("PH")
    @Expose
    var pH: String? = null

    @SerializedName("CREDIT_TERM")
    @Expose
    var creditTerm: String? = null

    @SerializedName("CREDIT_LIMIT")
    @Expose
    var creditLimit: String? = null

    @SerializedName("PAYMENT_TYPE")
    @Expose
    var paymentType: String? = null

    @SerializedName("customer_category_no")
    @Expose
    var customerCategoryNo: String? = null

    @SerializedName("CREDIT_AMT")
    @Expose
    var creditAmt: String? = null

    @SerializedName("DUE_AMT")
    @Expose
    var dueAmount: String? = null

    @SerializedName("PREPAID_AMT")
    @Expose
    var prepaidAmount: String? = null

    @SerializedName("IS_IN_ROUTE")
    @Expose
    var isInRoute: String? = null

    @SerializedName("LATITUDE")
    @Expose
    var latitude: String? = null

    @SerializedName("LONGITUDE")
    @Expose
    var longitude: String? = null

    @SerializedName("VISIT_RECORD")
    @Expose
    var visitRecord: String? = null

    @SerializedName("district_id")
    @Expose
    var districtId: Int = 0

    @SerializedName("state_division_id")
    @Expose
    var stateDivisionId: Int = 0

    @SerializedName("shop_type_id")
    @Expose
    var shopTypeId: Int = 0

    @SerializedName("StreetId")
    @Expose
    var streetId: Int = 0

    @SerializedName("Fax")
    @Expose
    var fax: String? = null

    @SerializedName("route_schedule_status")
    @Expose
    var routeScheduleStatus: String? = null

    @SerializedName("CreatedUserId")
    @Expose
    var createdUserId: String? = null

    @SerializedName("CreatedDate")
    @Expose
    var createdDate: String? = null

}
