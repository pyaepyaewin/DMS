package com.aceplussolutions.rms.ui.activities

import android.app.ProgressDialog
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.aceplussolutions.rms.constants.AppUtils
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance

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

    inline fun <reified VM : ViewModel, T> T.viewModel(): Lazy<VM> where T : KodeinAware, T : FragmentActivity {
        return lazy { ViewModelProviders.of(this, direct.instance()).get(VM::class.java) }
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