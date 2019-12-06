package com.aceplus.domain.vo.report

import com.aceplus.domain.entity.preorder.PreOrder

class EndOfDayReport(
    var totalPayAmt :Double,
    var preOrderList : ArrayList<PreOrder>,
    var amtArr :Array<Double>,
    var saleReturnCount : Int,
    var saleExchangeCount :Int,
    var totalCashAmount :Double,
    var totalCashCount :Int,
    var totalSaleCount:Int,
    var customerCount :Int,
    var newCustomerCount :Int,
    var planCustomerCount : Int,
    var notVisitCount :Int,
    var startTime : String

)