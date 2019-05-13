package com.aceplus.dms.viewmodel.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.aceplus.data.repoimpl.SyncRepoImpl
import com.aceplus.dms.viewmodel.SyncViewModel
import com.aceplus.domain.repo.SyncRepo


class SyncViewModelFactory(private val syncRepo: SyncRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SyncViewModel(syncRepo) as T
    }
}
