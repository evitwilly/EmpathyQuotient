package com.freeit.empathyquotient.presentation.navigator

import com.freeit.empathyquotient.core.LocalPrefsDataSource
import com.freeit.empathyquotient.presentation.screens.ScreenEntry

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
                appPrefs.save(key, entry.id().toString())
            } else {
                val ids = stackValue.split(",").toMutableList()
                ids.add(entry.id().toString())
                appPrefs.save(key, ids.joinToString(","))
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
            appPrefs.save(key, "")
        }
    }
}