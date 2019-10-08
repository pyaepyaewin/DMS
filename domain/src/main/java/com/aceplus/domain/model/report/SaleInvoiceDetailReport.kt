package com.aceplus.domain.model.report

class SaleInvoiceDetailReport(
    val product_name: String,
    val sold_quantity: Int,
    val discount_amount: String,
    val total_amount: Double
)