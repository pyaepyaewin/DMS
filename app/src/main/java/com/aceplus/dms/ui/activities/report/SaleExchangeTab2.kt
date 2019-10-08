package com.aceplus.dms.ui.activities.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.aceplus.dms.R
import com.aceplus.shared.ui.activities.BaseFragment

class SaleExchangeTab2:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_sale_invoice_report, container, false)

        val customerSpinner = view.findViewById(R.id.customer_spinner_fragment_invoice_report) as Spinner
        val fromDateEditTxt = view.findViewById(R.id.edit_text_sale_report_from_date) as EditText
        val toDateEditTxt = view.findViewById(R.id.edit_text_sale_report_to_date) as EditText
        val searchBtn = view.findViewById(R.id.btn_sale_report_search) as Button
        val clearBtn = view.findViewById(R.id.btn_sale_report_clear) as Button
        val fromDateTxtView = view.findViewById(R.id.txt_view_from_date) as TextView
        val toDateTxtView = view.findViewById(R.id.txt_view_to_date) as TextView
        val tbNet = view.findViewById(R.id.table_row_advance_amt) as TableRow
        val tbTotalAmt = view.findViewById(R.id.tb_1) as TableRow
        val tbDiscount = view.findViewById(R.id.tb_2) as TableRow
        val tbNetAmt = view.findViewById(R.id.tb_3) as TableRow

        tbNet.setVisibility(View.GONE)
        tbDiscount.setVisibility(View.GONE)
        tbTotalAmt.setVisibility(View.GONE)
        tbNetAmt.setVisibility(View.GONE)


        customerSpinner.setVisibility(View.GONE)
        fromDateEditTxt.setVisibility(View.GONE)
        toDateEditTxt.setVisibility(View.GONE)
        fromDateTxtView.setVisibility(View.GONE)
        toDateTxtView.setVisibility(View.GONE)
        searchBtn.setVisibility(View.GONE)
        clearBtn.setVisibility(View.GONE)

        return view
    }
}