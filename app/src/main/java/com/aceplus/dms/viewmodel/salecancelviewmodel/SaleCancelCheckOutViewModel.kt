package com.aceplus.dms.viewmodel.salecancelviewmodel

import com.aceplus.domain.repo.salecancelrepo.SaleCancelRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SaleCancelCheckOutViewModel(private val saleCancelRepo: SaleCancelRepo,
                                  private val schedulerProvider: SchedulerProvider
) : BaseViewModel()
{


}