package com.aceplus.dms.viewmodel.promotionviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.promotionDataClass.CategoryDiscountDataClass
import com.aceplus.domain.repo.promotionrepo.CategoryDiscountRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CategoryDiscountViewModel (private val categoryDiscountRepo: CategoryDiscountRepo, private val schedulerProvider: SchedulerProvider):
    BaseViewModel() {
    var categoryDiscountSuccessState = MutableLiveData<List<CategoryDiscountDataClass>>()
    var categoryDiscountErrorState = MutableLiveData<String>()
    fun loadCategoryDiscount() {
        launch {
            categoryDiscountRepo.getCategoryDiscountList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    categoryDiscountSuccessState.postValue(it)
                },{
                    categoryDiscountErrorState.value = it.localizedMessage
                })
        }
    }
}