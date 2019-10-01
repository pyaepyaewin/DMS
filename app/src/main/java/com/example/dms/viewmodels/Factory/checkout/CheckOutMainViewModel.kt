package com.example.dms.viewmodels.Factory.checkout

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.Invoice
import com.example.dms.data.database.table.InvoiceItem
import com.example.dms.data.repositories.CheckOutRepository
import kotlin.math.roundToInt

class CheckOutMainViewModel(
    private val checkOutRepo: CheckOutRepository
) : CheckOutBaseViewModel() {

    var errorState = MutableLiveData<String>()
    var discPrice = MutableLiveData<Float>()
    var discAmount = MutableLiveData<Int>()
    var netAmount = MutableLiveData<Int>()
    var refund = MutableLiveData<Int>()
    var tax = MutableLiveData<Int>()
    var totalAmount = 0


    fun calculateTotal(checkoutList: MutableList<InvoiceItem>): Int {
        for (i in checkoutList) {
            totalAmount += i.qty * ((i.price.toFloat().roundToInt() - ((i.price.toFloat().roundToInt() * i.discount) / 100)).roundToInt())
        }
        return totalAmount
    }


    fun calculateNetAmount(discAmt: String) {
        if (!discAmt.isNullOrEmpty()) {
            netAmount.postValue(totalAmount - discAmt.toInt())
        } else {
            netAmount.postValue(totalAmount - 0)
        }
    }

    fun calculateRefund(payAmt: Int, netAmount: Int) {
        refund.postValue(payAmt - netAmount)
    }

    fun calculateTax(netAmount: Int) {
        tax.postValue((netAmount * 5 / 100))
    }

    fun saveData(
        invoiceID: String,
        cid: String,
        saleDate: String,
        netAmount: String,
        discPercent: String,
        discAmt: String,
        checkoutList: MutableList<InvoiceItem>
    ) {

        var discPercent = discPercent
        var discAmt = discAmt
        discPercent = "0"
        discAmt = "0"

        val invoice = Invoice(invoiceID, cid, saleDate, netAmount, discPercent, discAmt)

        var calculatedList: MutableList<InvoiceItem> = mutableListOf()

        for (i in checkoutList) {
            val salePrice = i.price.toFloat().roundToInt()
            val promoPrice = (salePrice - ((salePrice * i.discount) / 100)).roundToInt()
            val amount = i.qty * promoPrice
            calculatedList.add(
                InvoiceItem(0,i.invoiceId,i.productId,i.um,i.qty,salePrice.toString(),false,
                    promoPrice.toFloat())

            )
        }

        checkOutRepo.saveDataIntoDatabase(invoice, calculatedList)

    }

}