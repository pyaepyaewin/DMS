package com.aceplus.data.database.dao.product

import com.aceplus.domain.entity.product.Product
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Observable


@Dao
interface ProductDao {

    @get:Query("select * from product")
    val allDataLD: LiveData<List<Product>>

    @get:Query("select * from product")
    val allData: List<Product>

//    @get:Query("select * from product")
//    val allProductData: Observable<List<Product>>

    @get:Query("select p.id, p.product_id, p.product_name, p.category_id, p.group_id, p.total_quantity, p.remaining_quantity, p.selling_price, p.purchase_price, p.discount_type, u.name as um, p.sold_quantity, p.order_quantity, p.exchange_quantity, p.return_quantity, p.delivery_quantity, p.present_quantity, p.device_issue_status, p.class_id from product as p, um as u where p.um = u.id")
    val allProductData: List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Product>)

    @Query("Delete from product")
    fun deleteAll()

}