package com.freeit.empathyquotient.core

import android.content.Context
import android.content.res.Configuration

class PortraitCheck(private val context: Context) {
    fun run(onPortrait: () -> Unit, onLandscape: () -> Unit) {
        if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            onPortrait()
        } else {
            onLandscape()
        }
    }

    fun isYes() = context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    fun <T> get(onPortrait: () -> T, onLandscape: () -> T) : T {
        return if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            onPortrait()
        } else {
            onLandscape()
        }
    }
}