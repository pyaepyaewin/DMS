package com.aceplus.dms.ui.activities.report

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.aceplus.dms.R
import com.aceplus.shared.ui.activities.BaseFragment

class SalesOrderHistoryReportFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_sale_invoice_report, container, false)

        val saleInvoiceReportsListView = view.findViewById(R.id.saleInvoceReports) as RecyclerView
        val customerSpinner =
            view.findViewById(R.id.customer_spinner_fragment_invoice_report) as Spinner
        val fromDateEditTxt = view.findViewById(R.id.edit_text_sale_report_from_date) as EditText
        val toDateEditTxt = view.findViewById(R.id.edit_text_sale_report_to_date) as EditText
        val searchBtn = view.findViewById(R.id.btn_sale_report_search) as Button
        val clearBtn = view.findViewById(R.id.btn_sale_report_clear) as Button
        val sale_order_history_report_total =
            view.findViewById(R.id.sale_report_total_amt) as TextView
        val sale_order_history_report_discount =
            view.findViewById(R.id.sale_report_discount) as TextView
        val sale_order_history_report_net = view.findViewById(R.id.sale_report_net_amt) as TextView
        val sale_report_discount_advance_amt_label =
            view.findViewById(R.id.sale_order_report_advanced_amount_label) as TextView
        val table_row_advance_amt = view.findViewById(R.id.table_row_advance_amt) as TableRow
        val sale_order_report_advance_amt =
            view.findViewById(R.id.sale_order_report_advanced_amt) as TextView

        table_row_advance_amt.visibility = View.VISIBLE
        sale_report_discount_advance_amt_label.setVisibility(View.VISIBLE)
        // Inflate the layout for this fragment
        return view
    }

}