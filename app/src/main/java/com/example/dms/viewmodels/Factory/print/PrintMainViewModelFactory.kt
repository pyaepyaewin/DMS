package com.example.dms.viewmodels.Factory.print

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PrintMainViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PrintMainViewModel() as T
    }
}