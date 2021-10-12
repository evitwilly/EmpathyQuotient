package com.freeit.empathyquotient.core.navigator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner

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