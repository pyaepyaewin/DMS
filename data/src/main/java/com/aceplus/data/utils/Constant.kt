package com.aceplus.data.utils

class Constant {

    companion object {
        var REAL_TIME_URL = "http://acedms-001-site1.atempurl.com/api/v1/upload.svc/"
        var REAL_TIME_AP_URL = "http://128.199.102.103:8080/api/v1/"

        var BASE_URL = "http://192.168.0.81:9000/api/v1/"//test ip for bi2

        var KEY_CHANGE_URL = "change_url"

        var SITE_ACTIVATION_KEY = "1234567"

        var TABLET_ACTIVATION_KEY = "1234567"

        var SALEMAN_ID = "saleman_id"

        var SALEMAN_NO = "saleman_no"

        var SALEMAN_NAME = "saleman_name"

        var SALEMAN_PWD = "saleman_pwd"

        var TABLET_KEY = "tablet_key"

        var MAX_KEY = "max_key"

        var ADDNEWCUSTOMERCOUNT = "addnewcustomerCount"

        var KEY_MAX_ZOOM = "12"

        var KEY_SALE_RETURN_AMOUNT = "sale_return_amount"

        var START_TIME = "START_TIME"

        var END_TIME = "END_TIME"

        var PRODUCT_COUNT = 0

        var INVOICE_COUNT = "invoice_count"

        fun changeUrl(ip: String) {
            BASE_URL = ""
            BASE_URL = ip
        }

        const val FOR_PACKAGE_SALE = "for-package-sale"
        const val FOR_PRE_ORDER_SALE = "for-pre-order-sale"
        const val FOR_DELIVERY = "for-delivery"
        const val FOR_OTHERS = "for-others"
        const val FOR_SALE = "for-sales"
        const val FOR_SALE_RETURN = "for-sale-return"
        const val FOR_SALE_RETURN_EXCHANGE = "for-sale_return_exchange"
        const val FOR_SALE_EXCHANGE = "for_sale_exchange"
        const val FOR_DISPLAY_ASSESSMENT = "for_display_assessment"
        const val FOR_OUTLET_STOCK_AVAILABILITY = "for_outlet_stock_availibility"
        const val FOR_SIZE_IN_STORE_SHARE = "for_size_in_store_share"
        const val FOR_COMPETITORACTIVITY = "for_competitoractivity"
        const val PRINT_FOR_NORMAL_SALE = "print-for-normal-sale"
        const val PRINT_FOR_C = "print-for-c"
        const val PRINT_FOR_PRE_ORDER = "print-for-preorder"
        const val FOR_VAN_ISSUE = "for-van-issue"

        const val HM_MESSAGE_STATE_CHANGE = 1
        const val HM_MESSAGE_READ = 2
        const val HM_MESSAGE_WRITE = 3
        const val HM_MESSAGE_DEVICE_NAME = 4
        const val HM_MESSAGE_TOAST = 5
        const val HM_MESSAGE_CONNECTION_LOST = 6
        const val HM_MESSAGE_UNABLE_CONNECT = 7

        const val RQC_GET_LOCATION = 100

    }

}
