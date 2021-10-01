package com.freeit.empathyquotient

import android.os.Bundle

interface SaveObserver {
    fun onSaveInstanceState(outState: Bundle)
    fun onRestoreInstanceState(savedInstanceState: Bundle)
}