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

        fun changeUrl(ip: String) {
            BASE_URL = ""
            BASE_URL = ip
        }

    }
}
