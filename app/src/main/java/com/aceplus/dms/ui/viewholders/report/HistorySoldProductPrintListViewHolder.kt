package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_product_print.view.*
import java.text.DecimalFormat

class HistorySoldProductPrintListViewHolder(private val view: View) :
    BaseViewHolder<SaleInvoiceDetailReport>(view) {
    override fun setData(data: SaleInvoiceDetailReport) {
        view.apply {
            name.text = data.productName
            qty.text = data.saleQuantity.toString()

            var sPrice = data.sellingPrice
            if (data.promotionPrice != 0.0)
                sPrice = data.promotionPrice
            price.text = Utils.formatAmount(sPrice)

            if (data.discountAmount.toDouble() > 0.0 || data.discountPercent > 0.0) {

                itemView.rl_discountItem.visibility = View.VISIBLE
                var discount = ""
                if (data.discountPercent > 0) discount = "----- ${data.discountPercent}% -----"
                if (data.discountAmount.toDouble() > 0) discount =
                    "----- ${data.discountAmount} -----"
                if (data.discountAmount.toDouble() > 0 && data.discountPercent > 0) {
                    val df = DecimalFormat(".##")
                    discount = "----- ${df.format(data.discountPercent)} -----"
                }

                itemView.tv_discountTxt.text = discount
                var disAmt = data.itemDiscountAmount * data.saleQuantity
                itemView.tv_dis_amt.text = disAmt.toString()

            } else {
                itemView.rl_discountItem.visibility = View.GONE
            }

            amt.text = Utils.formatAmount(data.totalAmount)
        }
    }


}