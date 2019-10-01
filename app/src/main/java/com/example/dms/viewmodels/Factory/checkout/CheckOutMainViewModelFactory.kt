package com.example.dms.viewmodels.Factory.checkout

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dms.data.repositories.CheckOutRepository

class CheckOutMainViewModelFactory (val repo:CheckOutRepository,val context: Context):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CheckOutMainViewModel(repo) as T
    }
}