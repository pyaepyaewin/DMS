package com.aceplus.domain.vo

import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.customer.Customer

data class RelatedDataForPrint(
    var customer: Customer,
    var routeName: String?,
    var customerTownShipName: String,
    var companyInfo: CompanyInformation,
    var orderSalePersonName: String?
)