package com.freeit.empathyquotient.core.navigator

import com.freeit.empathyquotient.core.LocalPrefsDataSource

interface TestStack {
    fun save(entry: ScreenEntry.Abstract)
    fun exists(entry: ScreenEntry.Abstract) : Boolean
    fun clear()

    companion object {
        private const val key = "test_stack_key"
    }

    class Base(private val appPrefs: LocalPrefsDataSource) : TestStack {
        override fun save(entry: ScreenEntry.Abstract) {
            val stackValue = appPrefs.str(key, "")
            if (stackValue.isEmpty()) {
                appPrefs.saveStr(key, entry.id().toString())
            } else {
                val ids = stackValue.split(",").toMutableList()
                ids.add(entry.id().toString())
                appPrefs.saveStr(key, ids.joinToString(","))
            }
        }

        override fun exists(entry: ScreenEntry.Abstract) : Boolean {
            val stackValue = appPrefs.str(key, "")
            return if (stackValue.isEmpty()) {
                false
            } else {
                stackValue.split(",").map { it.toInt() }.any {
                    it == entry.id()
                }
            }
        }

        override fun clear() {
            appPrefs.saveStr(key, "")
        }
    }
}