package com.aceplus.data.repoimpl

import android.content.SharedPreferences
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.remote.DownloadApiService
import com.aceplus.data.utils.Constant
import com.aceplus.domain.entity.route.RouteScheduleItemV2
import com.aceplus.domain.entity.route.RouteScheduleV2
import com.aceplus.domain.model.forApi.login.DataForLogin
import com.aceplus.domain.model.forApi.login.LoginResponse
import com.aceplus.domain.model.route.Route
import com.aceplus.domain.model.route.RouteAssign
import com.aceplus.domain.model.routeSchedule_v2.RouteSchedule_v2
import com.aceplus.domain.model.sale.SaleMan
import com.aceplus.domain.repo.LoginRepo
import com.aceplussolutions.rms.constants.AppUtils
import io.reactivex.Observable

class LoginRepoImpl(
    var downloadApi: DownloadApiService,
    private val db: MyDatabase,
    private val shf: SharedPreferences
) : LoginRepo {

    override fun isSaleManExist(): Boolean {
        return db.saleManDao().allData.count() > 0
    }

    override fun isSaleManCorrect(userId: String, password: String): Boolean {
        val saleMans = db.saleManDao().data(userId = userId, password = password)
        if (saleMans.count() > 0) {
            AppUtils.saveStringToShp(Constant.SALEMAN_ID, saleMans[0].id.toString(), shf)
            AppUtils.saveStringToShp(Constant.SALEMAN_NO, saleMans[0].user_id.toString(), shf)
            AppUtils.saveStringToShp(Constant.SALEMAN_NAME, saleMans[0].user_name.toString(), shf)
            AppUtils.saveStringToShp(Constant.SALEMAN_PWD, saleMans[0].password.toString(), shf)
            return true
        }
        return false
    }

    override fun loginUser(paramData: String): Observable<LoginResponse> {
        return downloadApi.login(paramData)
    }

    override fun saveLoginData(loginResponse: LoginResponse) {
        val dataForLoginList: List<DataForLogin> = loginResponse.dataForLogin
        AppUtils.saveStringToShp(Constant.SALEMAN_ID, dataForLoginList[0].saleMan[0].id, shf)
        AppUtils.saveStringToShp(Constant.SALEMAN_NO, dataForLoginList[0].saleMan[0].saleManNo, shf)
        AppUtils.saveStringToShp(Constant.SALEMAN_NAME, dataForLoginList[0].saleMan[0].saleManName, shf)
        AppUtils.saveStringToShp(Constant.SALEMAN_PWD, dataForLoginList[0].saleMan[0].password, shf)
        AppUtils.saveIntToShp(Constant.TABLET_KEY, loginResponse.tabletKey, shf)
        AppUtils.saveIntToShp(Constant.MAX_KEY, loginResponse.maxKey, shf)

        insertSaleMan(dataForLoginList[0].saleMan)
        insertRouteScheduleAndItemV2(dataForLoginList[0].routeSchedule)
        insertRouteAssign(dataForLoginList[0].routeAssign)
        insertRoute(dataForLoginList[0].route)

    }

    private fun insertRoute(routeList: List<Route>) {
        val routeEntityList = mutableListOf<com.aceplus.domain.entity.route.Route>()
        routeList.map {
            val routeEntity = com.aceplus.domain.entity.route.Route()
            routeEntity.id = it.id.toInt()
            routeEntity.route_no = it.routeNo
            routeEntity.route_name = it.routeName
            routeEntity.active = it.active
            routeEntity.timestamp = it.ts
            routeEntityList.add(routeEntity)
        }
        db.routeDao().insertAll(routeEntityList)
    }

    private fun insertRouteAssign(routeAssignList: List<RouteAssign>) {
        val routeAssignEntityList = mutableListOf<com.aceplus.domain.entity.route.RouteAssign>()
        routeAssignList.map {
            val routeAssignEntity = com.aceplus.domain.entity.route.RouteAssign()
            routeAssignEntity.id = it.id.toInt()
            routeAssignEntity.route_id = it.routeId.toInt()
            routeAssignEntity.customer_id = it.customerId.toInt()
            routeAssignEntity.assign_date = it.assignDate
            routeAssignEntity.active = it.active
            routeAssignEntity.timestamp = it.ts
        }
        db.routeAssignDao().insertAll(routeAssignEntityList)
    }

    private fun insertRouteScheduleAndItemV2(routeScheduleList: List<RouteSchedule_v2>) {
        val routeScheduleV2EntityList = mutableListOf<com.aceplus.domain.entity.route.RouteScheduleV2>()
        val routeScheduleItemV2EntityList = mutableListOf<com.aceplus.domain.entity.route.RouteScheduleItemV2>()
        routeScheduleList.map { routeSchedule ->
            val routeScheduleV2Entity = RouteScheduleV2()
            val routeScheduleItemV2Entity = RouteScheduleItemV2()
            routeScheduleV2Entity.id = routeSchedule.id.toInt()
            routeScheduleV2Entity.schedule_no = routeSchedule.scheduleNo
            routeScheduleV2Entity.schedule_date = routeSchedule.scheduleDate
            routeScheduleV2Entity.sale_man_id = routeSchedule.saleManId
            routeScheduleV2Entity.route_id = routeSchedule.routeId
            routeScheduleV2Entity.commission = routeSchedule.comission
            routeScheduleV2Entity.active = routeSchedule.active
            routeScheduleV2Entity.timestamp = routeSchedule.toString()
            routeScheduleV2EntityList.add(routeScheduleV2Entity)

            routeSchedule.routeScheduleItem.map {
                routeScheduleItemV2Entity.id = it.id.toInt()
                routeScheduleItemV2Entity.route_schedule_id = it.routeScheduleId.toInt()
                routeScheduleItemV2Entity.customer_id = it.customerId.toInt()
                routeScheduleItemV2Entity.serial_no = it.srNo
                routeScheduleItemV2EntityList.add(routeScheduleItemV2Entity)
            }
        }
        db.routeScheduleV2Dao().insertAll(routeScheduleV2EntityList)
        db.routeScheduleItemV2Dao().insertAll(routeScheduleItemV2EntityList)
    }

    private fun insertSaleMan(saleManList: List<SaleMan>) {
        val saleManEntityList = mutableListOf<com.aceplus.domain.entity.sale.SaleMan>()
        saleManList.map {
            val saleManEntity = com.aceplus.domain.entity.sale.SaleMan()
            saleManEntity.id = it.id
            saleManEntity.user_id = it.saleManNo
            saleManEntity.password = it.password
            saleManEntity.user_name = it.saleManName
            saleManEntityList.add(saleManEntity)
        }
        db.saleManDao().insertAll(saleManEntityList)
    }

    override fun isCustomerInDB(): Boolean {
        return db.customerDao().allData.count() > 0
    }


}