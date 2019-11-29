package com.aceplus.dms.ui.fragments.promotiofragments

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
import com.aceplus.dms.ui.adapters.promotionadapters.ClassDiscountForShowPriceAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.promotionviewmodels.PromotionViewModel
import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowPriceDataClass
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.tab_fragment_category_discount_quantity.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class ClassDiscountForShowPriceFragment:BaseFragment(),KodeinAware {
    override val kodein: Kodein by kodein()
    private val classDiscountForShowPriceAdapter: ClassDiscountForShowPriceAdapter by lazy {
        ClassDiscountForShowPriceAdapter()
    }

    private val classDiscountForShowPriceViewModel: PromotionViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_fragment_category_discount_quantity, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancel_img.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

        }
        product_name.text = "CLASS ID"
        category_discount_title.text = "CLASS DISCOUNT FOR SHOW PRICE"
        classDiscountForShowPriceViewModel.classDiscountForShowPriceSuccessState.observe(this, android.arch.lifecycle.Observer {
            classDiscountForShowPriceAdapter.setNewList(it as ArrayList<ClassDiscountForShowPriceDataClass>)
        })

        classDiscountForShowPriceViewModel.classDiscountForShowPriceErrorState.observe(this,android.arch.lifecycle.Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        rvCategoryDiscount.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = classDiscountForShowPriceAdapter
        }
        classDiscountForShowPriceViewModel.loadClassDiscountForShowPrice(Utils.getCurrentDate(true))
    }
    }

