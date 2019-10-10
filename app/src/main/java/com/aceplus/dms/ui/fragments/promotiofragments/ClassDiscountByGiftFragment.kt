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
import com.aceplus.dms.ui.adapters.promotionadapters.ClassDiscountByGiftAdapter
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.promotionviewmodels.ClassDiscountByGiftViewModel
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByGiftDataClass
import kotlinx.android.synthetic.main.tab_frag_class_dis_gift.*
import kotlinx.android.synthetic.main.tab_fragment_category_discount_quantity.cancel_img
import kotlinx.android.synthetic.main.tab_fragment_category_discount_quantity.category_discount_title
import kotlinx.android.synthetic.main.tab_fragment_category_discount_quantity.product_name
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class ClassDiscountByGiftFragment:Fragment(),KodeinAware {
    override val kodein: Kodein by kodein()
    private val classDiscountByGiftAdapter: ClassDiscountByGiftAdapter by lazy {
        ClassDiscountByGiftAdapter()
    }

    private val classDiscountByGiftViewModel: ClassDiscountByGiftViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(ClassDiscountByGiftViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_frag_class_dis_gift, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancel_img.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

        }
        category_discount_title.setText("CLASS DISCOUNT BY Gift")
        product_name.setText("CLASS ID")
        classDiscountByGiftViewModel.classDiscountByGiftSuccessState.observe(this, android.arch.lifecycle.Observer {
            classDiscountByGiftAdapter.setNewList(it as ArrayList<ClassDiscountByGiftDataClass>)
        })

        classDiscountByGiftViewModel.classDiscountByGiftErrorState.observe(this,android.arch.lifecycle.Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        rvClassDiscountByGift.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = classDiscountByGiftAdapter
        }
        classDiscountByGiftViewModel.loadClassDiscountByPrice()
    }


}


