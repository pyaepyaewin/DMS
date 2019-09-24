package com.example.dms.viewmodels.Factory.sale

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dms.data.repositories.SaleRepository

class SaleMainViewModelFactory (val repo: SaleRepository,val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SaleMainViewModel(repo,context) as T
    }
}