package com.example.dms.viewmodels.Factory.ItemReport

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dms.data.repositories.ItemReportRepository

class ItemReportViewModelFactory (val repo:ItemReportRepository):
    ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemReportMainViewModel(repo) as T
    }
}