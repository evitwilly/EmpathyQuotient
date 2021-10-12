package com.freeit.empathyquotient.core.navigator

import com.freeit.empathyquotient.core.LocalPrefsDataSource
import com.freeit.empathyquotient.presentation.screens.Prefix
import com.freeit.empathyquotient.presentation.screens.ScreenEntry
import com.freeit.empathyquotient.presentation.screens.intro.IntroScreen

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
                appPrefs.saveStr(stackValueKey, entry.id().toString())
            } else {
                val ids = stackValue.split(",").toMutableList()
                ids.add(entry.id().toString())
                appPrefs.saveStr(stackValueKey, ids.joinToString(","))
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
                appPrefs.saveStr(stackValueKey, ids.joinToString(","))
                ScreenEntry.Abstract.fromId(screenVitals, lastId.toInt(), appPrefs)
            }
        }

    }
}