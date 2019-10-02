package com.aceplus.data.database.dao.customer

import com.aceplus.domain.entity.customer.Customer

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.roomdb.StringObject
import io.reactivex.Observable

@Dao
interface CustomerDao {

    @get:Query("select * from customer")
    val allDataLD: LiveData<List<Customer>>

    @get:Query("select * from customer")
    val allData: List<Customer>

    //todo check
//    @get:Query("select c.id,c.customer_id,c.customer_name,c.customer_type_id,c.customer_type_name,c.address,c.phone,c.township,c.credit_term,c.credit_limit,c.credit_amount,c.due_amount,c.prepaid_amount,c.payment_type,c.is_in_route,c.latitude,c.longitude,c.visit_record,c.district_id,c.state_division_id,c.shop_type_id,c.street_id,c.fax,t.township_name as township_number,c.customer_category_no,c.contact_person,c.route_schedule_status,c.created_user_id,c.created_date,c.flag  from customer as c,township as t where c.township_number == t.township_id")
//    val allCustomerData: Observable<List<Customer>>

    /*@get:Query("select c.id, c.customer_id, c.customer_name, c.customer_type_id, c.customer_type_name, c.address, c.phone, c.township, c.credit_term, c.credit_limit, c.credit_amount, c.due_amount, c.prepaid_amount, c.payment_type, c.in_route, c.latitude, c.longitude, c.visit_record, c.district_id, c.state_division_id, c.shop_type_id, c.street_id, c.fax, t.township_name as township_number, c.customer_category_no, c.contact_person, c.route_schedule_status, c.created_user_id, c.created_date, c.flag from customer as c,township as t where c.township_number == t.township_id")
    val allCustomerData: List<Customer>*/

    //@Query("select c.id, c.customer_id, c.customer_name, c.customer_type_id, c.customer_type_name, c.address, c.phone, c.township, c.credit_term, c.credit_limit, c.credit_amount, c.due_amount, c.prepaid_amount, c.payment_type, c.in_route, c.latitude, c.longitude, c.visit_record, c.district_id, c.state_division_id, c.shop_type_id, c.street_id, c.fax, t.township_name as township_number, c.customer_category_no, c.contact_person, c.route_schedule_status, c.created_user_id, c.created_date, c.flag from customer as c inner join township as t on c.township_number == t.township_id")
    @get:Query("select * from customer")
    val allCustomerData: List<Customer>
    //fun allCustomerData(): List<Customer>

    @Query("select * from customer where id=:customerId")
    fun dataById(customerId: Int): Customer

    @get:Query("select id as data from customer")
    val allID: List<StringObject>

    @get:Query("select * from customer where flag =1")
    val customerList: List<Customer>

    @get:Query("select * from customer where flag =2")
    val existingCustomerList: List<Customer>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Customer>)

    @Query("Delete from customer")
    fun deleteAll()

}
