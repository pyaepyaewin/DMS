package com.example.dms.viewmodels.Factory.sale

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.dms.R
import com.example.dms.data.database.table.InvoiceItem
import com.example.dms.data.repositories.SaleRepository
import com.example.dms.network.response.Product
import com.example.dms.ui.adapters.SaleInvoiceAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.math.roundToInt

class SaleMainViewModel (
    private val saleRepo: SaleRepository,private val context: Context
) : SaleBaseViewModel() {
    var errorState = MutableLiveData<String>()
    var successState = MutableLiveData<List<Product>>()
    var netAmount = MutableLiveData<Int>()

    var selectedInvoiceItems: MutableList<InvoiceItem> = mutableListOf()

    val saleItemAdpter: SaleInvoiceAdapter by lazy {
        SaleInvoiceAdapter(
            this::onClickQty,
            this::onClickFoc,
            this::onClickDisc,
            this::calculateNetAmount
        )
    }

    fun loadSaleList(saleRequestParm: String) {
        saleRepo.saleData
            .observeForever {
                launch {
                    it
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext {
                            saleRepo.saveDataIntoDatabase(it.data[0].Product)
                        }
                        .subscribe({ response ->
                            saleRepo.saleData = MutableLiveData()
                            successState.postValue(response.data[0].Product)
                        }, { error ->
                            errorState.postValue(error.localizedMessage)
                        })
                }
            }
        saleRepo.loadSaleList(saleRequestParm)

    }

    fun addItem(product: Product) {
        var found = false
        for (i in selectedInvoiceItems.indices) {
            if (product.Id == selectedInvoiceItems[i].id) {
                selectedInvoiceItems[i].qty += 1
                saleItemAdpter.updateRow(this.selectedInvoiceItems, i)
                found = true
                break
            }
        }
        if (!found) {
            selectedInvoiceItems.add(
                InvoiceItem("0",
                   product.Id,
product.Product_id,product.um_id,product.total_qty,product.selling_price,false,0.0f))
            saleItemAdpter.addRow(this.selectedInvoiceItems)
        }
    }



    private fun onClickQty(position: Int, currentQty: Int) {

            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(R.layout.quantity)

            val edtQty = dialog.findViewById<EditText>(R.id.edtText)
            edtQty.setText(currentQty.toString())

            dialog.findViewById<ImageButton>(R.id.imgSub).setOnClickListener {
                edtQty.setText(calculateQty(edtQty.text.toString().toInt(), false).toString())
            }

            dialog.findViewById<ImageButton>(R.id.imgAdd).setOnClickListener {
                edtQty.setText(calculateQty(edtQty.text.toString().toInt(), true).toString())
            }

            dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener { dialog.dismiss() }

            dialog.findViewById<Button>(R.id.btnOK).setOnClickListener {
                selectedInvoiceItems[position].qty = edtQty.text.toString().toInt()
                saleItemAdpter.updateRow(selectedInvoiceItems, position)
                dialog.dismiss()
            }

            dialog.show()

        }

    private fun onClickFoc(position: Int, currentFoc: Boolean) {
            this.selectedInvoiceItems[position].foc = currentFoc
        }

    private fun onClickDisc(position: Int, currentDisc: Float) {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(R.layout.quantity)

            val edtDisc = dialog.findViewById<EditText>(R.id.edtText)
            edtDisc.setText(currentDisc.toString())

            dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener { dialog.dismiss() }
            dialog.findViewById<Button>(R.id.btnOK).setOnClickListener {
                var updateDisc = edtDisc.text.toString().toFloat()
                if (updateDisc > 100) {
                    selectedInvoiceItems[position].discount = 100.0f
                    Toast.makeText(context, "You can only give up to 100%", Toast.LENGTH_LONG)
                        .show()
                } else {
                    selectedInvoiceItems[position].discount = edtDisc.text.toString().toFloat()
                }
                saleItemAdpter.updateRow(selectedInvoiceItems, position)
                dialog.dismiss()
            }

            dialog.show()
        }

    private fun calculateQty(currentQty: Int, increase: Boolean): Int {

            var newQty: Int = if (increase) {
                currentQty + 1
            } else {
                currentQty - 1
            }

            return if (newQty >= 0) {
                newQty
            } else {
                0
            }
        }

    private fun calculateNetAmount() {
            var totalAmount = 0
            for (i in selectedInvoiceItems) {
                totalAmount += i.qty * ((i.price.toFloat().roundToInt() - ((i.price.toFloat().roundToInt() * i.discount) / 100)).roundToInt())
            }
            netAmount.postValue(totalAmount)
        }

    fun nullCheckingSalesItem(): MutableList<InvoiceItem>{
        var filteredList: MutableList<InvoiceItem> = mutableListOf()
        for (i in selectedInvoiceItems){
            if (i.qty > 0){
                filteredList.add(i)
            }
        }
        return filteredList
    }

    }
