package com.aceplus.dms.ui.fragments.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import com.aceplus.domain.model.sale.SaleTarget
import com.aceplus.shared.ui.activities.BaseFragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.fragment_product_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein


class TargetAndActualSalesForProductReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private var saleTargetArrayList = listOf<SaleTargetSaleMan>()
    private var targetArrList = HashMap<Int, SaleTarget>()
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
                val amount = mutableListOf<String>()
                val col = mutableListOf<Float>()
                saleTargetArrayList = it!!.first
                var dataSets: ArrayList<BarDataSet>?
                val xAxis = java.util.ArrayList<String>()
                if (xAxis.size == 0) {
                    xAxis.add("")
                }
                for (i in saleTargetArrayList) {
                    amount.add(i.target_amount.toString())
                    col.add(i.id.toFloat())
                    //get xAxis
                    if (i.stock_id != null) {
                        xAxis.add(getProductNameFromDb(Integer.parseInt(i.stock_id)))
                    } else if (i.group_code_id != null) {

                        xAxis.add(getGroupNameFromDb(Integer.parseInt(i.group_code_id)))
                    } else if (i.category_id != null) {

                        xAxis.add(getCategoryNameFromDb(i.category_id.toString()))
                    } else {
                        xAxis.add("")
                    }

                }
                Log.d("XXXXXX","$xAxis")

                Log.d("Amount", "$amount")
                Log.d("Column", "$col")
                //get data set
                val valueSet1 = ArrayList<BarEntry>()
                val valueSet2 = ArrayList<BarEntry>()
                var groupId: Int? = null
                for (i in amount.indices) {
                    val xyStart = BarEntry(0f, 0f) // Jan
                    valueSet1.add(xyStart)
                    val v1e1 = BarEntry(col[i], amount[i].toFloat()) // Jan
                    valueSet1.add(v1e1)
                    when {
                        saleTargetArrayList[i].group_code_id != null -> groupId =
                            Integer.valueOf(saleTargetArrayList[i].group_code_id)
                        saleTargetArrayList[i].category_id != null -> groupId =
                            Integer.valueOf(saleTargetArrayList[i].category_id)
                        saleTargetArrayList[i].stock_id != null -> groupId =
                            Integer.valueOf(saleTargetArrayList[i].stock_id)
                    }
                    if (targetArrList.containsKey(groupId)) {
                        val target = targetArrList[groupId]
                        val v1e13 =
                            BarEntry(((target!!.totalAmount)!!.toFloat()), i.toFloat()) // Dec
                        valueSet2.add(v1e13)
                    }
                }

                val barDataSet1 = BarDataSet(valueSet1, "Sale Target")
                val res1 = this.resources
                barDataSet1.color = res1.getColor(R.color.colorPrimary)
                val barDataSet2 = BarDataSet(valueSet2, "Actual Sale")
                val res2 = this.resources
                barDataSet2.color = res2.getColor(R.color.colorPrimaryDark)
                val data = BarData(barDataSet1)
                val description = Description()
                description.text = "Sale Target"
                chart.description = description
                chart.data = data
                chart.animateXY(2000, 2000)
                chart.invalidate()

            })
        targetAndActualSalesForProductReportViewModel.loadSaleTargetAndSaleManReport()
    }

    private fun getProductNameFromDb(stockId: Int): String {
        var name = ""
        targetAndActualSalesForProductReportViewModel.loadProductNameDataList.observe(
            this,
            Observer {
                for (pName in it!!) {
                    name = pName.product_name!!
                }
            })
        targetAndActualSalesForProductReportViewModel.loadProductNameList(stockId = stockId)
        return name
    }

    private fun getGroupNameFromDb(groupNo: Int): String {
        var name = ""
        targetAndActualSalesForProductReportViewModel.loadGroupCodeNameDataList.observe(this,
            Observer {
                for (gName in it!!) {
                    name = gName.name!!
                }
            })
        targetAndActualSalesForProductReportViewModel.loadGroupCodeNameList(groupNo)
        return name
    }

    private fun getCategoryNameFromDb(categoryId: String): String {
        var name = ""
        targetAndActualSalesForProductReportViewModel.loadProductCategoryNameDataList.observe(this,
            Observer {
                for (cName in it!!) {
                    name = cName.category_name!!
                }
            })
        targetAndActualSalesForProductReportViewModel.loadProductCategoryNameList(categoryId)
        return name
    }

}