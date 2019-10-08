package com.aceplus.dms.ui.promotiofragments

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
import com.aceplus.dms.ui.adapters.promotionadapters.ClassDiscountForShowGiftAdapter
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.promotionviewmodels.ClassDiscountForShowGiftViewModel
import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowGiftDataClass
import kotlinx.android.synthetic.main.tab_frag_class_dis_gift.*
import kotlinx.android.synthetic.main.tab_frag_class_dis_gift.cancel_img
import kotlinx.android.synthetic.main.tab_frag_class_dis_gift.category_discount_title
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class ClassDiscountForShowGiftFragment :Fragment(),KodeinAware{
    override val kodein: Kodein by kodein()
    private val classDiscountForShowGiftAdapter: ClassDiscountForShowGiftAdapter by lazy {
        ClassDiscountForShowGiftAdapter()
    }

    private val classDiscountForShowGiftViewModel: ClassDiscountForShowGiftViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(ClassDiscountForShowGiftViewModel::class.java)
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

        product_name.setText("CLASS ID")
        category_discount_title.setText("CLASS DISCOUNT FOR SHOW GIFT")

        classDiscountForShowGiftViewModel.classDiscountForShowGiftSuccessState.observe(this, android.arch.lifecycle.Observer {
            classDiscountForShowGiftAdapter.setNewList(it as ArrayList<ClassDiscountForShowGiftDataClass>)
        })

        classDiscountForShowGiftViewModel.classDiscountForShowGiftErrorState.observe(this,android.arch.lifecycle.Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        rvClassDiscountByGift.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = classDiscountForShowGiftAdapter
        }
        classDiscountForShowGiftViewModel.loadClassDiscountForShowGift()
    }
}


