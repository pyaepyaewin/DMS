package com.aceplus.dms.ui.fragments.report

import android.app.ActionBar
import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import com.aceplus.domain.model.forApi.sale.saletarget.SaleTargetForCustomer
import com.aceplus.domain.model.sale.SaleTarget
import com.aceplus.domain.vo.report.SaleTargetVO
import com.aceplus.shared.ui.activities.BaseFragment
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.fragment_sale_comparison_report.*
import org.achartengine.ChartFactory
import org.achartengine.GraphicalView
import org.achartengine.model.CategorySeries
import org.achartengine.renderer.DefaultRenderer
import org.achartengine.renderer.SimpleSeriesRenderer
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein


class TargetAndActualSalesForSalesManReportFragment : BaseFragment(), KodeinAware {

    override val kodein: Kodein by kodein()
    private var groupNameList = mutableListOf<String>()
    private var categoryNameList = mutableListOf<String>()
    private var categoryIdArr = mutableListOf<String>()
    private var groupIdArr = mutableListOf<String>()
    private var saleTargetArrayList = ArrayList<SaleTargetForCustomer>()
    private var actualTargetArrayList = ArrayList<SaleTarget>()
    private val NAME_LIST = arrayOf("Actual Sale", "Remaining Sale")
    private val COLORS = intArrayOf(R.color.colorPrimaryDark, R.color.colorPrimary)
    private var VALUE = ArrayList<Double>()
    private var mChartView: GraphicalView? = null
    private val mRenderer = DefaultRenderer()
    private val mSeries = CategorySeries("")
    private var allSaleTargetValue = 0.0
    private var allActualSaleValue = 0.0
    private var sale = 0.0
    private var remainingSale = 0.0
    private var categoryId: String = ""
    private var groupId: String = ""
    private val targetAndActualSalesForSalesManReportViewModel: ReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_comparison_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val printImg = activity!!.findViewById(R.id.print_img) as ImageView
        printImg.visibility = View.VISIBLE
        getTargetSaleDB(-1)
        getGroupCodeListFromDbAndCategoryListFromDb()
        if (categoryIdArr != null && categoryIdArr.size != 0) {
            categoryId = categoryIdArr[spinner_category.selectedItemPosition]
        }

        if (groupIdArr != null && groupIdArr.size != 0) {
            groupId = groupIdArr[spinner_group.selectedItemPosition]
        }
        catchEvents()
        targetAndActualSalesForSalesManReportViewModel.loadProductGroupAndProductCategoryList()
    }

    private fun getGroupCodeListFromDbAndCategoryListFromDb() {
        spinner_customer.visibility = View.GONE
        targetAndActualSalesForSalesManReportViewModel.productGroupAndCategoryDataList.observe(
            this,
            Observer {
                //select group list in spinner
                if (it!!.first != null) {
                    groupNameList.add("All Group")
                    groupIdArr.add("-1")
                    for (group in it!!.first) {
                        groupNameList.add(group.name!!)
                        groupIdArr.add(group.id.toString())
                    }
                }
                val groupNameSpinnerAdapter =
                    ArrayAdapter(context, android.R.layout.simple_spinner_item, groupNameList)
                groupNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_group.adapter = groupNameSpinnerAdapter
                //select category list in spinner

                if (it!!.second != null) {
                    categoryNameList.add("All Category")
                    categoryIdArr.add("-1")
                    for (category in it.second) {
                        categoryNameList.add(category.category_name!!)
                        categoryIdArr.add(category.category_id!!)
                    }
                }
                val categoryNameSpinnerAdapter =
                    ArrayAdapter(context, android.R.layout.simple_spinner_item, categoryNameList)
                categoryNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_category.adapter = categoryNameSpinnerAdapter
            })
    }

    private fun getTargetSaleDB(customerIdFromSpinner: Int) {
        saleTargetArrayList.clear()
        if (customerIdFromSpinner != -1) {
            targetAndActualSalesForSalesManReportViewModel.targetSaleDBList.observe(this, Observer {
                for (data in it!!) {
                    val id = data.id.toString()
                    val fromDate = data.from_date
                    val toDate = data.to_date
                    val saleManId = data.sale_man_id
                    val categoryId = data.category_id
                    val groupCodeId = data.group_code_id
                    val stockId = data.stock_id
                    val targetAmt = data.target_amount
                    val date = data.date
                    val invoiceNo = data.invoice_no
                    val saleTarget = SaleTargetForCustomer()
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
            })
            targetAndActualSalesForSalesManReportViewModel.loadTargetSaleDB(customerIdFromSpinner)
        }
    }

    private fun catchEvents() {
        //Click Group Spinner
        spinner_group.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                updateChartData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        //Click Category Spinner
        spinner_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                updateChartData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    private fun updateChartData() {
        if (categoryIdArr != null && categoryIdArr.size != 0) {
            categoryId = categoryIdArr[spinner_category.selectedItemPosition]
        }

        if (groupIdArr != null && groupIdArr.size != 0) {
            groupId = groupIdArr[spinner_group.selectedItemPosition]
        }
        if (categoryId != "-1") {
            targetAndActualSalesForSalesManReportViewModel.categorySaleTargetDataList.observe(
                this,
                Observer {
                    actualTargetArrayList.clear()
                    for (i in it!!) {
                        var productId = ""
                        var saleQty = 0.0
                        var totalSaleAmount = 0.0
                        if (i.productId != null) {
                            productId = i.productId
                        }
                        if (i.saleQuantity != null) {
                            saleQty = i.saleQuantity.toDouble()
                        }
                        if (i.totalAmount != null) {
                            totalSaleAmount = i.totalAmount
                        }
                        val sellingPrice = totalSaleAmount / saleQty
                        val saleTarget = SaleTarget()
                        saleTarget.productId = productId
                        saleTarget.quantity = saleQty
                        saleTarget.sellingPrice = sellingPrice
                        saleTarget.totalAmount = totalSaleAmount
                        actualTargetArrayList.add(saleTarget)
                    }
                })
            targetAndActualSalesForSalesManReportViewModel.loadCategorySaleTargetAndSaleIdList(categoryId)
        }
        if (groupId != "-1") {
            targetAndActualSalesForSalesManReportViewModel.groupSaleTargetDataList.observe(this, Observer {
                for (i in it!!) {
                    var productId = ""
                    var saleQty = 0.0
                    var totalSaleAmount = 0.0
                    if (i.productId != null) {
                        productId = i.productId
                    }
                    if (i.saleQuantity != null) {
                        saleQty = i.saleQuantity.toDouble()
                    }
                    if (i.totalAmount != null) {
                        totalSaleAmount = i.totalAmount
                    }
                    val sellingPrice = totalSaleAmount / saleQty
                    val saleTarget = SaleTarget()
                    saleTarget.productId = productId
                    saleTarget.quantity = saleQty
                    saleTarget.sellingPrice = sellingPrice
                    saleTarget.totalAmount = totalSaleAmount
                    actualTargetArrayList.add(saleTarget)
                }
            })
            targetAndActualSalesForSalesManReportViewModel.loadGroupSaleTargetAndSaleIdList(groupId)
        }
        initialize()
    }

    private fun initialize(){
        for (j in saleTargetArrayList.indices) {
            allSaleTargetValue += Integer.parseInt(saleTargetArrayList[j].targetAmount).toDouble()
        }
        for (j in actualTargetArrayList.indices) {
            allActualSaleValue += actualTargetArrayList[j].totalAmount
        }

        sale_target_txt.text = allSaleTargetValue.toString()
        sale_txt.text = allActualSaleValue.toString()

        if (allSaleTargetValue != 0.0) {
            sale = allActualSaleValue / (allSaleTargetValue / 100)
        }
        if (sale < 100) {
            remainingSale = 100 - sale
            VALUE.clear()
            sale = java.lang.Double.parseDouble(String.format("%.2f", sale))
            remainingSale = java.lang.Double.parseDouble(String.format("%.2f", remainingSale))
            VALUE.add(sale)
            VALUE.add(remainingSale)

        } else {
            sale = 100.0
            remainingSale = 0.0
            VALUE.clear()
            VALUE.add(sale)
            VALUE.add(remainingSale)
        }
        mRenderer.isApplyBackgroundColor = true
        mRenderer.backgroundColor = Color.WHITE
        mRenderer.labelsTextSize = 20f
        mRenderer.labelsColor = Color.BLACK
        mRenderer.legendTextSize = 20f
        mRenderer.margins = intArrayOf(100, 0)
        mRenderer.startAngle = 90f
        if (mSeries.itemCount != 0) {
            mSeries.clear()
            for (simpleSeriesRenderer in mRenderer.seriesRenderers) {
                mRenderer.removeSeriesRenderer(simpleSeriesRenderer)
            }
        }
        for (i in VALUE.indices) {
            mSeries.add(NAME_LIST[i] + " " + VALUE[i] + "%", VALUE[i])
            val renderer = SimpleSeriesRenderer()
            val res = this.resources
            renderer.color = res.getColor(COLORS[(mSeries.itemCount - 1) % COLORS.size])
            mRenderer.addSeriesRenderer(renderer)
        }
        if (mChartView != null) {
            mChartView!!.repaint()
        }

    }
    override fun onResume() {
        super.onResume()
        if (mChartView == null) {
            mChartView = ChartFactory.getPieChartView(activity, mSeries, mRenderer)
            mRenderer.isClickEnabled = false
            mRenderer.selectableBuffer = 10
            pieChart.addView(mChartView, ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT))
        } else {
            mChartView!!.repaint()
        }
    }

}