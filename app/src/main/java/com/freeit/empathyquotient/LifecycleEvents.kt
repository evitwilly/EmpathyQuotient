package com.freeit.empathyquotient

class LifecycleEvents {
    private val observers = mutableListOf<ActivityLifecycle>()

    fun insertEventObserver(observer: ActivityLifecycle) { observers.add(observer) }
    fun removeEventObserver(observer: ActivityLifecycle) { observers.remove(observer) }
    fun clear() { observers.clear() }

    fun onPause() { observers.forEach { observer -> observer.onPause() } }
}