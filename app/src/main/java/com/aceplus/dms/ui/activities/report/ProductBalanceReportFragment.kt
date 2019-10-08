package com.aceplus.dms.ui.activities.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.ProductBalanceReportAdapter
import com.aceplus.dms.viewmodel.report.ProductBalanceReportViewModel
import com.aceplus.domain.entity.product.Product
import com.aceplus.shared.ui.activities.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class ProductBalanceReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    lateinit var productBalanceReports: RecyclerView
    private val productBalanceReportAdapter: ProductBalanceReportAdapter by lazy { ProductBalanceReportAdapter() }
    private val productBalanceReportViewModel: ProductBalanceReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_balance_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productBalanceReports = view.findViewById(R.id.productBalanceReports) as RecyclerView

        productBalanceReportViewModel.productBalanceReportSuccessState.observe(this, Observer {
            productBalanceReportAdapter.setNewList(it as ArrayList<Product>)
        })

        productBalanceReportViewModel.productBalanceReportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        productBalanceReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productBalanceReportAdapter
        }
        productBalanceReportViewModel.productBalanceReport()
    }
}