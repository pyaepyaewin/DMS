package com.aceplus.dms.ui.fragments.report

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.shared.ui.activities.BaseFragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.fragment_product_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import com.github.mikephil.charting.utils.ColorTemplate
import android.R.attr.entries






class TargetAndActualSalesForProductReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.aceplus.dms.R.layout.fragment_product_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var dataSets: MutableList<BarDataSet>?
        val valueSet1 = ArrayList<BarEntry>()
        val v1e1 = BarEntry(110f, 0f) // Jan
        valueSet1.add(v1e1)
        val v1e2 = BarEntry(40.000f, 1f) // Feb
        valueSet1.add(v1e2)
        val v1e3 = BarEntry(60.000f, 2f) // Mar
        valueSet1.add(v1e3)
        val v1e4 = BarEntry(30.000f, 3f) // Apr
        valueSet1.add(v1e4)
        val v1e5 = BarEntry(90.000f, 4f) // May
        valueSet1.add(v1e5)
        val v1e6 = BarEntry(100.000f, 5f) // Jun
        valueSet1.add(v1e6)

        val valueSet2 = ArrayList<BarEntry>()
        val v2e1 = BarEntry(150.000f, 0f) // Jan
        valueSet2.add(v2e1)
        val v2e2 = BarEntry(90.000f, 1f) // Feb
        valueSet2.add(v2e2)
        val v2e3 = BarEntry(120.000f, 2f) // Mar
        valueSet2.add(v2e3)
        val v2e4 = BarEntry(60.000f, 3f) // Apr
        valueSet2.add(v2e4)
        val v2e5 = BarEntry(20.000f, 4f) // May
        valueSet2.add(v2e5)
        val v2e6 = BarEntry(80.000f, 5f) // Jun
        valueSet2.add(v2e6)

        val barDataSet1 = BarDataSet(valueSet1, "Brand 1")
        barDataSet1.color = Color.rgb(0, 155, 0)
        val barDataSet2 = BarDataSet(valueSet2, "Brand 2")
        barDataSet2.setColors(*ColorTemplate.COLORFUL_COLORS)
        dataSets = mutableListOf()
        dataSets.add(barDataSet1)
        dataSets.add(barDataSet2)

        val months = mutableListOf<String>()
        months.add("JAN")
        months.add("FEB")
        months.add("MAR")
        months.add("APR")
        months.add("MAY")
        months.add("JUN")

        dataSets = ArrayList()
        dataSets.add(barDataSet1)
        dataSets.add(barDataSet2)
        val data = BarData(months as BarDataSet,dataSets as BarDataSet)
        data.barWidth = 10f
        chart.setDrawBarShadow(false)
        val description = Description()
        description.text = "Sale Target"
        chart.description = description
        chart.data = data
        chart.animateXY(2000, 2000)
        chart.invalidate()
    }

}