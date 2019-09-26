package com.example.dms.viewmodels.Factory.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dms.data.repositories.ApiRepository

class ApiMainViewModelFactory (val repo: ApiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ApiMainViewModel(repo) as T
    }
}