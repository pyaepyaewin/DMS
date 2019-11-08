package com.aceplus.domain.vo.report

class EndOfDayReport(
    var userName: String,
    var routeName: String,
    var startTime: String,
    var endTime: String,
    var totalSales: String,
    var totalSalesOrder: String,
    var totalExchange: String,
    var totalReturn: String,
    var totalCashReceive: String,
    var netCash: String,
    var totalCustomer: String,
    var newCustomer: String,
    var planCustomer: String,
    var totalSalesCount: String,
    var totalOrderCount: String,
    var totalSalesExchangeOnly: String,
    var totalSalesReturnOnly: String,
    var totalCashReceiptCount: String,
    var notVisitedCount: String
)