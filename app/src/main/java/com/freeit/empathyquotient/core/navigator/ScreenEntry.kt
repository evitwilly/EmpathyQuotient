package com.freeit.empathyquotient.presentation.screens

import android.view.View
import android.view.ViewGroup
import com.freeit.empathyquotient.core.LocalPrefsDataSource
import com.freeit.empathyquotient.core.navigator.ScreenArg
import com.freeit.empathyquotient.presentation.screens.intro.IntroScreen
import com.freeit.empathyquotient.core.navigator.ScreenVitals
import com.freeit.empathyquotient.presentation.screens.score.ScoreScreen
import com.freeit.empathyquotient.presentation.screens.test.TestScreen

interface ScreenState {
    fun saveState(appPrefs: LocalPrefsDataSource)
    fun restoreState(appPrefs: LocalPrefsDataSource)
    fun clearState(appPrefs: LocalPrefsDataSource)

    class Empty : ScreenState {
        override fun clearState(appPrefs: LocalPrefsDataSource) {}

        override fun restoreState(appPrefs: LocalPrefsDataSource) {}

        override fun saveState(appPrefs: LocalPrefsDataSource) {}
    }
}

data class Prefix(private val prefix: Int) {
    fun self() = prefix

    fun isIntro() = prefix == introPrefix
    fun isScore() = prefix == scorePrefix
    fun isTest() = prefix == testPrefix

    companion object {
        private const val introPrefix = 1
        private const val testPrefix = 2
        private const val scorePrefix = 3

        fun intro() = Prefix(introPrefix)
        fun test() = Prefix(testPrefix)
        fun score() = Prefix(scorePrefix)
        fun fromId(id: Int) = Prefix(id / 100)
    }
}

interface ScreenEntry : ScreenState {

    fun isStartDestination() : Boolean

    fun root() : View

    fun push(parent: ViewGroup, oldRoot: View?, onAnim: (parent: ViewGroup, newRoot: View, oldRoot: View?) -> Unit)
    fun pop(oldRoot: View?)

    fun pushNext(parent: ViewGroup, onAnim: (parent: ViewGroup, root: View) -> Unit) {}
//    fun pushPrev(parent: ViewGroup, onAnim: (parent: ViewGroup, root: View) -> Unit) {}
    fun pushPrev(parent: ViewGroup)
    fun popPrev(onAnim: (root: View) -> Unit) {}
//    fun popCurrent(onAnim: (root: View) -> Unit) {}
    fun popCurrent()

    fun saveArg(appPrefs: LocalPrefsDataSource)

    abstract class Abstract(
        protected val screenVitals: ScreenVitals,
        private val screenArg: ScreenArg,
        protected var id: Int,
    ) : ScreenEntry {

        override fun restoreState(appPrefs: LocalPrefsDataSource) {}
        override fun saveState(appPrefs: LocalPrefsDataSource) {}

        fun changeId(id: Int) {
            this.id = id
        }

        fun id() = id

        override fun root() = root

        abstract fun prefix() : Prefix
        abstract fun view() : View

        override fun clearState(appPrefs: LocalPrefsDataSource) {}

        protected val root by lazy { view() }

        override fun saveArg(appPrefs: LocalPrefsDataSource) { screenArg.save(id, appPrefs) }

        override fun popCurrent() {
            (root.parent as? ViewGroup)?.removeView(root)
        }

        override fun pop(oldScreen: View?) {
            (oldScreen?.parent as? ViewGroup)?.let { parent ->
                parent.addView(root)
                parent.removeView(oldScreen)
            }
        }

        override fun push(
            parent: ViewGroup,
            oldRoot: View?,
            onAnim: (parent: ViewGroup, newRoot: View, oldRoot: View?) -> Unit
        ) {
            onAnim(parent, root, oldRoot)
        }

//        override fun popCurrent(onAnim: (root: View) -> Unit) {
//            onAnim(root)
//        }

        override fun popPrev(onAnim: (root: View) -> Unit) {
            onAnim(root)
        }

        override fun pushNext(parent: ViewGroup, onAnim: (parent: ViewGroup, root: View) -> Unit) {
            onAnim(parent, root)
        }

        override fun pushPrev(parent: ViewGroup) {
            parent.addView(root)
        }

        companion object {
            fun fromId(screenVitals: ScreenVitals, id: Int, appPrefs: LocalPrefsDataSource) : Abstract {
                val prefix = Prefix.fromId(id)
                return when {
                    prefix.isTest() -> TestScreen(screenVitals, ScreenArg.Test.fromId(id, appPrefs), id)
                    prefix.isScore() -> ScoreScreen(screenVitals, ScreenArg.Empty(), id)
                    else -> IntroScreen(screenVitals, ScreenArg.Empty(), id)
                }
            }
        }

        private fun generatedId() = prefix().self() * 100

        fun generateIdBy(screen: Abstract) : Int {
            return if (prefix() == screen.prefix()) screen.id() + 1 else generatedId()
        }

    }
}