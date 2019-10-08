package com.aceplus.domain.repo.report
import com.aceplus.domain.model.Product
import com.aceplus.domain.model.report.ProductBalanceReport
import io.reactivex.Observable

interface ProductBalanceReportRepo {
    fun productBalanceReport():Observable<List<com.aceplus.domain.entity.product.Product>>
}