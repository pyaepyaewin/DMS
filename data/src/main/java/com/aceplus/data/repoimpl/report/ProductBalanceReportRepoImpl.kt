package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.repo.report.ProductBalanceReportRepo
import io.reactivex.Observable

class ProductBalanceReportRepoImpl(private val db:MyDatabase):ProductBalanceReportRepo {
    override fun productBalanceReport(): Observable<List<com.aceplus.domain.entity.product.Product>> {
    return Observable.just(db.productDao().allData)
    }

}