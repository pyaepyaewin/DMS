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
import com.aceplus.dms.ui.adapters.promotionadapters.VolumeDiscountFilterAdapter
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.promotionviewmodels.VolumeDiscountFilterViewModel
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountFilterDataClass
import kotlinx.android.synthetic.main.tab_fragment_volume_discount_filter.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class VolumeDiscountFilterFragment:Fragment(),KodeinAware {
    override val kodein: Kodein by kodein()
    private val volumeDiscountFilteradapter: VolumeDiscountFilterAdapter by lazy {
        VolumeDiscountFilterAdapter()
    }

    private val volumeDiscountFilterViewModel: VolumeDiscountFilterViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(VolumeDiscountFilterViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_fragment_volume_discount_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancel_img.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

        }
        volumeDiscountFilterViewModel.volumeDiscountFilterSuccessState.observe(this, android.arch.lifecycle.Observer {
            volumeDiscountFilteradapter.setNewList(it as ArrayList<VolumeDiscountFilterDataClass>)
        })

        volumeDiscountFilterViewModel.volumeDiscountFilterErrorState.observe(this,android.arch.lifecycle.Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        rvVolumeDiscountFilter.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = volumeDiscountFilteradapter
        }
        volumeDiscountFilterViewModel.loadVolumeDiscountFilterList()
    }
    }
