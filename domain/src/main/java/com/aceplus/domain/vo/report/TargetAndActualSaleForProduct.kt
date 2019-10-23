package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class TargetAndActualSaleForProduct(

    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "sale_man_id")

    var saleManId: String? = null,

    @ColumnInfo(name = "category_id")

    var categoryId: String? = null,

    @ColumnInfo(name = "group_code_id")

    var groupCodeId: String? = null,

    @ColumnInfo(name = "stock_id")

    var stockId: String? = null,

    @ColumnInfo(name = "invoice_no")
    var invoiceNo: String? = null,

    @ColumnInfo(name = "target_amount")

    var targetAmount: String? = null,

    @ColumnInfo(name = "from_date")

    var fromDate: String? = null,

    @ColumnInfo(name = "to_date")
    var toDate: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null,

    @ColumnInfo(name = "day")
    var day: String? = null,

    @ColumnInfo(name = "date_status")
    var dateStatus: String? = null,

    @ColumnInfo(name = "product_name")
    var productName: String? = null,

    @ColumnInfo(name = "name")
    var groupCodeName: String? = null,

    @ColumnInfo(name = "category_name")
    var categoryName: String? = null



)