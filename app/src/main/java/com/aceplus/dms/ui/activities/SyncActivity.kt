package com.aceplus.dms.ui.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Toast
import com.aceplus.data.repoimpl.SyncRepoImpl
import com.aceplus.dms.R
import com.aceplus.dms.di.provideDB
import com.aceplus.dms.di.provideDownloadApi
import com.aceplus.dms.di.provideSharedPreferences
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.SyncViewModel
import com.aceplus.dms.viewmodel.factory.SyncViewModelFactory
import com.aceplus.domain.repo.SyncRepo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sync.*

class SyncActivity : BaseActivity(), Utils.OnActionClickListener {
    override val layoutId: Int
        get() = R.layout.activity_sync

    lateinit var syncViewModel: SyncViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = provideDB(applicationContext)
        val downloadApi = provideDownloadApi()
        val shf = provideSharedPreferences(applicationContext)
        val syncRepo: SyncRepo = SyncRepoImpl(downloadApi, db, shf)

        val syncViewModelFactory = SyncViewModelFactory(syncRepo)
        syncViewModel = ViewModelProviders.of(this, syncViewModelFactory).get(SyncViewModel::class.java)
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

        Utils.setOnActionClickListener(this)//set on action click listener
        buttonDownload.setOnClickListener {
            Utils.askConfirmationDialog("DOWNLOAD", "Do you want to confirm?", "download", this@SyncActivity)
        }
        buttonUpload.setOnClickListener {
            Utils.askConfirmationDialog("UPLOAD", "Do you want to confirm?", "upload", this@SyncActivity)
        }
        buttonReissue.setOnClickListener { syncViewModel.downloadReissue() }
        buttonSaleVisitRecord.setOnClickListener {
            Utils.askConfirmationDialog(
                "UPLOAD",
                "Do you want to upload Sale Visit Record?",
                "upload_sale_visit_record",
                this@SyncActivity
            )
        }
        buttonClearProductData.setOnClickListener {
            Utils.askConfirmationDialog(
                "DELETE PRODUCTS ",
                "Are you sure want to clear product data ?",
                "delete_product",
                this@SyncActivity
            )
        }
        buttonClearData.setOnClickListener {
            Utils.askConfirmationDialog(
                "DELETE",
                "Are you sure want to clear all data ?",
                "delete_all_data",
                this@SyncActivity
            )
        }
        cancel_img.setOnClickListener { onBackPressed() }
    }

    override fun onActionClick(type: String) {
        when (type) {
            "download" -> {
                Toast.makeText(applicationContext, "download", Toast.LENGTH_SHORT).show()
                Utils.callDialog("Please wait...", this)
                syncViewModel.downloadAllData()
            }
            "upload" -> {
                Toast.makeText(applicationContext, "upload", Toast.LENGTH_SHORT).show()
                Utils.callDialog("Please wait...", this)
                syncViewModel.uploadAllData()
            }
            "upload_sale_visit_record" -> {
                Toast.makeText(applicationContext, "upload sale visit record", Toast.LENGTH_SHORT).show()
                Utils.callDialog("Please wait...", this)
                syncViewModel.uploadSaleVisitRecord()
            }
            "delete_all_data" -> {
                Toast.makeText(applicationContext, "all data clear", Toast.LENGTH_SHORT).show()
                Utils.callDialog("Please wait...", this)
                syncViewModel.deleteAllData()
            }
            "delete_product" -> {
                Toast.makeText(applicationContext, "product clear", Toast.LENGTH_SHORT).show()
                Utils.callDialog("Please wait...", this)
                syncViewModel.deleteProductData()
            }
        }
    }

}
