package com.freeit.empathyquotient.core

import android.content.Context

class LocalPrefsDataSource(ctx: Context) {

    private val prefsName = "app_prefs"
    private val sharedPrefs = ctx.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun save(key: String, value: Int) {
        sharedPrefs.edit()
            .putInt(key, value)
            .apply()
    }

    fun save(key: String, value: String) {
        sharedPrefs.edit()
            .putString(key, value)
            .apply()
    }

    fun str(key: String, default: String) : String {
        return sharedPrefs.getString(key, default) ?: default
    }

    fun int(key: String, default: Int) : Int {
        return sharedPrefs.getInt(key, default)
    }
}