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
import com.aceplus.dms.utils.Utils
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
        val unpaidAmt=data.amount-data.pay_amount!!
        if (unpaidAmt !== 0.0) {
            startActivity(CreditCollectionCheckoutActivity.getIntent(this,data.id,data.customer_name))

        } else {
            Utils.commonDialog("No credit for this customer", this, 2)
        }
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
                it?.let {
                    if (it.isEmpty()) {
                        Utils.commonDialog("No credit to pay", this, 2)
                    } else

                        creditCollectionAdapter.setNewList(it as ArrayList<CreditCollectionDataClass>)
                }

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
