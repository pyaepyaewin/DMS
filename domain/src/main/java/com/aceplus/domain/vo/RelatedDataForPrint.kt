package com.aceplus.domain.vo

import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.customer.Customer

data class RelatedDataForPrint(
    var customer: Customer,
    var routeID: Int,
    var customerTownShipName: String,
    var companyInfo: CompanyInformation
)