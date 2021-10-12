package com.freeit.empathyquotient.presentation.navigator

import com.freeit.empathyquotient.core.LocalPrefsDataSource

interface ScreenArg {

    fun save(id: Int, appPrefs: LocalPrefsDataSource)


    class Empty : ScreenArg {
        override fun save(id: Int, appPrefs: LocalPrefsDataSource) {}
    }
    class Test(private var questionId: Int = 0) : ScreenArg {
        fun qId() = questionId

        companion object {
            fun fromId(id: Int, appPrefs: LocalPrefsDataSource) : Test {
                val questionId = appPrefs.int("${id}_question_id", 0)
                return Test(questionId)
            }
        }

        override fun save(id: Int, appPrefs: LocalPrefsDataSource) {
            appPrefs.saveInt("${id}_question_id", questionId)
        }
    }
}