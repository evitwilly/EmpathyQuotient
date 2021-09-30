package com.freeit.empathyquotient.presentation.navigator

import com.freeit.empathyquotient.core.LocalPrefsDataSource
import com.freeit.empathyquotient.presentation.screens.Prefix
import com.freeit.empathyquotient.presentation.screens.ScreenEntry
import com.freeit.empathyquotient.presentation.screens.intro.IntroScreen
import com.freeit.empathyquotient.presentation.screens.intro.ScreenVitals
import com.freeit.empathyquotient.presentation.screens.test.State

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

interface ScreenStack {
    fun save(screenVitals: ScreenVitals, entry: ScreenEntry.Abstract)
    fun last(screenVitals: ScreenVitals) : ScreenEntry.Abstract
    fun pop(screenVitals: ScreenVitals) : ScreenEntry.Abstract

    class Base(private val appPrefs: LocalPrefsDataSource, private val testStack: TestStack) : ScreenStack {

        private val stackValueKey = "stack_value_key"

        override fun save(screenVitals: ScreenVitals, entry: ScreenEntry.Abstract) {
            val stackValue = appPrefs.str(stackValueKey, "")
            entry.saveArg(appPrefs)
            if (stackValue.isEmpty()) {
                appPrefs.save(stackValueKey, entry.id().toString())
            } else {
                val ids = stackValue.split(",").toMutableList()
                ids.add(entry.id().toString())
                appPrefs.save(stackValueKey, ids.joinToString(","))
            }
        }

        override fun last(screenVitals: ScreenVitals): ScreenEntry.Abstract {
            val stackValue = appPrefs.str(stackValueKey, "")
            return if (stackValue.isEmpty()) {
                IntroScreen(screenVitals, ScreenArg.Empty(), Prefix.intro().self() * 100)
            } else {
                val lastEntryId = stackValue.split(",").last().toInt()
                ScreenEntry.Abstract.fromId(screenVitals, lastEntryId, appPrefs)
            }
        }

        override fun pop(screenVitals: ScreenVitals): ScreenEntry.Abstract {
            val stackValue = appPrefs.str(stackValueKey, "")
            return if (stackValue.isEmpty()) {
                IntroScreen(screenVitals, ScreenArg.Empty(), Prefix.intro().self() * 100)
            } else {
                val ids = stackValue.split(",").toMutableList()
                val lastId = ids.removeLast()
                appPrefs.save(stackValueKey, ids.joinToString(","))
                ScreenEntry.Abstract.fromId(screenVitals, lastId.toInt(), appPrefs)
            }
        }

    }
}