package com.freeit.empathyquotient

import androidx.lifecycle.ViewModel
import com.freeit.empathyquotient.core.navigator.Navigator

class MainViewModel(private val navigator: Navigator) : ViewModel() {

//    fun observeScreen(lifecycleOwner: LifecycleOwner, observer: Observer<ScreenEntry.Abstract>) =
//        navigator.observe(lifecycleOwner, observer)

    fun isBackPressed() = navigator.back()

}