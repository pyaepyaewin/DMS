package com.example.dms.viewmodels.Factory.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dms.data.repositories.CustomerRepository

class CustomerMainViewModelFactory(val repo: CustomerRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
return CustomerMainViewModel(repo) as T

}}