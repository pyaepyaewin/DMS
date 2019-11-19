package com.aceplus.dms.ui.activities.customer.sale

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log.i
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.sale.SalesCancelAdapter
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.salecancelviewmodel.SaleCancelViewModel
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_credit_collection.*
import kotlinx.android.synthetic.main.activity_sale_cancel.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class SaleCancelActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    override val layoutId: Int
        get() = R.layout.activity_sale_cancel


    companion object {
        fun newIntentFromCustomer(context: Context): Intent {
            return Intent(context, SaleCancelActivity::class.java)
        }
    }

    private val saleCancelViewModel: SaleCancelViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(SaleCancelViewModel::class.java)
    }

    private val saleCancelAdapter: SalesCancelAdapter by lazy {
        SalesCancelAdapter(this::onClickNoticeListItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sale_cancel_img.setOnClickListener {
            onBackPressed()
            true
        }
        saleCancelViewModel.saleCancelSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {

                saleCancelAdapter.setNewList(it as ArrayList<SaleCancelItem>)


            })

        saleCancelViewModel.saleCancelErrorState.observe(
            this,
            android.arch.lifecycle.Observer {
                i("Tag", it)
            })
        sale_cancel_item_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = saleCancelAdapter
        }
        saleCancelViewModel.loadSaleCancelList()

    }

    private fun onClickNoticeListItem(data: SaleCancelItem) {
        startActivity(SaleCancelDetailActivity.getIntent(this, data.invoice_id, data.sale_date,data.id,data.customer_name))
        finish()
    }
}
