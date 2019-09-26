package com.example.dms.data.repositories

import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.Date

interface CheckOutRepository {
    fun saveDataIntoDatabase(dateList:List<Date>)
    fun saveCheckOutIntoDatabase(checkOutList:List<CheckOut>)

}