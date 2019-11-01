package com.aceplus.dms.viewmodel.creditcollection

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.Location
import com.aceplus.domain.entity.cash.CashReceive
import com.aceplus.domain.entity.cash.CashReceiveItem
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.model.credit.CreditInvoice
import com.aceplus.domain.model.customer.CustomerCredit
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionCheckOutRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class CreditCollectionCheckOutViewModel(
    private val creditCollectionCheckOutRepo: CreditCollectionCheckOutRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var creditList = listOf<Credit>()
    var creditCollectionCheckOutSuccessState = MutableLiveData<List<Credit>>()
    var creditCollectionCheckOutErrorState = MutableLiveData<String>()
    fun loadCreditCollectionCheckOut(customerId: String) {
        launch {
            creditCollectionCheckOutRepo.getCreditCollectionCheckOutList(customerId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    creditCollectionCheckOutSuccessState.postValue(it)
                    creditList = it

                }, {
                    creditCollectionCheckOutErrorState.value = it.localizedMessage
                })
        }
    }

    var locationSuccessState = MutableLiveData<List<Location>>()
    var locationErrorState = MutableLiveData<String>()
    fun getLocationID() {
        launch {
            creditCollectionCheckOutRepo.getLocation()
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    locationSuccessState.postValue(it)
                }, {
                    locationErrorState.value = it.localizedMessage
                })
        }
    }


    fun insertCashReceiveData(creditDataList: List<Credit>) {
        var cashList: MutableList<CashReceive> = mutableListOf()
        var cashItemList: MutableList<CashReceiveItem> = mutableListOf()

        for (credit in creditDataList) {
            val cashReceive = CashReceive()
            val cashReceiveItem = CashReceiveItem()

            cashReceive.id = credit.id
            cashReceive.receive_no = credit.invoice_no
            cashReceive.receive_date = credit.invoice_date
            cashReceive.customer_id = credit.customer_id.toString()
            cashReceive.amount = credit.pay_amount.toString()
            var creditAmt = credit.amount - credit.pay_amount!!
            cashReceive.currency_id = ""
            if (creditAmt == 0.0 || credit.amount == credit.pay_amount) {
                cashReceive.payment_type = "CA"
            } else {
                cashReceive.payment_type = "CR"
            }
            cashReceive.location_id = ""
            cashReceive.status = ""
            cashReceive.cash_receive_type = ""
            cashReceive.sale_id = credit.id.toString()
            cashReceive.sale_man_id = ""
            cashReceiveItem.receive_no = credit.invoice_no?.replace("W", "CR")
            cashReceiveItem.sale_id = credit.id.toString()

            cashList.add(cashReceive)
            cashItemList.add(cashReceiveItem)
            updatePayAmount(credit.pay_amount,credit.invoice_no!!)

        }
        creditCollectionCheckOutRepo.saveCashReceiveDataIntoDatabase(cashList,cashItemList)
    }




    fun updatePayAmount(payAmt: Double, invoiceNo: String) {

            creditCollectionCheckOutRepo.updatePayAmount(payAmt, invoiceNo)

        }


    fun calculatePayAmount(payAmt: String): List<Credit> {

        var payAmount: Double = payAmt.replace(",", "").toDouble()
        val remainList = ArrayList<Credit>()
        val tempCreditList = ArrayList<Credit>()
        tempCreditList.addAll(creditList)

        for (i in creditList) {
            val creditAmount = i.amount - i.pay_amount

            if (payAmount != 0.0 && payAmount < creditAmount) {
                i.pay_amount = payAmount
                payAmount = 0.0
                remainList.add(i)

            } else if (payAmount != 0.0 && payAmount > creditAmount) {
                payAmount -= creditAmount
                i.pay_amount = creditAmount
                tempCreditList.remove(i)
                remainList.add(i)

            } else if (payAmount != 0.0 && payAmount == creditAmount) {
                payAmount -= creditAmount
                i.pay_amount = creditAmount
                tempCreditList.remove(i)
                remainList.add(i)
            }

        }

        Log.i("REMAIN -> ", payAmount.toString() + "")
        Log.i("Remain item -> ", remainList.size.toString() + "")
        Log.i("item remove -> ", tempCreditList.size.toString() + "")

        return remainList
    }

}
