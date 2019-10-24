package com.aceplus.dms.ui.activities

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.creditcollectionadapters.CreditCollectionCheckOutAdapter
import com.aceplus.dms.viewmodel.creditcollection.CreditCollectionCheckOutViewModel
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionCheckoutDataClass
import kotlinx.android.synthetic.main.activity_credit_collection.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class CreditCollectionCheckoutActivity : AppCompatActivity(),KodeinAware
{
    override val kodein: Kodein by kodein()
    private val creditCollectionCheckOutAdapter: CreditCollectionCheckOutAdapter by lazy {
        CreditCollectionCheckOutAdapter(this::onClickNoticeListItem)
    }
    private fun onClickNoticeListItem(data: CreditCollectionCheckoutDataClass) {
//        Toast.makeText(
//            applicationContext,
//            "You clicked at ${data.customer_name}",
//            Toast.LENGTH_SHORT
//        ).show()
//        val checkOutIntent = Intent(this, CreditCollectionCheckoutActivity::class.java)
//        startActivity(checkOutIntent)
    }

    private val creditCollectionCheckOutViewModel: CreditCollectionCheckOutViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(CreditCollectionCheckOutViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_collection)

        cancel_img.setOnClickListener {
            onBackPressed()
            true
        }

        creditCollectionCheckOutViewModel.creditCollectionCheckOutSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                creditCollectionCheckOutAdapter.setNewList(it as ArrayList<CreditCollectionCheckoutDataClass>)


            })

        creditCollectionCheckOutViewModel.creditCollectionCheckOutErrorState.observe(
            this,
            android.arch.lifecycle.Observer {
            })
        rvCreditCheckOut.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = creditCollectionCheckOutAdapter
        }
        //creditCollectionCheckOutViewModel.loadCreditCollectionCheckOut()


    }
}
