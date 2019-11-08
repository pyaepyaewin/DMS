package com.aceplus.data.database.dao.customer

import com.aceplus.domain.entity.customer.Customer

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.predefine.Township
import com.aceplus.domain.model.customer.prospectcustomer.NewCustomer
import com.aceplus.domain.model.roomdb.StringObject
import com.aceplus.domain.model.routedataclass.CustomerLocationDataClass
import io.reactivex.Observable

@Dao
interface CustomerDao {

    @get:Query("select * from customer")
    val allDataLD: LiveData<List<Customer>>

    @get:Query("select * from customer")
    val allData: List<Customer>

    @Query("select c.id, c.customer_id, c.customer_name, c.customer_type_id, c.customer_type_name, c.address, c.phone, c.township, c.credit_term, c.credit_limit, c.credit_amount, c.due_amount, c.prepaid_amount, c.payment_type, c.in_route, c.latitude, c.longitude, c.visit_record, c.district_id, c.state_division_id, c.shop_type_id, c.street_id, c.fax, t.township_name as township_number, c.customer_category_no, c.contact_person, c.route_schedule_status, c.created_user_id, c.created_date, c.flag from customer as c,township as t where c.township_number = t.id")
    fun allCustomerData(): List<Customer>

    @Query("select * from customer where id = :customerId")
    fun dataById(customerId: Int): Customer

    @Query("select * from customer where id = :customerId")
    fun deliveryCustomerDataList(customerId: Int): Customer

    @get:Query("select id as data from customer")
    val allID: List<StringObject>

    @get:Query("select * from customer where flag =1")
    val customerList: List<Customer>

    @get:Query("select * from customer where flag =2")
    val existingCustomerList: List<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Customer>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomerData(customer:Customer)

    @Query("Delete from customer")
    fun deleteAll()

    @Query("update customer set latitude = :latitude, longitude = :longitude where id = :customerID")
    fun updateCustomerData(customerID: Int, latitude: String?, longitude: String?)

    @Query("select customer.latitude,customer.longitude from customer where customer.route_schedule_status=1")
    fun getCustomerLocation():List<CustomerLocationDataClass>

    @Query("select * from customer where customer_id=:customerID")
    fun getCustomerName(customerID: String):List<Customer>


}
