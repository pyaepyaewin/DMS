package com.aceplus.data.database.dao.tdiscount

import com.aceplus.domain.entity.tdiscount.TDiscountByCategoryQuantity
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.promotionDataClass.CategoryDiscountDataClass


@Dao
interface TDiscountByCategoryQuantityDao{

    @get:Query("select * from t_discount_by_category_quantity")
    val allDataLD: LiveData<List<TDiscountByCategoryQuantity>>

    @get:Query("select * from t_discount_by_category_quantity")
    val allData: List<TDiscountByCategoryQuantity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<TDiscountByCategoryQuantity>)

    @Query("Delete from t_discount_by_category_quantity")
    fun deleteAll()

    @Query("select product_category.category_name,t_discount_by_category_quantity_item.from_quantity,t_discount_by_category_quantity_item.to_quantity,t_discount_by_category_quantity_item.discount_percent from product_category,t_discount_by_category_quantity_item,t_discount_by_category_quantity where t_discount_by_category_quantity.category_id=product_category.category_id and t_discount_by_category_quantity_item.t_promotion_plan_id=t_discount_by_category_quantity.id")
    fun getCategoryDiscount():List<CategoryDiscountDataClass>

}