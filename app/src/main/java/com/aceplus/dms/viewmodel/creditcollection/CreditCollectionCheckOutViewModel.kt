package com.aceplus.dms.viewmodel.creditcollection

import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.util.Log
import com.aceplus.data.utils.Constant
import com.aceplus.dms.di.provideDownloadApi
import com.aceplus.domain.entity.Location
import com.aceplus.domain.entity.cash.CashReceive
import com.aceplus.domain.entity.cash.CashReceiveItem
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.aceplussolutions.rms.constants.AppUtils
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class CreditCollectionCheckOutViewModel(
    private val creditCollectionCheckOutRepo: CreditCollectionRepo,
    private val schedulerProvider: SchedulerProvider,
    private val shf: SharedPreferences
) : BaseViewModel() {
    private var invoiceData : Credit? =null

    var creditList = listOf<Credit>()
    var creditCollectionCheckOutSuccessState = MutableLiveData<List<Credit>>()
    var creditCollectionCheckOutErrorState = MutableLiveData<String>()
    fun loadCreditCollectionCheckOut(customerId: String) {
        launch {
            creditCollectionCheckOutRepo.getCreditCollectionCheckOutList(customerId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //                    creditCollectionCheckOutSuccessState.postValue(it)
                    creditCollectionCheckOutSuccessState.value = it
                    creditList = it
//                    creditCollectionCheckOutSuccessState.value = null

                }, {
                    creditCollectionCheckOutErrorState.value = it.localizedMessage
                })
        }
    }


    fun getLocationID():String {

          return  creditCollectionCheckOutRepo.getLocation()

    }

    var cashReceiveCountSuccessState = MutableLiveData<Int>()
    var cashReceiveCountErrorState = MutableLiveData<String>()
    fun getCashReceiveCount() {
        launch {
            creditCollectionCheckOutRepo.getCashReceiveCount()
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    cashReceiveCountSuccessState.postValue(it)
                }, {
                    cashReceiveCountErrorState.value = it.localizedMessage
                })
        }
    }

    fun insertCashReceiveData(creditDataList: List<Credit>, count: Int) {
        var cashList: MutableList<CashReceive> = mutableListOf()
        var cashItemList: MutableList<CashReceiveItem> = mutableListOf()
        var newCount=count
       // creditCollectionCheckOutRepo.getCashReceiveCount()
        for (credit in creditDataList) {
            val cashReceive = CashReceive()
            val cashReceiveItem = CashReceiveItem()
            newCount += 1
            cashReceive.id = newCount
            cashReceive.receive_no = credit.invoice_no?.replace("W", "CR")
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
            val saleManId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, shf)
            var locationId =getLocationID()
            cashReceive.location_id = locationId.toString()
            cashReceive.status = ""
            cashReceive.cash_receive_type = ""
            cashReceive.sale_id = credit.id.toString()
            cashReceive.sale_man_id = saleManId
            cashReceiveItem.receive_no = credit.invoice_no?.replace("W", "CR")
            cashReceiveItem.sale_id = credit.id.toString()

            cashList.add(cashReceive)
            cashItemList.add(cashReceiveItem)
            updatePayAmount(credit.pay_amount, credit.invoice_no!!)

        }
        creditCollectionCheckOutRepo.saveCashReceiveDataIntoDatabase(cashList, cashItemList)
    }


    fun updatePayAmount(payAmt: Double, invoiceNo: String) {

        creditCollectionCheckOutRepo.updatePayAmount(payAmt, invoiceNo)

    }

    fun getTownShipName(customerId: Int): String {
        return creditCollectionCheckOutRepo.getTownShipName(customerId)
    }

    fun calculatePayAmount(payAmt: String): List<Credit> {

        var payAmount: Double = payAmt.replace(",", "").toDouble()
        val remainList = ArrayList<Credit>()

        val tempCreditList = ArrayList<Credit>()
        tempCreditList.addAll(creditList)

        for (i in creditList) {

            val creditAmount = i.amount - i.pay_amount
            if (creditAmount != 0.0) {

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

        }
        return remainList
    }


    fun calculatePayAmountForSelectedInvoice(payAmt: String, position: Int): List<Credit> {

        var payAmount: Double = payAmt.replace(",", "").toDouble()
//        val remainList = ArrayList<Credit>()
        var remainList = mutableListOf<Credit>()
        val tempCreditList = ArrayList<Credit>()
        tempCreditList.addAll(creditList)


        val creditAmount = creditList[position].amount - creditList[position].pay_amount

        if (payAmount != 0.0 && payAmount < creditAmount) {
            creditList[position].pay_amount = payAmount
            payAmount = 0.0
            remainList.add(creditList[position])

        }

        else if (payAmount != 0.0 && payAmount > creditAmount) {
            var refund = payAmount - creditAmount
            creditList[position].pay_amount = creditAmount
            tempCreditList.remove(creditList[position])
            remainList.add(creditList[position])
            for (i in tempCreditList) {
                var newAmt=i.amount-i.pay_amount
                if (newAmt!=0.0)
                {
                    if (refund != 0.0 && refund < newAmt) {
                        i.pay_amount = refund
                        refund = 0.0
                        remainList.add(i)


                    } else if (refund != 0.0 && refund > newAmt) {
                        refund -= newAmt
                        i.pay_amount = newAmt
                        remainList.add(i)


                    } else if (refund != 0.0 && refund == newAmt) {
                        refund -= newAmt
                        i.pay_amount = newAmt
                        remainList.add(i)

                    }
                }


            }

            return remainList

        } else if (payAmount != 0.0 && payAmount == creditAmount) {
            payAmount -= creditAmount
            creditList[position].pay_amount = creditAmount
            tempCreditList.remove(creditList[position])
            remainList.add(creditList[position])
        }


        return remainList
    }

}


