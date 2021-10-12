package com.freeit.empathyquotient.core

import android.content.Context

interface IntPrefs {
    fun saveInt(ket: String, value: Int)
    fun int(key: String, default: Int) : Int
}

interface StringPrefs {
    fun saveStr(key: String, value: String)
    fun str(key: String, default: String) : String
}

class LocalPrefsDataSource(ctx: Context) : IntPrefs, StringPrefs {

    private val prefsName = "app_prefs"
    private val sharedPrefs = ctx.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    override fun saveInt(key: String, value: Int) {
        sharedPrefs.edit().putInt(key, value).apply()
    }

    override fun saveStr(key: String, value: String) {
        sharedPrefs.edit().putString(key, value).apply()
    }

    override fun str(key: String, default: String) : String {
        return sharedPrefs.getString(key, default) ?: default
    }

    override fun int(key: String, default: Int) : Int {
        return sharedPrefs.getInt(key, default)
    }

}