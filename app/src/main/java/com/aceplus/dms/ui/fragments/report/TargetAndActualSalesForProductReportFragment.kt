package com.aceplus.dms.ui.fragments.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import com.aceplus.domain.model.forApi.sale.saletarget.SaleTargetForSaleMan
import com.aceplus.domain.model.sale.SaleTarget
import com.aceplus.shared.ui.activities.BaseFragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.fragment_product_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein


class TargetAndActualSalesForProductReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val targetAndActualSalesForProductReportViewModel: ReportViewModel by viewModel()
    private var saleTargetArrayList = ArrayList<SaleTargetForSaleMan>()
    private var productListFromDB = ArrayList<Product>()
    private var targetArrList = HashMap<Int, SaleTarget>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        targetAndActualSalesForProductReportViewModel.saleTargetAndSaleManForProductReportSuccessState.observe(
            this,
            Observer {
                targetArrList.clear()
                getTargetSaleDB(it!!.first)
                getActualSaleDB(it.second)
                initialize()

            })
        targetAndActualSalesForProductReportViewModel.loadNameListForSaleTargetProduct()
    }

    private fun getTargetSaleDB(targetSaleList: List<SaleTargetSaleMan>) {
        for (list in targetSaleList) {
            val id = list.id.toString()
            val fromDate = list.from_date
            val toDate = list.to_date
            val saleManId = list.sale_man_id
            val categoryId = list.category_id
            val groupCodeId = list.group_code_id
            val stockId = list.stock_id
            val targetAmt = list.target_amount
            val date = list.date
            val invoiceNo = list.invoice_no
            val saleTarget = SaleTargetForSaleMan()
            saleTarget.id = id
            saleTarget.fromDate = fromDate
            saleTarget.toDate = toDate
            saleTarget.saleManId = saleManId
            saleTarget.categoryId = categoryId
            saleTarget.groupCodeId = groupCodeId
            saleTarget.stockId = stockId
            saleTarget.targetAmount = targetAmt
            saleTarget.date = date
            saleTarget.invoiceNo = invoiceNo
            saleTargetArrayList.add(saleTarget)
        }
    }

    private fun getActualSaleDB(actualSaleList: Triple<List<Product>, List<Product>, List<Product>>) {
        var type = 0
        var commonList = actualSaleList.first
        if (commonList.count() == 0) {
            commonList = actualSaleList.second
            type = 1
            if (commonList.count() == 0) {
                commonList = actualSaleList.third
                type = 2
            }
        }
        if (commonList != null && commonList.count() > 0) {
            for (data in commonList) {
                val id = data.id.toString()
                targetAndActualSalesForProductReportViewModel.invoiceProductList.observe(
                    this,
                    Observer {
                        if (it != null) {
                            for (i in it) {
                                val productId = i.productId!!
                                val totalAmount = i.totalAmount
                                val saleQuantity = i.saleQuantity!!.toDouble()
                                var sellingPrice = 0.0
                                if (i.totalAmount != null) {
                                    sellingPrice = totalAmount / saleQuantity
                                }
                                val saleTarget = SaleTarget()
                                saleTarget.productId = productId
                                saleTarget.quantity = saleQuantity
                                saleTarget.sellingPrice = sellingPrice
                                saleTarget.totalAmount = totalAmount

                                if (type == 0) {
                                    targetAndActualSalesForProductReportViewModel.groupIdListFromProduct.observe(
                                        this,
                                        Observer { productList ->
                                            productListFromDB = productList as ArrayList<Product>
                                            if (productList != null) {
                                                for (i in productList) {
                                                    val groupId = i.group_id!!.toInt()
                                                    if (!targetArrList.containsKey(groupId)) {
                                                        targetArrList[groupId] = saleTarget
                                                    } else {
                                                        val temp = targetArrList[groupId]
                                                        var totalAmt = temp!!.totalAmount
                                                        totalAmt += totalAmount
                                                        saleTarget.totalAmount = totalAmt
                                                        targetArrList[groupId] = saleTarget

                                                    }
                                                }
                                            }
                                        })
                                    targetAndActualSalesForProductReportViewModel.loadGroupIdListFromProduct(
                                        productId.toInt()
                                    )
                                } else if (type == 1) {
                                    if (productListFromDB != null) {
                                        for (i in productListFromDB) {
                                            val categoryId = i.category_id!!.toInt()
                                            if (!targetArrList.containsKey(categoryId)) {
                                                targetArrList[categoryId] = saleTarget
                                            } else {
                                                val temp = targetArrList[categoryId]
                                                var totalAmt = temp!!.totalAmount
                                                totalAmt += totalAmount
                                                saleTarget.totalAmount = totalAmt
                                                targetArrList[categoryId] = saleTarget

                                            }
                                        }
                                    }
                                } else if (type == 2) {
                                    targetArrList[productId.toInt()] = saleTarget
                                }
                            }
                        }
                    })
                targetAndActualSalesForProductReportViewModel.loadInvoiceProductList(id)
            }
        }
    }

    private fun initialize() {
        val xAxisList = getXAxisValues()
        if (xAxisList.size == 0) {
            xAxisList.add("")
        }
        val barDataArrayList = getDataSet()
        var data: BarData? = null
        data = BarData(barDataArrayList[0],barDataArrayList[1])
        chart.canScrollHorizontally(20)
        chart.data = data
        val description = Description()
        description.text = "Sale Target"
        chart.description = description
        chart.animateXY(2000, 2000)
        chart.xAxis.getFormattedLabel(0)
        chart.invalidate()
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisList)
    }

    private fun getXAxisValues(): ArrayList<String> {
        val xAxis = ArrayList<String>()
        for (saleTarget in saleTargetArrayList) {
            when {
                saleTarget.stockId != null -> xAxis.add(
                    targetAndActualSalesForProductReportViewModel.loadProductNameFromProduct(
                        Integer.parseInt(saleTarget.stockId)
                    )
                )
                saleTarget.groupCodeId != null -> xAxis.add(
                    targetAndActualSalesForProductReportViewModel.loadGroupCodeNameFromGroupCode(
                        Integer.parseInt(saleTarget.groupCodeId)
                    )
                )
                saleTarget.categoryId != null -> xAxis.add(
                    targetAndActualSalesForProductReportViewModel.loadCategoryNameFromProductCategory(
                        Integer.parseInt(saleTarget.categoryId)
                    )
                )
                else -> xAxis.add("")
            }
        }
        return xAxis
    }

    private fun getDataSet(): ArrayList<BarDataSet> {
        var dataSets: ArrayList<BarDataSet>? = null
        val valueSet1 = ArrayList<BarEntry>()
        val valueSet2 = ArrayList<BarEntry>()
        for (i in saleTargetArrayList.indices) {
            val v1e12 = BarEntry((saleTargetArrayList[i].targetAmount).toFloat(), i.toFloat()) // Dec
            valueSet1.add(v1e12)
            var groupId: Int? = null // Dec
            when {
                saleTargetArrayList[i].groupCodeId != null -> groupId =
                    (saleTargetArrayList[i].groupCodeId).toInt()
                saleTargetArrayList[i].categoryId != null -> groupId =
                    (saleTargetArrayList[i].categoryId).toInt()
                saleTargetArrayList[i].stockId != null -> groupId =
                    (saleTargetArrayList[i].stockId).toInt()
            }
            if (targetArrList.containsKey(groupId)) {
                val target = targetArrList[groupId]
                val vlel = BarEntry(((target!!.totalAmount).toString().toFloat()), i.toFloat()) // Dec
                valueSet2.add(vlel)
            }
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
        return dataSets
    }

}
