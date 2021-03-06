package com.freeit.empathyquotient

import android.os.Bundle

class SaveStateEvents {
    private val observers = mutableListOf<SaveObserver>()

    fun onSaveInstanceState(outState: Bundle) {
        this.observers.forEach {
            it.onSaveInstanceState(outState)
        }
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        this.observers.forEach {
            it.onRestoreInstanceState(savedInstanceState)
        }
    }
}