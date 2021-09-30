package com.freeit.empathyquotient

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.freeit.empathyquotient.presentation.navigator.Navigator
import com.freeit.empathyquotient.presentation.screens.ScreenEntry

class MainViewModel(private val navigator: Navigator) : ViewModel() {

//    fun observeScreen(lifecycleOwner: LifecycleOwner, observer: Observer<ScreenEntry.Abstract>) =
//        navigator.observe(lifecycleOwner, observer)

    fun isBackPressed() = navigator.back()

}