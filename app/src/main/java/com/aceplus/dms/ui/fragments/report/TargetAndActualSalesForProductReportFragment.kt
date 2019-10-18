package com.aceplus.dms.ui.fragments.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.shared.ui.activities.BaseFragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.fragment_product_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*


class TargetAndActualSalesForProductReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    // private var saleTargetArrayList = listOf<SaleTargetSaleMan>()
    var actualSale = mutableListOf<Invoice>()
    private val targetAndActualSalesForProductReportViewModel: ReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        targetAndActualSalesForProductReportViewModel.saleTargetAndSaleManReportSuccessState.observe(
            this,
            Observer {
                val targetArrList = HashMap<Int, Invoice>()
                val dataSets: ArrayList<BarDataSet>?
                val valueSet1 = ArrayList<BarEntry>()
                val valueSet2 = ArrayList<BarEntry>()
                val arr = mutableListOf<String>()
                for (i in it!!.first)
                    arr.add(i.target_amount.toString())
                Log.d("All","$arr")

                for (i in it!!.first.indices) {
                    val v1e12 =
                        BarEntry((it!!.first[i].target_amount)!!.toFloat(), i.toFloat()) // Dec
                    valueSet1.add(v1e12)
//                    var groupId: Int? = null
//                    when {
//                        it.first[i].group_code_id != null -> groupId = Integer.valueOf(it.first[i].group_code_id)
//                        it.first[i].category_id != null -> groupId = Integer.valueOf(it.first[i].category_id)
//                        it.first[i].stock_id != null -> groupId = Integer.valueOf(it.first[i].stock_id)
//                    }
//                    if (targetArrList.containsKey(groupId)) {
//                        val target = targetArrList[groupId]
//                        val v1e13 = BarEntry(((target!!.total_amount)!!.toFloat()),i.toFloat()) // Dec
//                        valueSet2.add(v1e13)
//                    }
                }
                val barDataSet1 = BarDataSet(valueSet1, "Target Sale")
                val res1 = this.resources
                barDataSet1.color = res1.getColor(R.color.colorPrimary)
                val barDataSet2 = BarDataSet(valueSet2, "Actual Sale")
                val res2 = this.resources
                barDataSet2.color = res2.getColor(R.color.colorPrimaryDark)

                dataSets = ArrayList()
                dataSets.add(barDataSet1)
                dataSets.add(barDataSet2)
                //......................//
                var data: BarData?
                data = BarData(dataSets as MutableList<IBarDataSet>)
                chart.canScrollHorizontally(20)
                chart.data = data
                val description = Description()
                description.text = "Sale Target"
                chart.description = description
                chart.animateXY(2000, 2000)
                chart.invalidate()

            })
        targetAndActualSalesForProductReportViewModel.loadSaleTargetAndSaleManReport()
    }
}