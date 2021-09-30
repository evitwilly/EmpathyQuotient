package com.freeit.empathyquotient.presentation.navigator

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.freeit.empathyquotient.core.LocalPrefsDataSource
import com.freeit.empathyquotient.presentation.screens.ScreenEntry
import com.freeit.empathyquotient.presentation.screens.intro.ScreenVitals
import com.freeit.empathyquotient.presentation.screens.test.State

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
            appPrefs.save("${id}_question_id", questionId)
        }
    }
}

interface Navigator {
    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<ScreenEntry.Abstract>)
    fun navigate(screenBuilder: (screenVitals: ScreenVitals, arg: ScreenArg, id: Int) -> ScreenEntry.Abstract,
                 arg: ScreenArg,
                 popToInclusive: Int,
                 onPush: (parent: ViewGroup, newRoot: View, oldRoot: View?) -> Unit)

    fun back(): Boolean

    class Empty : Navigator {
        override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<ScreenEntry.Abstract>) {}
        override fun navigate(screenBuilder: (screenVitals: ScreenVitals, arg: ScreenArg, id: Int) -> ScreenEntry.Abstract,
                              arg: ScreenArg,
                              onPopToInclusive: Int,
                              onPush: (parent: ViewGroup, newRoot: View, oldRoot: View?) -> Unit,) {}
        override fun back() = false
    }

    class Base(
        private val screenVitals: ScreenVitals,
        private val screenStack: ScreenStack,
        private val testStack: TestStack,
        private val appPrefs: LocalPrefsDataSource
    ) : Navigator {

        private val screenVitalsWithNavigator = screenVitals.copyWith(this)
        private val _screen = mutableListOf<ScreenEntry.Abstract>()

        init {
            val lastScreen = screenStack.last(screenVitalsWithNavigator)
            lastScreen.restoreState(appPrefs)
            lastScreen.push(screenVitalsWithNavigator.parent(), null) { parent, newRoot, _ -> parent.addView(newRoot) }
            _screen.add(lastScreen)
        }

        override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<ScreenEntry.Abstract>) {

        }

        private val defaultId = 0

        override fun navigate(
            screenBuilder: (screenVitals: ScreenVitals, arg: ScreenArg, id: Int) -> ScreenEntry.Abstract,
            arg: ScreenArg,
            popToInclusive: Int,
            onPush: (parent: ViewGroup, newRoot: View, oldRoot: View?) -> Unit,
        ) {

            if (popToInclusive > 0) {
                var screen: ScreenEntry.Abstract = screenStack.pop(screenVitals)
                while (screen.id() != popToInclusive) {
                    screen = screenStack.pop(screenVitals)
                }
            }

            val newScreen = screenBuilder(screenVitalsWithNavigator, arg, defaultId)
            val newId = newScreen.generateIdBy(screenStack.last(screenVitals))
            newScreen.changeId(newId)
            if (testStack.exists(newScreen)) {
                newScreen.restoreState(appPrefs)
            }

            val prevScreen = _screen.firstOrNull()
            prevScreen?.run {
                saveState(appPrefs)
                testStack.save(this)
            }

            val oldRoot = prevScreen?.root()

            _screen.clear()
            _screen.add(newScreen)

            newScreen.push(screenVitalsWithNavigator.parent(), oldRoot, onPush)
            screenStack.save(screenVitalsWithNavigator, newScreen)
        }

        override fun back(): Boolean {
            val screenEntry = screenStack.last(screenVitalsWithNavigator)
            if (screenEntry.isStartDestination()) {
                return true
            }
            screenStack.pop(screenVitalsWithNavigator)

            val prevScreen = _screen.firstOrNull()
            prevScreen?.run {
                saveState(appPrefs)
                testStack.save(this)
            }

            val newScreen = screenStack.last(screenVitalsWithNavigator)
            newScreen.restoreState(appPrefs)
            _screen.clear()
            _screen.add(newScreen)

            newScreen.pop(prevScreen?.root())

            return false
        }

    }
}