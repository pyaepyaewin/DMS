package com.aceplus.dms.ui.activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.SyncViewModel
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sync.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class SyncActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sync

    private val syncViewModel: SyncViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        syncViewModel.successState.observe(this, Observer {
            Utils.cancelDialog()
            it?.let { successResult ->
                Utils.commonDialog(successResult.first, this@SyncActivity, 0)
                when (successResult.second) {
                    "download" -> {
                    }
                    "upload" -> {
                    }
                    "upload_sale_visit_record" -> {
                    }
                    "delete_all_data" -> {
                        Utils.backToLogin(this@SyncActivity)
                    }
                    "delete_product" -> {
                    }
                    "download_reissue" -> {

                    }
                }
            }
        })
        syncViewModel.errorState.observe(this, Observer {
            Utils.cancelDialog()
            it?.let { errorMessage -> Utils.commonDialog(errorMessage.first, this, 1) }
        })

        buttonDownload.setOnClickListener {
            Utils.askConfirmationDialog("DOWNLOAD", "Do you want to confirm?", "download", this@SyncActivity) { type ->
                Toast.makeText(applicationContext, type, Toast.LENGTH_SHORT).show()
                Utils.callDialog("Please wait...", this)
                syncViewModel.downloadAllData()
            }
        }
        buttonUpload.setOnClickListener {
            Utils.askConfirmationDialog("UPLOAD", "Do you want to confirm?", "upload", this@SyncActivity) { type ->
                Toast.makeText(applicationContext, type, Toast.LENGTH_SHORT).show()
                Utils.callDialog("Please wait...", this)
                syncViewModel.uploadAllData()
            }
        }
        buttonReissue.setOnClickListener { syncViewModel.downloadReissue() }
        buttonSaleVisitRecord.setOnClickListener {
            Utils.askConfirmationDialog(
                "UPLOAD",
                "Do you want to upload Sale Visit Record?",
                "upload_sale_visit_record",
                this@SyncActivity
            ) { type ->
                Toast.makeText(applicationContext, type, Toast.LENGTH_SHORT).show()
                Utils.callDialog("Please wait...", this)
                syncViewModel.uploadSaleVisitRecord()
            }
        }
        buttonClearProductData.setOnClickListener {
            Utils.askConfirmationDialog(
                "DELETE PRODUCTS ",
                "Are you sure want to clear product data ?",
                "delete_product",
                this@SyncActivity
            ) { type ->
                Toast.makeText(applicationContext, type, Toast.LENGTH_SHORT).show()
                Utils.callDialog("Please wait...", this)
                syncViewModel.deleteProductData()

            }
        }
        buttonClearData.setOnClickListener {
            Utils.askConfirmationDialog(
                "DELETE",
                "Are you sure want to clear all data ?",
                "delete_all_data",
                this@SyncActivity
            ) { type ->
                Toast.makeText(applicationContext, type, Toast.LENGTH_SHORT).show()
                Utils.callDialog("Please wait...", this)
                syncViewModel.deleteAllData()
            }
        }
        cancel_img.setOnClickListener { onBackPressed() }
    }


}
