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
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
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

class TargetAndActualSalesForCustomerReportFragment : BaseFragment(), KodeinAware {
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
    private var customerIdArr = mutableListOf<String>()
    private var customerNameList = mutableListOf<String>()
    private var customerId: String = ""
    private var categoryId: String = ""
    private var groupId: String = ""
    private val targetAndActualSalesForCustomerReportViewModel: ReportViewModel by viewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_comparison_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val printImg = activity!!.findViewById(R.id.print_img) as ImageView
        printImg.visibility = View.VISIBLE
        getTargetSaleDB(-1)
        getCustomerListAndGroupCodeListFromDbAndCategoryListFromDb()
        catchEvents()
        targetAndActualSalesForCustomerReportViewModel.loadCustomerAndProductGroupAndProductCategoryList()
    }

    private fun getCustomerListAndGroupCodeListFromDbAndCategoryListFromDb() {
        targetAndActualSalesForCustomerReportViewModel.customerAndProductGroupAndProductCategoryList.observe(
            this,
            Observer {
                customerNameList.clear()
                groupNameList.clear()
                categoryNameList.clear()
                //select customer list in spinner
                customerNameList.add("All Customer")
                customerIdArr.add("-1")
                for (customer in it!!.first) {
                    customerNameList.add(customer.customer_name!!)
                    customerIdArr.add(customer.id.toString())
                }
                //add customer name in spinner
                val customerNameSpinnerAdapter =
                    ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
                customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_customer.adapter = customerNameSpinnerAdapter

                //select group list in spinner
                groupNameList.add("All Group")
                groupIdArr.add("-1")
                for (group in it.second) {
                    groupNameList.add(group.name!!)
                    groupIdArr.add(group.id.toString())
                }
                val groupNameSpinnerAdapter =
                    ArrayAdapter(context!!, android.R.layout.simple_spinner_item, groupNameList)
                groupNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_group.adapter = groupNameSpinnerAdapter

                //select category list in spinner
                categoryNameList.add("All Category")
                categoryIdArr.add("-1")
                for (category in it.third) {
                    categoryNameList.add(category.category_name!!)
                    categoryIdArr.add(category.category_id.toString())
                }
                val categoryNameSpinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, categoryNameList)
                categoryNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_category.adapter = categoryNameSpinnerAdapter

            })

    }

    private fun getTargetSaleDB(customerIdFromSpinner: Int) {
        saleTargetArrayList.clear()
        if (customerIdFromSpinner != -1) {
            targetAndActualSalesForCustomerReportViewModel.targetSaleDBListForCustomer.observe(
                this,
                Observer {
                    for (data in it!!) {
                        val id = data.id.toString()
                        val fromDate = data.from_date
                        val toDate = data.to_date
                        val customerId = data.customer_id
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
                        saleTarget.customerId = customerId
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
            targetAndActualSalesForCustomerReportViewModel.loadTargetSaleDBForCustomer(
                customerIdFromSpinner
            )

        }
    }

    private fun catchEvents() {
        //Click Customer Spinner
        spinner_customer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                updateChartData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

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

    private fun updateChartData(){

        if (customerIdArr != null && customerIdArr.size != 0) {
            customerId = customerIdArr[spinner_customer.selectedItemPosition]
            getTargetSaleDB(Integer.parseInt(customerId))
        } else {
            getTargetSaleDB(-1)
        }

        if (categoryIdArr != null && categoryIdArr.size != 0) {
            categoryId = categoryIdArr[spinner_category.selectedItemPosition]
        }

        if (groupIdArr != null && groupIdArr.size != 0) {
            groupId = groupIdArr[spinner_group.selectedItemPosition]
        }

        if (customerId == "-1" && groupId == "-1" && categoryId == "-1"){
            targetAndActualSalesForCustomerReportViewModel.allSaleTargetDataList.observe(this, Observer {
                allActualSaleValue = 0.0
                if (it!!.isEmpty()) {
                    sale_txt.text = "0.0"
                } else {
                    for (i in it!!) {
                        allActualSaleValue += i.totalAmount
                        sale_txt.text = allActualSaleValue.toString()
                    }
                }
            })
            targetAndActualSalesForCustomerReportViewModel.loadAllSaleTargetAndSaleIdList()
        }

        if (groupId != "-1") {
            targetAndActualSalesForCustomerReportViewModel.groupSaleTargetDataList.observe(this, Observer {
                allActualSaleValue = 0.0
                if (it!!.isEmpty()) {
                    sale_txt.text = "0.0"
                } else {
                    for (i in it!!) {
                        allActualSaleValue += i.totalAmount
                        sale_txt.text = allActualSaleValue.toString()
                    }
                }
            })
            targetAndActualSalesForCustomerReportViewModel.loadGroupSaleTargetAndSaleIdList(groupId)
        }

        if (categoryId != "-1") {
            targetAndActualSalesForCustomerReportViewModel.categorySaleTargetDataList.observe(this, Observer {
                if (it!!.isEmpty()) {
                    sale_txt.text = "0.0"
                } else {
                    for (i in it!!) {
                        allActualSaleValue += i.totalAmount
                        sale_txt.text = allActualSaleValue.toString()
                    }
                }
            })
            targetAndActualSalesForCustomerReportViewModel.loadCategorySaleTargetAndSaleIdList(categoryId)
        }

        if (customerId != "-1"){
            targetAndActualSalesForCustomerReportViewModel.customerSaleTargetDataList.observe(this, Observer {
                allActualSaleValue = 0.0
                if (it!!.isEmpty()) {
                    sale_txt.text = "0.0"
                } else {
                    for (i in it!!) {
                        allActualSaleValue += i.totalAmount
                        sale_txt.text = allActualSaleValue.toString()
                    }
                }
            })
            targetAndActualSalesForCustomerReportViewModel.loadCustomerSaleTargetAndSaleIdList(customerId)
        }

        initialize()
    }

    private fun initialize(){
        for (j in saleTargetArrayList.indices) {
            allSaleTargetValue += Integer.parseInt(saleTargetArrayList[j].targetAmount).toDouble()
        }

        sale_target_txt.text = allSaleTargetValue.toString()

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