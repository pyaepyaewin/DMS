package com.example.dms.viewmodels.Factory.report

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dms.data.repositories.ReportRepository

class ReportMainViewModelFactory (val repo:ReportRepository,val context: Context):
    ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReportMainViewModel(repo) as T
    }
}