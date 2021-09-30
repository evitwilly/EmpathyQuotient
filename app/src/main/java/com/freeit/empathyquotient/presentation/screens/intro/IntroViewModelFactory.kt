package com.freeit.empathyquotient.presentation.screens.intro

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freeit.empathyquotient.core.LocalPrefsDataSource

class IntroViewModelFactory(
    private val ctx: Context,
    private val localPrefsDataSource: LocalPrefsDataSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IntroViewModel(ctx, localPrefsDataSource) as T
    }
}