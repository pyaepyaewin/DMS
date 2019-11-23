package com.aceplus.dms.ui.activities.creditcollection

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log.i
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.creditcollectionadapters.CreditCollectionAdapter
import com.aceplus.dms.viewmodel.creditcollection.CreditCollectionViewModel
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionDataClass
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_credit_collect.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class CreditCollectionActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    override val layoutId: Int
        get() = R.layout.activity_credit_collect
    private val creditCollectionAdapter: CreditCollectionAdapter by lazy {
        CreditCollectionAdapter(this::onClickNoticeListItem)
    }

    private fun onClickNoticeListItem(data: CreditCollectionDataClass) {
        Toast.makeText(
            applicationContext,
            "You clicked at ${data.customer_name}",
            Toast.LENGTH_SHORT
        ).show()
        startActivity(CreditCollectionCheckoutActivity.getIntent(this,data.id,data.customer_name))
    }

    private val creditCollectionViewModel: CreditCollectionViewModel by viewModel()



    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CreditCollectionActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cancel_img.setOnClickListener {
            onBackPressed()
            true
        }
        creditCollectionViewModel.creditCollectionSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {


                    creditCollectionAdapter.setNewList(it as ArrayList<CreditCollectionDataClass>)


            })

        creditCollectionViewModel.creditCollectionErrorState.observe(
            this,
            android.arch.lifecycle.Observer {
                i("Error",it)
            })
        rvCreditCollection.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = creditCollectionAdapter
        }
        creditCollectionViewModel.loadCreditCollection()


    }
}
