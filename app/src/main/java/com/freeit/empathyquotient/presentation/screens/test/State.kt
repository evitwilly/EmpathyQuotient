package com.freeit.empathyquotient.presentation.screens.test

import com.freeit.empathyquotient.core.LocalPrefsDataSource
import com.freeit.empathyquotient.core.navigator.Prefix

abstract class State {

    companion object {
        fun fromId(id: Int, appPrefs: LocalPrefsDataSource) : State {
            val prefix = Prefix(id)
            return when {
                prefix.isTest() -> Test.fromId(id, appPrefs)
                else -> Empty()
            }
        }
    }

    class Empty : State()

    class Test(private val answerId: Int) : State() {

        fun save(id: Int, appPrefs: LocalPrefsDataSource) {
            appPrefs.saveInt("${id}_$answerIdKey", answerId)
        }

        fun aId() = answerId

        companion object {
            private const val answerIdKey = "answer_id_key"

            fun fromId(id: Int, appPrefs: LocalPrefsDataSource) : Test {
                return Test(appPrefs.int("${id}_$answerIdKey", -1))
            }
        }
    }

}