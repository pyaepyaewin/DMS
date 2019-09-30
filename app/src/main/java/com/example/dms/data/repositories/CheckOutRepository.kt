package com.example.dms.data.repositories

import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.Invoice
import com.example.dms.data.database.table.InvoiceItem
import com.example.dms.data.database.table.InvoiceReport

interface CheckOutRepository {
    fun saveDataIntoDatabase(invoiceItem: Invoice, checkoutItems: MutableList<InvoiceItem>)


}