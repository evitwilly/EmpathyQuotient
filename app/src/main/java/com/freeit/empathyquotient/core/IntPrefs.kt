package com.freeit.empathyquotient.core

interface IntPrefs {
    fun saveInt(ket: String, value: Int)
    fun int(key: String, default: Int) : Int
}