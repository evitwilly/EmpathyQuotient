package com.freeit.empathyquotient

class LifecycleEvents {
    private val observers = mutableListOf<ActivityLifecycle>()

    fun insertEventObserver(observer: ActivityLifecycle) { observers.add(observer) }
    fun removeEventObserver(observer: ActivityLifecycle) { observers.remove(observer) }
    fun clear() { observers.clear() }

    fun onStop() { observers.forEach { observer -> observer.onStop() } }
    fun onPause() { observers.forEach { observer -> observer.onPause() } }
    fun onResume() { observers.forEach { observer -> observer.onResume() } }
    fun onStart() { observers.forEach { observer -> observer.onStart() } }
}