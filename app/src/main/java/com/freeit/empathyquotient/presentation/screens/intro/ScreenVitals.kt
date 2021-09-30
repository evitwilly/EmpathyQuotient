package com.freeit.empathyquotient.presentation.screens.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.freeit.empathyquotient.presentation.navigator.Navigator

interface ScreenVitals {
    fun inflater() : LayoutInflater
    fun parent() : ViewGroup
    fun navigator() : Navigator
    fun lifecycleOwner(): LifecycleOwner
    fun copyWith(navigator: Navigator) : ScreenVitals

    data class Base(
        private val parent: ViewGroup,
        private val activity: AppCompatActivity,
        private val navigator: Navigator = Navigator.Empty()
    ) : ScreenVitals {
        override fun inflater() = activity.layoutInflater
        override fun lifecycleOwner() = activity
        override fun copyWith(navigator: Navigator) : ScreenVitals = copy(navigator = navigator)
        override fun parent() = parent
        override fun navigator() = navigator

    }
}