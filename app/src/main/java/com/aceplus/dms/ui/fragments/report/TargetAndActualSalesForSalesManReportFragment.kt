package com.aceplus.dms.ui.fragments.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.shared.ui.activities.BaseFragment
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
        return inflater.inflate(R.layout.fragment_sale_comparison_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
    }
}