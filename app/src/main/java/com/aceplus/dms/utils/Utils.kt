package com.aceplus.dms.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
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
import com.aceplus.domain.model.sale.SaleManDailyReport
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

    val decimalFormatterWithoutComma = DecimalFormat("##0.##")
    val decimalFormatterWithComma = DecimalFormat("###,##0")
    private val decimalFormatterWithComma1 = DecimalFormat("###,##0.##")

    /*const val forPackageSale = "for-package-sale"
    const val forPreOrderSale = "for-pre-order-sale"*/

    const val MODE_CUSTOMER_FEEDBACK = "mode_customer_feedback"
    const val MODE_GENERAL_SALE = "mode_general_sale"

    const val RQ_BACK_TO_CUSTOMER = 2
    const val RQ_BACK_TO_CUSTOMER_VISIT = 3

    private var progressDialog: ProgressDialog? = null

    val isOsMarshmallow: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    private lateinit var onActionClickListener: OnActionClickListener

    interface OnActionClickListener {
        fun onActionClick(type: String)
    }

    /*private val mCallback = Communication.SendCallback { result, communicateResult ->
        val msg:String
        var flg = 1
        when (communicateResult) {
            Communication.Result.Success -> {
                msg = "Success!"
                flg = 0
            }
            Communication.Result.ErrorOpenPort -> msg = "Fail to openPort"
            Communication.Result.ErrorBeginCheckedBlock -> msg = "Printer is offline (beginCheckedBlock)"
            Communication.Result.ErrorEndCheckedBlock -> msg = "Printer is offline (endCheckedBlock)"
            Communication.Result.ErrorReadPort -> msg = "Read port error (readPort)"
            Communication.Result.ErrorWritePort -> msg = "Write port error (writePort)"
            else -> msg = "Unknown error"
        }

        commonDialog(msg, act, flg)
    }*/

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

    fun formatAmount(amount: Double?): String = decimalFormatterWithComma1.format(amount)

    fun getCurrentDate(withTime: Boolean): String {

        var dateFormat = "yyyy-MM-dd"
        if (withTime) dateFormat += " HH:mm:ss"

        return SimpleDateFormat(dateFormat).format(Date())
    }

    fun getDate(withTime: Boolean): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val newDate = calendar.time
        var dateFormat = "yyyy-MM-dd"
        if (withTime) dateFormat += " HH:mm:ss"

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
        return Settings.Secure.getString(activity.contentResolver, Settings.Secure.ANDROID_ID)
//        val telephonyManager = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        return telephonyManager.imei

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

    fun showToast(activity: Activity, message: String) {
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

    fun isInternetAccess(context: Context): Boolean{

        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        if (activeNetwork != null)
            return activeNetwork.isConnected

        return false

    }



    @Throws(UnsupportedEncodingException::class)
    private fun convertFromListByteArrayTobyteArray(ByteArray: List<ByteArray>): ByteArray {
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

    private fun sendDataByte2BT(mActivity: Activity, mService: BluetoothService, data: ByteArray) {
        if (mService.state !== BluetoothService.STATE_CONNECTED) {
            Toast.makeText(mActivity, "Not Connected", Toast.LENGTH_SHORT)
                .show()
            return
        }
        mService.write(data)
        commonDialog("Success", mActivity, 0)

    }


}
