package com.aceplus.dms.ui.promotiofragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.MainActivity
import com.aceplus.dms.ui.adapters.promotionadapters.PromotionPriceAdapter
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.promotionviewmodels.PromotionPriceViewModel
import com.aceplus.domain.model.promotionDataClass.PromotionPriceDataClass
import kotlinx.android.synthetic.main.fragment_tab_promotion_by_customer_item.*
import kotlinx.android.synthetic.main.tab_fragment_promotion_price.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class PromotionPriceFragment: Fragment() , KodeinAware {
    override val kodein: Kodein by kodein()
    private val promotionPriceAdapter: PromotionPriceAdapter by lazy {
        PromotionPriceAdapter()
    }

    private val promotionPriceViewModel: PromotionPriceViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(PromotionPriceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_fragment_promotion_price, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancel_imgPromotionPrice.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

        }

        promotionPriceViewModel.promotionPriceSuccessState.observe(this, Observer {
            promotionPriceAdapter.setNewList(it as ArrayList<PromotionPriceDataClass>)
        })
        promotionPriceViewModel.promotionPriceErrorState.observe(this,android.arch.lifecycle.Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        rvPromotionPrice.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = promotionPriceAdapter
        }
        promotionPriceViewModel.loadPromotionPrice()
    }
    }
