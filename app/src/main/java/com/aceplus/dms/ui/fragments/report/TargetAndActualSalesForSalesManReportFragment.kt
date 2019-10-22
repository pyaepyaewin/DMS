package com.aceplus.dms.ui.fragments.report

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.shared.ui.activities.BaseFragment
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.fragment_sale_comparison_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein


class TargetAndActualSalesForSalesManReportFragment : BaseFragment(), KodeinAware {

    override val kodein: Kodein by kodein()
    var groupNameList = mutableListOf<String>()
    var categoryNameList = mutableListOf<String>()
    private val targetAndActualSalesForSalesManReportViewModel: ReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_sale_comparison_report,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        targetAndActualSalesForSalesManReportViewModel.saleTargetAndSaleManReportSuccessState.observe(
            this,
            Observer {
                var value = mutableListOf<Float>()
                var targetAmount = 0.0
                var saleAmount = 0.0
                val sumAmount :Double
                val tAmount :Int
                val sAmount :Int
                Log.d("First List", "${it!!.first.size}")
                Log.d("Second List", "${it!!.second.size}")
                for (target in it!!.first) {
                    targetAmount += target.target_amount!!.toDouble()
                }
                sale_target_txt.text = targetAmount.toString()

                for (sale in it.second) {
                    saleAmount += sale.total_amount!!.toDouble()
                }
                sale_txt.text = saleAmount.toString()
                if (targetAmount == 0.0 && saleAmount == 0.0) {
                    tAmount = 50
                    sAmount = 50
                } else {
                    sumAmount = targetAmount + saleAmount
                    Log.d("Sum Amount", "$sumAmount")
                    tAmount = (targetAmount.toInt() * 100) / sumAmount.toInt()
                    Log.d("Multiply Amount", "$tAmount")
                    sAmount = (saleAmount.toInt() / sumAmount.toInt()) * 100
                }
                //pie chart show
                pieChart?.setUsePercentValues(true)
                val legend: Legend? = pieChart?.legend
                legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                value = mutableListOf(tAmount.toFloat(), sAmount.toFloat())
                Log.d("All Amount", "${value[0]}")
                val label = mutableListOf("Sale Target $targetAmount", "Sale $saleAmount")

                val entry = ArrayList<PieEntry>()
                for (i in value.indices) {
                    entry.add(PieEntry(value[i], label[i]))
                }
                val dataSet = PieDataSet(entry, "Result")
                dataSet.setColors(
                    resources.getColor(R.color.colorPrimary),
                    resources.getColor(R.color.colorPrimaryDark)
                )
                dataSet.setDrawValues(true)
                val pieData = PieData(dataSet)
                dataSet.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
                dataSet.valueLinePart1OffsetPercentage = 10f
                dataSet.valueLinePart1Length = 0.43f
                dataSet.valueLinePart2Length = .1f
                dataSet.valueTextColor = Color.BLACK
                dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                pieChart.setEntryLabelColor(Color.BLUE)
                pieData.setValueFormatter(PercentFormatter() as ValueFormatter?)
                pieData.setValueTextSize(10f)
                pieData.setValueTextColor(Color.BLACK)
                pieChart?.data = pieData
                chart.canScrollHorizontally(20)
                pieChart.animateXY(2000, 2000)
                pieChart.invalidate()
            })

        targetAndActualSalesForSalesManReportViewModel.groupDataList.observe(this, Observer {
            if (it != null) {
                groupNameList.add("All Group")
                for (group in it) {
                    groupNameList.add(group.group_name.toString())
                }
            }
            val groupNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, groupNameList)
            groupNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_group.adapter = groupNameSpinnerAdapter
        })
        targetAndActualSalesForSalesManReportViewModel.categoryDataList.observe(this, Observer {
            if (it != null) {
                categoryNameList.add("All Category")
                for (category in it) {
                    categoryNameList.add(category.category_name.toString())
                }
            }
            val categoryNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, categoryNameList)
            categoryNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_category.adapter = categoryNameSpinnerAdapter
        })
        targetAndActualSalesForSalesManReportViewModel.loadProductGroup()
        targetAndActualSalesForSalesManReportViewModel.loadProductCategory()
        targetAndActualSalesForSalesManReportViewModel.loadSaleTargetAndSaleManReport()

    }

}