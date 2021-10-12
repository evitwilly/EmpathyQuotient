package com.freeit.empathyquotient.core

interface StringPrefs {
    fun saveStr(key: String, value: String)
    fun str(key: String, default: String) : String
}