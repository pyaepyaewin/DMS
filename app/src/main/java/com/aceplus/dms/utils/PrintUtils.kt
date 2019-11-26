package com.aceplus.dms.utils

import android.app.Activity
import android.graphics.BitmapFactory
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.credit.CreditInvoice
import com.aceplus.domain.vo.SaleExchangeProductInfo
import com.aceplus.domain.vo.SoldProductInfo
import gems.com.command.sdk.PrintPicture
import java.io.UnsupportedEncodingException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

object PrintUtils{

    const val forPackageSale = "for-package-sale"
    const val forPreOrderSale = "for-pre-order-sale"
    const val FOR_DELIVERY = "for-delivery"
    const val FOR_OTHERS = "for-others"
    const val FOR_SALE = "for-sales"
    const val FOR_SALE_RETURN = "for-sale-return"
    const val FOR_SALE_RETURN_EXCHANGE = "for-sale_return_exchange"
    const val FOR_SALE_EXCHANGE = "for_sale_exchange"
    const val FOR_DISPLAY_ASSESSMENT = "for_display_assessment"
    const val FOR_OUTLET_STOCK_AVAILABILITY = "for_outlet_stock_availibility"
    const val FOR_SIZE_IN_STORE_SHARE = "for_size_in_store_share"
    const val FOR_COMPETITORACTIVITY = "for_competitoractivity"
    const val PRINT_FOR_NORMAL_SALE = "print-for-normal-sale"
    const val PRINT_FOR_C = "print-for-c"
    const val PRINT_FOR_PRE_ORDER = "print-for-preorder"
    const val FOR_VAN_ISSUE = "for-van-issue"

    private var formatter: Formatter? = null
    private var totalDiscountAmt = 0.0


    fun printWithHSPOS(
        activity: Activity,
        customerName: String?,
        cus_address: String?,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        townshipName: String,
        invoice: Invoice,
        soldProductList: ArrayList<SoldProductInfo>,
        presentList: ArrayList<Promotion>,
        printFor: String,
        mode: String,
        mBTService: BluetoothService,
        companyInfo: CompanyInformation,
        printMode: String?
    ) {
        try {
            val printDataByteArray = convertFromListByteArrayToByteArray(
                getPrintDataByteArrayList(
                    customerName,
                    cus_address,
                    invoiceNumber,
                    salePersonName,
                    routeName,
                    townshipName,
                    invoice,
                    soldProductList,
                    presentList,
                    printFor,
                    mode,
                    ByteArray(7),
                    companyInfo,
                    printMode
                )
            )
            sendDataByte2BT(activity, mBTService, printDataByteArray)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    fun printCreditWithHSPOS(
        activity: Activity,
        customerName: String?,
        cus_address: String?,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        townshipName: String,
        creditList: CreditInvoice,
        mBTService: BluetoothService,
        companyInfo: CompanyInformation
    ){
        try {
            val printDataByteArray = convertFromListByteArrayToByteArray(
                getPrintDataByteArrayListForCredit(
                    customerName,
                    cus_address,
                    invoiceNumber,
                    salePersonName,
                    townshipName,
                    creditList,
                    companyInfo,
                    routeName
                )
            )
            sendDataByte2BT(activity, mBTService, printDataByteArray)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

    }

    fun printDeliverWithHSPOS(
        activity: Activity,
        customerName: String?,
        cus_address: String?,
        orderInvoiceNo: String,
        orderSalePersonName: String?,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        townshipName: String,
        invoice: Invoice,
        soldProductList: ArrayList<SoldProductInfo>,
        presentList: ArrayList<Promotion>,
        prepaidAmount: Double,
        printFor: String,
        mode: String,
        mBTService: BluetoothService,
        companyInfo: CompanyInformation
    ){
        try {
            val printDataByteArray = convertFromListByteArrayToByteArray(
                getPrintDataByteArrayListDeliver(
                    activity,
                    customerName,
                    cus_address,
                    orderInvoiceNo,
                    orderSalePersonName,
                    invoiceNumber,
                    salePersonName,
                    routeName,
                    townshipName,
                    invoice,
                    soldProductList,
                    presentList,
                    prepaidAmount,
                    printFor,
                    mode,
                    companyInfo
                )
            )
            sendDataByte2BT(activity, mBTService, printDataByteArray)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getPrintDataByteArrayList(
        customerName: String?,
        cus_address: String?,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        townshipName: String,
        invoice: Invoice,
        soldProductList: ArrayList<SoldProductInfo>,
        presentList: ArrayList<Promotion>,
        printFor: String,
        mode: String,
        imgByte: ByteArray,
        companyInfo: CompanyInformation,
        printMode: String?
    ): ArrayList<ByteArray>{

        val printDataByteArrayList: ArrayList<ByteArray> = ArrayList()
        printDataByteArrayList.add(imgByte)
        printDataByteArrayList.add("\n".toByteArray())

        val focProductList: ArrayList<SoldProductInfo> = ArrayList()

        var totalAmount = 0.0
        var totalNetAmount = 0.0
        totalDiscountAmt = 0.0

        val address = companyInfo.address
        val txtForFooter = companyInfo.pos_voucher_footer1
        val companyTaxRegNo = companyInfo.company_tax_reg_no
        val phNo = companyInfo.phone_number

        printDataByteArrayList.add((address + "\n").toByteArray())
        printDataByteArrayList.add("Ph No         :   $phNo\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Tax Reg No    :   $companyTaxRegNo\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Customer      :   $customerName\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Township      :   $townshipName\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Address       :   $cus_address\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Invoice No    :   $invoiceNumber\n".toByteArray())
        printDataByteArrayList.add("Sale Person   :   $salePersonName\n".toByteArray())
        printDataByteArrayList.add("RouteNo       :   $routeName\n".toByteArray()) // ToDo
        printDataByteArrayList.add(("Sale Date     :   " + SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US).format(Date()) + "\n").toByteArray())

        if (invoice.due_date != null && printMode.equals("sale", true))
            printDataByteArrayList.add("Delivery Date :   ${invoice.due_date}\n".toByteArray())

        printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

        var promoFlg = false

        for (soldProduct in soldProductList){
            if (soldProduct.promotionPrice == 0.0){
                promoFlg = false
                break
            } else{
                promoFlg = true
                break
            }
        }

        if (promoFlg){
            formatter = Formatter(StringBuilder(), Locale.US)
            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n",
                    "Item",
                    "Qty",
                    "Price",
                    "Amount"
                ).toString().toByteArray()
            )
            formatter!!.close()

            printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

            for (soldProduct in soldProductList){
                val quantity = soldProduct.quantity

                var pricePerUnit: Double = if (soldProduct.promotionPrice == 0.0)
                    soldProduct.product.selling_price?.toDouble() ?: 0.0
                else
                    soldProduct.promotionPrice

                val amount = soldProduct.totalAmt
                val pricePerUnitWithDiscount = soldProduct.discountAmount
                val netAmount = amount - pricePerUnitWithDiscount
                val itemFocPercent = soldProduct.focPercent
                val itemFocAmount = soldProduct.focAmount
                val itemFocDiscountAmt = soldProduct.itemDiscountAmount

                totalAmount += amount
                totalNetAmount += netAmount

                val nameFragments = soldProduct.product.product_name!!.split(" ")
                val nameList = setupPrintLayoutWithPromo(nameFragments as ArrayList<String>)

                if (amount != 0.0) {
                    if (printFor == PRINT_FOR_PRE_ORDER) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t  %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameList[0],
                                quantity,
                                Utils.decimalFormatterWithoutComma.format(pricePerUnit),
                                Utils.decimalFormatterWithComma.format(amount)
                            ).toString().toByteArray()
                        )

                        formatter!!.close()
                    }

                    if (printFor != PRINT_FOR_PRE_ORDER) {

                        formatter = Formatter(StringBuilder(), Locale.US)
                        var nameTxt = ""
                        if (nameList.size > 0)
                            nameTxt = nameList[0]
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameTxt,
                                quantity,
                                Utils.decimalFormatterWithoutComma.format(pricePerUnit),
                                Utils.decimalFormatterWithComma.format(netAmount)
                            ).toString().toByteArray()
                        )


                    }

                    if (nameList.size > 0) {
                        nameList.removeAt(0)
                        for (cutName in nameList) {
                            formatter = Formatter(StringBuilder(), Locale.US)
                            printDataByteArrayList.add(
                                formatter!!.format(
                                    "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n",
                                    cutName,
                                    "",
                                    "",
                                    ""
                                ).toString().toByteArray()
                            )

                            formatter!!.close()
                        }
                    }

                    if (itemFocPercent > 0 || itemFocAmount > 0) {
                        val disItemAmt = itemFocDiscountAmt * soldProduct.quantity
                        var text =
                            "Discount---" + Utils.decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
                        if (itemFocAmount > 0) {
                            text =
                                "Discount---" + Utils.decimalFormatterWithoutComma.format(itemFocAmount) + "---"
                        }
                        if (itemFocPercent > 0 && itemFocPercent > 0) {
                            text =
                                "Discount---" + Utils.decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
                        }
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$2s \t \t %3$1s \t \t \t %4$9s\n",
                                text,
                                "",
                                " (" + Utils.decimalFormatterWithoutComma.format(disItemAmt) + ") ",
                                ""
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                        totalDiscountAmt += disItemAmt
                    }

                    printDataByteArrayList.add("\n".toByteArray())
                } else {
                    focProductList.add(soldProduct)
                }
            }
        } else {
            formatter = Formatter(StringBuilder(), Locale.US)

            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n",
                    "Item",
                    "Qty",
                    "Price",
                    "Amount"
                ).toString().toByteArray()
            )
            formatter!!.close()
            printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

            focProductList.clear()
            for (soldProduct in soldProductList) {

                val quantity = soldProduct.quantity

                var pricePerUnit = if (soldProduct.promotionPrice === 0.0) {
                    soldProduct.product.selling_price!!.toDouble()
                } else {
                    soldProduct.promotionPrice
                }

                val amount = soldProduct.totalAmount
                val pricePerUnitWithDiscount: Double = soldProduct.discountAmount
                val netAmount = soldProduct.totalAmount - pricePerUnitWithDiscount

                val itemFocPercent = soldProduct.focPercent
                val itemFocAmount = soldProduct.focAmount
                val itemFocDiscountAmt = soldProduct.itemDiscountAmount

                totalAmount += amount
                totalNetAmount += netAmount

                val nameFragments = soldProduct.product.product_name!!.split(" ")
                val nameList = setupPrintLayoutNoPromo(nameFragments as ArrayList<String>)

                if (amount != 0.0) {
                    if (printFor == PRINT_FOR_PRE_ORDER) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameList[0],
                                quantity,
                                Utils.decimalFormatterWithoutComma.format(pricePerUnit),
                                Utils.decimalFormatterWithComma.format(amount)
                            ).toString().toByteArray()
                        )

                        formatter!!.close()
                    }

                    if (printFor != PRINT_FOR_PRE_ORDER) {

                        formatter = Formatter(StringBuilder(), Locale.US)
                        var nameTxt = ""
                        if (nameList.size > 0)
                            nameTxt = nameList[0]
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameTxt,
                                quantity,
                                Utils.decimalFormatterWithoutComma.format(pricePerUnit),
                                Utils.decimalFormatterWithComma.format(netAmount)
                            ).toString().toByteArray()
                        )

                    }

                    if (nameList.size > 0) {
                        nameList.removeAt(0)
                        for (cutName in nameList) {
                            formatter = Formatter(StringBuilder(), Locale.US)
                            printDataByteArrayList.add(
                                formatter!!.format(
                                    "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n",
                                    cutName,
                                    "",
                                    "",
                                    ""
                                ).toString().toByteArray()
                            )

                            formatter!!.close()
                        }
                    }

                    if (itemFocPercent > 0 || itemFocAmount > 0) {
                        val disItemAmt = itemFocDiscountAmt * soldProduct.quantity
                        var text =
                            "Discount---" + Utils.decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
                        if (itemFocAmount > 0) {
                            text =
                                "Discount---" + Utils.decimalFormatterWithoutComma.format(itemFocAmount) + "---"
                        }
                        if (itemFocPercent > 0 && itemFocPercent > 0) {
                            text =
                                "Discount---" + Utils.decimalFormatterWithoutComma.format(itemFocPercent) + "%---"
                        }
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$2s \t \t %3$1s \t \t \t %4$9s\n",
                                text,
                                "",
                                " (" + Utils.decimalFormatterWithoutComma.format(disItemAmt) + ") ",
                                ""
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                        totalDiscountAmt += disItemAmt
                    }

                    printDataByteArrayList.add("\n".toByteArray())
                } else {
                    focProductList.add(soldProduct)
                }
            }

        }

        printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

        formatter = Formatter(StringBuilder(), Locale.US)

        val taxType = companyInfo.tax_type

        var taxText: String
        if (taxType.equals("E", ignoreCase = true)) {
            taxText = "(Tax " + String.format("%.2f", invoice.tax_amount) + " Excluded)"
            totalNetAmount = invoice.total_amount!!.toDouble() - invoice.total_discount_amount + invoice.tax_amount
        } else {
            taxText = "(Tax " + String.format("%.2f", invoice.tax_amount) + " Included)"
            totalNetAmount = invoice.total_amount!!.toDouble() - invoice.total_discount_amount
        }

        when {
            printFor == PRINT_FOR_PRE_ORDER -> printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-13s%2$21s\n%3$-15s%4$21s\n%5$-13s%6$21s\n\n\n",
                    "Total Amount      :        ",
                    Utils.decimalFormatterWithComma.format(totalAmount),
                    taxText,
                    ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/,
                    "Total Discount    :        ",
                    Utils.decimalFormatterWithComma.format(totalDiscountAmt),
                    "Net Amount        :        ",
                    Utils.decimalFormatterWithComma.format(invoice.pay_amount),
                    "Pay Amount        :        ",
                    Utils.decimalFormatterWithComma.format(invoice.pay_amount)
                ).toString().toByteArray()
            )
            mode == FOR_DELIVERY -> printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-13s%2$21s\n%3$-13s%4$21s\n%5$-13s%6$21s\n%7$-13s%8$21s\n%9$-13s%10$21s\n\n\n",
                    "Total Amount      :        ",
                    Utils.decimalFormatterWithComma.format(totalAmount),
                    taxText,
                    ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/,
                    "Total Discount    :        ",
                    Utils.decimalFormatterWithoutComma.format(totalDiscountAmt),
                    "Net Amount        :        ",
                    Utils.decimalFormatterWithComma.format(totalNetAmount),
                    "Pay Amount        :        ",
                    Utils.decimalFormatterWithComma.format(invoice.pay_amount)
                ).toString().toByteArray()
            )
            else -> {
                var creditBalance: Double? = 0.0
                if (totalNetAmount > invoice.pay_amount!!.toDouble()) {
                    creditBalance = totalNetAmount - invoice.pay_amount!!.toDouble()
                }
                var refundAmt = 0.0
                if (invoice.refund_amount != null)
                    refundAmt = invoice.refund_amount!!.toDouble()

                printDataByteArrayList.add(
                    formatter!!.format(
                        "%1$-13s%2$21s\n%3$-13s%4$21s\n%5$-13s%6$21s\n%7$-13s%8$21s\n%9$-13s%10$21s\n%11$-13s%12$21s\n%13$-13s%14$21s\n%15$-13s%16$21s\n",
                        "Total Amount      :        ",
                        Utils.decimalFormatterWithComma.format(totalAmount),
                        taxText,
                        ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + new DecimalFormat("#0.00").format(taxPercent) + "%)"*/,
                        "Total Discount    :        ",
                        Utils.decimalFormatterWithComma.format(totalDiscountAmt),
                        "Vol: Discount     :        ",
                        Utils.decimalFormatterWithComma.format(invoice.total_discount_amount),
                        "Net Amount        :        ",
                        Utils.decimalFormatterWithComma.format(totalNetAmount),
                        "Pay Amount        :        ",
                        Utils.decimalFormatterWithComma.format(invoice.pay_amount),
                        "Credit Balance    :        ",
                        Utils.decimalFormatterWithComma.format(abs(creditBalance!!)),
                        "Refund            :        ",
                        Utils.decimalFormatterWithComma.format(refundAmt)
                    ).toString().toByteArray()
                )
            }
        }

        printDataByteArrayList.add("\n".toByteArray())

        if (presentList != null && presentList.size > 0) {

            formatter = Formatter(StringBuilder(), Locale.US)

            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n",
                    "Foc",
                    "Qty",
                    "Price",
                    "Amount"
                ).toString().toByteArray()
            )

            formatter!!.close()
            printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

            /* PRESENT LIST */
            for (invoicePresent in presentList) {

                val quantity = invoicePresent.promotion_quantity
                val presentPrice = 0.0

                // Shorthand the name.
                val productName = "Temporary" //getProductNameAndPrice(invoicePresent) Check point !!! ToDo
                val nameFragments = productName!!.split(" ")
                val nameList = setupPrintLayoutNoPromo(nameFragments as ArrayList<String>)

                if (printFor == PRINT_FOR_PRE_ORDER) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                            nameList[0],
                            quantity,
                            Utils.decimalFormatterWithComma.format(presentPrice),
                            "0.0"
                        ).toString().toByteArray()
                    )
                    formatter!!.close()
                }

                if (printFor != PRINT_FOR_PRE_ORDER) {

                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                            nameList[0],
                            quantity,
                            Utils.decimalFormatterWithComma.format(presentPrice),
                            "0.0"
                        ).toString().toByteArray()
                    )
                    formatter!!.close()
                }

                nameList.removeAt(0)
                for (cutName in nameList) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n", cutName, "", "", ""
                        ).toString().toByteArray()
                    )

                    formatter!!.close()
                }

                printDataByteArrayList.add("\n".toByteArray())

            }

        }

        printDataByteArrayList.add("\n\n              $txtForFooter\n\n".toByteArray())
        printDataByteArrayList.add("\nSignature       :\n\n                 Thank You. \n\n".toByteArray())
        printDataByteArrayList.add("\n".toByteArray())

        return printDataByteArrayList
    }

    private fun getPrintDataByteArrayListForSaleExchange(
        imgByte: ByteArray,
        companyInfo: CompanyInformation,
        customerName: String?,
        cus_address: String?,
        townshipName: String,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        soldProductList: ArrayList<SoldProductInfo>,
        invoice: Invoice,
        returnProductList: ArrayList<SaleExchangeProductInfo>
    ){

        val printDataByteArrayList = java.util.ArrayList<ByteArray>()
        printDataByteArrayList.add(imgByte)
        printDataByteArrayList.add("\n".toByteArray())

        var totalAmount = 0.0
        var totalNetAmount = 0.0
        var totalReturnAmount = 0.0

        val address = companyInfo.address
        val companyTaxRegNo = companyInfo.company_tax_reg_no
        val phNo = companyInfo.phone_number

        printDataByteArrayList.add((address + "\n").toByteArray())
        printDataByteArrayList.add("Ph No         :   $phNo\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Tax Reg No    :   $companyTaxRegNo\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Customer      :   $customerName\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Township      :   $townshipName\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Address       :   $cus_address\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Invoice No    :   $invoiceNumber\n".toByteArray())
        printDataByteArrayList.add("Sale Person   :   $salePersonName\n".toByteArray())
        printDataByteArrayList.add("RouteNo       :   $routeName\n".toByteArray()) // ToDo
        printDataByteArrayList.add(("Sale Date     :   " + SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US).format(Date()) + "\n").toByteArray())
        printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

        formatter = Formatter(StringBuilder(), Locale.US)
        printDataByteArrayList.add(formatter!!.format("%1$-20s \t %2$4s \t %3$5s \t %4$7s\n", "Sales", "Qty", "Price", "Amount").toString().toByteArray())
        formatter!!.close()
        printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

        for (soldProduct in soldProductList){

            val quantity = soldProduct.product.sold_quantity
            var pricePerUnit: Double = soldProduct.product.selling_price?.toDouble() ?: 0.0
            val amount = pricePerUnit * quantity // To check with other function
            val netAmount = soldProduct.totalAmt - soldProduct.discountAmount

            totalAmount += amount
            totalNetAmount += netAmount

            val nameFragments = soldProduct.product.product_name!!.split("")
            val nameList = setupPrintLayoutNoPromo(nameFragments as ArrayList<String>)

            formatter = Formatter(StringBuilder(), Locale.US)
            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-20s \t %2$4s \t %3$5s \t %4$9s\n",
                    nameList[0],
                    quantity,
                    Utils.decimalFormatterWithoutComma.format(pricePerUnit),
                    Utils.decimalFormatterWithComma.format(amount)
                ).toString().toByteArray()
            )
            formatter!!.close()

            nameList.removeAt(0)
            for (cutName in nameList) {
                formatter = Formatter(StringBuilder(), Locale.US)
                printDataByteArrayList.add(formatter!!.format("%1$-20s \t %2$1s \t %3$1s \t %4$1s\n", cutName, "", "", "").toString().toByteArray())
                formatter!!.close()
            }

            printDataByteArrayList.add("\n".toByteArray())

        }

        printDataByteArrayList.add("------------------------------------------------\n".toByteArray())

        val taxType = companyInfo.tax_type
        var taxText: String

        if (taxType.equals("E", ignoreCase = true)) {
            taxText = "(Tax " + String.format("%.2f", invoice.tax_amount) + " Excluded)"
            totalNetAmount = invoice.total_amount!!.toDouble() - invoice.total_discount_amount + invoice.tax_amount
        } else {
            taxText = "(Tax " + String.format("%.2f", invoice.tax_amount) + " Included)"
            totalNetAmount = invoice.total_amount!!.toDouble() - invoice.total_discount_amount
        }

        formatter = Formatter(StringBuilder(), Locale.US)
        printDataByteArrayList.add(
            formatter!!.format(
                "%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s\n%6$-13s%7$19s\n\n",
                "Total Amount       :        ", Utils.decimalFormatterWithComma.format(totalAmount),
                "Discount           :        ", Utils.decimalFormatterWithComma.format(invoice.total_discount_amount).toString() + " (" + DecimalFormat("#0.00").format(invoice.total_discount_percent) + "%)",
                taxText,
                "Net Amount         :        ",
                Utils.decimalFormatterWithComma.format(totalNetAmount)
            ).toString().toByteArray()
        )
        formatter!!.close()

        formatter = Formatter(StringBuilder(), Locale.US)
        printDataByteArrayList.add(
            formatter!!.format(
                "%1$-20s \t %2$4s \t %3$5s \t %4$7s\n",
                "Return",
                "Qty",
                "Price",
                "Amount"
            ).toString().toByteArray()
        )
        formatter!!.close()
        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())

        for(returnProduct in returnProductList){

            val quantity = returnProduct.quantity
            val unitPrice = returnProduct.price?.toDouble() ?: 0.0
            val amount = unitPrice * quantity
            // No discount data to calculate
            totalReturnAmount += amount

            val nameFragments = returnProduct.product_name.toString().split(" ")
            // ToDo

        }

    }

    private fun getPrintDataByteArrayListForCredit(
        customerName: String?,
        cus_address: String?,
        invoiceNumber: String,
        salePersonName: String?,
        townshipName: String,
        creditList: CreditInvoice,
        companyInfo: CompanyInformation,
        routeName: String?
    ): ArrayList<ByteArray>{

        val printDataByteArrayList = ArrayList<ByteArray>()

        val address = companyInfo.address
        val companyTaxRegNo = companyInfo.company_tax_reg_no
        val txtForFooter = companyInfo.pos_voucher_footer1

        printDataByteArrayList.add((address + "\n").toByteArray())
        printDataByteArrayList.add((companyTaxRegNo + "\n").toByteArray())
        printDataByteArrayList.add("Customer           :  $customerName\n".toByteArray())
        printDataByteArrayList.add("Township           :  $townshipName\n".toByteArray())
        printDataByteArrayList.add("Address            :  $cus_address\n".toByteArray())
        printDataByteArrayList.add("Offical Receive No :  $invoiceNumber\n".toByteArray())
        printDataByteArrayList.add("Collect Person     :  $salePersonName\n".toByteArray())
        printDataByteArrayList.add("RouteNo            :  $routeName\n".toByteArray()) // ToDo
        printDataByteArrayList.add(("Total Amount       :  " + Utils.decimalFormatterWithComma.format(creditList.amt) + "\n").toByteArray())
        printDataByteArrayList.add("Discount           :  0.0 (0%)\n".toByteArray())
        printDataByteArrayList.add(("Net Amount         :  " + Utils.decimalFormatterWithComma.format(creditList.amt) + "\n").toByteArray())
        printDataByteArrayList.add(("Receive            :  " + Utils.decimalFormatterWithComma.format(creditList.payAmt) + "\n").toByteArray())
        printDataByteArrayList.add((txtForFooter + "\n\n").toByteArray())
        printDataByteArrayList.add("\nSignature          :\n\n                 Thank You. \n\n".toByteArray())
        printDataByteArrayList.add(byteArrayOf(0x1b, 0x64, 0x02)) // Cut
        printDataByteArrayList.add(byteArrayOf(0x07)) // Kick cash drawer

        return printDataByteArrayList

    }

    private fun getPrintDataByteArrayListDeliver(
        activity: Activity,
        customerName: String?,
        cus_address: String?,
        orderInvoiceNo: String,
        orderSalePersonName: String?,
        invoiceNumber: String,
        salePersonName: String?,
        routeName: String?,
        townshipName: String,
        invoice: Invoice,
        soldProductList: ArrayList<SoldProductInfo>,
        presentList: ArrayList<Promotion>,
        prepaidAmount: Double,
        printFor: String,
        mode: String,
        companyInfo: CompanyInformation
    ): ArrayList<ByteArray>{

        val printDataByteArrayList = ArrayList<ByteArray>()
        val imgByte = printBmp(activity)

        imgByte?.let { printDataByteArrayList.add(it) }
        printDataByteArrayList.add("\n".toByteArray())

        var totalAmount = 0.0
        var totalNetAmount = 0.0

        val address = companyInfo.address
        val txtForFooter = companyInfo.pos_voucher_footer1
        val companyTaxRegNo = companyInfo.company_tax_reg_no
        val phNo = companyInfo.phone_number

        printDataByteArrayList.add((address + "\n").toByteArray())
        printDataByteArrayList.add("Ph No           : $phNo\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Tax Reg No      :  $companyTaxRegNo\n".toByteArray())
        printDataByteArrayList.add("Customer        :  $customerName\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Township        :  $townshipName\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Address         :  $cus_address\n".toByteArray(charset("UTF-8")))
        printDataByteArrayList.add("Order Invoice   :  $orderInvoiceNo\n".toByteArray())
        printDataByteArrayList.add("Order Person    :  $orderSalePersonName\n".toByteArray())
        printDataByteArrayList.add("Delivery No     :  $invoiceNumber\n".toByteArray())
        printDataByteArrayList.add("Delivery Person :  $salePersonName\n".toByteArray())
        printDataByteArrayList.add("RouteNo         :  $routeName\n".toByteArray())
        printDataByteArrayList.add(("Delivery Date   :  " + SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US).format(Date()) + "\n").toByteArray())

        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())

        var promoFlg = false

        for (soldProduct in soldProductList) {
            if (soldProduct.promotionPrice == 0.0) {
                promoFlg = false
                break
            } else {
                promoFlg = true
                break
            }
        }

        if (promoFlg){
            formatter = Formatter(StringBuilder(), Locale.US)
            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-10s \t %2$6s \t %3$5s \t %4$5s \t %5$7s\n",
                    "Item",
                    "Qty",
                    "Price",
                    "Pro:Price",
                    "Amount"
                ).toString().toByteArray()
            )
            formatter!!.close()
            printDataByteArrayList.add("----------------------------------------------\n".toByteArray())

            for (soldProduct in soldProductList){
                val quantity = soldProduct.quantity
                var pricePerUnit = 0.0
                var promoPrice = 0.0

                pricePerUnit = soldProduct.product.selling_price!!.toDouble()
                promoPrice = soldProduct.promotionPrice

                val amount = soldProduct.totalAmount
                val pricePerUnitWithDiscount: Double = soldProduct.discountAmount
                val netAmount: Double


                netAmount = soldProduct.totalAmount - pricePerUnitWithDiscount

                totalAmount += amount
                totalNetAmount += netAmount

                // Shorthand the name.
                val nameFragments = soldProduct.product.product_name!!.split(" ")
                val nameList = setupPrintLayoutWithPromo(nameFragments as ArrayList<String>)

                if (printFor == PRINT_FOR_PRE_ORDER) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n",
                            nameList[0],
                            quantity,
                            Utils.decimalFormatterWithoutComma.format(pricePerUnit),
                            Utils.decimalFormatterWithoutComma.format(promoPrice),
                            Utils.decimalFormatterWithComma.format(amount)
                        ).toString().toByteArray()
                    )
                    formatter!!.close()
                }

                if (printFor != PRINT_FOR_PRE_ORDER) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n",
                            nameList[0],
                            quantity,
                            Utils.decimalFormatterWithoutComma.format(pricePerUnit),
                            Utils.decimalFormatterWithoutComma.format(promoPrice),
                            Utils.decimalFormatterWithComma.format(netAmount)
                        ).toString().toByteArray()
                    )
                    formatter!!.close()
                }

                nameList.removeAt(0)
                for (cutName in nameList) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t %2$1s \t %3$1s \t %4$1s \t %5$1s\n", cutName, "", "", "", ""
                        ).toString().toByteArray()
                    )

                    formatter!!.close()
                }

                printDataByteArrayList.add("\n".toByteArray())
            }

            if (presentList != null && presentList.size > 0) {

                for (invoicePresent in presentList) {
                    val quantity = invoicePresent.promotion_quantity
                    val presentPrice = 0.0
                    val promoPrice = 0.0

                    // Shorthand the name.
                    val productName = "Temporary" //getProductNameAndPrice(invoicePresent) Check point !!! ToDo
                    val nameFragments = productName!!.split(" ")
                    val nameList =
                        setupPrintLayoutWithPromo(nameFragments as ArrayList<String>)

                    if (printFor == PRINT_FOR_PRE_ORDER) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n",
                                nameList.get(0),
                                quantity,
                                Utils.decimalFormatterWithComma.format(presentPrice),
                                Utils.decimalFormatterWithoutComma.format(promoPrice),
                                "0.0"
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                    }

                    if (printFor != PRINT_FOR_PRE_ORDER) {

                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n",
                                nameList.get(0),
                                quantity,
                                Utils.decimalFormatterWithComma.format(presentPrice),
                                Utils.decimalFormatterWithoutComma.format(promoPrice),
                                "0.0"
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                    }

                    nameList.removeAt(0)
                    for (cutName in nameList) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t %2$1s \t %3$1s \t %4$1s \t %5$1s\n",
                                cutName,
                                "",
                                "",
                                "",
                                ""
                            ).toString().toByteArray()
                        )

                        formatter!!.close()
                    }

                    printDataByteArrayList.add("\n".toByteArray())
                }
                printDataByteArrayList.add("----------------------------------------------\n".toByteArray())
            }
        } else{
            formatter = Formatter(StringBuilder(), Locale.US)

            printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-20s \t \t %2$4s \t \t %3$4s \t \t \t %4$9s\n",
                    "Item",
                    "Qty",
                    "Price",
                    "Amount"
                ).toString().toByteArray()
            )
            formatter!!.close()
            printDataByteArrayList.add("----------------------------------------------\n".toByteArray())

            for (soldProduct in soldProductList){
                val quantity = soldProduct.quantity
                var pricePerUnit = soldProduct.product.selling_price!!.toDouble()

                val amount = soldProduct.totalAmount
                val pricePerUnitWithDiscount: Double = soldProduct.discountAmount
                val netAmount: Double

                netAmount = soldProduct.totalAmount - pricePerUnitWithDiscount

                totalAmount += amount
                totalNetAmount += netAmount

                val nameFragments = soldProduct.product.product_name!!.split(" ")
                val nameList = setupPrintLayoutNoPromo(nameFragments as ArrayList<String>)

                if (printFor == PRINT_FOR_PRE_ORDER) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                            nameList[0],
                            quantity,
                            Utils.decimalFormatterWithoutComma.format(pricePerUnit),
                            Utils.decimalFormatterWithComma.format(amount)
                        ).toString().toByteArray()
                    )

                    formatter!!.close()
                }

                if (printFor != PRINT_FOR_PRE_ORDER) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                            nameList[0],
                            quantity,
                            Utils.decimalFormatterWithoutComma.format(pricePerUnit),
                            Utils.decimalFormatterWithComma.format(netAmount)
                        ).toString().toByteArray()
                    )
                }

                nameList.removeAt(0)
                for (cutName in nameList) {
                    formatter = Formatter(StringBuilder(), Locale.US)
                    printDataByteArrayList.add(
                        formatter!!.format(
                            "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n", cutName, "", "", ""
                        ).toString().toByteArray()
                    )

                    formatter!!.close()
                }

                printDataByteArrayList.add("\n".toByteArray())
            }

            if (presentList != null && presentList.size > 0) {

                for (invoicePresent in presentList) {
                    val quantity = invoicePresent.promotion_quantity
                    val presentPrice = 0.0

                    // Shorthand the name.
                    val productName = "Temporary" //getProductNameAndPrice(invoicePresent) Check point !!! ToDo
                    val nameFragments = productName!!.split(" ")
                    val nameList = setupPrintLayoutNoPromo(nameFragments as ArrayList<String>)

                    if (printFor == PRINT_FOR_PRE_ORDER) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameList[0],
                                quantity,
                                Utils.decimalFormatterWithComma.format(presentPrice),
                                "0.0"
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                    }

                    if (printFor != PRINT_FOR_PRE_ORDER) {

                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$4s \t \t %3$5s \t \t %4$9s\n",
                                nameList[0],
                                quantity,
                                Utils.decimalFormatterWithComma.format(presentPrice),
                                "0.0"
                            ).toString().toByteArray()
                        )
                        formatter!!.close()
                    }

                    nameList.removeAt(0)
                    for (cutName in nameList) {
                        formatter = Formatter(StringBuilder(), Locale.US)
                        printDataByteArrayList.add(
                            formatter!!.format(
                                "%1$-20s \t \t %2$1s \t \t %3$1s \t \t %4$1s\n",
                                cutName,
                                "",
                                "",
                                ""
                            ).toString().toByteArray()
                        )

                        formatter!!.close()
                    }

                    printDataByteArrayList.add("\n".toByteArray())
                }

            }
        }

        formatter = Formatter(StringBuilder(), Locale.US)

        val taxType = companyInfo.tax_type

        var taxText: String
        if (taxType.equals("E", ignoreCase = true)) {
            taxText = "(Tax " + String.format("%.2f", invoice.tax_amount) + " Excluded)"
            totalNetAmount = invoice.total_amount!!.toDouble() - invoice.total_discount_amount + invoice.tax_amount
        } else {
            taxText = "(Tax " + String.format("%.2f", invoice.tax_amount) + " Included)"
            totalNetAmount = invoice.total_amount!!.toDouble() - invoice.total_discount_amount
        }

        printDataByteArrayList.add("----------------------------------------------\n".toByteArray())

        when {
            printFor == PRINT_FOR_PRE_ORDER -> printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-13s%2$19s\n%3$-15s%4$17s\n%5$-13s%6$19s\n\n\n",
                    "Total Amount    :",
                    Utils.decimalFormatterWithComma.format(totalAmount),
                    taxText,
                    ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/,
                    "Discount        :",
                    Utils.decimalFormatterWithComma.format(invoice.total_discount_amount) + " (" + DecimalFormat("#0.00").format(invoice.total_discount_percent) + "%)",
                    "Net Amount  :",
                    Utils.decimalFormatterWithComma.format(invoice.pay_amount),
                    "Receive      :",
                    Utils.decimalFormatterWithComma.format(invoice.pay_amount)
                ).toString().toByteArray()
            )
            mode == FOR_DELIVERY -> printDataByteArrayList.add(
                formatter!!.format(
                    "%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s%6$19s\n%7$-13s%8$19s\n%9$-13s%10$19s\n\n\n",
                    "Total Amount    :",
                    Utils.decimalFormatterWithComma.format(totalAmount),
                    taxText,
                    ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/,
                    "Discount        :",
                    Utils.decimalFormatterWithComma.format(invoice.total_discount_amount) + " (" + DecimalFormat("#0.00").format(invoice.total_discount_percent) + "%)",
                    "Net Amount      :",
                    Utils.decimalFormatterWithComma.format(totalNetAmount),
                    "Pay Amount      :",
                    Utils.decimalFormatterWithComma.format(invoice.pay_amount)
                ).toString().toByteArray()
            )
            else -> {
                var creditBalance: Double? = 0.0
                totalNetAmount -= prepaidAmount
                if (totalNetAmount > invoice.pay_amount?.toDouble() ?: 0.0) {
                    creditBalance = totalNetAmount - (invoice.pay_amount?.toDouble() ?: 0.0)
                }

                printDataByteArrayList.add(
                    formatter!!.format(
                        "%1$-13s%2$21s\n%3$-13s%4$21s\n%5$-13s%6$21s\n%7$-13s%8$21s\n%9$-13s%10$21s\n%11$-13s%12$21s\n%13$-13s%14$21s\n",
                        "Total Amount    :        ",
                        Utils.decimalFormatterWithComma.format(totalAmount),
                        taxText,
                        "" /*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + new DecimalFormat("#0.00").format(taxPercent) + "%)"*/,
                        "Discount        :        ",
                        Utils.decimalFormatterWithComma.format(invoice.total_discount_amount) + " (" + DecimalFormat("#0.00").format(invoice.total_discount_percent) + "%)",
                        "Prepaid Amount  :        ",
                        Utils.decimalFormatterWithComma.format(prepaidAmount),
                        "Net Amount      :        ",
                        Utils.decimalFormatterWithComma.format(totalNetAmount),
                        "Pay Amount      :        ",
                        Utils.decimalFormatterWithComma.format(invoice.pay_amount),
                        "Credit Balance  :        ",
                        Utils.decimalFormatterWithComma.format(abs(creditBalance!!))
                    ).toString().toByteArray()
                )
            }
        }

        printDataByteArrayList.add("\n\n              $txtForFooter\n\n".toByteArray())
        printDataByteArrayList.add("\nSignature       :\n\n                 Thank You. \n\n".toByteArray())
        printDataByteArrayList.add(byteArrayOf(0x1b, 0x64, 0x02)) // Cut
        printDataByteArrayList.add(byteArrayOf(0x07)) // Kick cash drawer

        return printDataByteArrayList

    }


    private fun sendDataByte2BT(mActivity: Activity, mService: BluetoothService, data: ByteArray) {

        if (mService.state !== BluetoothService.STATE_CONNECTED) {
            Toast.makeText(mActivity, "Not Connected", Toast.LENGTH_SHORT).show()
            return
        }
        mService.write(data)
        Utils.commonDialog("Success", mActivity, 0)

    }

    @Throws(UnsupportedEncodingException::class)
    private fun convertFromListByteArrayToByteArray(ByteArray: List<ByteArray>): ByteArray {
        var dataLength = 0
        for (i in ByteArray.indices) {
            dataLength += ByteArray[i].size
        }

        var distPosition = 0
        val byteArray = ByteArray(dataLength)
        for (i in ByteArray.indices) {
            System.arraycopy(ByteArray[i], 0, byteArray, distPosition, ByteArray[i].size)
            distPosition += ByteArray[i].size
        }

        return byteArray
    }

    private fun setupPrintLayoutWithPromo(nameFragments: ArrayList<String>): ArrayList<String> {

        val nameList = ArrayList<String>()
        var concatName = nameFragments[0]
        var i = 1
        while (i != nameFragments.size) {
            if (concatName.length > 13) {
                nameList.add(concatName)
                concatName = nameFragments[i]
                nameList.add(concatName)
                concatName = ""
            } else {

                var concatName1 = if (concatName.equals("", ignoreCase = true)) {
                    nameFragments[i]
                } else {
                    "$concatName ${nameFragments[i]}"
                }

                if (concatName1.length < 13) {
                    concatName += if (concatName.isNotEmpty()) {
                        " " + nameFragments[i]
                    } else {
                        nameFragments[i] + " "
                    }
                } else {
                    nameList.add(concatName)
                    nameList.add(nameFragments[i])
                    concatName = ""
                }
            }
            i++
        }

        if (nameList.size == 0) {
            nameList.add(concatName)
        } else {
            val lastString = nameList[nameList.size - 1]
            if (lastString.length > 13) {
                nameList.add(concatName)
            } else {
                nameList[nameList.size - 1] = lastString + concatName
            }
        }

        for (j in nameList.indices) {
            if (nameList[j].length < 13) {
                val stringLength = 10 - nameList[j].length
                var s = nameList[j]
                for (k in 0 until stringLength) {
                    s += " "
                    nameList[j] = s
                }
            }
        }

        return nameList
    }

    private fun setupPrintLayoutNoPromo(nameFragments: ArrayList<String>): ArrayList<String> {
        val nameList = ArrayList<String>()
        var concatName = nameFragments[0]
        var i = 1
        while (i != nameFragments.size) {
            if (concatName.length > 23) {
                nameList.add(concatName)
                concatName = nameFragments[i]
                nameList.add(concatName)
                concatName = ""
            } else {

                var concatName1 = if (concatName.equals("", ignoreCase = true)) {
                    nameFragments[i]
                } else {
                    concatName + " " + nameFragments[i]
                }

                if (concatName1.length < 20) {
                    concatName += if (concatName.isNotEmpty()) {
                        " " + nameFragments[i]
                    } else {
                        nameFragments[i] + " "
                    }


                } else {
                    nameList.add(concatName)
                    nameList.add(nameFragments[i])
                    concatName = ""
                }
            }
            i++
        }


        if (nameList.size > 0) {
            val lastString = nameList[nameList.size - 1]
            if (lastString.length > 23) {
                nameList.add(concatName)
            } else {
                nameList[nameList.size - 1] = lastString + concatName
            }
        } else if (nameList.size == 0 && concatName != "") {
            nameList.add(concatName)
        }

        for (j in nameList.indices) {
            if (nameList[j].length < 20) {
                val stringLength = 10 - nameList[j].length
                var s = nameList[j]
                for (k in 0 until stringLength) {
                    s += " "
                    nameList[j] = s
                }
            }
        }
        return nameList
    }

    private fun printBmp(mActivity: Activity): ByteArray? {
        val mBitmap = BitmapFactory.decodeResource(mActivity.resources, R.drawable.global_sky_logo)
        val nMode = 0
        val nPaperWidth = 384
        return if (mBitmap != null) {
            PrintPicture.POS_PrintBMP(mBitmap, nPaperWidth, nMode)
        } else null
    }

}