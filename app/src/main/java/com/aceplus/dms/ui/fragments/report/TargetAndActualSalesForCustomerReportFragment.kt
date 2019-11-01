package com.aceplus.dms.ui.fragments.report

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
import com.aceplus.domain.vo.report.SaleTargetVO
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

class TargetAndActualSalesForCustomerReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val targetAndActualSalesForCustomerReportViewModel: ReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_comparison_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val customerNameList = mutableListOf<String>()
        val groupNameList = mutableListOf<String>()
        val categoryNameList = mutableListOf<String>()
        var customerId = 0
        var groupId = 0
        var categoryId = 0
        targetAndActualSalesForCustomerReportViewModel.customerAndProductGroupAndProductCategoryList.observe(
            this, Observer {
                //select customer list in spinner
                Log.d("Size", "${it!!.first.size}")
                customerNameList.add("All Customer")
                for (customer in it.first) {
                    customerNameList.add(customer.customer_name.toString())
                }
                //add customer name in spinner
                val customerNameSpinnerAdapter =
                    ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
                customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_customer.adapter = customerNameSpinnerAdapter
                spinner_customer.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            customerId = p2
                            Log.d("CuIndex", "$customerId")
                            if (customerId == 0) {
                                targetAndActualSalesForCustomerReportViewModel.saleTargetCustomerSuccessState.observe(
                                    this@TargetAndActualSalesForCustomerReportFragment,
                                    Observer {
                                        pieChart(it!!.first, it.second)
                                    })
                            } else {
                                targetAndActualSalesForCustomerReportViewModel.saleTargetAmountForCustomerList.observe(
                                    this@TargetAndActualSalesForCustomerReportFragment,
                                    Observer {
                                        pieChart1(it!!.first, it.second)
                                    })

                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                    }
                //select group list in spinner
                groupNameList.add("All Group")
                for (group in it.second) {
                    groupNameList.add(group.group_name.toString())
                }
                val groupNameSpinnerAdapter =
                    ArrayAdapter(context!!, android.R.layout.simple_spinner_item, groupNameList)
                groupNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_group.adapter = groupNameSpinnerAdapter
                spinner_group.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            groupId = p2
                            Log.d("GIndex", "$groupId")
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                    }
                //select category list in spinner
                categoryNameList.add("All Category")
                for (category in it.third) {
                    categoryNameList.add(category.category_name.toString())
                }
                val categoryNameSpinnerAdapter =
                    ArrayAdapter(context, android.R.layout.simple_spinner_item, categoryNameList)
                categoryNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_category.adapter = categoryNameSpinnerAdapter
                spinner_category.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            categoryId = p2
                            Log.d("CaIndex", "$categoryId")
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }


                    }

                Log.d("Index", "$customerId,$groupId,$categoryId")
            })
        targetAndActualSalesForCustomerReportViewModel.loadSaleTargetAndActualForCustomerReport()
        targetAndActualSalesForCustomerReportViewModel.loadCustomerAndProductGroupAndProductCategoryList()
        targetAndActualSalesForCustomerReportViewModel.loadSaleTargetAndActualAmountsForCustomerList(
            customerId, customerId.toString(),
            groupId,
            categoryId
        )
    }

    private fun pieChart(first: List<SaleTargetCustomer>, second: List<Invoice>) {
        var targetAmount = 0.0
        var saleAmount = 0.0
        val sumAmount: Double
        val tAmount: Int
        val sAmount: Int
        Log.d("First List", "${first.size}")
        Log.d("Second List", "${second.size}")
        for (target in first) {
            targetAmount += target.target_amount!!.toDouble()
        }
        sale_target_txt.text = targetAmount.toString()

        for (sale in second) {
            saleAmount += sale.total_amount!!.toDouble()
        }
        sale_txt.text = saleAmount.toString()
        if (targetAmount == 0.0 && saleAmount == 0.0) {
            tAmount = 50
            sAmount = 50
        } else {
            sumAmount = targetAmount + saleAmount
            tAmount = (targetAmount.toInt() * 100) / sumAmount.toInt()
            sAmount = (saleAmount.toInt() * 100) / sumAmount.toInt()
        }
        showChart(tAmount, sAmount, targetAmount, saleAmount)

    }

    private fun pieChart1(first: List<SaleTargetCustomer>, second: List<SaleTargetVO>) {
        var targetAmount = 0.0
        var saleAmount = 0.0
        val sumAmount: Double
        val tAmount: Int
        val sAmount: Int
        Log.d("First List", "${first.size}")
        Log.d("Second List", "${second.size}")
        for (target in first) {
            targetAmount += target.target_amount!!.toDouble()
        }
        sale_target_txt.text = targetAmount.toString()

        for (sale in second) {
            saleAmount += sale.totalAmount!!.toDouble()
        }
        sale_txt.text = saleAmount.toString()
        if (targetAmount == 0.0 && saleAmount == 0.0) {
            tAmount = 50
            sAmount = 50
        } else {
            sumAmount = targetAmount + saleAmount
            tAmount = (targetAmount.toInt() * 100) / sumAmount.toInt()
            sAmount = (saleAmount.toInt() * 100) / sumAmount.toInt()
        }
        showChart(tAmount, sAmount, targetAmount, saleAmount)

    }

    private fun showChart(tAmount: Int, sAmount: Int, targetAmount: Double, saleAmount: Double) {
        var value = mutableListOf<Float>()
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
    }
}