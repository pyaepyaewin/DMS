package com.aceplus.dms.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.util.Base64
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.LoginActivity
import com.aceplus.domain.model.INVOICECANCEL
import com.aceplus.domain.model.forApi.credit.CreditApi
import com.aceplus.domain.model.forApi.login.LoginCreditRequest
import com.aceplus.domain.model.forApi.login.LoginRequest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private val decimalFormatterWithComma = DecimalFormat("###,##0")
    private val decimalFormatterWithComma1 = DecimalFormat("###,##0.##")

    var progressDialog: ProgressDialog? = null

    val MODE_CUSTOMER_FEEDBACK = "mode_customer_feedback"
    val MODE_GENERAL_SALE = "mode_general_sale"

    val forPackageSale = "for-package-sale"
    val forPreOrderSale = "for-pre-order-sale"
    val FOR_DELIVERY = "for-delivery"
    val FOR_OTHERS = "for-others"
    val FOR_SALE = "for-sales"
    val FOR_SALE_RETURN = "for-sale-return"
    val FOR_SALE_RETURN_EXCHANGE = "for-sale_return_exchange"
    val FOR_SALE_EXCHANGE = "for_sale_exchange"
    val FOR_DISPLAY_ASSESSMENT = "for_display_assessment"
    val FOR_OUTLET_STOCK_AVAILABILITY = "for_outlet_stock_availibility"
    val FOR_SIZE_IN_STORE_SHARE = "for_size_in_store_share"
    val FOR_COMPETITORACTIVITY = "for_competitoractivity"
    val PRINT_FOR_NORMAL_SALE = "print-for-normal-sale"
    val PRINT_FOR_C = "print-for-c"
    val PRINT_FOR_PRE_ORDER = "print-for-preorder"
    val FOR_VAN_ISSUE = "for-van-issue"

    private var formatter: Formatter? = null
    private var act: Activity? = null
    private var taxPercent = 0.0
    private var taxType: String? = null
    internal var totalDiscountAmt = 0.0


    val isOsMarshmallow: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    private lateinit var onActionClickListener: OnActionClickListener

    interface OnActionClickListener {
        fun onActionClick(type: String)
    }

//    private val mCallback = Communication.SendCallback { result, communicateResult ->
//        val msg:String
//        var flg = 1
//        when (communicateResult) {
//            Communication.Result.Success -> {
//                msg = "Success!"
//                flg = 0
//            }
//            Communication.Result.ErrorOpenPort -> msg = "Fail to openPort"
//            Communication.Result.ErrorBeginCheckedBlock -> msg = "Printer is offline (beginCheckedBlock)"
//            Communication.Result.ErrorEndCheckedBlock -> msg = "Printer is offline (endCheckedBlock)"
//            Communication.Result.ErrorReadPort -> msg = "Read port error (readPort)"
//            Communication.Result.ErrorWritePort -> msg = "Write port error (writePort)"
//            else -> msg = "Unknown error"
//        }
//
//        commonDialog(msg, act, flg)
//    }

//    fun getInvoiceID(context: Context, mode:String, salemanID:String, locationCode:String):String {
//
//        if (database == null)
//        {
//
//            database = Database(context).getReadableDatabase()
//        }
//
//        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
//
//        var idLength = 0
//        var prefix = locationCode + salemanID
//        var currentInvoiceNumber:Long = 0
//        if (mode == MODE_CUSTOMER_FEEDBACK)
//        {
//
//            idLength = 20
//            prefix += SimpleDateFormat("yyMMdd", Locale.ENGLISH).format(Date())
//
//            //			currentInvoiceNumber += DatabaseUtils.queryNumEntries(database, "DID_CUSTOMER_FEEDBACK");
//            currentInvoiceNumber += DatabaseUtils.queryNumEntries(database, "INVOICE")
//
//            val lastUploadedDate = Preferences.getCustomerFeedbackLastUploadedDate(context)
//            if (lastUploadedDate != null)
//            {
//
//                try
//                {
//                    val todayDate = simpleDateFormat.parse(simpleDateFormat.format(Date()))
//                    if (todayDate.after(lastUploadedDate!!))
//                    {
//
//                        Preferences.resetNumberOfCustomerFeedbackUploaded(context)
//                    }
//
//                    currentInvoiceNumber += Preferences.getCustomerFeedbackUploadedCount(context)
//                }
//                catch (e: ParseException) {
//
//                    e.printStackTrace()
//                }
//
//            }
//            currentInvoiceNumber++
//        }
//        else if (mode == MODE_GENERAL_SALE)
//        {
//
//            idLength = 20
//            prefix += SimpleDateFormat("yyMMdd", Locale.ENGLISH).format(Date())
//
//            currentInvoiceNumber += DatabaseUtils.queryNumEntries(database, "DID_CUSTOMER_FEEDBACK")
//
//            val lastUploadedDate = Preferences.getSaleLastUploadedDate(context)
//            if (lastUploadedDate != null)
//            {
//
//                try
//                {
//                    val todayDate = simpleDateFormat.parse(simpleDateFormat.format(Date()))
//                    if (todayDate.after(lastUploadedDate!!))
//                    {
//
//                        Preferences.resetNumberOfSaleUploaded(context)
//                    }
//
//                    currentInvoiceNumber += Preferences.getSaleUploadedCount(context)
//                }
//                catch (e: ParseException) {
//
//                    e.printStackTrace()
//                }
//
//            }
//            currentInvoiceNumber++
//        }
//
//        return prefix + String.format("%0" + (idLength - prefix.length) + "d", currentInvoiceNumber)
//    }

//    fun getRouteID_v2(context: Context, saleman_Id:String):Int {
//        var routeID = 0
//        if (database == null)
//        {
//            database = Database(context).getReadableDatabase()
//        }
//
//        val cursor = database!!.rawQuery(
//            "select * from " + DatabaseContract.RouteSchedule_v2.tb + " where " +
//                    DatabaseContract.RouteSchedule_v2.saleManId + " = '" + saleman_Id + "' ", null)
//        if (cursor != null && cursor!!.count > 0)
//        {
//            while (cursor!!.moveToNext())
//            {
//                routeID = cursor!!.getInt(cursor!!.getColumnIndex(DatabaseContract.RouteSchedule_v2.routeId))
//            }
//            cursor!!.close()
//        }
//        Log.i("routeID>>>", (routeID).toString() + "")
//        return routeID
//
//
//    }
//    fun getRouteID_v2(db: SQLiteDatabase, saleman_Id:String):Int {
//        var routeID = 0
//        val cursor = db.rawQuery(("select * from " + DatabaseContract.RouteSchedule_v2.tb + " where " +
//                DatabaseContract.RouteSchedule_v2.saleManId + " = '" + saleman_Id + "' "), null)
//        if (cursor != null && cursor!!.count > 0)
//        {
//            while (cursor!!.moveToNext())
//            {
//                routeID = cursor!!.getInt(cursor!!.getColumnIndex(DatabaseContract.RouteSchedule_v2.routeId))
//            }
//            cursor!!.close()
//        }
//        Log.i("routeID>>>", (routeID).toString() + "")
//        return routeID
//
//
//    }

//    fun getRouteName(context: Context, routeId:Int):String {
//        var routeName = ""
//        if (database == null)
//        {
//            database = Database(context).getReadableDatabase()
//        }
//
//        val cursor = database!!.rawQuery(("select * from " + DatabaseContract.Route.tb + " where " +
//                DatabaseContract.Route.id + " = '" + routeId + "' "), null)
//        while (cursor.moveToNext())
//        {
//            routeName = cursor.getString(cursor.getColumnIndex(DatabaseContract.Route.routeName))
//        }
//        cursor.close()
//        return routeName
//    }

//    fun getRouteScheduleID_v2(context: Context, saleman_Id:String):Int {
//        var routeScheduleID = 0
//        var id = 0
//        if (database == null)
//        {
//            database = Database(context).getReadableDatabase()
//        }
//        val cursor = database!!.rawQuery(("select * from " + DatabaseContract.RouteSchedule_v2.tb + " where " +
//                DatabaseContract.RouteSchedule_v2.saleManId + " = '" + saleman_Id + "' "), null)
//        while (cursor.moveToNext())
//        {
//            id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.RouteSchedule_v2.id))
//            val cus = database!!.rawQuery(("select * from " + DatabaseContract.RouteScheduleItem_v2.tb + " where " +
//                    DatabaseContract.RouteScheduleItem_v2.routeScheduleId + " = '" + id + "' "), null)
//            while (cus.moveToNext())
//            {
//                routeScheduleID = cus.getInt(cus.getColumnIndex(DatabaseContract.RouteScheduleItem_v2.routeScheduleId))
//            }
//            cus.close()
//        }
//        cursor.close()
//        Log.i("routeID>>>", (routeScheduleID).toString() + "")
//        return routeScheduleID
//    }

//    fun backToHome(activity: Activity) {
//        val intent = Intent(activity, HomeActivity::class.java)
//        activity.startActivity(intent)
//        activity.finish()
//    }

    /**
     * Go to Login activity.
     *
     * @param activity current activity name
     */
    fun backToLogin(activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
        activity.finish()
    }

//    fun backToCustomerVisit(activity: Activity) {
//        val intent = Intent(activity, CustomerVisitActivity::class.java)
//        activity.startActivity(intent)
//        activity.finish()
//    }
//
//    fun backToCustomer(activity: Activity) {
//        val intent = Intent(activity, CustomerActivity::class.java)
//        intent.putExtra("SaleExchange", "no")
//        activity.startActivity(intent)
//        activity.finish()
//    }
//
//    fun backToMarketingActivity(activity: Activity) {
//        val intent = Intent(activity, MarketingActivity::class.java)
//        activity.startActivity(intent)
//        activity.finish()
//    }

    fun formatAmount(amount: Double?): String {

        return decimalFormatterWithComma1.format(amount)
    }


    fun getCurrentDate(withTime: Boolean): String {

        var dateFormat = "yyyy-MM-dd"
        if (withTime) {

            dateFormat += " HH:mm:ss"
        }

        return SimpleDateFormat(dateFormat).format(Date())
    }

    fun getDate(withTime: Boolean): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val newDate = calendar.time
        var dateFormat = "yyyy-MM-dd"
        if (withTime) {

            dateFormat += " HH:mm:ss"
        }

        return SimpleDateFormat(dateFormat).format(newDate)
    }

    fun callDialog(message: String, activity: Activity) {

        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT)
        }

        progressDialog!!.isIndeterminate = false
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.setMessage(message)

        val handler = Handler()
        handler.post(Runnable {
            if (activity.isFinishing) {
                return@Runnable
            } else {
                progressDialog = ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT)
                progressDialog!!.isIndeterminate = false
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setMessage(message)
                progressDialog!!.show()
                progressDialog!!.setCanceledOnTouchOutside(false)
            }
        })
    }

    fun cancelDialog() {
        if (progressDialog != null) {
            progressDialog!!.cancel()
        }
    }

    fun commonDialog(message: String, activity: Activity?, flag: Int) {
        val handler = Handler()
        handler.post(Runnable {
            if (activity!!.isFinishing) {
                return@Runnable
            } else {
                var statusImage = 0
                var title = ""
                if (flag == 0) {
                    statusImage = R.drawable.success
                    title = "Success"
                } else if (flag == 1) {
                    statusImage = R.drawable.fail
                    title = "Error"
                } else if (flag == 2) {
                    statusImage = R.drawable.info
                    title = "Info"
                }

                AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .setCancelable(false)
                    .setIcon(statusImage)
                    .show()
            }
        })
    }

    fun encodePassword(str: String): String {
        var data: ByteArray? = null
        try {
            data = str.toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return Base64.encodeToString(data, Base64.NO_WRAP)
    }

    fun getJsonString(`object`: Any): String {
        val gson = Gson()
        val jsonString = gson.toJson(`object`).toString()
        Log.d("jsonString>>>", jsonString)
        return jsonString
    }

    private fun getJsonFromObject(`object`: Any): String {
        val gson = GsonBuilder().serializeNulls().create()
        return gson.toJson(`object`)
    }

    fun createParamData(user_no: String, password: String, routeId: Int): String {
        var paramData = ""
        val loginRequest = LoginRequest()
        loginRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        loginRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        loginRequest.userId = user_no
        loginRequest.password = password
        loginRequest.date = Utils.getCurrentDate(false)
        loginRequest.route = routeId
        val objectList = ArrayList<Any>()
        loginRequest.data = objectList

        val jsonObject = JSONObject()
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY)
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY)
            jsonObject.put("user_id", loginRequest.userId)
            jsonObject.put("password", loginRequest.password)
            jsonObject.put("route", loginRequest.route)
            jsonObject.put("date", loginRequest.date)
            jsonObject.put("data", loginRequest.data)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.i("param_data>>>", jsonObject.toString())

        paramData = jsonObject.toString()
        return paramData
    }

    fun createParamData(user_no: String, password: String, routeId: Int, saleman_id: String): String {
        var paramData = ""
        val loginRequest = LoginRequest()
        loginRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        loginRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        loginRequest.userId = user_no
        loginRequest.saleManId = saleman_id
        loginRequest.password = password
        loginRequest.date = Utils.getCurrentDate(false)
        loginRequest.route = routeId
        val objectList = ArrayList<Any>()
        loginRequest.data = objectList

        val jsonObject = JSONObject()
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY)
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY)
            jsonObject.put("user_id", loginRequest.userId)
            jsonObject.put("password", loginRequest.password)
            jsonObject.put("route", loginRequest.route)
            jsonObject.put("date", loginRequest.date)
            jsonObject.put("data", loginRequest.data)
            jsonObject.put("saleman_id", loginRequest.saleManId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.i("param_data>>>", jsonObject.toString())

        paramData = jsonObject.toString()
        return paramData
    }

    fun createLoginParamData(user_no: String, password: String, routeId: Int, tabletKey: String): String {
        var paramData = ""
        val loginRequest = LoginRequest()
        loginRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        loginRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        loginRequest.userId = user_no
        loginRequest.password = password
        loginRequest.date = Utils.getCurrentDate(false)
        loginRequest.route = routeId
        loginRequest.tabletKey = tabletKey
        val objectList = ArrayList<Any>()
        loginRequest.data = objectList

        val jsonObject = JSONObject()
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY)
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY)
            jsonObject.put("user_id", loginRequest.getUserId())
            jsonObject.put("password", loginRequest.getPassword())
            jsonObject.put("route", loginRequest.getRoute())
            jsonObject.put("tablet_key", loginRequest.getTabletKey())
            jsonObject.put("date", loginRequest.getDate())
            jsonObject.put("data", loginRequest.getData())
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.i("param_data>>>", jsonObject.toString())

        paramData = jsonObject.toString()
        return paramData
    }

    fun createDownloadProductParamData(user_no: String, password: String, routeId: Int, status: String): String {
        var paramData = ""
        val loginRequest = LoginRequest()
        loginRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        loginRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        loginRequest.userId = user_no
        loginRequest.password = password
        loginRequest.date = Utils.getCurrentDate(false)
        loginRequest.route = routeId
        val objectList = ArrayList<Any>()
        loginRequest.data = objectList

        val jsonObject = JSONObject()
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY)
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY)
            jsonObject.put("user_id", loginRequest.userId)
            jsonObject.put("password", loginRequest.password)
            jsonObject.put("route", loginRequest.route)
            jsonObject.put("date", loginRequest.date)
            jsonObject.put("data", loginRequest.data)
            jsonObject.put("status", status)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.i("param_data>>>", jsonObject.toString())

        paramData = jsonObject.toString()
        return paramData
    }

    fun createParamDataWithCustomerIDList(
        saleManNo: String,
        password: String,
        routeId: Int,
        customerIdList: List<Int>
    ): String {
        val loginRequest = LoginCreditRequest()
        loginRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        loginRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        loginRequest.userId = saleManNo
        loginRequest.password = password
        loginRequest.date = Utils.getCurrentDate(false)
        loginRequest.route = routeId

        val creditApiList = ArrayList<CreditApi>()
        val creditApi = CreditApi()
        creditApi.idList = customerIdList
        creditApiList.add(creditApi)
        loginRequest.data = creditApiList
        return getJsonFromObject(loginRequest)
    }


    fun getDeviceId(activity: Activity): String {
        return Settings.Secure.getString(
            activity.contentResolver,
            Settings.Secure.ANDROID_ID
        )

    }

//    fun getDeviceIssueInvoiceNo(context: Context):String {
//        if (database == null)
//        {
//
//            database = (Database(context)).getDataBase()
//        }
//
//        val idLength = 18
//        var invoiceNo = String()
//        invoiceNo += "HODQ"
//        invoiceNo += SimpleDateFormat("yyMMdd").format(Date())
//        var next = 0
//        val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM INVOICE", null)
//        if (cursor.moveToNext())
//        {
//
//            next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//        }
//        cursor.close()
//
//        return invoiceNo + String.format("%0" + (idLength - invoiceNo.length) + "d", next)
//    }

//    fun getInvoiceNo(context: Context, salemanId:String, locationCode:String, mode:String):String {
//
//        if (database == null)
//        {
//            database = (Database(context)).getDataBase()
//        }
//
//        //		int idLength = 15;
//        //		if (mode.equals(Utils.forPreOrderSale)) {
//        //
//        //			idLength = 16;
//        //		}
//        val idLength = 18
//
//        var invoiceNo = String()
//        if (mode == Utils.forPackageSale)
//        {
//
//            invoiceNo += "P"
//        }
//        else if (mode == Utils.forPreOrderSale)
//        {
//
//            invoiceNo += "SO"
//        }
//        else if (mode == Utils.FOR_DELIVERY)
//        {
//
//            invoiceNo += "OS"
//        }
//        else if (mode == Utils.FOR_SALE_RETURN)
//        {
//
//            invoiceNo += "SR"
//        }
//        else if (mode == Utils.FOR_SALE_EXCHANGE || mode == Utils.FOR_SALE_RETURN_EXCHANGE)
//        {
//
//            invoiceNo += "SX"
//
//        }
//        else if (mode == Utils.FOR_DISPLAY_ASSESSMENT)
//        {
//
//            invoiceNo += "DA"
//        }
//        else if (mode == Utils.FOR_OUTLET_STOCK_AVAILABILITY)
//        {
//
//            invoiceNo += "OSA"
//
//        }
//        else if (mode == Utils.FOR_SIZE_IN_STORE_SHARE)
//        {
//
//            invoiceNo += "SIS"
//
//        }
//        else if (mode == Utils.FOR_COMPETITORACTIVITY)
//        {
//
//            invoiceNo += "CA"
//
//        }
//        else if (mode == Utils.FOR_VAN_ISSUE)
//        {
//            invoiceNo += "DQ"
//        }
//
//        invoiceNo += locationCode
//        invoiceNo += salemanId
//        invoiceNo += SimpleDateFormat("yyMMdd").format(Date())
//
//        var next = 0
//        if (mode == Utils.FOR_SALE)
//        {
//            next += context.getSharedPreferences("PREFERENCE",
//                Activity.MODE_PRIVATE).getInt("INVOICE_COUNT", -1)
//        }
//        else if (mode == Utils.FOR_OTHERS || mode == Utils.forPackageSale)
//        {
//
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM DID_CUSTOMER_FEEDBACK", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT"))
//            }
//            cursor.close()
//            /*cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM INVOICE", null);
//                       if (cursor.moveToNext()) {
//
//                           next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
//                       }*/
//        }
//        else if (mode == Utils.forPreOrderSale)
//        {
//
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM PRE_ORDER", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//            }
//            cursor.close()
//        }
//        else if (mode == Utils.FOR_DELIVERY)
//        {
//
//            //			next = 1;
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM DELIVERY_UPLOAD", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//            }
//            cursor.close()
//        }
//        else if (mode == Utils.FOR_SALE_RETURN)
//        {
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM SALE_RETURN WHERE SALE_RETURN_ID LIKE 'SR%' ", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//            }
//            cursor.close()
//        }
//        else if (mode == Utils.FOR_SALE_RETURN_EXCHANGE)
//        {
//
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM SALE_RETURN WHERE SALE_RETURN_ID LIKE 'SX%' ", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//            }
//            cursor.close()
//        }
//        else if (mode == Utils.FOR_SALE_EXCHANGE)
//        {
//
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM INVOICE WHERE INVOICE_ID LIKE 'SX%' ", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//            }
//            cursor.close()
//        }
//        else if (mode == Utils.FOR_DISPLAY_ASSESSMENT)
//        {
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM OUTLET_VISIBILITY", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//            }
//            cursor.close()
//        }
//        else if (mode == Utils.FOR_OUTLET_STOCK_AVAILABILITY)
//        {
//
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM outlet_stock_availability", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//            }
//            cursor.close()
//
//        }
//        else if (mode == Utils.FOR_SIZE_IN_STORE_SHARE)
//        {
//
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM size_in_store_share", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//            }
//            cursor.close()
//        }
//        else if (mode == Utils.FOR_COMPETITORACTIVITY)
//        {
//
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM COMPETITOR_ACTIVITY", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//
//            }
//            cursor.close()
//        }
//        else if (mode == Utils.FOR_VAN_ISSUE)
//        {
//            val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM DeviceIssueRequest", null)
//            if (cursor.moveToNext())
//            {
//
//                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//            }
//            cursor.close()
//        }
//
//        return invoiceNo + String.format("%0" + (idLength - invoiceNo.length) + "d", next)
//    }

//    fun getInvoiceNoForPOSM(context: Context, saleManId:String, locationCode:String):String {
//
//        val idLength = 14
//
//        if (database == null)
//        {
//
//            database = (Database(context)).getDataBase()
//        }
//        var next = 0
//
//        val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM POSM_BY_CUSTOMER", null)
//        if (cursor.moveToNext())
//        {
//
//            next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
//        }
//        cursor.close()
//        var invoiceNo = ""
//
//        invoiceNo += locationCode
//        invoiceNo += saleManId
//        invoiceNo += SimpleDateFormat("yyMMdd").format(Date())
//
//        return invoiceNo + String.format("%0" + (idLength - invoiceNo.length) + "d", next)
//    }

    fun isNumeric(str: String): Boolean {
        try {
            val d = java.lang.Double.parseDouble(str)
        } catch (nfe: NumberFormatException) {
            return false
        }

        return true
    }

    fun setOnActionClickListener(onActionClickListener: OnActionClickListener) {
        Utils.onActionClickListener = onActionClickListener
    }

    fun askConfirmationDialog(title: String, message: String, type: String, activity: Activity) {
        val alertDialog = AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                "Yes"
            ) { dialogInterface, i -> onActionClickListener.onActionClick(type) }
            .setNegativeButton("No", null)
            .show()

        val textViewYes = alertDialog.findViewById<TextView>(android.R.id.button1)
        textViewYes?.textSize = 25f
        val textViewNo = alertDialog.findViewById<TextView>(android.R.id.button2)
        textViewNo?.textSize = 25f
    }


    fun askConfirmationDialog(title: String, message: String, type: String, activity: Activity,action:(String)->Unit) {
        val alertDialog = AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                "Yes"
            ) { dialogInterface, i -> action(type) }
            .setNegativeButton("No", null)
            .show()

        val textViewYes = alertDialog.findViewById<TextView>(android.R.id.button1)
        textViewYes?.textSize = 25f
        val textViewNo = alertDialog.findViewById<TextView>(android.R.id.button2)
        textViewNo?.textSize = 25f
    }

//    fun print(activity: Activity, customerName:String, cus_address:String, invoiceNumber:String, salePersonName:String, routeId:Int, townshipName:String, invoice:Invoice, soldProductList:List<SoldProduct>, presentList:List<Promotion>, printFor:String, mode:String) {
//
//        var portInfoList:List<PortInfo>? = null
//
//        try
//        {
//
//            portInfoList = StarIOPort.searchPrinter("BT:Star")
//        }
//        catch (e: StarIOPortException) {
//
//            e.printStackTrace()
//        }
//
//        if (portInfoList == null || portInfoList!!.size == 0)
//        {
//
//            return
//        }
//
//        val availableBluetoothPrinterNameList = ArrayList<String>()
//        for (portInfo in portInfoList!!)
//        {
//
//            availableBluetoothPrinterNameList.add(portInfo.portName)
//        }
//        val arrayAdapter = ArrayAdapter(
//            activity, R.layout.select_dialog_singlechoice, availableBluetoothPrinterNameList)
//        android.app.AlertDialog.Builder(activity)
//            .setTitle("Select Printer")
//            .setNegativeButton("Cancel", null)
//            .setAdapter(arrayAdapter, object: DialogInterface.OnClickListener {
//
//                override fun onClick(dialog: DialogInterface, position:Int) {
//
//                    var starIOPort: StarIOPort? = null
//                    try
//                    {
//
//                        starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position)!!, "mini", 10000)
//                        if (starIOPort!!.retreiveStatus().offline)
//                        {
//
//                            if (!starIOPort!!.retreiveStatus().compulsionSwitch)
//                            {
//
//                                showToast(activity, "The Drawer is offline\nCash Drawer: Close")
//                            }
//                            else
//                            {
//
//                                showToast(activity, "The Drawer is offline\nCash Drawer: Open")
//                            }
//
//                            return
//                        }
//                        else
//                        {
//
//                            if (starIOPort!!.retreiveStatus().compulsionSwitch)
//                            {
//
//                                showToast(activity, "The Drawer is online\nCash Drawer: Open")
//                            }
//                            else
//                            {
//
//                                val printDataByteArray = convertFromListByteArrayTobyteArray(
//                                    getPrintDataByteArrayList(
//                                        activity, customerName, cus_address, invoiceNumber, salePersonName, routeId, townshipName, invoice, soldProductList, presentList, printFor, mode, Print_BMP(activity)))
//
//                                // configure printer setting using StarIo 1.3.0 lib
//                                val context = activity.applicationContext
//                                val setting = PrinterSetting(context)
//                                val emulation = setting.emulation
//                                val builder = StarIoExt.createCommandBuilder(emulation)
//                                builder.beginDocument()
//                                builder.append(printDataByteArray)
//                                // choosing font type
//                                builder.appendFontStyle(ICommandBuilder.FontStyleType.B)
//                                // chooding all data for one page
//                                builder.appendCutPaper(ICommandBuilder.CutPaperAction.FullCut)
//                                builder.endDocument()
//                                act = activity
//                                Communication.sendCommands(this, builder.commands, "BT:00:15:0E:E1:CF:B1", "mini", 10000, activity, mCallback)
//                            }
//                        }
//                    }
//                    catch (e: StarIOPortException) {
//
//                        showToast(activity, "Failed to connect to drawer")
//                        e.printStackTrace()
//                    }
//                    catch (e: UnsupportedEncodingException) {
//                        e.printStackTrace()
//                    }
//                    finally
//                    {
//                        if (starIOPort != null)
//                        {
//
//                            try
//                            {
//
//                                StarIOPort.releasePort(starIOPort)
//                            }
//                            catch (e: StarIOPortException) {
//
//                                e.printStackTrace()
//                            }
//
//                        }
//                    }
//                }
//            })
//            .show()
//    }


//    private fun Print_BMP(mActivity: Activity):ByteArray? {
//        val mBitmap = BitmapFactory.decodeResource(mActivity.resources,
//            R.drawable.global_sky_logo)
//        val buffer = PrinterCommand.POS_Set_PrtInit()
//        val nMode = 0
//        val nPaperWidth = 384
//        if (mBitmap != null)
//        {
//
//            return PrintPicture.POS_PrintBMP(mBitmap, nPaperWidth, nMode)
//        }
//        return null
//    }

//    fun printForEndOfDayReport(activity: Activity, saleManDailyReport:SaleManDailyReport, mBTService:BluetoothService) {
//
//        try
//        {
//            val printDataByteArray = convertFromListByteArrayTobyteArray(
//                getPrintDataByteArrayListForDailyReport(
//                    activity, saleManDailyReport))
//            sendDataByte2BT(activity, mBTService, printDataByteArray)
//        }
//        catch (e: UnsupportedEncodingException) {
//            e.printStackTrace()
//        }
//
//    }

//    fun printWithHSPOS(activity: Activity, customerName:String, cus_address:String, invoiceNumber:String, salePersonName:String, routeId:Int, townshipName:String, invoice:Invoice, soldProductList:List<SoldProduct>, presentList:List<Promotion>, printFor:String, mode:String, mBTService:BluetoothService) {
//        try
//        {
//
//
//            val printDataByteArray = convertFromListByteArrayTobyteArray(
//                getPrintDataByteArrayList(
//                    activity, customerName, cus_address, invoiceNumber, salePersonName, routeId, townshipName, invoice, soldProductList, presentList, printFor, mode, Print_BMP(activity)))
//            sendDataByte2BT(activity, mBTService, printDataByteArray)
//        }
//        catch (e: UnsupportedEncodingException) {
//            e.printStackTrace()
//        }
//
//    }

//    private fun sendDataByte2BT(mActivity: Activity, mService:BluetoothService, data:ByteArray) {
//
//        if (mService.getState() !== BluetoothService.STATE_CONNECTED)
//        {
//            Toast.makeText(mActivity, "Not Connected", Toast.LENGTH_SHORT)
//                .show()
//            return
//        }
//        mService.write(data)
//        commonDialog("Success", mActivity, 0)
//
//    }

//    fun printSaleExchange(activity: Activity,
//                          invoiceNumber:String,
//                          saleReturnInvoiceNumber:String, salePersonName:String, invoice:Invoice, soldProductList:List<SoldProduct>, saleReturnList:List<SoldProduct>, returnDiscountAmt:Double) {
//
//        var portInfoList:List<PortInfo>? = null
//
//        try
//        {
//
//            portInfoList = StarIOPort.searchPrinter("BT:Star")
//        }
//        catch (e: StarIOPortException) {
//
//            e.printStackTrace()
//        }
//
//        if (portInfoList == null || portInfoList!!.size == 0)
//        {
//
//            return
//        }
//
//        val availableBluetoothPrinterNameList = ArrayList<String>()
//        for (portInfo in portInfoList!!)
//        {
//
//            availableBluetoothPrinterNameList.add(portInfo.portName)
//        }
//        val arrayAdapter = ArrayAdapter(
//            activity, R.layout.select_dialog_singlechoice, availableBluetoothPrinterNameList)
//        android.app.AlertDialog.Builder(activity)
//            .setTitle("Select Printer")
//            .setNegativeButton("Cancel", null)
//            .setAdapter(arrayAdapter, object: DialogInterface.OnClickListener {
//
//                override fun onClick(dialog: DialogInterface, position:Int) {
//
//                    var starIOPort: StarIOPort? = null
//                    try
//                    {
//
//                        starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position)!!, "mini", 10000)
//                        if (starIOPort!!.retreiveStatus().offline)
//                        {
//
//                            if (!starIOPort!!.retreiveStatus().compulsionSwitch)
//                            {
//
//                                showToast(activity, "The Drawer is offline\nCash Drawer: Close")
//                            }
//                            else
//                            {
//
//                                showToast(activity, "The Drawer is offline\nCash Drawer: Open")
//                            }
//
//                            return
//                        }
//                        else
//                        {
//
//                            if (starIOPort!!.retreiveStatus().compulsionSwitch)
//                            {
//
//                                showToast(activity, "The Drawer is online\nCash Drawer: Open")
//                            }
//                            else
//                            {
//
//                                val printDataByteArray = convertFromListByteArrayTobyteArray(
//                                    getPrintDataByteArrayListForSaleExchange(
//                                        activity, invoiceNumber, saleReturnInvoiceNumber, salePersonName, invoice, soldProductList, saleReturnList, returnDiscountAmt, "", 0, "", "", Print_BMP(activity)))
//
//                                // configure printer setting using StarIo 1.3.0 lib
//                                val context = activity.applicationContext
//                                val setting = PrinterSetting(context)
//                                val emulation = setting.emulation
//                                val builder = StarIoExt.createCommandBuilder(emulation)
//                                builder.beginDocument()
//                                builder.append(printDataByteArray)
//                                // choosing font type
//                                builder.appendFontStyle(ICommandBuilder.FontStyleType.B)
//                                // chooding all data for one page
//                                builder.appendCutPaper(ICommandBuilder.CutPaperAction.FullCut)
//                                builder.endDocument()
//                                act = activity
//                                Communication.sendCommands(this, builder.commands, "BT:00:15:0E:E1:CF:B1", "mini", 10000, activity, mCallback)
//                            }
//                        }
//                    }
//                    catch (e: StarIOPortException) {
//
//                        showToast(activity, "Failed to connect to drawer")
//                        e.printStackTrace()
//                    }
//                    catch (e: UnsupportedEncodingException) {
//                        e.printStackTrace()
//                    }
//                    finally
//                    {
//                        if (starIOPort != null)
//                        {
//
//                            try
//                            {
//
//                                StarIOPort.releasePort(starIOPort)
//                            }
//                            catch (e: StarIOPortException) {
//
//                                e.printStackTrace()
//                            }
//
//                        }
//                    }
//                }
//            })
//            .show()
//    }

//    fun printSaleExchangeWithHSPOS(activity: Activity,
//                                   invoiceNumber:String,
//                                   saleReturnInvoiceNumber:String, salePersonName:String, invoice:Invoice, soldProductList:List<SoldProduct>, saleReturnList:List<SoldProduct>, returnDiscountAmt:Double, mBTService:BluetoothService, cusName:String, routeId:Int, township:String, cusAddress:String) {
//        try
//        {
//
//
//            val printDataByteArray = convertFromListByteArrayTobyteArray(
//                getPrintDataByteArrayListForSaleExchange(
//                    activity, invoiceNumber, saleReturnInvoiceNumber, salePersonName, invoice, soldProductList, saleReturnList, returnDiscountAmt, cusName, routeId, township, cusAddress, Print_BMP(activity)))
//            sendDataByte2BT(activity, mBTService, printDataByteArray)
//        }
//        catch (e: UnsupportedEncodingException) {
//            e.printStackTrace()
//        }
//
//    }


//    fun printCreditWithHSPOS(activity: Activity, customerName:String, customerAddress:String, invoiceNumber:String, townshipName:String, salePersonName:String, routeId:Int, creditInvoiceList:CreditInvoice, mBTService:BluetoothService) {
//
//        try
//        {
//            val printDataByteArray = convertFromListByteArrayTobyteArray(
//                getPrintDataByteArrayListForCredit(
//                    activity, customerName, customerAddress, invoiceNumber, townshipName, salePersonName, routeId, creditInvoiceList))
//            sendDataByte2BT(activity, mBTService, printDataByteArray)
//        }
//        catch (e: UnsupportedEncodingException) {
//            e.printStackTrace()
//        }
//
//    }

//    fun printCredit(activity: Activity, customerName:String, cus_address:String, invoiceNumber:String, townshipName:String, salePersonName:String, routeId:Int, creditInvoiceList:CreditInvoice) {
//
//        var portInfoList:List<PortInfo>? = null
//
//        try
//        {
//
//            portInfoList = StarIOPort.searchPrinter("BT:Star")
//        }
//        catch (e: StarIOPortException) {
//
//            e.printStackTrace()
//        }
//
//        if (portInfoList == null || portInfoList!!.size == 0)
//        {
//
//            return
//        }
//
//        val availableBluetoothPrinterNameList = ArrayList<String>()
//        for (portInfo in portInfoList!!)
//        {
//
//            availableBluetoothPrinterNameList.add(portInfo.portName)
//        }
//        val arrayAdapter = ArrayAdapter(
//            activity, R.layout.select_dialog_singlechoice, availableBluetoothPrinterNameList)
//        android.app.AlertDialog.Builder(activity)
//            .setTitle("Select Printer")
//            .setNegativeButton("Cancel", null)
//            .setAdapter(arrayAdapter, DialogInterface.OnClickListener { dialog, position ->
//                var starIOPort: StarIOPort? = null
//                try {
//
//                    starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position)!!, "mini", 10000)
//                    if (starIOPort!!.retreiveStatus().offline) {
//
//                        if (!starIOPort!!.retreiveStatus().compulsionSwitch) {
//
//                            showToast(activity, "The Drawer is offline\nCash Drawer: Close")
//                        } else {
//
//                            showToast(activity, "The Drawer is offline\nCash Drawer: Open")
//                        }
//
//                        return@OnClickListener
//                    } else {
//
//                        if (starIOPort!!.retreiveStatus().compulsionSwitch) {
//
//                            showToast(activity, "The Drawer is online\nCash Drawer: Open")
//                        } else {
//
//                            val printDataByteArray = convertFromListByteArrayTobyteArray(
//                                getPrintDataByteArrayListForCredit(
//                                    activity, customerName, cus_address, invoiceNumber, townshipName, salePersonName, routeId, creditInvoiceList))
//                            starIOPort!!.writePort(printDataByteArray, 0, printDataByteArray.size)
//                        }
//                    }
//                } catch (e: StarIOPortException) {
//
//                    showToast(activity, "Failed to connect to drawer")
//                    e.printStackTrace()
//                } catch (e: UnsupportedEncodingException) {
//                    e.printStackTrace()
//                } finally {
//                    if (starIOPort != null) {
//
//                        try {
//
//                            StarIOPort.releasePort(starIOPort)
//                        } catch (e: StarIOPortException) {
//
//                            e.printStackTrace()
//                        }
//
//                    }
//                }
//            })
//            .show()
//    }

//    fun printDailyReportForSaleMan(activity: Activity, saleManDailyReport:SaleManDailyReport) {
//
//        var portInfoList:List<PortInfo>? = null
//
//        try
//        {
//
//            portInfoList = StarIOPort.searchPrinter("BT:Star")
//        }
//        catch (e: StarIOPortException) {
//
//            e.printStackTrace()
//        }
//
//        if (portInfoList == null || portInfoList!!.size == 0)
//        {
//
//            return
//        }
//
//        val availableBluetoothPrinterNameList = ArrayList<String>()
//        for (portInfo in portInfoList!!)
//        {
//
//            availableBluetoothPrinterNameList.add(portInfo.portName)
//        }
//        val arrayAdapter = ArrayAdapter(
//            activity, R.layout.select_dialog_singlechoice, availableBluetoothPrinterNameList)
//        android.app.AlertDialog.Builder(activity)
//            .setTitle("Select Printer")
//            .setNegativeButton("Cancel", null)
//            .setAdapter(arrayAdapter, DialogInterface.OnClickListener { dialog, position ->
//                var starIOPort: StarIOPort? = null
//                try {
//
//                    starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position)!!, "mini", 10000)
//                    if (starIOPort!!.retreiveStatus().offline) {
//
//                        if (!starIOPort!!.retreiveStatus().compulsionSwitch) {
//
//                            showToast(activity, "The Drawer is offline\nCash Drawer: Close")
//                        } else {
//
//                            showToast(activity, "The Drawer is offline\nCash Drawer: Open")
//                        }
//
//                        return@OnClickListener
//                    } else {
//
//                        if (starIOPort!!.retreiveStatus().compulsionSwitch) {
//
//                            showToast(activity, "The Drawer is online\nCash Drawer: Open")
//                        } else {
//
//                            val printDataByteArray = convertFromListByteArrayTobyteArray(
//                                getPrintDataByteArrayListForDailyReport(
//                                    activity, saleManDailyReport))
//                            starIOPort!!.writePort(printDataByteArray, 0, printDataByteArray.size)
//                        }
//                    }
//                } catch (e: StarIOPortException) {
//
//                    showToast(activity, "Failed to connect to drawer")
//                    e.printStackTrace()
//                } catch (e: UnsupportedEncodingException) {
//                    e.printStackTrace()
//                } finally {
//                    if (starIOPort != null) {
//
//                        try {
//
//                            StarIOPort.releasePort(starIOPort)
//                        } catch (e: StarIOPortException) {
//
//                            e.printStackTrace()
//                        }
//
//                    }
//                }
//            })
//            .show()
//    }


//    fun printDeliverWithHSPOS(activity: Activity, customerName:String, cus_address:String, orderInvoiceNo:String, orderSaleManName:String, invoiceNumber:String, salePersonName:String, routeId:Int, townshipName:String, invoice:Invoice, soldProductList:List<SoldProduct>, presentList:List<Promotion>, printFor:String, mode:String, mBTService:BluetoothService) {
//        try
//        {
//
//
//            val printDataByteArray = convertFromListByteArrayTobyteArray(
//                getPrintDataByteArrayListDeliver(
//                    activity, customerName, cus_address, orderInvoiceNo, orderSaleManName, invoiceNumber, salePersonName, routeId, townshipName, invoice, soldProductList, presentList, printFor, mode))
//            sendDataByte2BT(activity, mBTService, printDataByteArray)
//        }
//        catch (e: UnsupportedEncodingException) {
//            e.printStackTrace()
//        }
//
//    }

//    fun printDeliver(activity: Activity, customerName:String, cus_address:String, orderInvoiceNo:String, orderSaleManName:String, invoiceNumber:String, salePersonName:String, routeId:Int, townshipName:String, invoice:Invoice, soldProductList:List<SoldProduct>, presentList:List<Promotion>, printFor:String, mode:String) {
//
//        var portInfoList:List<PortInfo>? = null
//
//        try
//        {
//
//            portInfoList = StarIOPort.searchPrinter("BT:Star")
//        }
//        catch (e: StarIOPortException) {
//
//            e.printStackTrace()
//        }
//
//        if (portInfoList == null || portInfoList!!.size == 0)
//        {
//
//            return
//        }
//
//        val availableBluetoothPrinterNameList = ArrayList<String>()
//        for (portInfo in portInfoList!!)
//        {
//
//            availableBluetoothPrinterNameList.add(portInfo.portName)
//        }
//        val arrayAdapter = ArrayAdapter(
//            activity, R.layout.select_dialog_singlechoice, availableBluetoothPrinterNameList)
//        android.app.AlertDialog.Builder(activity)
//            .setTitle("Select Printer")
//            .setNegativeButton("Cancel", null)
//            .setAdapter(arrayAdapter, DialogInterface.OnClickListener { dialog, position ->
//                var starIOPort: StarIOPort? = null
//                try {
//
//                    starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position)!!, "mini", 10000)
//                    if (starIOPort!!.retreiveStatus().offline) {
//
//                        if (!starIOPort!!.retreiveStatus().compulsionSwitch) {
//
//                            showToast(activity, "The Drawer is offline\nCash Drawer: Close")
//                        } else {
//
//                            showToast(activity, "The Drawer is offline\nCash Drawer: Open")
//                        }
//
//                        return@OnClickListener
//                    } else {
//
//                        if (starIOPort!!.retreiveStatus().compulsionSwitch) {
//
//                            showToast(activity, "The Drawer is online\nCash Drawer: Open")
//                        } else {
//
//                            val printDataByteArray = convertFromListByteArrayTobyteArray(
//                                getPrintDataByteArrayListDeliver(
//                                    activity, customerName, cus_address, orderInvoiceNo, orderSaleManName, invoiceNumber, salePersonName, routeId, townshipName, invoice, soldProductList, presentList, printFor, mode))
//                            starIOPort!!.writePort(printDataByteArray, 0, printDataByteArray.size)
//                        }
//                    }
//                } catch (e: StarIOPortException) {
//
//                    showToast(activity, "Failed to connect to drawer")
//                    e.printStackTrace()
//                } catch (e: UnsupportedEncodingException) {
//                    e.printStackTrace()
//                } finally {
//                    if (starIOPort != null) {
//
//                        try {
//
//                            StarIOPort.releasePort(starIOPort)
//                        } catch (e: StarIOPortException) {
//
//                            e.printStackTrace()
//                        }
//
//                    }
//                }
//            })
//            .show()
//    }

    private fun showToast(activity: Activity, message: String) {

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    @Throws(UnsupportedEncodingException::class)
//    private fun getPrintDataByteArrayList(activity: Activity, customerName:String, cus_address:String, invoiceNumber:String, salePersonName:String, routeId:Int, townshipName:String, invoice:Invoice, soldProductList:List<SoldProduct>, presentList:List<Promotion>?, printFor:String, mode:String, imgByte:ByteArray?):List<ByteArray> {
//
//
//        val printDataByteArrayList = ArrayList<ByteArray>()
//        printDataByteArrayList.add(imgByte)
//        printDataByteArrayList.add("\n".toByteArray())
//
//        val FocProductList = ArrayList<SoldProduct>()
//
//        val decimalFormatterWithoutComma = DecimalFormat("##0.##")
//        val decimalFormatterWithComma = DecimalFormat("###,##0.##")
//
//        var totalAmount = 0.0
//        var totalNetAmount = 0.0
//        totalDiscountAmt = 0.0
//
//        var companyName = ""
//        var address = ""
//        var txtForFooter = ""
//        var commTaxRegNo = ""
//        var phNo = ""
//        val cus = database!!.rawQuery("SELECT * FROM " + DatabaseContract.CompanyInformation.tb, null)
//        if (cus != null)
//        {
//            while (cus!!.moveToNext())
//            {
//                companyName = cus!!.getString(cus!!.getColumnIndex(DatabaseContract.CompanyInformation.CompanyName))
//                commTaxRegNo = cus!!.getString(57)
//                address = cus!!.getString(cus!!.getColumnIndex(DatabaseContract.CompanyInformation.Address))
//                txtForFooter = cus!!.getString(cus!!.getColumnIndex(DatabaseContract.CompanyInformation.POSVoucherFooter1))
//                phNo = cus!!.getString(cus!!.getColumnIndex(DatabaseContract.CompanyInformation.PhoneNumber))
//
//            }
//            cus!!.close()
//        }
//
//        /*String[] companyNames = companyName.split(" ");
//               String names = "         ", fullName = "";
//               for (String s : companyNames) {
//                   if (names.length() < 30) {
//                       names += s + " ";
//                   } else {
//                       fullName += (names + "         ");
//                       names = "\n         " + s;
//                   }
//               }
//               fullName += names;*/
//
//        printDataByteArrayList.add((address + "\n").toByteArray())
//        //        printDataByteArrayList.add((commTaxRegNo + "\n").getBytes());
//        printDataByteArrayList.add(("Ph No         :   $phNo\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Tax Reg No    :   $commTaxRegNo\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Customer      :   $customerName\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Township      :   $townshipName\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Address       :   $cus_address\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Invoice No    :   $invoiceNumber\n").toByteArray())
//        printDataByteArrayList.add(("Sale Person   :   $salePersonName\n").toByteArray())
//        printDataByteArrayList.add(("RouteNo       :   " + Utils.getRouteName(activity, routeId) + "\n").toByteArray())
//        printDataByteArrayList.add((("Sale Date     :   " + SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US)
//            .format(Date()) + "\n")).toByteArray())
//        if (invoice.getDueDate() != null && invoice.getPrintmode().equalsIgnoreCase("sale"))
//            printDataByteArrayList.add(("Delivery Date :   " + invoice.getDueDate() + "\n").getBytes())
//        printDataByteArrayList.add("------------------------------------------------\n".toByteArray())
//
//        var promoFlg = false
//
//        for (soldProduct in soldProductList)
//        {
//            if (soldProduct.getPromotionPrice() === 0.0)
//            {
//                promoFlg = false
//                break
//            }
//            else
//            {
//                promoFlg = true
//                break
//            }
//        }
//
//        // print format with promo price
//        if (promoFlg)
//        {
//            formatter = Formatter(StringBuilder(), Locale.US)
//
//            /* printDataByteArrayList.add(
//                               formatter.format(
//                                       "%1$-10s \t \t %2$8s \t \t %3$5s \t \t %4$5s \t \t %5$7s\n"
//                                       , "Item"
//                                       , "Qty"
//                                       , "Price"
//                                       , "Pro:Price"
//                                       , "Amount").toString().getBytes());*/
//            printDataByteArrayList.add(
//                formatter!!.format(
//                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n", "Item", "Qty", "Price", "Amount").toString().toByteArray())
//            formatter!!.close()
//            printDataByteArrayList.add("------------------------------------------------\n".toByteArray())
//
//
//            for (soldProduct in soldProductList)
//            {
//
//                val name = String()
//                val quantity = soldProduct.getQuantity()
//                var pricePerUnit = 0.0
//                val promoPrice = 0.0
//
//                if (soldProduct.getPromotionPrice() === 0.0)
//                {
//                    pricePerUnit = soldProduct.getProduct().getPrice()
//                }
//                else
//                {
//                    pricePerUnit = soldProduct.getPromotionPrice()
//                }
//
//                //                pricePerUnit = (double) Math.round(pricePerUnit);
//                val amount = soldProduct.getTotalAmount()
//                //                double amount = soldProduct.getQuantity() * pricePerUnit;
//                val pricePerUnitWithDiscount:Double
//                val netAmount:Double
//
//                val discount = soldProduct.getDiscount(activity)
//                val itemFocPercent = soldProduct.getFocPercent()
//                val itemFocAmount = soldProduct.getFocAmount()
//                val itemFocDiscountAmt = soldProduct.getItemDiscountAmount()
//
//                pricePerUnitWithDiscount = soldProduct.getDiscountAmount()
//                netAmount = amount - pricePerUnitWithDiscount
//
//                totalAmount += amount
//                totalNetAmount += netAmount
//
//                val nameFragments = soldProduct.getProduct().getName().split(" ")
//                val nameList = setupPrintLayoutWithPromo(nameFragments)
//
//                if (amount != 0.0)
//                {
//                    if (printFor == Utils.PRINT_FOR_PRE_ORDER)
//                    {
//                        formatter = Formatter(StringBuilder(), Locale.US)
//                        printDataByteArrayList.add(
//                            formatter!!.format(
//                                "%1$-20s \t \t  %2$4s \t \t %3$5s \t \t %4$9s\n",
//                                nameList[0], quantity, decimalFormatterWithoutComma.format(pricePerUnit), decimalFormatterWithComma.format(amount)).toString().toByteArray())
//
//                        formatter!!.close()
//                    }
//
//                    if (printFor != Utils.PRINT_FOR_PRE_ORDER)
//                    {
//
//                        formatter = Formatter(StringBuilder(), Locale.US)
//                        var nameTxt = ""
//                        if (nameList.size > 0)
//                            nameTxt = nameList[0]
//                        printDataByteArrayList.add(
//                            formatter!!.format(
//                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n", nameTxt, quantity, decimalFormatterWithoutComma.format(pricePerUnit), decimalFormatterWithComma.format(netAmount)).toString().toByteArray())
//
//
//                    }
//
//                    if (nameList.size > 0)
//                    {
//                        nameList.removeAt(0)
//                        for (cutName in nameList)
//                        {
//                            formatter = Formatter(StringBuilder(), Locale.US)
//                            printDataByteArrayList.add(
//                                formatter!!.format(
//                                    "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n", cutName, "", "", "").toString().toByteArray())
//
//                            formatter!!.close()
//                        }
//                    }
//                    //                    printDataByteArrayList.add("\n".getBytes());
//
//                    if (itemFocPercent > 0 || itemFocAmount > 0)
//                    {
//                        val disItemAmt = itemFocDiscountAmt * soldProduct.getQuantity()
//                        var text = "Discount---" + decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
//                        if (itemFocAmount > 0)
//                        {
//                            text = "Discount---" + decimalFormatterWithoutComma.format(itemFocAmount) + "---"
//                        }
//                        if (itemFocPercent > 0 && itemFocPercent > 0)
//                        {
//                            text = "Discount---" + decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
//                        }
//                        formatter = Formatter(StringBuilder(), Locale.US)
//                        printDataByteArrayList.add(formatter!!.format(
//                            "%1$-20s \t \t %2$2s \t \t %3$1s \t \t \t %4$9s\n", text, "", " (" + decimalFormatterWithoutComma.format(disItemAmt) + ") ", "").toString().toByteArray())
//                        formatter!!.close()
//                        totalDiscountAmt += disItemAmt
//                    }
//
//                    printDataByteArrayList.add("\n".toByteArray())
//                }
//                else
//                {
//                    //                    double amt = soldProduct.getTotalAmt();
//                    FocProductList.add(soldProduct)
//                }
//
//            }
//
//            /* if (presentList != null && presentList.size() > 0) {
//
//                        *//* PRESENT LIST *//*
//                for (Promotion invoicePresent : presentList) {
//                    {
//                        String name = new String();
//                        int quantity = invoicePresent.getPromotionQty();
//                        double presentPrice = 0.0;
//                        double promoPrice = 0.0;
//                        // Shorthand the name.
//                        String productName = getProductNameAndPrice(invoicePresent);
//
//                        String[] nameFragments = productName.split(" ");
//
//                        List<String> nameList = setupPrintLayoutWithPromo(nameFragments);
//
//                        if (printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {
//                            formatter = new Formatter(new StringBuilder(), Locale.US);
//                            printDataByteArrayList.add(
//                                    formatter.format(
//                                            "%1$-10s \t \t %2$6s \t \t %3$5s \t \t %4$6s \t \t %5$9s\n"
//                                            , nameList.get(0)
//                                            , quantity
//                                            , decimalFormatterWithComma.format(presentPrice)
//                                            , decimalFormatterWithoutComma.format(promoPrice)
//                                            , "0.0").toString().getBytes());
//                            formatter.close();
//                        }
//
//                        if (!printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {
//
//                            formatter = new Formatter(new StringBuilder(), Locale.US);
//                            printDataByteArrayList.add(
//                                    formatter.format(
//                                            "%1$-10s \t \t %2$6s \t \t %3$5s \t \t %4$6s \t \t %5$9s\n"
//                                            , nameList.get(0)
//                                            , quantity
//                                            , decimalFormatterWithComma.format(presentPrice)
//                                            , decimalFormatterWithoutComma.format(promoPrice)
//                                            , "0.0").toString().getBytes());
//                            formatter.close();
//                        }
//
//                        nameList.remove(0);
//                        for (String cutName : nameList) {
//                            formatter = new Formatter(new StringBuilder(), Locale.US);
//                            printDataByteArrayList.add(
//                                    formatter.format(
//                                            "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s \t \t %5$1s\n"
//                                            , cutName
//                                            , ""
//                                            , ""
//                                            , ""
//                                            , "").toString().getBytes());
//
//                            formatter.close();
//                        }
//
//                        printDataByteArrayList.add("\n".getBytes());
//                    }
//                }
//
//            }*/
//            /* END OF PRESENT LIST */
//        }
//        else
//        {
//            formatter = Formatter(StringBuilder(), Locale.US)
//
//            printDataByteArrayList.add(
//                formatter!!.format(
//                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n", "Item", "Qty", "Price", "Amount").toString().toByteArray())
//            formatter!!.close()
//            printDataByteArrayList.add("------------------------------------------------\n".toByteArray())
//
//            FocProductList.clear()
//            for (soldProduct in soldProductList)
//            {
//
//                val name = String()
//                val quantity = soldProduct.getQuantity()
//                var pricePerUnit = 0.0
//                val promoPrice = 0.0
//
//                if (soldProduct.getPromotionPrice() === 0.0)
//                {
//                    pricePerUnit = soldProduct.getProduct().getPrice()
//                }
//                else
//                {
//                    pricePerUnit = soldProduct.getPromotionPrice()
//                }
//
//                val amount = soldProduct.getTotalAmount()
//                //                double amount = pricePerUnit * soldProduct.getQuantity(); //soldProduct.getTotalAmount();
//                val pricePerUnitWithDiscount:Double
//                val netAmount:Double
//
//                val discount = soldProduct.getDiscount(activity)
//                val itemFocPercent = soldProduct.getFocPercent()
//                val itemFocAmount = soldProduct.getFocAmount()
//                val itemFocDiscountAmt = soldProduct.getItemDiscountAmount()
//                pricePerUnitWithDiscount = soldProduct.getDiscountAmount()
//                netAmount = soldProduct.getTotalAmount() - pricePerUnitWithDiscount
//
//                totalAmount += amount
//                totalNetAmount += netAmount
//
//                //                soldProduct.getProduct().setName("Supper kiddy Diaper Large (pieces)");
//                val nameFragments = soldProduct.getProduct().getName().split(" ")
//                val nameList = setupPrintLayoutNoPromo(nameFragments)
//
//                if (amount != 0.0)
//                {
//                    if (printFor == Utils.PRINT_FOR_PRE_ORDER)
//                    {
//                        formatter = Formatter(StringBuilder(), Locale.US)
//                        printDataByteArrayList.add(
//                            formatter!!.format(
//                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
//                                nameList[0], quantity, decimalFormatterWithoutComma.format(pricePerUnit), decimalFormatterWithComma.format(amount)).toString().toByteArray())
//
//                        formatter!!.close()
//                    }
//
//                    if (printFor != Utils.PRINT_FOR_PRE_ORDER)
//                    {
//
//                        formatter = Formatter(StringBuilder(), Locale.US)
//                        var nameTxt = ""
//                        if (nameList.size > 0)
//                            nameTxt = nameList[0]
//                        printDataByteArrayList.add(
//                            formatter!!.format(
//                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n", nameTxt, quantity, decimalFormatterWithoutComma.format(pricePerUnit), decimalFormatterWithComma.format(netAmount)).toString().toByteArray())
//
//                    }
//
//                    if (nameList.size > 0)
//                    {
//                        nameList.removeAt(0)
//                        for (cutName in nameList)
//                        {
//                            formatter = Formatter(StringBuilder(), Locale.US)
//                            printDataByteArrayList.add(
//                                formatter!!.format(
//                                    "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n", cutName, "", "", "").toString().toByteArray())
//
//                            formatter!!.close()
//                        }
//                    }
//                    //                    printDataByteArrayList.add("\n".getBytes());
//
//                    if (itemFocPercent > 0 || itemFocAmount > 0)
//                    {
//                        val disItemAmt = itemFocDiscountAmt * soldProduct.getQuantity()
//                        var text = "Discount---" + decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
//                        if (itemFocAmount > 0)
//                        {
//                            text = "Discount---" + decimalFormatterWithoutComma.format(itemFocAmount) + "---"
//                        }
//                        if (itemFocPercent > 0 && itemFocPercent > 0)
//                        {
//                            text = "Discount---" + decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
//                        }
//                        formatter = Formatter(StringBuilder(), Locale.US)
//                        printDataByteArrayList.add(formatter!!.format(
//                            "%1$-20s \t \t %2$2s \t \t %3$1s \t \t \t %4$9s\n", text, "", " (" + decimalFormatterWithoutComma.format(disItemAmt) + ") ", "").toString().toByteArray())
//                        formatter!!.close()
//                        totalDiscountAmt += disItemAmt
//                    }
//
//                    printDataByteArrayList.add("\n".toByteArray())
//                }
//                else
//                {
//
//                    FocProductList.add(soldProduct)
//                }
//            }
//
//            /*if (presentList != null && presentList.size() > 0) {
//
//                        *//* PRESENT LIST *//*
//                for (Promotion invoicePresent : presentList) {
//                    {
//
//                        String name = new String();
//                        int quantity = invoicePresent.getPromotionQty();
//                        double presentPrice = 0.0;
//                        double promoPrice = 0.0;
//                        // Shorthand the name.
//                        String productName = getProductNameAndPrice(invoicePresent);
//
//                        String[] nameFragments = productName.split(" ");
//                        List<String> nameList = setupPrintLayoutNoPromo(nameFragments);
//
//                        if (printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {
//                            formatter = new Formatter(new StringBuilder(), Locale.US);
//                            printDataByteArrayList.add(
//                                    formatter.format(
//                                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n"
//                                            , nameList.get(0)
//                                            , quantity
//                                            , decimalFormatterWithComma.format(presentPrice)
//                                            , "0.0").toString().getBytes());
//                            formatter.close();
//                        }
//
//                        if (!printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {
//
//                            formatter = new Formatter(new StringBuilder(), Locale.US);
//                            printDataByteArrayList.add(
//                                    formatter.format(
//                                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n"
//                                            , nameList.get(0)
//                                            , quantity
//                                            , decimalFormatterWithComma.format(presentPrice)
//                                            , "0.0").toString().getBytes());
//                            formatter.close();
//                        }
//
//                        nameList.remove(0);
//                        for (String cutName : nameList) {
//                            formatter = new Formatter(new StringBuilder(), Locale.US);
//                            printDataByteArrayList.add(
//                                    formatter.format(
//                                            "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n"
//                                            , cutName
//                                            , ""
//                                            , ""
//                                            , "").toString().getBytes());
//
//                            formatter.close();
//                        }
//
////                        printDataByteArrayList.add("\n".getBytes());
//                    }
//                }
//
//            }*/
//            /* END OF PRESENT LIST */
//        }// print format with no promo price
//
//        printDataByteArrayList.add("------------------------------------------------\n".toByteArray())
//
//        formatter = Formatter(StringBuilder(), Locale.US)
//
//        getTaxAmount()
//
//        var taxText = ""
//        if (taxType!!.equals("E", ignoreCase = true))
//        {
//            taxText = "(Tax " + invoice.getTaxAmount() + " Excluded)"
//            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt() + invoice.getTaxAmount()
//        }
//        else
//        {
//            taxText = "(Tax " + invoice.getTaxAmount() + " Included)"
//            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt()
//        }
//
//        if (printFor == Utils.PRINT_FOR_PRE_ORDER)
//        {
//
//            printDataByteArrayList.add(
//                formatter!!.format("%1$-13s%2$21s\n%3$-15s%4$21s\n%5$-13s%6$21s\n\n\n", "Total Amount      :        ", decimalFormatterWithComma.format(totalAmount), taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/, "Total Discount    :        ", decimalFormatterWithComma.format(totalDiscountAmt), "Net Amount        :        ", decimalFormatterWithComma.format(invoice.getTotalPayAmt()), "Pay Amount        :        ", decimalFormatterWithComma.format(invoice.getTotalPayAmt()))//                            , "Total Discount        :", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoice.getDiscountPercent()) + "%)"
//                    .toString().toByteArray())
//        }
//        else if (mode == Utils.FOR_DELIVERY)
//        {
//
//            printDataByteArrayList.add(
//                formatter!!.format("%1$-13s%2$21s\n%3$-13s%4$21s\n%5$-13s%6$21s\n%7$-13s%8$21s\n%9$-13s%10$21s\n\n\n", "Total Amount      :        ", decimalFormatterWithComma.format(totalAmount), taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/, "Total Discount    :        ", decimalFormatterWithoutComma.format(totalDiscountAmt), "Net Amount        :        ", decimalFormatterWithComma.format(totalNetAmount), "Pay Amount        :        ", decimalFormatterWithComma.format(invoice.getTotalPayAmt()))//                            , "Discount        :", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoice.getDiscountPercent()) + "%)"
//                    .toString().toByteArray())
//        }
//        else
//        {
//            var crediBalance:Double? = 0.0
//            if (totalNetAmount > invoice.getTotalPayAmt())
//            {
//                crediBalance = totalNetAmount - invoice.getTotalPayAmt()
//            }
//            var refundAmt = 0.0
//            if (invoice.getTotalRefundAmt() != null)
//                refundAmt = invoice.getTotalRefundAmt()
//
//            printDataByteArrayList.add(
//                formatter!!.format("%1$-13s%2$21s\n%3$-13s%4$21s\n%5$-13s%6$21s\n%7$-13s%8$21s\n%9$-13s%10$21s\n%11$-13s%12$21s\n%13$-13s%14$21s\n%15$-13s%16$21s\n", "Total Amount      :        ", decimalFormatterWithComma.format(totalAmount), taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + new DecimalFormat("#0.00").format(taxPercent) + "%)"*/, "Total Discount    :        ", decimalFormatterWithComma.format(totalDiscountAmt), "Vol: Discount     :        ", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()), "Net Amount        :        ", decimalFormatterWithComma.format(totalNetAmount), "Pay Amount        :        ", decimalFormatterWithComma.format(invoice.getTotalPayAmt()), "Credit Balance    :        ", decimalFormatterWithComma.format(Math.abs(crediBalance!!)), "Refund            :        ", decimalFormatterWithComma.format(refundAmt))//                            , "Discount        :        ", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoice.getDiscountPercent()) + "%)"
//                    .toString().toByteArray())
//        }
//
//        printDataByteArrayList.add("\n".toByteArray())
//
//
//        //For FOC Item List
//
//        if (presentList != null && presentList!!.size > 0)
//        {
//
//            formatter = Formatter(StringBuilder(), Locale.US)
//
//            printDataByteArrayList.add(
//                formatter!!.format(
//                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n", "Foc", "Qty", "Price", "Amount").toString().toByteArray())
//            formatter!!.close()
//            printDataByteArrayList.add("------------------------------------------------\n".toByteArray())
//
//            /* PRESENT LIST */
//            for (invoicePresent in presentList!!)
//            {
//
//
//                val name = String()
//                val quantity = invoicePresent.getPromotionQty()
//                val presentPrice = 0.0
//                val promoPrice = 0.0
//                // Shorthand the name.
//                val productName = getProductNameAndPrice(invoicePresent)
//
//                val nameFragments = productName!!.split((" ").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                val nameList = setupPrintLayoutNoPromo(nameFragments)
//
//                if (printFor == Utils.PRINT_FOR_PRE_ORDER)
//                {
//                    formatter = Formatter(StringBuilder(), Locale.US)
//                    printDataByteArrayList.add(
//                        formatter!!.format(
//                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
//                            nameList[0], quantity, decimalFormatterWithComma.format(presentPrice), "0.0").toString().toByteArray())
//                    formatter!!.close()
//                }
//
//                if (printFor != Utils.PRINT_FOR_PRE_ORDER)
//                {
//
//                    formatter = Formatter(StringBuilder(), Locale.US)
//                    printDataByteArrayList.add(
//                        formatter!!.format(
//                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
//                            nameList[0], quantity, decimalFormatterWithComma.format(presentPrice), "0.0").toString().toByteArray())
//                    formatter!!.close()
//                }
//
//                nameList.removeAt(0)
//                for (cutName in nameList)
//                {
//                    formatter = Formatter(StringBuilder(), Locale.US)
//                    printDataByteArrayList.add(
//                        formatter!!.format(
//                            "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n", cutName, "", "", "").toString().toByteArray())
//
//                    formatter!!.close()
//                }
//
//                printDataByteArrayList.add("\n".toByteArray())
//
//            }
//
//        }
//        /*  if(FocProductList.size() > 0) {
//
//                   formatter = new Formatter(new StringBuilder(), Locale.US);
//
//                   printDataByteArrayList.add(
//                           formatter.format(
//                                   "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n"
//                                   , "Foc"
//                                   , "Qty"
//                                   , "Price"
//                                   , "Amount").toString().getBytes());
//                   formatter.close();
//                   printDataByteArrayList.add("------------------------------------------------\n".getBytes());
//
//                   for(int i = 0; i < FocProductList.size(); i++) {
//                       SoldProduct soldProduct = FocProductList.get(i);
//                       String name = new String();
//                       int quantity = soldProduct.getQuantity();
//                       double pricePerUnit = 0.0;
//                       pricePerUnit = soldProduct.getProduct().getPrice();
//                       double amount = soldProduct.getTotalAmount();
//                       double pricePerUnitWithDiscount;
//                       double netAmount;
//
//                       pricePerUnitWithDiscount = soldProduct.getDiscountAmount();
//                       netAmount = soldProduct.getTotalAmount() - pricePerUnitWithDiscount;
//
//                       totalAmount += amount;
//                       totalNetAmount += netAmount;
//
//                       String[] nameFragments = soldProduct.getProduct().getName().split(" ");
//                       List<String> nameList = setupPrintLayoutNoPromo(nameFragments);
//
//                       formatter = new Formatter(new StringBuilder(), Locale.US);
//                       String nameTxt = "";
//                       if (nameList.size() > 0)
//                           nameTxt = nameList.get(0);
//                       printDataByteArrayList.add(
//                               formatter.format(
//                                       "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n"
//                                       , nameTxt
//                                       , quantity
//                                       , decimalFormatterWithoutComma.format(pricePerUnit)
//                                       , decimalFormatterWithComma.format(netAmount)).toString().getBytes());
//
//                       formatter.close();
//
//                       if (nameList.size() > 0) {
//                           nameList.remove(0);
//                           for (String cutName : nameList) {
//                               formatter = new Formatter(new StringBuilder(), Locale.US);
//                               printDataByteArrayList.add(
//                                       formatter.format(
//                                               "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n"
//                                               , cutName
//                                               , ""
//                                               , ""
//                                               , "").toString().getBytes());
//
//                               formatter.close();
//                           }
//                       }
//
//                       printDataByteArrayList.add("\n".getBytes());
//                   }
//               }
//
//       */
//
//        printDataByteArrayList.add(("\n\n              $txtForFooter\n\n").toByteArray())
//        printDataByteArrayList.add(("\nSignature       :\n\n                 Thank You. \n\n").toByteArray())
//        printDataByteArrayList.add("\n".toByteArray())
//        //        printDataByteArrayList.add(new byte[]{0x1b, 0x64, 0x02}); // Cut
//        //        printDataByteArrayList.add(new byte[]{0x07}); // Kick cash drawer
//
//        return printDataByteArrayList
//    }

//    @Throws(UnsupportedEncodingException::class)
//    private fun getPrintDataByteArrayListDeliver(activity: Activity, customerName:String, cus_address:String, orderInvoiceNo:String, orderSaleManName:String, invoiceNumber:String, salePersonName:String, routeId:Int, townshipName:String, invoice:Invoice, soldProductList:List<SoldProduct>, presentList:List<Promotion>?, printFor:String, mode:String):List<ByteArray> {
//
//        val printDataByteArrayList = ArrayList<ByteArray>()
//
//        val decimalFormatterWithoutComma = DecimalFormat("##0.##")
//        val decimalFormatterWithComma = DecimalFormat("###,##0.##")
//
//        var totalAmount = 0.0
//        var totalNetAmount = 0.0
//
//        var companyName = ""
//        var address = ""
//        var companyTaxRegNo = ""
//        var txtForFooter = ""
//        var phNo = ""
//        val companyInfoCursor = database!!.rawQuery("SELECT * FROM " + DatabaseContract.CompanyInformation.tb, null)
//        if (companyInfoCursor.moveToNext())
//        {
//            companyName = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.CompanyName))
//            address = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.Address))
//            txtForFooter = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.POSVoucherFooter1))
//            companyTaxRegNo = companyInfoCursor.getString(57)
//            phNo = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.PhoneNumber))
//
//        }
//        companyInfoCursor.close()
//
//        /*String[] companyNames = companyName.split(" ");
//               String names = "         ", fullName = "";
//               for (String s : companyNames) {
//                   if (names.length() < 30) {
//                       names += s + " ";
//                   } else {
//                       fullName += (names + "         ");
//                       names = "\n         " + s;
//                   }
//               }
//               fullName += names;*/
//
//        printDataByteArrayList.add((address + "\n").toByteArray())
//        printDataByteArrayList.add(("Ph No           :  $phNo\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add((companyTaxRegNo + "\n").toByteArray())
//        printDataByteArrayList.add(("Customer        :  $customerName\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Township        :  $townshipName\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Address         :  $cus_address\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Order Invoice   :  $orderInvoiceNo\n").toByteArray())
//        printDataByteArrayList.add(("Order Person    :  $orderSaleManName\n").toByteArray())
//        printDataByteArrayList.add(("Delivery No     :  $invoiceNumber\n").toByteArray())
//        printDataByteArrayList.add(("Delivery Person :  $salePersonName\n").toByteArray())
//        printDataByteArrayList.add(("RouteNo         :  " + Utils.getRouteName(activity, routeId) + "\n").toByteArray())
//        printDataByteArrayList.add((("Delivery Date   :  " + SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US)
//            .format(Date()) + "\n")).toByteArray())
//        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
//
//        var promoFlg = false
//
//        for (soldProduct in soldProductList)
//        {
//            if (soldProduct.getPromotionPrice() === 0.0)
//            {
//                promoFlg = false
//                break
//            }
//            else
//            {
//                promoFlg = true
//                break
//            }
//        }
//
//        // print format with promo price
//        if (promoFlg)
//        {
//            formatter = Formatter(StringBuilder(), Locale.US)
//            printDataByteArrayList.add(
//                formatter!!.format(
//                    "%1$-10s \t %2$6s \t %3$5s \t %4$5s \t %5$7s\n", "Item", "Qty", "Price", "Pro:Price", "Amount").toString().toByteArray())
//            formatter!!.close()
//            printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
//
//            for (soldProduct in soldProductList)
//            {
//
//                val name = String()
//                val quantity = soldProduct.getQuantity()
//                var pricePerUnit = 0.0
//                var promoPrice = 0.0
//
//                pricePerUnit = soldProduct.getProduct().getPrice()
//                promoPrice = soldProduct.getPromotionPrice()
//                //}
//
//                val amount = soldProduct.getTotalAmount()
//                val pricePerUnitWithDiscount:Double
//                val netAmount:Double
//
//                val discount = soldProduct.getDiscount(activity)
//
//                pricePerUnitWithDiscount = soldProduct.getDiscountAmount()
//                netAmount = soldProduct.getTotalAmount() - pricePerUnitWithDiscount
//
//                totalAmount += amount
//                totalNetAmount += netAmount
//
//                // Shorthand the name.
//                val nameFragments = soldProduct.getProduct().getName().split(" ")
//                val nameList = setupPrintLayoutWithPromo(nameFragments)
//
//                if (printFor == Utils.PRINT_FOR_PRE_ORDER)
//                {
//                    formatter = Formatter(StringBuilder(), Locale.US)
//                    printDataByteArrayList.add(
//                        formatter!!.format(
//                            "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n",
//                            nameList[0], quantity, decimalFormatterWithoutComma.format(pricePerUnit), decimalFormatterWithoutComma.format(promoPrice), decimalFormatterWithComma.format(amount)).toString().toByteArray())
//                    formatter!!.close()
//                }
//
//                if (printFor != Utils.PRINT_FOR_PRE_ORDER)
//                {
//
//                    formatter = Formatter(StringBuilder(), Locale.US)
//                    printDataByteArrayList.add(
//                        formatter!!.format(
//                            "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n",
//                            nameList[0], quantity, decimalFormatterWithoutComma.format(pricePerUnit), decimalFormatterWithoutComma.format(promoPrice), decimalFormatterWithComma.format(netAmount)).toString().toByteArray())
//                    formatter!!.close()
//                }
//
//                nameList.removeAt(0)
//                for (cutName in nameList)
//                {
//                    formatter = Formatter(StringBuilder(), Locale.US)
//                    printDataByteArrayList.add(
//                        formatter!!.format(
//                            "%1$-20s \t %2$1s \t %3$1s \t %4$1s \t %5$1s\n", cutName, "", "", "", "").toString().toByteArray())
//
//                    formatter!!.close()
//                }
//
//                printDataByteArrayList.add("\n".toByteArray())
//            }
//            //printDataByteArrayList.add("----------------------------------------------\n".getBytes());
//
//            if (presentList != null && presentList!!.size > 0)
//            {
//
//                /* PRESENT LIST */
//                for (invoicePresent in presentList!!)
//                {
//                    run { val name = String()
//                        val quantity = invoicePresent.getPromotionQty()
//                        val presentPrice = 0.0
//                        val promoPrice = 0.0
//                        // Shorthand the name.
//                        val productName = getProductNameAndPrice(invoicePresent)
//
//                        val nameFragments = productName!!.split((" ").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//
//                        val nameList = setupPrintLayoutWithPromo(nameFragments)
//
//                        if (printFor == Utils.PRINT_FOR_PRE_ORDER) {
//                            formatter = Formatter(StringBuilder(), Locale.US)
//                            printDataByteArrayList.add(
//                                formatter!!.format(
//                                    "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n", nameList.get(0), quantity, decimalFormatterWithComma.format(presentPrice), decimalFormatterWithoutComma.format(promoPrice), "0.0").toString().toByteArray())
//                            formatter!!.close()
//                        }
//
//                        if (printFor != Utils.PRINT_FOR_PRE_ORDER) {
//
//                            formatter = Formatter(StringBuilder(), Locale.US)
//                            printDataByteArrayList.add(
//                                formatter!!.format(
//                                    "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n", nameList.get(0), quantity, decimalFormatterWithComma.format(presentPrice), decimalFormatterWithoutComma.format(promoPrice), "0.0").toString().toByteArray())
//                            formatter!!.close()
//                        }
//
//                        nameList.removeAt(0)
//                        for (cutName in nameList) {
//                            formatter = Formatter(StringBuilder(), Locale.US)
//                            printDataByteArrayList.add(
//                                formatter!!.format(
//                                    "%1$-20s \t %2$1s \t %3$1s \t %4$1s \t %5$1s\n", cutName, "", "", "", "").toString().toByteArray())
//
//                            formatter!!.close()
//                        }
//
//                        printDataByteArrayList.add("\n".toByteArray()) }
//                }
//                printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
//
//            }
//            /* END OF PRESENT LIST */
//        }
//        else
//        {
//            formatter = Formatter(StringBuilder(), Locale.US)
//
//            printDataByteArrayList.add(
//                formatter!!.format(
//                    "%1$-20s \t %2$4s \t %3$5s \t %4$7s\n", "Item", "Qty", "Price", "Amount").toString().toByteArray())
//            formatter!!.close()
//            printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
//
//            for (soldProduct in soldProductList)
//            {
//
//                val name = String()
//                val quantity = soldProduct.getQuantity()
//                var pricePerUnit = 0.0
//                var promoPrice = 0.0
//
//                //if(soldProduc0t.getPromotionPrice() == 0.0) {
//                pricePerUnit = soldProduct.getProduct().getPrice()
//                //} else {
//                promoPrice = soldProduct.getPromotionPrice()
//                //}
//
//                val amount = soldProduct.getTotalAmount()
//                val pricePerUnitWithDiscount:Double
//                val netAmount:Double
//
//                val discount = soldProduct.getDiscount(activity)
//
//                pricePerUnitWithDiscount = soldProduct.getDiscountAmount()
//                netAmount = soldProduct.getTotalAmount() - pricePerUnitWithDiscount
//
//                totalAmount += amount
//                totalNetAmount += netAmount
//
//                val nameFragments = soldProduct.getProduct().getName().split(" ")
//                val nameList = setupPrintLayoutNoPromo(nameFragments)
//
//                if (printFor == Utils.PRINT_FOR_PRE_ORDER)
//                {
//                    formatter = Formatter(StringBuilder(), Locale.US)
//                    printDataByteArrayList.add(
//                        formatter!!.format(
//                            "%1$-20s \t %2$4s \t %3$5s \t %4$9s\n\n",
//                            nameList[0], quantity, decimalFormatterWithoutComma.format(pricePerUnit), decimalFormatterWithComma.format(amount)).toString().toByteArray())
//
//                    formatter!!.close()
//                }
//
//                if (printFor != Utils.PRINT_FOR_PRE_ORDER)
//                {
//
//                    formatter = Formatter(StringBuilder(), Locale.US)
//                    printDataByteArrayList.add(
//                        formatter!!.format(
//                            "%1$-20s \t %2$4s \t %3$5s \t %4$9s\n\n",
//                            nameList[0], quantity, decimalFormatterWithoutComma.format(pricePerUnit), decimalFormatterWithComma.format(netAmount)).toString().toByteArray())
//
//
//                }
//
//                nameList.removeAt(0)
//                for (cutName in nameList)
//                {
//                    formatter = Formatter(StringBuilder(), Locale.US)
//                    printDataByteArrayList.add(
//                        formatter!!.format(
//                            "%1$-20s \t %2$1s \t %3$1s \t %4$1s\n", cutName, "", "", "").toString().toByteArray())
//
//                    formatter!!.close()
//                }
//
//                printDataByteArrayList.add("\n".toByteArray())
//
//            }
//
//            if (presentList != null && presentList!!.size > 0)
//            {
//
//                /* PRESENT LIST */
//                for (invoicePresent in presentList!!)
//                {
//                    run { val name = String()
//                        val quantity = invoicePresent.getPromotionQty()
//                        val presentPrice = 0.0
//                        val promoPrice = 0.0
//                        // Shorthand the name.
//                        val productName = getProductNameAndPrice(invoicePresent)
//
//                        val nameFragments = productName!!.split((" ").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                        val nameList = setupPrintLayoutNoPromo(nameFragments)
//
//                        if (printFor == Utils.PRINT_FOR_PRE_ORDER) {
//                            formatter = Formatter(StringBuilder(), Locale.US)
//                            printDataByteArrayList.add(
//                                formatter!!.format(
//                                    "%1$-20s \t %2$4s \t %3$5s \t %4$9s\n\n", nameList.get(0), quantity, decimalFormatterWithComma.format(presentPrice), "0.0").toString().toByteArray())
//                            formatter!!.close()
//                        }
//
//                        if (printFor != Utils.PRINT_FOR_PRE_ORDER) {
//
//                            formatter = Formatter(StringBuilder(), Locale.US)
//                            printDataByteArrayList.add(
//                                formatter!!.format(
//                                    "%1$-20s \t %2$4s \t %3$5s \t %4$9s\n\n", nameList.get(0), quantity, decimalFormatterWithComma.format(presentPrice), "0.0").toString().toByteArray())
//                            formatter!!.close()
//                        }
//
//                        nameList.removeAt(0)
//                        for (cutName in nameList) {
//                            formatter = Formatter(StringBuilder(), Locale.US)
//                            printDataByteArrayList.add(
//                                formatter!!.format(
//                                    "%1$-20s \t %2$1s \t %3$1s \t %4$1s\n", cutName, "", "", "").toString().toByteArray())
//
//                            formatter!!.close()
//                        }
//
//                        printDataByteArrayList.add("\n".toByteArray()) }
//                }
//
//            }
//            /* END OF PRESENT LIST */
//        }// print format with no promo price
//
//        formatter = Formatter(StringBuilder(), Locale.US)
//
//        getTaxAmount()
//
//        var taxText = ""
//        if (taxType!!.equals("E", ignoreCase = true))
//        {
//            taxText = "(Tax " + invoice.getTaxAmount() + " Excluded)"
//            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt() + invoice.getTaxAmount()
//        }
//        else
//        {
//            taxText = "(Tax " + invoice.getTaxAmount() + " Included)"
//            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt()
//        }
//
//        if (printFor == Utils.PRINT_FOR_PRE_ORDER)
//        {
//
//            printDataByteArrayList.add(
//                formatter!!.format("%1$-13s%2$19s\n%3$-15s%4$17s\n%5$-13s%6$19s\n\n\n", "Total Amount    :", decimalFormatterWithComma.format(totalAmount), taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/, "Discount        :", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + DecimalFormat("#0.00").format(invoice.getDiscountPercent()) + "%)", "Net Amount  :", decimalFormatterWithComma.format(invoice.getTotalPayAmt()), "Receive      :", decimalFormatterWithComma.format(invoice.getTotalPayAmt())).toString().toByteArray())
//        }
//        else if (mode == Utils.FOR_DELIVERY)
//        {
//
//            printDataByteArrayList.add(
//                formatter!!.format("%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s%6$19s\n%7$-13s%8$19s\n%9$-13s%10$19s\n\n\n", "Total Amount    :", decimalFormatterWithComma.format(totalAmount), taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/, "Discount        :", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + DecimalFormat("#0.00").format(invoice.getDiscountPercent()) + "%)", "Net Amount      :", decimalFormatterWithComma.format(totalNetAmount), "Pay Amount      :", decimalFormatterWithComma.format(invoice.getTotalPayAmt())).toString().toByteArray())
//        }
//        else
//        {
//            var crediBalance:Double? = 0.0
//            if (totalNetAmount > invoice.getTotalPayAmt())
//            {
//                crediBalance = totalNetAmount - invoice.getTotalPayAmt()
//            }
//
//            printDataByteArrayList.add(
//                formatter!!.format("%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s%6$19s\n%7$-13s%8$19s\n%9$-13s%10$19s\n%11$-13s%12$19s\n", "Total Amount    :        ", decimalFormatterWithComma.format(totalAmount), taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + new DecimalFormat("#0.00").format(taxPercent) + "%)"*/, "Discount        :        ", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + DecimalFormat("#0.00").format(invoice.getDiscountPercent()) + "%)", "Net Amount      :        ", decimalFormatterWithComma.format(totalNetAmount), "Receive         :        ", decimalFormatterWithComma.format(invoice.getTotalPayAmt()), "Credit Balance  :        ", decimalFormatterWithComma.format(Math.abs(crediBalance!!))).toString().toByteArray())
//        }
//        printDataByteArrayList.add((txtForFooter + "\n\n").toByteArray())
//        printDataByteArrayList.add(("\nSignature       :\n\n                 Thank You. \n\n").toByteArray())
//        printDataByteArrayList.add(byteArrayOf(0x1b, 0x64, 0x02)) // Cut
//        printDataByteArrayList.add(byteArrayOf(0x07)) // Kick cash drawer
//
//        return printDataByteArrayList
//    }

//    @Throws(UnsupportedEncodingException::class)
//    private fun convertFromListByteArrayTobyteArray(ByteArray:List<ByteArray>):ByteArray {
//        var dataLength = 0
//        for (i in ByteArray.indices)
//        {
//            dataLength += ByteArray[i].size
//        }
//
//        var distPosition = 0
//        val byteArray = ByteArray(dataLength)
//        for (i in ByteArray.indices)
//        {
//            System.arraycopy(ByteArray[i], 0, byteArray, distPosition, ByteArray[i].size)
//            distPosition += ByteArray[i].size
//        }
//
//        return byteArray
//    }

//    internal fun getTaxAmount() {
//        val cursorTax = database!!.rawQuery("SELECT TaxType, Tax FROM COMPANYINFORMATION", null)
//        while (cursorTax.moveToNext()) {
//            taxType = cursorTax.getString(cursorTax.getColumnIndex("TaxType"))
//            taxPercent = cursorTax.getDouble(cursorTax.getColumnIndex("Tax"))
//        }
//        cursorTax.close()
//    }

//    fun getProductNameAndPrice(invoicePresent:Promotion):String? {
//        val cursorProductName = database!!.rawQuery("SELECT PRODUCT_NAME, SELLING_PRICE FROM PRODUCT WHERE ID = " + invoicePresent.getPromotionProductId(), null)
//        var productName:String? = null
//        while (cursorProductName.moveToNext())
//        {
//            productName = cursorProductName.getString(cursorProductName.getColumnIndex("PRODUCT_NAME"))
//            invoicePresent.setPrice(cursorProductName.getDouble(cursorProductName.getColumnIndex("SELLING_PRICE")))
//        }
//        cursorProductName.close()
//        return productName
//    }

//    private fun getPrintDataByteArrayListForCredit(activity: Activity, customerName:String, cus_address:String, invoiceNumber:String, townShipName:String, salePersonName:String, routeId:Int, creditInvoice:CreditInvoice):List<ByteArray> {
//        val printDataByteArrayList = ArrayList<ByteArray>()
//
//        //DecimalFormat decimalFormatterWithoutComma = new DecimalFormat("##0");
//        val decimalFormatterWithComma = DecimalFormat("###,##0.##")
//
//        //double totalAmount = 0, totalNetAmount = 0;
//        var companyName = ""
//        var address = ""
//        var companyTaxRegNo = ""
//        var txtForFooter = ""
//        var phNo = ""
//        val companyInfoCursor = database!!.rawQuery("SELECT * FROM " + DatabaseContract.CompanyInformation.tb, null)
//        if (companyInfoCursor.moveToNext())
//        {
//            companyName = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.CompanyName))
//            address = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.Address))
//            txtForFooter = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.POSVoucherFooter1))
//            companyTaxRegNo = companyInfoCursor.getString(57)
//            phNo = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.PhoneNumber))
//        }
//        companyInfoCursor.close()
//        /*String[] companyNames = companyName.split(" ");
//               String names = "         ", fullName = "";
//               for (String s : companyNames) {
//                   if (names.length() < 30) {
//                       names += s + " ";
//                   } else {
//                       fullName += (names + "         ");
//                       names = "\n         " + s;
//                   }
//               }
//               fullName += names;*/
//
//        printDataByteArrayList.add((address + "\n").toByteArray())
//        printDataByteArrayList.add((companyTaxRegNo + "\n").toByteArray())
//        printDataByteArrayList.add(("Customer           :  $customerName\n").toByteArray())
//        printDataByteArrayList.add(("Township           :  $townShipName\n").toByteArray())
//        printDataByteArrayList.add(("Address            :  $cus_address\n").toByteArray())
//        printDataByteArrayList.add(("Offical Receive No :  $invoiceNumber\n").toByteArray())
//        printDataByteArrayList.add(("Collect Person     :  $salePersonName\n").toByteArray())
//        printDataByteArrayList.add(("RouteNo            :  " + Utils.getRouteName(activity, routeId) + "\n").toByteArray())
//        printDataByteArrayList.add(("Total Amount       :  " + decimalFormatterWithComma.format(creditInvoice.getAmt()) + "\n").toByteArray())
//
//        printDataByteArrayList.add(("Discount           :  " + "0.0 (0%)" + "\n").toByteArray())
//
//        printDataByteArrayList.add(("Net Amount         :  " + decimalFormatterWithComma.format(creditInvoice.getAmt()) + "\n").toByteArray())
//
//        printDataByteArrayList.add(("Receive            :  " + decimalFormatterWithComma.format(creditInvoice.getPayAmt()) + "\n").toByteArray())
//
//        printDataByteArrayList.add((txtForFooter + "\n\n").toByteArray())
//        printDataByteArrayList.add(("\nSignature          :\n\n                 Thank You. \n\n").toByteArray())
//
//        printDataByteArrayList.add(byteArrayOf(0x1b, 0x64, 0x02)) // Cut
//        printDataByteArrayList.add(byteArrayOf(0x07)) // Kick cash drawer
//
//        return printDataByteArrayList
//    }

//    private fun getPrintDataByteArrayListForDailyReport(activity: Activity, saleManDailyReport:SaleManDailyReport):List<ByteArray> {
//        val printDataByteArrayList = ArrayList<ByteArray>()
//
//        //DecimalFormat decimalFormatterWithoutComma = new DecimalFormat("##0");
//        val decimalFormatterWithComma = DecimalFormat("###,##0")
//
//        //double totalAmount = 0, totalNetAmount = 0;
//        /*String companyName = "";
//        Cursor companyInfoCursor = database.rawQuery("SELECT " + DatabaseContract.CompanyInformation.CompanyName + " FROM " + DatabaseContract.CompanyInformation.tb, null);
//        if (companyInfoCursor.moveToNext()) {
//            companyName = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.CompanyName));
//        }
//
//        String[] companyNames = companyName.split(" ");
//        String names = "         ", fullName = "";
//        for (String s : companyNames) {
//            if (names.length() < 30) {
//                names += s + " ";
//            } else {
//                fullName += (names + "         ");
//                names = "\n         " + s;
//            }
//        }
//        fullName += names;*/
//
//        printDataByteArrayList.add(("               End of Day Report " + "\n\n").toByteArray())
//        printDataByteArrayList.add(("Sale Man                 :  " + saleManDailyReport.getSaleMan() + "\n").getBytes())
//        printDataByteArrayList.add(("Route                    :  " + saleManDailyReport.getRouteName() + "\n").getBytes())
//        printDataByteArrayList.add(("Date                     :  " + saleManDailyReport.getDate() + "\n").getBytes())
//
//        /* formatter = new Formatter(new StringBuilder(), Locale.US);
//               printDataByteArrayList.add(
//                       formatter.format(
//                               "%1$-10s  %2$4s      %3$5s  %4$5s\n"
//                               , "Start Time : "
//                               , saleManDailyReport.getStartTime()
//                               , "End Time   : "
//                               , saleManDailyReport.getEndTime()).toString().getBytes());
//
//               formatter.close();*/
//        printDataByteArrayList.add(("Start Time               :  " + saleManDailyReport.getStartTime() + "\n").getBytes())
//        printDataByteArrayList.add(("End Time                 :  " + saleManDailyReport.getEndTime() + "\n").getBytes())
//
//        printDataByteArrayList.add(("Total Sale               :  " + decimalFormatterWithComma.format(saleManDailyReport.getSaleAmt()) + "\n").toByteArray())
//        printDataByteArrayList.add(("Total Order Sales        :  " + decimalFormatterWithComma.format(saleManDailyReport.getOrderAmt()) + "\n").toByteArray())
//        printDataByteArrayList.add(("Total Exchange           :  (" + decimalFormatterWithComma.format(saleManDailyReport.getExchangeAmt()) + ")\n").toByteArray())
//        printDataByteArrayList.add(("Total Return             :  (" + decimalFormatterWithComma.format(saleManDailyReport.getReturnAmt()) + ")\n").toByteArray())
//        printDataByteArrayList.add(("Total Cash Receipt       :  " + decimalFormatterWithComma.format(saleManDailyReport.getCashReceive()) + "\n").toByteArray())
//
//        printDataByteArrayList.add(("Net Cash                 :  " + decimalFormatterWithComma.format(saleManDailyReport.getNetAmt()) + "\n").toByteArray())
//        printDataByteArrayList.add(("Total Customer           :  " + saleManDailyReport.getCustomerCount() + "\n").getBytes())
//        printDataByteArrayList.add(("New Customer             :  " + saleManDailyReport.getNewCustomer() + "\n").getBytes())
//        printDataByteArrayList.add(("Plan Customer            :  " + saleManDailyReport.getPlanCustomer() + "\n").getBytes())
//
//        printDataByteArrayList.add(("Total Sale Count         :  " + saleManDailyReport.getSaleCount() + "\n").getBytes())
//
//        printDataByteArrayList.add(("Total Order Count        :  " + saleManDailyReport.getOrderCount() + "\n").getBytes())
//
//        printDataByteArrayList.add(("Total Exchange Only      :  " + saleManDailyReport.getExchangeCount() + "\n").getBytes())
//
//        printDataByteArrayList.add(("Total Sale Return Only   :  " + saleManDailyReport.getReturnCount() + "\n").getBytes())
//
//        printDataByteArrayList.add(("Total Cash Receipt Count :  " + saleManDailyReport.getCashReceiveCount() + "\n").getBytes())
//
//        printDataByteArrayList.add(("Not Visited Count        :  " + saleManDailyReport.getNotVisitCount() + "\n").getBytes())
//
//        printDataByteArrayList.add(("\nSignature          :\n\n                 Thank You. \n\n").toByteArray())
//
//        printDataByteArrayList.add(byteArrayOf(0x1b, 0x64, 0x02)) // Cut
//        printDataByteArrayList.add(byteArrayOf(0x07)) // Kick cash drawer
//
//        return printDataByteArrayList
//    }


//    @Throws(UnsupportedEncodingException::class)
//    private fun getPrintDataByteArrayListForSaleExchange(activity: Activity, saleInvoiceNumber:String, saleReturnInvoiceNumber:String, salePersonName:String, invoice:Invoice, soldProductList:List<SoldProduct>, saleReturnList:List<SoldProduct>, returnDiscountAmt:Double?, cusName:String, routeId:Int, township:String, cusAddress:String, imgByte:ByteArray?):List<ByteArray> {
//
//        val printDataByteArrayList = ArrayList<ByteArray>()
//        printDataByteArrayList.add(imgByte)
//        printDataByteArrayList.add("\n".toByteArray())
//
//        val decimalFormatterWithoutComma = DecimalFormat("##0")
//        val decimalFormatterWithComma = DecimalFormat("###,##0")
//
//        var totalAmount = 0.0
//        var totalNetAmount = 0.0
//        var totalReturnAmount = 0.0
//        var totalReturnNetAmount = 0.0
//        //double totalAmount = 0, totalNetAmount = 0, totalReturnAmount = 0;
//
//        var companyName = ""
//        var address = ""
//        var txtForFooter = ""
//        var commTaxRegNo = ""
//        var phNo = ""
//        val cus = database!!.rawQuery("SELECT * FROM " + DatabaseContract.CompanyInformation.tb, null)
//        if (cus != null)
//        {
//            while (cus!!.moveToNext())
//            {
//                companyName = cus!!.getString(cus!!.getColumnIndex(DatabaseContract.CompanyInformation.CompanyName))
//                commTaxRegNo = cus!!.getString(57)
//                address = cus!!.getString(cus!!.getColumnIndex(DatabaseContract.CompanyInformation.Address))
//                txtForFooter = cus!!.getString(cus!!.getColumnIndex(DatabaseContract.CompanyInformation.POSVoucherFooter1))
//                phNo = cus!!.getString(cus!!.getColumnIndex(DatabaseContract.CompanyInformation.PhoneNumber))
//            }
//            cus!!.close()
//        }
//
//        /*String[] companyNames = companyName.split(" ");
//               String names = "         ", fullName = "";
//               for (String s : companyNames) {
//                   if (names.length() < 30) {
//                       names += s + " ";
//                   } else {
//                       fullName += (names + "         ");
//                       names = "\n         " + s;
//                   }
//               }
//               fullName += names;*/
//
//        printDataByteArrayList.add((address + "\n").toByteArray())
//        //        printDataByteArrayList.add((commTaxRegNo + "\n").getBytes());
//        printDataByteArrayList.add(("Ph No       :   $phNo\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Tax Reg No  :   $commTaxRegNo\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Customer    :   $cusName\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Township    :   $township\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Address     :   $cusAddress\n").toByteArray(charset("UTF-8")))
//        printDataByteArrayList.add(("Invoice No  :   $saleInvoiceNumber\n").toByteArray())
//        printDataByteArrayList.add(("Sale Person :   $salePersonName\n").toByteArray())
//        printDataByteArrayList.add(("RouteNo     :   " + Utils.getRouteName(activity, routeId) + "\n").toByteArray())
//        printDataByteArrayList.add((("Sale Date   :   " + SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US)
//            .format(Date()) + "\n")).toByteArray())
//        printDataByteArrayList.add("------------------------------------------------\n".toByteArray())
//
//
//        formatter = Formatter(StringBuilder(), Locale.US)
//
//        printDataByteArrayList.add(
//            formatter!!.format(
//                "%1$-20s \t %2$4s \t %3$5s \t %4$7s\n", "Sales", "Qty", "Price", "Amount").toString().toByteArray())
//        formatter!!.close()
//        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
//
//        for (soldProduct in soldProductList)
//        {
//
//            val name = String()
//            val quantity = soldProduct.getProduct().getSoldQty()
//            var pricePerUnit = 0.0
//            val promoPrice = 0.0
//
//            pricePerUnit = soldProduct.getProduct().getPrice()
//
//            val amount = soldProduct.getProduct().getSoldQty() * soldProduct.getProduct().getPrice()
//            val pricePerUnitWithDiscount:Double
//            val netAmount:Double
//
//            val discount = soldProduct.getDiscount(activity)
//
//            pricePerUnitWithDiscount = soldProduct.getDiscountAmount()
//            netAmount = soldProduct.getTotalAmount() - pricePerUnitWithDiscount
//
//            totalAmount += amount
//            totalNetAmount += netAmount
//
//            val nameFragments = soldProduct.getProduct().getName().split(" ")
//            val nameList = setupPrintLayoutNoPromo(nameFragments)
//
//            formatter = Formatter(StringBuilder(), Locale.US)
//            printDataByteArrayList.add(
//                formatter!!.format(
//                    "%1$-20s \t %2$4s \t %3$5s \t %4$9s\n",
//                    nameList[0], quantity, decimalFormatterWithoutComma.format(pricePerUnit), decimalFormatterWithComma.format(amount)).toString().toByteArray())
//
//            formatter!!.close()
//
//            //            if (printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {
//            //
//            //            }
//            //
//            //            if (!printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {
//            //
//            //                formatter = new Formatter(new StringBuilder(), Locale.US);
//            //                printDataByteArrayList.add(
//            //                        formatter.format(
//            //                                "%1$-20s \t %2$4s \t %3$5s \t %4$9s\n"
//            //                                , nameList.get(0)
//            //                                , quantity
//            //                                , decimalFormatterWithoutComma.format(pricePerUnit)
//            //                                , decimalFormatterWithComma.format(netAmount)).toString().getBytes());
//            //
//            //
//            //            }
//
//            nameList.removeAt(0)
//            for (cutName in nameList)
//            {
//                formatter = Formatter(StringBuilder(), Locale.US)
//                printDataByteArrayList.add(
//                    formatter!!.format(
//                        "%1$-20s \t %2$1s \t %3$1s \t %4$1s\n", cutName, "", "", "").toString().toByteArray())
//
//                formatter!!.close()
//            }
//
//            printDataByteArrayList.add("\n".toByteArray())
//
//        }
//
//        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
//
//        formatter = Formatter(StringBuilder(), Locale.US)
//
//        getTaxAmount()
//
//        var taxText = ""
//        if (taxType!!.equals("E", ignoreCase = true))
//
//        {
//            taxText = "(Tax " + invoice.getTaxAmount() + " Excluded)"
//            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt() + invoice.getTaxAmount()
//        }
//        else
//
//        {
//            taxText = "(Tax " + invoice.getTaxAmount() + " Included)"
//            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt()
//        }
//
//        var crediBalance:Double? = 0.0
//        if (totalNetAmount > invoice.getTotalPayAmt())
//        {
//            crediBalance = totalNetAmount - invoice.getTotalPayAmt()
//        }
//
//        printDataByteArrayList.add(
//            formatter!!.format("%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s\n%6$-13s%7$19s\n\n", "Total Amount       :        ", decimalFormatterWithComma.format(totalAmount), "Discount           :        ", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + DecimalFormat("#0.00").format(invoice.getDiscountPercent()) + "%)", taxText, "Net Amount         :        ", decimalFormatterWithComma.format(totalNetAmount)).toString().toByteArray())
//
//        /** End of sale exchange list  */
//
//
//        /** start of sale return print  */
//        /*  printDataByteArrayList.add((fullName + "\n\n").getBytes());
//          printDataByteArrayList.add((
//                  "Invoice No     :     " + saleReturnInvoiceNumber + "\n").getBytes());
//          printDataByteArrayList.add((
//                  "Sale Person    :     " + salePersonName + "\n").getBytes());
//          printDataByteArrayList.add((
//                  "Sale Date      :     " + new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US)
//                          .format(new Date()) + "\n").getBytes());
//          printDataByteArrayList.add("----------------------------------------------\n".getBytes());*/
//
//        formatter = Formatter(StringBuilder(), Locale.US)
//
//        printDataByteArrayList.add(
//            formatter!!.format(
//                "%1$-20s \t %2$4s \t %3$5s \t %4$7s\n", "Return", "Qty", "Price", "Amount").toString().toByteArray())
//        formatter!!.close()
//        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
//
//        for (soldProduct in saleReturnList)
//        {
//
//            val name = String()
//            val quantity = soldProduct.getProduct().getSoldQty()
//            var pricePerUnit = 0.0
//            val promoPrice = 0.0
//
//            pricePerUnit = soldProduct.getProduct().getPrice()
//
//            val amount = soldProduct.getProduct().getSoldQty() * soldProduct.getProduct().getPrice()
//            val pricePerUnitWithDiscount:Double
//            val netAmount:Double
//
//            val discount = soldProduct.getDiscount(activity)
//
//            pricePerUnitWithDiscount = soldProduct.getDiscountAmount()
//            netAmount = soldProduct.getTotalAmount() - pricePerUnitWithDiscount
//
//            totalReturnAmount += amount
//            totalReturnNetAmount += netAmount
//
//            val nameFragments = soldProduct.getProduct().getName().split(" ")
//            val nameList = setupPrintLayoutNoPromo(nameFragments)
//            formatter = Formatter(StringBuilder(), Locale.US)
//            printDataByteArrayList.add(
//                formatter!!.format(
//                    "%1$-20s \t %2$4s \t %3$5s \t %4$9s\n",
//                    nameList[0], quantity, decimalFormatterWithoutComma.format(pricePerUnit), decimalFormatterWithComma.format(amount)).toString().toByteArray())
//
//            formatter!!.close()
//
//            nameList.removeAt(0)
//            for (cutName in nameList)
//            {
//                formatter = Formatter(StringBuilder(), Locale.US)
//                printDataByteArrayList.add(
//                    formatter!!.format(
//                        "%1$-20s \t %2$1s \t %3$1s \t %4$1s\n", cutName, "", "", "").toString().toByteArray())
//
//                formatter!!.close()
//            }
//
//            printDataByteArrayList.add("\n".toByteArray())
//
//        }
//
//        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
//
//        formatter = Formatter(StringBuilder(), Locale.US)
//
//        getTaxAmount()
//
//        var taxReturnText = ""
//        if (taxType!!.equals("E", ignoreCase = true))
//
//        {
//            taxReturnText = "(Tax " + invoice.getTaxAmount() + " Excluded)"
//            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt() + invoice.getTaxAmount()
//        }
//        else
//
//        {
//            taxReturnText = "(Tax " + invoice.getTaxAmount() + " Included)"
//            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt()
//        }
//
//        printDataByteArrayList.add(
//            formatter!!.format("%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s\n%6$-13s%7$19s\n", "Total Amount       :        ", decimalFormatterWithComma.format(totalReturnAmount), "Discount           :        ", decimalFormatterWithComma.format(returnDiscountAmt) + " (" + DecimalFormat("#0.00").format(invoice.getDiscountPercent()) + "%)", taxReturnText, "Net Amount         :        ", decimalFormatterWithComma.format(totalReturnAmount - returnDiscountAmt!!)).toString().toByteArray())
//
//        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
//
//        formatter = Formatter(StringBuilder(), Locale.US)
//        printDataByteArrayList.add(
//            formatter!!.format("%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s%6$19s\n", "Sale Amount        :        ", decimalFormatterWithComma.format(totalAmount), "Sale Return Amount :        ", decimalFormatterWithComma.format(totalReturnAmount - returnDiscountAmt!!), "Net Cash           :        ", decimalFormatterWithComma.format(Math.abs(totalAmount - (totalReturnAmount - returnDiscountAmt!!)))).toString().toByteArray())
//
//        printDataByteArrayList.add(("\nSignature          :\n\n                 Thank You. \n\n").toByteArray())
//
//        return printDataByteArrayList
//    }

//    fun checkDuplicateInvoice(context: Context, invoiceNo:String, tableName:String, columnName:String):Boolean {
//        if (database == null)
//        {
//            database = Database(context).getReadableDatabase()
//        }
//
//        val duplicateCursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM $tableName WHERE $columnName = '$invoiceNo'", null)
//        var count = 0
//
//        if (duplicateCursor != null)
//        {
//            if (duplicateCursor!!.moveToNext())
//            {
//                count = duplicateCursor!!.getInt(duplicateCursor!!.getColumnIndex("COUNT"))
//            }
//            duplicateCursor!!.close()
//        }
//
//        return if (count > 0) {
//            false
//        } else {
//            true
//        }
//    }

    fun saveInvoiceImageIntoGallery(invoiceNo: String, context: Context, bitmap: Bitmap, directoryName: String) {
        val sdCard = Environment.getExternalStorageDirectory()
        val directory = File(sdCard.absolutePath + "/ScreenShot/" + directoryName)
        directory.mkdirs()

        var filename = "$invoiceNo.jpg"
        var yourFile = File(directory, filename)
        val time = getCurrentDate(true)

        while (yourFile.exists()) {
            filename = invoiceNo + "_" + time + ".jpg"
            yourFile = File(directory, filename)
            break
        }

        val image = yourFile.toString()
        Log.e(image, "ImageOOO")


        val fos: FileOutputStream
        try {
            fos = FileOutputStream(yourFile, true)
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, fos)//change 100 to 40

            fos.flush()
            fos.close()
            Toast.makeText(context, "Image saved to /sdcard/ScreenShot/$directoryName$filename.jpg", Toast.LENGTH_SHORT)
                .show()
        } catch (e: FileNotFoundException) {
            Log.e("GREC", e.message, e)
        } catch (e: IOException) {
            Log.e("GREC", e.message, e)
        }

        val bitmapOrg1 = BitmapFactory.decodeFile(image)
        val bao1 = ByteArrayOutputStream()
        Log.e(bitmapOrg1.toString() + "", "BitMap1")
        Log.e(bao1.toString(), "BAO")
        bitmapOrg1.compress(Bitmap.CompressFormat.JPEG, 40, bao1) //change 90 to 40

        Log.e("here1", "here1")
        val ba1 = bao1.toByteArray()

        val imgBase64Str = Base64.encodeToString(ba1, Base64.NO_WRAP)
        Log.e("ImageBase64String", imgBase64Str.toString() + "aa")
    }

    internal fun setupPrintLayoutWithPromo(nameFragments: Array<String>): MutableList<String> {
        val nameList = ArrayList<String>()
        var concatName = nameFragments[0]
        var i = 1
        while (i != nameFragments.size) {
            if (concatName.length > 13) {
                nameList.add(concatName)
                concatName = nameFragments[i]
                nameList.add(concatName)
                concatName = ""
            } else {

                var concatName1 = ""
                if (concatName.equals("", ignoreCase = true)) {
                    concatName1 = nameFragments[i]
                } else {
                    concatName1 = concatName + " " + nameFragments[i]
                }

                if (concatName1.length < 13) {
                    if (concatName.length > 0) {
                        concatName += " " + nameFragments[i]
                    } else {
                        concatName += nameFragments[i] + " "
                    }
                } else {
                    nameList.add(concatName)
                    nameList.add(nameFragments[i])
                    concatName = ""
                }
            }
            i++
        }

        if (nameList.size == 0) {
            nameList.add(concatName)
        } else {
            val lastString = nameList[nameList.size - 1]
            if (lastString.length > 13) {
                nameList.add(concatName)
            } else {
                nameList[nameList.size - 1] = lastString + concatName
            }
        }

        for (j in nameList.indices) {
            if (nameList[j].length < 13) {
                val stringLength = 10 - nameList[j].length
                var s = nameList[j]
                for (k in 0 until stringLength) {
                    s += " "
                    nameList[j] = s
                }
            }
        }

        return nameList
    }

    fun onDecimalFormat(value: Double): Double {
        val f = DecimalFormat("##.0000")
        val formattedValue = f.format(value)
        return java.lang.Double.parseDouble(formattedValue)
    }

    internal fun setupPrintLayoutNoPromo(nameFragments: Array<String>): MutableList<String> {
        val nameList = ArrayList<String>()
        var concatName = nameFragments[0]
        //boolean flag = true;
        val firstName = ""
        var i = 1
        while (i != nameFragments.size) {
            if (concatName.length > 23) {
                nameList.add(concatName)
                concatName = nameFragments[i]
                nameList.add(concatName)
                concatName = ""
            } else {

                var concatName1 = ""
                if (concatName.equals("", ignoreCase = true)) {
                    concatName1 = nameFragments[i]
                } else {
                    concatName1 = concatName + " " + nameFragments[i]
                }

                if (concatName1.length < 20) {
                    if (concatName.length > 0) {
                        concatName += " " + nameFragments[i]
                    } else {
                        concatName += nameFragments[i] + " "
                    }


                } else {
                    nameList.add(concatName)
                    nameList.add(nameFragments[i])
                    concatName = ""
                }
            }
            i++
        }


        if (nameList.size > 0) {
            val lastString = nameList[nameList.size - 1]
            if (lastString.length > 23) {
                nameList.add(concatName)
            } else {
                nameList[nameList.size - 1] = lastString + concatName
            }
        } else if (nameList.size == 0 && concatName != "") {
            nameList.add(concatName)
        }

        for (j in nameList.indices) {
            if (nameList[j].length < 20) {
                val stringLength = 10 - nameList[j].length
                var s = nameList[j]
                for (k in 0 until stringLength) {
                    s += " "
                    nameList[j] = s
                }
            }
        }
        return nameList
    }

    /**
     * Calculate total amount, total discount amount and total net amount of given product list
     *
     * @param productList SoldProduct Object List
     * @param flag        true: sale; false: sale order
     * @return 0 index: total amount, 1 index total discount amount, 2 index total net amount
     */
    fun calculateReportAmounts(productList: List<JSONObject>, flag: Boolean): DoubleArray {
        val amountArray = doubleArrayOf(0.0, 0.0, 0.0, 0.0)

        for (soldProduct in productList) {
            try {
                amountArray[0] += soldProduct.getDouble("totalAmount")
                amountArray[1] += soldProduct.getDouble("discount")
                amountArray[2] += soldProduct.getDouble("netAmount")
                if (!flag) {
                    amountArray[3] += soldProduct.getDouble("advancedPaymentAmount")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }

        return amountArray
    }

    fun calculate_Cancel_Amounts(invoicecancelList: List<INVOICECANCEL>, flag: Boolean): DoubleArray {
        val amountArray = doubleArrayOf(0.0, 0.0, 0.0, 0.0)
        for (invoicecancel in invoicecancelList) {
            amountArray[0] += invoicecancel.getTotalAmount()
            amountArray[1] += invoicecancel.getDisAmount()
            amountArray[2] += invoicecancel.getNetAmount()
        }
        return amountArray
    }
}
