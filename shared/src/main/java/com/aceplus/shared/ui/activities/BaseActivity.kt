package com.aceplussolutions.rms.ui.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.aceplussolutions.rms.constants.AppConstants
import com.aceplussolutions.rms.constants.AppUtils

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId:Int
    var pd: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        pd = AppUtils.getDialog(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun displayProgressDialog() {
        pd?.show()
    }

    fun hideProgressDialog() {
            pd?.dismiss()
    }

    fun displayAlertError(str: String) {
//        val dialog = AppUtils.getAlertDialog(str, this)
//        if (!dialog.isShowing) {
//            dialog.show()
//        }
    }
}