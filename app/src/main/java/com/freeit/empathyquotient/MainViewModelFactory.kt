package com.freeit.empathyquotient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freeit.empathyquotient.presentation.navigator.Navigator

class MainViewModelFactory(private val navigator: Navigator): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(navigator) as T
    }
}