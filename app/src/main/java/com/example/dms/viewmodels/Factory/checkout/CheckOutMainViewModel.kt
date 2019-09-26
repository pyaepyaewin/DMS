package com.example.dms.viewmodels.Factory.checkout

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.dms.data.repositories.CheckOutRepository
import com.example.dms.network.request.saleInvoice
import com.example.dms.ui.adapters.CheckOutAdapter
import kotlin.math.roundToInt

class CheckOutMainViewModel(
    private val checkOutRepo: CheckOutRepository,private val context: Context
):CheckOutBaseViewModel()  {

    var errorState = MutableLiveData<String>()
var discPercent=MutableLiveData<Float>()
    var discAmount=MutableLiveData<Int>()
    var netAmount = MutableLiveData<Int>()
    var refund=MutableLiveData<Int>()
    var tax=MutableLiveData<Int>()
    var totalAmount=0
    var successState = MutableLiveData<List<saleInvoice>>()


    fun calculateTotal(checkoutList: MutableList<saleInvoice>): Int{
        for (i in checkoutList){
            totalAmount += i.qty * ((i.price.toFloat().roundToInt() - ((i.price.toFloat().roundToInt() * i.discount) / 100)).roundToInt())
        }
        return totalAmount
    }

//    fun calculateDiscAmt(disc: Float){
//        discAmount.postValue((totalAmount * disc / 100).roundToInt())
//    }
//
//    fun calculateDiscPercent(disc: Int){
//        discPercent.postValue((disc * 100 / totalAmount).toFloat())
//    }

    fun calculateNetAmount(discAmt: String){
        if (!discAmt.isNullOrEmpty()){
            netAmount.postValue(totalAmount - discAmt.toInt())
        } else{
            netAmount.postValue(totalAmount - 0)
        }
    }

    fun calculateRefund(payAmt: Int, netAmount: Int){
        refund.postValue(payAmt - netAmount)
    }

    fun calculateTax(netAmount: Int){
        tax.postValue((netAmount * 5 / 100))
    }
//    var selectedSaleItems: MutableList<saleInvoice> = mutableListOf()
//
//    fun nullCheckingSalesItem(): MutableList<saleInvoice>{
//        var filteredList: MutableList<saleInvoice> = mutableListOf()
//        for (i in selectedSaleItems){
//            if (i.qty > 0){
//                filteredList.add(i)
//            }
//        }
//        return filteredList
//    }
}