package com.aceplus.dms.utils

import android.annotation.SuppressLint
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
import android.telephony.TelephonyManager
import android.util.Base64
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.LoginActivity
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.INVOICECANCEL
import com.aceplus.domain.model.credit.CreditInvoice
import com.aceplus.domain.model.forApi.ConfirmRequestSuccess
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.constants.AppUtils
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

import gems.com.command.sdk.PrintPicture

@SuppressLint("StaticFieldLeak")
object Utils {

    private val decimalFormatterWithoutComma = DecimalFormat("##0.##")
    private val decimalFormatterWithComma = DecimalFormat("###,##0")
    private val decimalFormatterWithComma1 = DecimalFormat("###,##0.##")

    const val forPackageSale = "for-package-sale"
    const val forPreOrderSale = "for-pre-order-sale"
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

    var progressDialog: ProgressDialog? = null

    const val MODE_CUSTOMER_FEEDBACK = "mode_customer_feedback"
    const val MODE_GENERAL_SALE = "mode_general_sale"

    const val RQ_BACK_TO_CUSTOMER = 2

    private var formatter: Formatter? = null
    private var act: Activity? = null
    private var taxPercent = 0.0
    private var taxType: String? = null
    private var totalDiscountAmt = 0.0


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
        val intent = LoginActivity.newIntent(activity)
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

    @SuppressLint("SimpleDateFormat")
    fun confirmRequestSuccessForProduct(salesman_Id: String, routeIdV2: Int): String {
        val confirmRequestSuccess =ConfirmRequestSuccess()
        confirmRequestSuccess.siteActivationKey = Constant.SITE_ACTIVATION_KEY
        confirmRequestSuccess.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
        confirmRequestSuccess.userId = salesman_Id
        confirmRequestSuccess.route = routeIdV2.toString()
        confirmRequestSuccess.date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        confirmRequestSuccess.successCode = 200
        return ParamUtils.getJsonFromObject(confirmRequestSuccess)
    }

    fun getDeviceId(activity: Activity): String {
//        return Settings.Secure.getString(
//            activity.contentResolver,
//            Settings.Secure.ANDROID_ID
//        )
        val telephonyManager =
            activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.imei

    }

    /*fun getDeviceIssueInvoiceNo(context: Context):String {
        if (database == null)
        {

            database = (Database(context)).getDataBase()
        }

        val idLength = 18
        var invoiceNo = String()
        invoiceNo += "HODQ"
        invoiceNo += SimpleDateFormat("yyMMdd").format(Date())
        var next = 0
        val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM INVOICE", null)
        if (cursor.moveToNext())
        {

            next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
        }
        cursor.close()

        return invoiceNo + String.format("%0" + (idLength - invoiceNo.length) + "d", next)
    }*/

//    fun getInvoiceNo1(context: Context, salemanId:String, locationCode:String, mode:String):String {
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

    fun getInvoiceNo(saleManId: String, locationCode: String, mode: String, nextCount: Int): String {

        val idLength = 18
        var invoiceNo = String()

        if (mode == Constant.FOR_PACKAGE_SALE)
        {
            invoiceNo += "P"
        }
        else if (mode == Constant.FOR_PRE_ORDER_SALE) {
            invoiceNo += "SO"
        }
        else if (mode == Constant.FOR_DELIVERY)
        {
            invoiceNo += "OS"
        }
        else if (mode == Constant.FOR_SALE_RETURN)
        {
            invoiceNo += "SR"
        }
        else if (mode == Constant.FOR_SALE_EXCHANGE || mode == Constant.FOR_SALE_RETURN_EXCHANGE)
        {
            invoiceNo += "SX"
        }
        else if (mode == Constant.FOR_DISPLAY_ASSESSMENT)
        {
            invoiceNo += "DA"
        }
        else if (mode == Constant.FOR_OUTLET_STOCK_AVAILABILITY)
        {
            invoiceNo += "OSA"
        }
        else if (mode == Constant.FOR_SIZE_IN_STORE_SHARE)
        {
            invoiceNo += "SIS"
        }
        else if (mode == Constant.FOR_COMPETITORACTIVITY)
        {
            invoiceNo += "CA"
        }
        else if (mode == Constant.FOR_VAN_ISSUE)
        {
            invoiceNo += "DQ"
        }

        invoiceNo += locationCode
        invoiceNo += saleManId
        invoiceNo += SimpleDateFormat("yyMMdd").format(Date())

        val formatString = "%0${(idLength - invoiceNo.length)}d"
        val nextString = String.format(formatString, nextCount)
        return invoiceNo + nextString //To Check ERROR

//        return invoiceNo //To Check ERROR
    }

    /*fun getInvoiceNoForPOSM(context: Context, saleManId:String, locationCode:String):String {

        val idLength = 14

        if (database == null)
        {

            database = (Database(context)).getDataBase()
        }
        var next = 0

        val cursor = database!!.rawQuery("SELECT COUNT(*) AS COUNT FROM POSM_BY_CUSTOMER", null)
        if (cursor.moveToNext())
        {

            next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1
        }
        cursor.close()
        var invoiceNo = ""

        invoiceNo += locationCode
        invoiceNo += saleManId
        invoiceNo += SimpleDateFormat("yyMMdd").format(Date())

        return invoiceNo + String.format("%0" + (idLength - invoiceNo.length) + "d", next)
    }*/

    fun isNumeric(str: String): Boolean {

        try {
            java.lang.Double.parseDouble(str)
        } catch (nfe: NumberFormatException) {
            return false
        }

        return true
    }

    fun setOnActionClickListener(onActionClickListener: OnActionClickListener) {
        Utils.onActionClickListener = onActionClickListener
    }


    fun askConfirmationDialog(
        title: String,
        message: String,
        type: String,
        activity: Activity,
        action: (String) -> Unit
    ) {
        val alertDialog = AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                "Yes"
            ) { _, _ -> action(type) }
            .setNegativeButton("No", null)
            .show()

        val textViewYes = alertDialog.findViewById<TextView>(android.R.id.button1)
        textViewYes?.textSize = 25f
        val textViewNo = alertDialog.findViewById<TextView>(android.R.id.button2)
        textViewNo?.textSize = 25f
    }

    /*fun print(activity: Activity, customerName:String, cus_address:String, invoiceNumber:String, salePersonName:String, routeId:Int, townshipName:String, invoice:Invoice, soldProductList:List<SoldProduct>, presentList:List<Promotion>, printFor:String, mode:String) {

        var portInfoList:List<PortInfo>? = null

        try
        {

            portInfoList = StarIOPort.searchPrinter("BT:Star")
        }
        catch (e: StarIOPortException) {

            e.printStackTrace()
        }

        if (portInfoList == null || portInfoList!!.size == 0)
        {

            return
        }

        val availableBluetoothPrinterNameList = ArrayList<String>()
        for (portInfo in portInfoList!!)
        {

            availableBluetoothPrinterNameList.add(portInfo.portName)
        }
        val arrayAdapter = ArrayAdapter(
            activity, R.layout.select_dialog_singlechoice, availableBluetoothPrinterNameList)
        android.app.AlertDialog.Builder(activity)
            .setTitle("Select Printer")
            .setNegativeButton("Cancel", null)
            .setAdapter(arrayAdapter, object: DialogInterface.OnClickListener {

                override fun onClick(dialog: DialogInterface, position:Int) {

                    var starIOPort: StarIOPort? = null
                    try
                    {

                        starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position)!!, "mini", 10000)
                        if (starIOPort!!.retreiveStatus().offline)
                        {

                            if (!starIOPort!!.retreiveStatus().compulsionSwitch)
                            {

                                showToast(activity, "The Drawer is offline\nCash Drawer: Close")
                            }
                            else
                            {

                                showToast(activity, "The Drawer is offline\nCash Drawer: Open")
                            }

                            return
                        }
                        else
                        {

                            if (starIOPort!!.retreiveStatus().compulsionSwitch)
                            {

                                showToast(activity, "The Drawer is online\nCash Drawer: Open")
                            }
                            else
                            {

                                val printDataByteArray = convertFromListByteArrayTobyteArray(
                                    getPrintDataByteArrayList(
                                        activity, customerName, cus_address, invoiceNumber, salePersonName, routeId, townshipName, invoice, soldProductList, presentList, printFor, mode, printBmp(activity)))

                                // configure printer setting using StarIo 1.3.0 lib
                                val context = activity.applicationContext
                                val setting = PrinterSetting(context)
                                val emulation = setting.emulation
                                val builder = StarIoExt.createCommandBuilder(emulation)
                                builder.beginDocument()
                                builder.append(printDataByteArray)
                                // choosing font type
                                builder.appendFontStyle(ICommandBuilder.FontStyleType.B)
                                // chooding all data for one page
                                builder.appendCutPaper(ICommandBuilder.CutPaperAction.FullCut)
                                builder.endDocument()
                                act = activity
                                Communication.sendCommands(this, builder.commands, "BT:00:15:0E:E1:CF:B1", "mini", 10000, activity, mCallback)
                            }
                        }
                    }
                    catch (e: StarIOPortException) {

                        showToast(activity, "Failed to connect to drawer")
                        e.printStackTrace()
                    }
                    catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                    finally
                    {
                        if (starIOPort != null)
                        {

                            try
                            {

                                StarIOPort.releasePort(starIOPort)
                            }
                            catch (e: StarIOPortException) {

                                e.printStackTrace()
                            }

                        }
                    }
                }
            })
            .show()
    }


    private fun printBmp(mActivity: Activity):ByteArray? {
        val mBitmap = BitmapFactory.decodeResource(mActivity.resources,
            R.drawable.global_sky_logo)
        val buffer = PrinterCommand.POS_Set_PrtInit()
        val nMode = 0
        val nPaperWidth = 384
        if (mBitmap != null)
        {

            return PrintPicture.POS_PrintBMP(mBitmap, nPaperWidth, nMode)
        }
        return null
    }

    fun printForEndOfDayReport(activity: Activity, saleManDailyReport:SaleManDailyReport, mBTService:BluetoothService) {

        try
        {
            val printDataByteArray = convertFromListByteArrayTobyteArray(
                getPrintDataByteArrayListForDailyReport(
                    activity, saleManDailyReport))
            sendDataByte2BT(activity, mBTService, printDataByteArray)
        }
        catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

    }

    fun printWithHSPOS(activity: Activity, customerName:String, cus_address:String, invoiceNumber:String, salePersonName:String, routeId:Int, townshipName:String, invoice:Invoice, soldProductList:List<SoldProduct>, presentList:List<Promotion>, printFor:String, mode:String, mBTService:BluetoothService) {
        try
        {


            val printDataByteArray = convertFromListByteArrayTobyteArray(
                getPrintDataByteArrayList(
                    activity, customerName, cus_address, invoiceNumber, salePersonName, routeId, townshipName, invoice, soldProductList, presentList, printFor, mode, printBmp(activity)))
            sendDataByte2BT(activity, mBTService, printDataByteArray)
        }
        catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

    }

    private fun sendDataByte2BT(mActivity: Activity, mService:BluetoothService, data:ByteArray) {

        if (mService.getState() !== BluetoothService.STATE_CONNECTED)
        {
            Toast.makeText(mActivity, "Not Connected", Toast.LENGTH_SHORT)
                .show()
            return
        }
        mService.write(data)
        commonDialog("Success", mActivity, 0)

    }

    fun printSaleExchange(activity: Activity,
                          invoiceNumber:String,
                          saleReturnInvoiceNumber:String, salePersonName:String, invoice:Invoice, soldProductList:List<SoldProduct>, saleReturnList:List<SoldProduct>, returnDiscountAmt:Double) {

        var portInfoList:List<PortInfo>? = null

        try
        {

            portInfoList = StarIOPort.searchPrinter("BT:Star")
        }
        catch (e: StarIOPortException) {

            e.printStackTrace()
        }

        if (portInfoList == null || portInfoList!!.size == 0)
        {

            return
        }

        val availableBluetoothPrinterNameList = ArrayList<String>()
        for (portInfo in portInfoList!!)
        {

            availableBluetoothPrinterNameList.add(portInfo.portName)
        }
        val arrayAdapter = ArrayAdapter(
            activity, R.layout.select_dialog_singlechoice, availableBluetoothPrinterNameList)
        android.app.AlertDialog.Builder(activity)
            .setTitle("Select Printer")
            .setNegativeButton("Cancel", null)
            .setAdapter(arrayAdapter, object: DialogInterface.OnClickListener {

                override fun onClick(dialog: DialogInterface, position:Int) {

                    var starIOPort: StarIOPort? = null
                    try
                    {

                        starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position)!!, "mini", 10000)
                        if (starIOPort!!.retreiveStatus().offline)
                        {

                            if (!starIOPort!!.retreiveStatus().compulsionSwitch)
                            {

                                showToast(activity, "The Drawer is offline\nCash Drawer: Close")
                            }
                            else
                            {

                                showToast(activity, "The Drawer is offline\nCash Drawer: Open")
                            }

                            return
                        }
                        else
                        {

                            if (starIOPort!!.retreiveStatus().compulsionSwitch)
                            {

                                showToast(activity, "The Drawer is online\nCash Drawer: Open")
                            }
                            else
                            {

                                val printDataByteArray = convertFromListByteArrayTobyteArray(
                                    getPrintDataByteArrayListForSaleExchange(
                                        activity, invoiceNumber, saleReturnInvoiceNumber, salePersonName, invoice, soldProductList, saleReturnList, returnDiscountAmt, "", 0, "", "", printBmp(activity)))

                                // configure printer setting using StarIo 1.3.0 lib
                                val context = activity.applicationContext
                                val setting = PrinterSetting(context)
                                val emulation = setting.emulation
                                val builder = StarIoExt.createCommandBuilder(emulation)
                                builder.beginDocument()
                                builder.append(printDataByteArray)
                                // choosing font type
                                builder.appendFontStyle(ICommandBuilder.FontStyleType.B)
                                // chooding all data for one page
                                builder.appendCutPaper(ICommandBuilder.CutPaperAction.FullCut)
                                builder.endDocument()
                                act = activity
                                Communication.sendCommands(this, builder.commands, "BT:00:15:0E:E1:CF:B1", "mini", 10000, activity, mCallback)
                            }
                        }
                    }
                    catch (e: StarIOPortException) {

                        showToast(activity, "Failed to connect to drawer")
                        e.printStackTrace()
                    }
                    catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                    finally
                    {
                        if (starIOPort != null)
                        {

                            try
                            {

                                StarIOPort.releasePort(starIOPort)
                            }
                            catch (e: StarIOPortException) {

                                e.printStackTrace()
                            }

                        }
                    }
                }
            })
            .show()
    }

    fun printSaleExchangeWithHSPOS(activity: Activity,
                                   invoiceNumber:String,
                                   saleReturnInvoiceNumber:String, salePersonName:String, invoice:Invoice, soldProductList:List<SoldProduct>, saleReturnList:List<SoldProduct>, returnDiscountAmt:Double, mBTService:BluetoothService, cusName:String, routeId:Int, township:String, cusAddress:String) {
        try
        {


            val printDataByteArray = convertFromListByteArrayTobyteArray(
                getPrintDataByteArrayListForSaleExchange(
                    activity, invoiceNumber, saleReturnInvoiceNumber, salePersonName, invoice, soldProductList, saleReturnList, returnDiscountAmt, cusName, routeId, township, cusAddress, printBmp(activity)))
            sendDataByte2BT(activity, mBTService, printDataByteArray)
        }
        catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

    }


    fun printCreditWithHSPOS(activity: Activity, customerName:String, customerAddress:String, invoiceNumber:String, townshipName:String, salePersonName:String, routeId:Int, creditInvoiceList:CreditInvoice, mBTService:BluetoothService) {

        try
        {
            val printDataByteArray = convertFromListByteArrayTobyteArray(
                getPrintDataByteArrayListForCredit(
                    activity, customerName, customerAddress, invoiceNumber, townshipName, salePersonName, routeId, creditInvoiceList))
            sendDataByte2BT(activity, mBTService, printDataByteArray)
        }
        catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

    }

    fun printCredit(activity: Activity, customerName:String, cus_address:String, invoiceNumber:String, townshipName:String, salePersonName:String, routeId:Int, creditInvoiceList:CreditInvoice) {

        var portInfoList:List<PortInfo>? = null

        try
        {

            portInfoList = StarIOPort.searchPrinter("BT:Star")
        }
        catch (e: StarIOPortException) {

            e.printStackTrace()
        }

        if (portInfoList == null || portInfoList!!.size == 0)
        {

            return
        }

        val availableBluetoothPrinterNameList = ArrayList<String>()
        for (portInfo in portInfoList!!)
        {

            availableBluetoothPrinterNameList.add(portInfo.portName)
        }
        val arrayAdapter = ArrayAdapter(
            activity, R.layout.select_dialog_singlechoice, availableBluetoothPrinterNameList)
        android.app.AlertDialog.Builder(activity)
            .setTitle("Select Printer")
            .setNegativeButton("Cancel", null)
            .setAdapter(arrayAdapter, DialogInterface.OnClickListener { dialog, position ->
                var starIOPort: StarIOPort? = null
                try {

                    starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position)!!, "mini", 10000)
                    if (starIOPort!!.retreiveStatus().offline) {

                        if (!starIOPort!!.retreiveStatus().compulsionSwitch) {

                            showToast(activity, "The Drawer is offline\nCash Drawer: Close")
                        } else {

                            showToast(activity, "The Drawer is offline\nCash Drawer: Open")
                        }

                        return@OnClickListener
                    } else {

                        if (starIOPort!!.retreiveStatus().compulsionSwitch) {

                            showToast(activity, "The Drawer is online\nCash Drawer: Open")
                        } else {

                            val printDataByteArray = convertFromListByteArrayTobyteArray(
                                getPrintDataByteArrayListForCredit(
                                    activity, customerName, cus_address, invoiceNumber, townshipName, salePersonName, routeId, creditInvoiceList))
                            starIOPort!!.writePort(printDataByteArray, 0, printDataByteArray.size)
                        }
                    }
                } catch (e: StarIOPortException) {

                    showToast(activity, "Failed to connect to drawer")
                    e.printStackTrace()
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                } finally {
                    if (starIOPort != null) {

                        try {

                            StarIOPort.releasePort(starIOPort)
                        } catch (e: StarIOPortException) {

                            e.printStackTrace()
                        }

                    }
                }
            })
            .show()
    }

    fun printDailyReportForSaleMan(activity: Activity, saleManDailyReport:SaleManDailyReport) {

        var portInfoList:List<PortInfo>? = null

        try
        {

            portInfoList = StarIOPort.searchPrinter("BT:Star")
        }
        catch (e: StarIOPortException) {

            e.printStackTrace()
        }

        if (portInfoList == null || portInfoList!!.size == 0)
        {

            return
        }

        val availableBluetoothPrinterNameList = ArrayList<String>()
        for (portInfo in portInfoList!!)
        {

            availableBluetoothPrinterNameList.add(portInfo.portName)
        }
        val arrayAdapter = ArrayAdapter(
            activity, R.layout.select_dialog_singlechoice, availableBluetoothPrinterNameList)
        android.app.AlertDialog.Builder(activity)
            .setTitle("Select Printer")
            .setNegativeButton("Cancel", null)
            .setAdapter(arrayAdapter, DialogInterface.OnClickListener { dialog, position ->
                var starIOPort: StarIOPort? = null
                try {

                    starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position)!!, "mini", 10000)
                    if (starIOPort!!.retreiveStatus().offline) {

                        if (!starIOPort!!.retreiveStatus().compulsionSwitch) {

                            showToast(activity, "The Drawer is offline\nCash Drawer: Close")
                        } else {

                            showToast(activity, "The Drawer is offline\nCash Drawer: Open")
                        }

                        return@OnClickListener
                    } else {

                        if (starIOPort!!.retreiveStatus().compulsionSwitch) {

                            showToast(activity, "The Drawer is online\nCash Drawer: Open")
                        } else {

                            val printDataByteArray = convertFromListByteArrayTobyteArray(
                                getPrintDataByteArrayListForDailyReport(
                                    activity, saleManDailyReport))
                            starIOPort!!.writePort(printDataByteArray, 0, printDataByteArray.size)
                        }
                    }
                } catch (e: StarIOPortException) {

                    showToast(activity, "Failed to connect to drawer")
                    e.printStackTrace()
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                } finally {
                    if (starIOPort != null) {

                        try {

                            StarIOPort.releasePort(starIOPort)
                        } catch (e: StarIOPortException) {

                            e.printStackTrace()
                        }

                    }
                }
            })
            .show()
    }*/

    fun printWithHSPOS(
        activity: Activity,
        customerName: String?,
        cus_address: String?,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        townshipName: String,
        invoice: Invoice,
        soldProductList: ArrayList<SoldProductInfo>,
        presentList: ArrayList<Promotion>,
        printFor: String,
        mode: String,
        mBTService: BluetoothService,
        companyInfo: CompanyInformation,
        printMode: String?
    ) {
        try {
            val printDataByteArray = convertFromListByteArrayToByteArray(
                getPrintDataByteArrayList(
                    customerName,
                    cus_address,
                    invoiceNumber,
                    salePersonName,
                    routeName,
                    townshipName,
                    invoice,
                    soldProductList,
                    presentList,
                    printFor,
                    mode,
                    ByteArray(7),
                    companyInfo,
                    printMode
                )
            )
            sendDataByte2BT(activity, mBTService, printDataByteArray)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    fun printCreditWithHSPOS(
        activity: Activity,
        customerName: String?,
        cus_address: String?,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        townshipName: String,
        creditList: CreditInvoice,
        mBTService: BluetoothService,
        companyInfo: CompanyInformation
    ){
        try {
            val printDataByteArray = convertFromListByteArrayToByteArray(
                getPrintDataByteArrayListForCredit(
                    customerName,
                    cus_address,
                    invoiceNumber,
                    salePersonName,
                    townshipName,
                    creditList,
                    companyInfo,
                    routeName
                )
            )
            sendDataByte2BT(activity, mBTService, printDataByteArray)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

    }

    fun printDeliverWithHSPOS(
        activity: Activity,
        customerName: String?,
        cus_address: String?,
        orderInvoiceNo: String,
        orderSalePersonName: String?,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        townshipName: String,
        invoice: Invoice,
        soldProductList: ArrayList<SoldProductInfo>,
        presentList: ArrayList<Promotion>,
        prepaidAmount: Double,
        printFor: String,
        mode: String,
        mBTService: BluetoothService,
        companyInfo: CompanyInformation
    ){
        try {
            val printDataByteArray = convertFromListByteArrayToByteArray(
                getPrintDataByteArrayListDeliver(
                    activity,
                    customerName,
                    cus_address,
                    orderInvoiceNo,
                    orderSalePersonName,
                    invoiceNumber,
                    salePersonName,
                    routeName,
                    townshipName,
                    invoice,
                    soldProductList,
                    presentList,
                    prepaidAmount,
                    printFor,
                    mode,
                    companyInfo
                )
            )
            sendDataByte2BT(activity, mBTService, printDataByteArray)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    private fun sendDataByte2BT(mActivity: Activity, mService: BluetoothService, data: ByteArray) {

        if (mService.state !== BluetoothService.STATE_CONNECTED) {
            Toast.makeText(mActivity, "Not Connected", Toast.LENGTH_SHORT).show()
            return
        }
        mService.write(data)
        commonDialog("Success", mActivity, 0)

    }

    @Throws(UnsupportedEncodingException::class)
    private fun convertFromListByteArrayToByteArray(ByteArray: List<ByteArray>): ByteArray {
        var dataLength = 0
        for (i in ByteArray.indices) {
            dataLength += ByteArray[i].size
        }

        var distPosition = 0
        val byteArray = ByteArray(dataLength)
        for (i in ByteArray.indices) {
            System.arraycopy(ByteArray[i], 0, byteArray, distPosition, ByteArray[i].size)
            distPosition += ByteArray[i].size
        }

        return byteArray
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getPrintDataByteArrayList(
        customerName: String?,
        cus_address: String?,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        townshipName: String,
        invoice: Invoice,
        soldProductList: ArrayList<SoldProductInfo>,
        presentList: ArrayList<Promotion>,
        printFor: String,
        mode: String,
        imgByte: ByteArray,
        companyInfo: CompanyInformation,
        printMode: String?
    ): ArrayList<ByteArray>{

        val printDataByteArrayList: ArrayList<ByteArray> = ArrayList()
        printDataByteArrayList.add(imgByte)
        printDataByteArrayList.add("\n".toByteArray())

        val focProductList: ArrayList<SoldProductInfo> = ArrayList()

        var totalAmount = 0.0
        var totalNetAmount = 0.0
        totalDiscountAmt = 0.0

        val address = companyInfo.address
        val txtForFooter = companyInfo.pos_voucher_footer1
        val companyTaxRegNo = companyInfo.company_tax_reg_no
        val phNo = companyInfo.phone_number

        printDataByteArrayList.add((address + "\n").toByteArray())
        printDataByteArrayList.add("Ph No         :   $phNo\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Tax Reg No    :   $companyTaxRegNo\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Customer      :   $customerName\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Township      :   $townshipName\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Address       :   $cus_address\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Invoice No    :   $invoiceNumber\n".toByteArray())
        printDataByteArrayList.add("Sale Person   :   $salePersonName\n".toByteArray())
        printDataByteArrayList.add("RouteNo       :   $routeName\n".toByteArray()) // ToDo
        printDataByteArrayList.add(("Sale Date     :   " + SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US).format(Date()) + "\n").toByteArray())

        if (invoice.due_date != null && printMode.equals("sale", true))
            printDataByteArrayList.add("Delivery Date :   ${invoice.due_date}\n".toByteArray())

        printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

        var promoFlg = false

        for (soldProduct in soldProductList){
            if (soldProduct.promotionPrice == 0.0){
                promoFlg = false
                break
            } else{
                promoFlg = true
                break
            }
        }

        if (promoFlg){
            formatter = Formatter(StringBuilder(), Locale.US)
            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n",
                    "Item",
                    "Qty",
                    "Price",
                    "Amount"
                ).toString().toByteArray()
            )
            formatter!!.close()

            printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

            for (soldProduct in soldProductList){
                val quantity = soldProduct.quantity
                var pricePerUnit = 0.0

                pricePerUnit = if (soldProduct.promotionPrice == 0.0)
                    soldProduct.product.selling_price?.toDouble() ?: 0.0
                else
                    soldProduct.promotionPrice

                val amount = soldProduct.totalAmt
                val pricePerUnitWithDiscount = soldProduct.discountAmount
                val netAmount = amount - pricePerUnitWithDiscount
                val itemFocPercent = soldProduct.focPercent
                val itemFocAmount = soldProduct.focAmount
                val itemFocDiscountAmt = soldProduct.itemDiscountAmount

                totalAmount += amount
                totalNetAmount += netAmount

                val nameFragments = soldProduct.product.product_name!!.split(" ")
                val nameList = setupPrintLayoutWithPromo(nameFragments as ArrayList<String>)

                if (amount != 0.0) {
                    if (printFor == PRINT_FOR_PRE_ORDER) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t  %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameList[0],
                                quantity,
                                decimalFormatterWithoutComma.format(pricePerUnit),
                                decimalFormatterWithComma.format(amount)
                            ).toString().toByteArray()
                        )

                        formatter!!.close()
                    }

                    if (printFor != PRINT_FOR_PRE_ORDER) {

                        formatter = Formatter(StringBuilder(), Locale.US)
                        var nameTxt = ""
                        if (nameList.size > 0)
                            nameTxt = nameList[0]
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameTxt,
                                quantity,
                                decimalFormatterWithoutComma.format(pricePerUnit),
                                decimalFormatterWithComma.format(netAmount)
                            ).toString().toByteArray()
                        )


                    }

                    if (nameList.size > 0) {
                        nameList.removeAt(0)
                        for (cutName in nameList) {
                            formatter = Formatter(StringBuilder(), Locale.US)
                            printDataByteArrayList.add(
                                formatter!!.format(
                                    "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n",
                                    cutName,
                                    "",
                                    "",
                                    ""
                                ).toString().toByteArray()
                            )

                            formatter!!.close()
                        }
                    }

                    if (itemFocPercent > 0 || itemFocAmount > 0) {
                        val disItemAmt = itemFocDiscountAmt * soldProduct.quantity
                        var text =
                            "Discount---" + decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
                        if (itemFocAmount > 0) {
                            text =
                                "Discount---" + decimalFormatterWithoutComma.format(itemFocAmount) + "---"
                        }
                        if (itemFocPercent > 0 && itemFocPercent > 0) {
                            text =
                                "Discount---" + decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
                        }
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$2s \t \t %3$1s \t \t \t %4$9s\n",
                                text,
                                "",
                                " (" + decimalFormatterWithoutComma.format(disItemAmt) + ") ",
                                ""
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                        totalDiscountAmt += disItemAmt
                    }

                    printDataByteArrayList.add("\n".toByteArray())
                } else {
                    focProductList.add(soldProduct)
                }
            }
        } else {
            formatter = Formatter(StringBuilder(), Locale.US)

            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n",
                    "Item",
                    "Qty",
                    "Price",
                    "Amount"
                ).toString().toByteArray()
            )
            formatter!!.close()
            printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

            focProductList.clear()
            for (soldProduct in soldProductList) {

                val quantity = soldProduct.quantity

                var pricePerUnit = if (soldProduct.promotionPrice === 0.0) {
                    soldProduct.product.selling_price!!.toDouble()
                } else {
                    soldProduct.promotionPrice
                }

                val amount = soldProduct.totalAmount
                val pricePerUnitWithDiscount: Double = soldProduct.discountAmount
                val netAmount = soldProduct.totalAmount - pricePerUnitWithDiscount

                val itemFocPercent = soldProduct.focPercent
                val itemFocAmount = soldProduct.focAmount
                val itemFocDiscountAmt = soldProduct.itemDiscountAmount

                totalAmount += amount
                totalNetAmount += netAmount

                val nameFragments = soldProduct.product.product_name!!.split(" ")
                val nameList = setupPrintLayoutNoPromo(nameFragments as ArrayList<String>)

                if (amount != 0.0) {
                    if (printFor == PRINT_FOR_PRE_ORDER) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameList[0],
                                quantity,
                                decimalFormatterWithoutComma.format(pricePerUnit),
                                decimalFormatterWithComma.format(amount)
                            ).toString().toByteArray()
                        )

                        formatter!!.close()
                    }

                    if (printFor != PRINT_FOR_PRE_ORDER) {

                        formatter = Formatter(StringBuilder(), Locale.US)
                        var nameTxt = ""
                        if (nameList.size > 0)
                            nameTxt = nameList[0]
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameTxt,
                                quantity,
                                decimalFormatterWithoutComma.format(pricePerUnit),
                                decimalFormatterWithComma.format(netAmount)
                            ).toString().toByteArray()
                        )

                    }

                    if (nameList.size > 0) {
                        nameList.removeAt(0)
                        for (cutName in nameList) {
                            formatter = Formatter(StringBuilder(), Locale.US)
                            printDataByteArrayList.add(
                                formatter!!.format(
                                    "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n",
                                    cutName,
                                    "",
                                    "",
                                    ""
                                ).toString().toByteArray()
                            )

                            formatter!!.close()
                        }
                    }

                    if (itemFocPercent > 0 || itemFocAmount > 0) {
                        val disItemAmt = itemFocDiscountAmt * soldProduct.quantity
                        var text =
                            "Discount---" + decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
                        if (itemFocAmount > 0) {
                            text =
                                "Discount---" + decimalFormatterWithoutComma.format(itemFocAmount) + "---"
                        }
                        if (itemFocPercent > 0 && itemFocPercent > 0) {
                            text =
                                "Discount---" + decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
                        }
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$2s \t \t %3$1s \t \t \t %4$9s\n",
                                text,
                                "",
                                " (" + decimalFormatterWithoutComma.format(disItemAmt) + ") ",
                                ""
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                        totalDiscountAmt += disItemAmt
                    }

                    printDataByteArrayList.add("\n".toByteArray())
                } else {
                    focProductList.add(soldProduct)
                }
            }

        }

        printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

        formatter = Formatter(StringBuilder(), Locale.US)

        val taxType = companyInfo.tax_type

        var taxText: String
        if (taxType.equals("E", ignoreCase = true)) {
            taxText = "(Tax " + String.format("%.2f", invoice.tax_amount) + " Excluded)"
            totalNetAmount = invoice.total_amount!!.toDouble() - invoice.total_discount_amount + invoice.tax_amount
        } else {
            taxText = "(Tax " + String.format("%.2f", invoice.tax_amount) + " Included)"
            totalNetAmount = invoice.total_amount!!.toDouble() - invoice.total_discount_amount
        }

        when {
            printFor == PRINT_FOR_PRE_ORDER -> printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-13s%2$21s\n%3$-15s%4$21s\n%5$-13s%6$21s\n\n\n",
                    "Total Amount      :        ",
                    decimalFormatterWithComma.format(totalAmount),
                    taxText,
                    ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/,
                    "Total Discount    :        ",
                    decimalFormatterWithComma.format(totalDiscountAmt),
                    "Net Amount        :        ",
                    decimalFormatterWithComma.format(invoice.pay_amount),
                    "Pay Amount        :        ",
                    decimalFormatterWithComma.format(invoice.pay_amount)
                ).toString().toByteArray()
            )
            mode == FOR_DELIVERY -> printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-13s%2$21s\n%3$-13s%4$21s\n%5$-13s%6$21s\n%7$-13s%8$21s\n%9$-13s%10$21s\n\n\n",
                    "Total Amount      :        ",
                    decimalFormatterWithComma.format(totalAmount),
                    taxText,
                    ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/,
                    "Total Discount    :        ",
                    decimalFormatterWithoutComma.format(totalDiscountAmt),
                    "Net Amount        :        ",
                    decimalFormatterWithComma.format(totalNetAmount),
                    "Pay Amount        :        ",
                    decimalFormatterWithComma.format(invoice.pay_amount)
                ).toString().toByteArray()
            )
            else -> {
                var creditBalance: Double? = 0.0
                if (totalNetAmount > invoice.pay_amount!!.toDouble()) {
                    creditBalance = totalNetAmount - invoice.pay_amount!!.toDouble()
                }
                var refundAmt = 0.0
                if (invoice.refund_amount != null)
                    refundAmt = invoice.refund_amount!!.toDouble()

                printDataByteArrayList.add(
                    formatter!!.format(
                        "%1$-13s%2$21s\n%3$-13s%4$21s\n%5$-13s%6$21s\n%7$-13s%8$21s\n%9$-13s%10$21s\n%11$-13s%12$21s\n%13$-13s%14$21s\n%15$-13s%16$21s\n",
                        "Total Amount      :        ",
                        decimalFormatterWithComma.format(totalAmount),
                        taxText,
                        ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + new DecimalFormat("#0.00").format(taxPercent) + "%)"*/,
                        "Total Discount    :        ",
                        decimalFormatterWithComma.format(totalDiscountAmt),
                        "Vol: Discount     :        ",
                        decimalFormatterWithComma.format(invoice.total_discount_amount),
                        "Net Amount        :        ",
                        decimalFormatterWithComma.format(totalNetAmount),
                        "Pay Amount        :        ",
                        decimalFormatterWithComma.format(invoice.pay_amount),
                        "Credit Balance    :        ",
                        decimalFormatterWithComma.format(abs(creditBalance!!)),
                        "Refund            :        ",
                        decimalFormatterWithComma.format(refundAmt)
                    ).toString().toByteArray()
                )
            }
        }

        printDataByteArrayList.add("\n".toByteArray())

        if (presentList != null && presentList.size > 0) {

            formatter = Formatter(StringBuilder(), Locale.US)

            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n",
                    "Foc",
                    "Qty",
                    "Price",
                    "Amount"
                ).toString().toByteArray()
            )

            formatter!!.close()
            printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

            /* PRESENT LIST */
            for (invoicePresent in presentList) {

                val quantity = invoicePresent.promotion_quantity
                val presentPrice = 0.0

                // Shorthand the name.
                val productName = "Temporary" //getProductNameAndPrice(invoicePresent) Check point !!! ToDo
                val nameFragments = productName!!.split(" ")
                val nameList = setupPrintLayoutNoPromo(nameFragments as ArrayList<String>)

                if (printFor == PRINT_FOR_PRE_ORDER) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                            nameList[0],
                            quantity,
                            decimalFormatterWithComma.format(presentPrice),
                            "0.0"
                        ).toString().toByteArray()
                    )
                    formatter!!.close()
                }

                if (printFor != PRINT_FOR_PRE_ORDER) {

                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                            nameList[0],
                            quantity,
                            decimalFormatterWithComma.format(presentPrice),
                            "0.0"
                        ).toString().toByteArray()
                    )
                    formatter!!.close()
                }

                nameList.removeAt(0)
                for (cutName in nameList) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n", cutName, "", "", ""
                        ).toString().toByteArray()
                    )

                    formatter!!.close()
                }

                printDataByteArrayList.add("\n".toByteArray())

            }

        }

        printDataByteArrayList.add("\n\n              $txtForFooter\n\n".toByteArray())
        printDataByteArrayList.add("\nSignature       :\n\n                 Thank You. \n\n".toByteArray())
        printDataByteArrayList.add("\n".toByteArray())

        return printDataByteArrayList
    }

    private fun getPrintDataByteArrayListForCredit(
        customerName: String?,
        cus_address: String?,
        invoiceNumber: String,
        salePersonName: String?,
        townshipName: String,
        creditList: CreditInvoice,
        companyInfo: CompanyInformation,
        routeName: String?
    ): ArrayList<ByteArray>{

        val printDataByteArrayList = ArrayList<ByteArray>()

        val address = companyInfo.address
        val companyTaxRegNo = companyInfo.company_tax_reg_no
        val txtForFooter = companyInfo.pos_voucher_footer1

        printDataByteArrayList.add((address + "\n").toByteArray())
        printDataByteArrayList.add((companyTaxRegNo + "\n").toByteArray())
        printDataByteArrayList.add("Customer           :  $customerName\n".toByteArray())
        printDataByteArrayList.add("Township           :  $townshipName\n".toByteArray())
        printDataByteArrayList.add("Address            :  $cus_address\n".toByteArray())
        printDataByteArrayList.add("Offical Receive No :  $invoiceNumber\n".toByteArray())
        printDataByteArrayList.add("Collect Person     :  $salePersonName\n".toByteArray())
        printDataByteArrayList.add("RouteNo            :  $routeName\n".toByteArray()) // ToDo
        printDataByteArrayList.add(("Total Amount       :  " + decimalFormatterWithComma.format(creditList.amt) + "\n").toByteArray())
        printDataByteArrayList.add("Discount           :  0.0 (0%)\n".toByteArray())
        printDataByteArrayList.add(("Net Amount         :  " + decimalFormatterWithComma.format(creditList.amt) + "\n").toByteArray())
        printDataByteArrayList.add(("Receive            :  " + decimalFormatterWithComma.format(creditList.payAmt) + "\n").toByteArray())
        printDataByteArrayList.add((txtForFooter + "\n\n").toByteArray())
        printDataByteArrayList.add("\nSignature          :\n\n                 Thank You. \n\n".toByteArray())
        printDataByteArrayList.add(byteArrayOf(0x1b, 0x64, 0x02)) // Cut
        printDataByteArrayList.add(byteArrayOf(0x07)) // Kick cash drawer

        return printDataByteArrayList

    }

    private fun getPrintDataByteArrayListDeliver(
        activity: Activity,
        customerName: String?,
        cus_address: String?,
        orderInvoiceNo: String,
        orderSalePersonName: String?,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        townshipName: String,
        invoice: Invoice,
        soldProductList: ArrayList<SoldProductInfo>,
        presentList: ArrayList<Promotion>,
        prepaidAmount: Double,
        printFor: String,
        mode: String,
        companyInfo: CompanyInformation
    ): ArrayList<ByteArray>{

        val printDataByteArrayList = ArrayList<ByteArray>()
        val imgByte = printBmp(activity)

        imgByte?.let { printDataByteArrayList.add(it) }
        printDataByteArrayList.add("\n".toByteArray())

        var totalAmount = 0.0
        var totalNetAmount = 0.0

        val address = companyInfo.address
        val txtForFooter = companyInfo.pos_voucher_footer1
        val companyTaxRegNo = companyInfo.company_tax_reg_no
        val phNo = companyInfo.phone_number

        printDataByteArrayList.add((address + "\n").toByteArray())
        printDataByteArrayList.add("Ph No           : $phNo\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Tax Reg No      :  $companyTaxRegNo\n".toByteArray())
        printDataByteArrayList.add("Customer        :  $customerName\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Township        :  $townshipName\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Address         :  $cus_address\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Order Invoice   :  $orderInvoiceNo\n".toByteArray())
        printDataByteArrayList.add("Order Person    :  $orderSalePersonName\n".toByteArray())
        printDataByteArrayList.add("Delivery No     :  $invoiceNumber\n".toByteArray())
        printDataByteArrayList.add("Delivery Person :  $salePersonName\n".toByteArray())
        printDataByteArrayList.add("RouteNo         :  $routeName\n".toByteArray())
        printDataByteArrayList.add(("Delivery Date   :  " + SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US).format(Date()) + "\n").toByteArray())

        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())

        var promoFlg = false

        for (soldProduct in soldProductList) {
            if (soldProduct.promotionPrice == 0.0) {
                promoFlg = false
                break
            } else {
                promoFlg = true
                break
            }
        }

        if (promoFlg){
            formatter = Formatter(StringBuilder(), Locale.US)
            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-10s \t %2$6s \t %3$5s \t %4$5s \t %5$7s\n",
                    "Item",
                    "Qty",
                    "Price",
                    "Pro:Price",
                    "Amount"
                ).toString().toByteArray()
            )
            formatter!!.close()
            printDataByteArrayList.add("----------------------------------------------\n".toByteArray())

            for (soldProduct in soldProductList){
                val quantity = soldProduct.quantity
                var pricePerUnit = 0.0
                var promoPrice = 0.0

                pricePerUnit = soldProduct.product.selling_price!!.toDouble()
                promoPrice = soldProduct.promotionPrice

                val amount = soldProduct.totalAmount
                val pricePerUnitWithDiscount: Double = soldProduct.discountAmount
                val netAmount: Double


                netAmount = soldProduct.totalAmount - pricePerUnitWithDiscount

                totalAmount += amount
                totalNetAmount += netAmount

                // Shorthand the name.
                val nameFragments = soldProduct.product.product_name!!.split(" ")
                val nameList = setupPrintLayoutWithPromo(nameFragments as ArrayList<String>)

                if (printFor == PRINT_FOR_PRE_ORDER) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n",
                            nameList[0],
                            quantity,
                            decimalFormatterWithoutComma.format(pricePerUnit),
                            decimalFormatterWithoutComma.format(promoPrice),
                            decimalFormatterWithComma.format(amount)
                        ).toString().toByteArray()
                    )
                    formatter!!.close()
                }

                if (printFor != PRINT_FOR_PRE_ORDER) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n",
                            nameList[0],
                            quantity,
                            decimalFormatterWithoutComma.format(pricePerUnit),
                            decimalFormatterWithoutComma.format(promoPrice),
                            decimalFormatterWithComma.format(netAmount)
                        ).toString().toByteArray()
                    )
                    formatter!!.close()
                }

                nameList.removeAt(0)
                for (cutName in nameList) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t %2$1s \t %3$1s \t %4$1s \t %5$1s\n", cutName, "", "", "", ""
                        ).toString().toByteArray()
                    )

                    formatter!!.close()
                }

                printDataByteArrayList.add("\n".toByteArray())
            }

            if (presentList != null && presentList.size > 0) {

                for (invoicePresent in presentList) {
                    val quantity = invoicePresent.promotion_quantity
                    val presentPrice = 0.0
                    val promoPrice = 0.0

                    // Shorthand the name.
                    val productName = "Temporary" //getProductNameAndPrice(invoicePresent) Check point !!! ToDo
                    val nameFragments = productName!!.split(" ")
                    val nameList = setupPrintLayoutWithPromo(nameFragments as ArrayList<String>)

                    if (printFor == PRINT_FOR_PRE_ORDER) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n",
                                nameList.get(0),
                                quantity,
                                decimalFormatterWithComma.format(presentPrice),
                                decimalFormatterWithoutComma.format(promoPrice),
                                "0.0"
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                    }

                    if (printFor != PRINT_FOR_PRE_ORDER) {

                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n",
                                nameList.get(0),
                                quantity,
                                decimalFormatterWithComma.format(presentPrice),
                                decimalFormatterWithoutComma.format(promoPrice),
                                "0.0"
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                    }

                    nameList.removeAt(0)
                    for (cutName in nameList) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t %2$1s \t %3$1s \t %4$1s \t %5$1s\n",
                                cutName,
                                "",
                                "",
                                "",
                                ""
                            ).toString().toByteArray()
                        )

                        formatter!!.close()
                    }

                    printDataByteArrayList.add("\n".toByteArray())
                }
                printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
            }
        } else{
            formatter = Formatter(StringBuilder(), Locale.US)

            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n",
                    "Item",
                    "Qty",
                    "Price",
                    "Amount"
                ).toString().toByteArray()
            )
            formatter!!.close()
            printDataByteArrayList.add("----------------------------------------------\n".toByteArray())

            for (soldProduct in soldProductList){
                val quantity = soldProduct.quantity
                var pricePerUnit = soldProduct.product.selling_price!!.toDouble()

                val amount = soldProduct.totalAmount
                val pricePerUnitWithDiscount: Double = soldProduct.discountAmount
                val netAmount: Double

                netAmount = soldProduct.totalAmount - pricePerUnitWithDiscount

                totalAmount += amount
                totalNetAmount += netAmount

                val nameFragments = soldProduct.product.product_name!!.split(" ")
                val nameList = setupPrintLayoutNoPromo(nameFragments as ArrayList<String>)

                if (printFor == PRINT_FOR_PRE_ORDER) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                            nameList[0],
                            quantity,
                            decimalFormatterWithoutComma.format(pricePerUnit),
                            decimalFormatterWithComma.format(amount)
                        ).toString().toByteArray()
                    )

                    formatter!!.close()
                }

                if (printFor != PRINT_FOR_PRE_ORDER) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                            nameList[0],
                            quantity,
                            decimalFormatterWithoutComma.format(pricePerUnit),
                            decimalFormatterWithComma.format(netAmount)
                        ).toString().toByteArray()
                    )
                }

                nameList.removeAt(0)
                for (cutName in nameList) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n", cutName, "", "", ""
                        ).toString().toByteArray()
                    )

                    formatter!!.close()
                }

                printDataByteArrayList.add("\n".toByteArray())
            }

            if (presentList != null && presentList.size > 0) {

                for (invoicePresent in presentList) {
                    val quantity = invoicePresent.promotion_quantity
                    val presentPrice = 0.0

                    // Shorthand the name.
                    val productName = "Temporary" //getProductNameAndPrice(invoicePresent) Check point !!! ToDo
                    val nameFragments = productName!!.split(" ")
                    val nameList = setupPrintLayoutNoPromo(nameFragments as ArrayList<String>)

                    if (printFor == PRINT_FOR_PRE_ORDER) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameList[0],
                                quantity,
                                decimalFormatterWithComma.format(presentPrice),
                                "0.0"
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                    }

                    if (printFor != PRINT_FOR_PRE_ORDER) {

                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameList[0],
                                quantity,
                                decimalFormatterWithComma.format(presentPrice),
                                "0.0"
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                    }

                    nameList.removeAt(0)
                    for (cutName in nameList) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n",
                                cutName,
                                "",
                                "",
                                ""
                            ).toString().toByteArray()
                        )

                        formatter!!.close()
                    }

                    printDataByteArrayList.add("\n".toByteArray())
                }

            }
        }

        formatter = Formatter(StringBuilder(), Locale.US)

        val taxType = companyInfo.tax_type

        var taxText: String
        if (taxType.equals("E", ignoreCase = true)) {
            taxText = "(Tax " + String.format("%.2f", invoice.tax_amount) + " Excluded)"
            totalNetAmount = invoice.total_amount!!.toDouble() - invoice.total_discount_amount + invoice.tax_amount
        } else {
            taxText = "(Tax " + String.format("%.2f", invoice.tax_amount) + " Included)"
            totalNetAmount = invoice.total_amount!!.toDouble() - invoice.total_discount_amount
        }

        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())

        when {
            printFor == PRINT_FOR_PRE_ORDER -> printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-13s%2$19s\n%3$-15s%4$17s\n%5$-13s%6$19s\n\n\n",
                    "Total Amount    :",
                    decimalFormatterWithComma.format(totalAmount),
                    taxText,
                    ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/,
                    "Discount        :",
                    decimalFormatterWithComma.format(invoice.total_discount_amount) + " (" + DecimalFormat("#0.00").format(invoice.total_discount_percent) + "%)",
                    "Net Amount  :",
                    decimalFormatterWithComma.format(invoice.pay_amount),
                    "Receive      :",
                    decimalFormatterWithComma.format(invoice.pay_amount)
                ).toString().toByteArray()
            )
            mode == FOR_DELIVERY -> printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s%6$19s\n%7$-13s%8$19s\n%9$-13s%10$19s\n\n\n",
                    "Total Amount    :",
                    decimalFormatterWithComma.format(totalAmount),
                    taxText,
                    ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/,
                    "Discount        :",
                    decimalFormatterWithComma.format(invoice.total_discount_amount) + " (" + DecimalFormat("#0.00").format(invoice.total_discount_percent) + "%)",
                    "Net Amount      :",
                    decimalFormatterWithComma.format(totalNetAmount),
                    "Pay Amount      :",
                    decimalFormatterWithComma.format(invoice.pay_amount)
                ).toString().toByteArray()
            )
            else -> {
                var creditBalance: Double? = 0.0
                totalNetAmount -= prepaidAmount
                if (totalNetAmount > invoice.pay_amount?.toDouble() ?: 0.0) {
                    creditBalance = totalNetAmount - (invoice.pay_amount?.toDouble() ?: 0.0)
                }

                printDataByteArrayList.add(
                    formatter!!.format(
                        "%1$-13s%2$21s\n%3$-13s%4$21s\n%5$-13s%6$21s\n%7$-13s%8$21s\n%9$-13s%10$21s\n%11$-13s%12$21s\n%13$-13s%14$21s\n",
                        "Total Amount    :        ",
                        decimalFormatterWithComma.format(totalAmount),
                        taxText,
                        "" /*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + new DecimalFormat("#0.00").format(taxPercent) + "%)"*/,
                        "Discount        :        ",
                        decimalFormatterWithComma.format(invoice.total_discount_amount) + " (" + DecimalFormat("#0.00").format(invoice.total_discount_percent) + "%)",
                        "Prepaid Amount  :        ",
                        decimalFormatterWithComma.format(prepaidAmount),
                        "Net Amount      :        ",
                        decimalFormatterWithComma.format(totalNetAmount),
                        "Pay Amount      :        ",
                        decimalFormatterWithComma.format(invoice.pay_amount),
                        "Credit Balance  :        ",
                        decimalFormatterWithComma.format(abs(creditBalance!!))
                    ).toString().toByteArray()
                )
            }
        }

        printDataByteArrayList.add("\n\n              $txtForFooter\n\n".toByteArray())
        printDataByteArrayList.add("\nSignature       :\n\n                 Thank You. \n\n".toByteArray())
        printDataByteArrayList.add(byteArrayOf(0x1b, 0x64, 0x02)) // Cut
        printDataByteArrayList.add(byteArrayOf(0x07)) // Kick cash drawer

        return printDataByteArrayList

    }


    private fun printBmp(mActivity: Activity): ByteArray? {
        val mBitmap = BitmapFactory.decodeResource(mActivity.resources, R.drawable.global_sky_logo)
        val nMode = 0
        val nPaperWidth = 384
        return if (mBitmap != null) {
            PrintPicture.POS_PrintBMP(mBitmap, nPaperWidth, nMode)
        } else null
    }

    /*private fun getProductNameAndPrice(invoicePresent: Promotion): String? {
        val cursorProductName = database.rawQuery(
            "SELECT PRODUCT_NAME, SELLING_PRICE FROM PRODUCT WHERE ID = " + invoicePresent.getPromotionProductId(),
            null
        )
        var productName: String? = null
        while (cursorProductName.moveToNext()) {
            productName =
                cursorProductName.getString(cursorProductName.getColumnIndex("PRODUCT_NAME"))
            invoicePresent.setPrice(cursorProductName.getDouble(cursorProductName.getColumnIndex("SELLING_PRICE")))
        }
        cursorProductName.close()
        return productName
    }*/

    private fun setupPrintLayoutWithPromo(nameFragments: ArrayList<String>): ArrayList<String> {

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

                var concatName1 = if (concatName.equals("", ignoreCase = true)) {
                    nameFragments[i]
                } else {
                    "$concatName ${nameFragments[i]}"
                }

                if (concatName1.length < 13) {
                    concatName += if (concatName.isNotEmpty()) {
                        " " + nameFragments[i]
                    } else {
                        nameFragments[i] + " "
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

    /*private fun setupPrintLayoutWithPromo(nameFragments: Array<String>): MutableList<String> {
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

                var concatName1 = if (concatName.equals("", ignoreCase = true)) {
                    nameFragments[i]
                } else {
                    concatName + " " + nameFragments[i]
                }

                if (concatName1.length < 13) {
                    concatName += if (concatName.isNotEmpty()) {
                        " " + nameFragments[i]
                    } else {
                        nameFragments[i] + " "
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
    }*/

    private fun setupPrintLayoutNoPromo(nameFragments: ArrayList<String>): ArrayList<String> {
        val nameList = ArrayList<String>()
        var concatName = nameFragments[0]
        var i = 1
        while (i != nameFragments.size) {
            if (concatName.length > 23) {
                nameList.add(concatName)
                concatName = nameFragments[i]
                nameList.add(concatName)
                concatName = ""
            } else {

                var concatName1 = if (concatName.equals("", ignoreCase = true)) {
                    nameFragments[i]
                } else {
                    concatName + " " + nameFragments[i]
                }

                if (concatName1.length < 20) {
                    concatName += if (concatName.isNotEmpty()) {
                        " " + nameFragments[i]
                    } else {
                        nameFragments[i] + " "
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

    private fun showToast(activity: Activity, message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun onDecimalFormat(value: Double): Double {
        val f = DecimalFormat("##.0000")
        val formattedValue = f.format(value)
        return java.lang.Double.parseDouble(formattedValue)
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
        for (invoiceCancel in invoicecancelList) {
            amountArray[0] += invoiceCancel.getTotalAmount()
            amountArray[1] += invoiceCancel.getDisAmount()
            amountArray[2] += invoiceCancel.getNetAmount()
        }
        return amountArray
    }


    fun backupDatabase(context: Context) {
        val database = MyDatabase.getInstance(context)
        database!!.close()
        val today = AppUtils.getCurrentDate(true)

        try {
            val sd = Environment.getExternalStorageDirectory()
            val data = Environment.getDataDirectory()

            if (sd.canWrite()) {
                Toast.makeText(
                    context, "Backup database is starting...",
                    Toast.LENGTH_SHORT
                ).show()
                val currentDBPath = "/data/" + context.packageName + "/databases/dms.db"

                val backupDBPath = "DMS_DB_Backup_$today.db"
                val currentDB = File(data, currentDBPath)

                val folderPath = "mnt/sdcard/DMS_DB_BACKUP"
                val f = File(folderPath)
                f.mkdir()
                val backupDB = File(f, backupDBPath)
                val source = FileInputStream(currentDB).channel
                val destination = FileOutputStream(backupDB).channel
                destination.transferFrom(source, 0, source.size())
                source.close()
                destination.close()
                Toast.makeText(
                    context, "Backup database Successful!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(context, "Please set Permission for Storage in Setting!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Cannot Backup!", Toast.LENGTH_SHORT).show()
        }

    }

}
