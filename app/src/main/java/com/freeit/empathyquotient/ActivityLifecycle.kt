package com.freeit.empathyquotient

interface ActivityLifecycle {
    fun onStop()
    fun onPause()
    fun onStart()
    fun onResume()
}