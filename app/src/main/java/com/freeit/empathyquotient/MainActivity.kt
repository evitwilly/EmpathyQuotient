package com.freeit.empathyquotient

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.freeit.empathyquotient.core.App
import com.freeit.empathyquotient.databinding.ActivityMainBinding
import com.freeit.empathyquotient.presentation.navigator.Navigator
import com.freeit.empathyquotient.presentation.navigator.ScreenStack
import com.freeit.empathyquotient.presentation.navigator.TestStack
import com.freeit.empathyquotient.presentation.screens.intro.ScreenVitals
import com.freeit.empathyquotient.presentation.view.RippleImageButton

private fun Int.dp(ctx: Context) = (ctx.resources.displayMetrics.density * this)

interface SaveObserver {
    fun onSaveInstanceState(outState: Bundle)
    fun onRestoreInstanceState(savedInstanceState: Bundle)
}

interface ActivityLifecycle {
    fun onStop()
    fun onPause()
    fun onStart()
    fun onResume()
}

class LifecycleEvents {
    private val observers = mutableListOf<ActivityLifecycle>()

    fun insertEventObserver(observer: ActivityLifecycle) { observers.add(observer) }
    fun removeEventObserver(observer: ActivityLifecycle) { observers.remove(observer) }
    fun clear() { observers.clear() }

    fun onStop() { observers.forEach { observer -> observer.onStop() } }
    fun onPause() { observers.forEach { observer -> observer.onPause() } }
    fun onResume() { observers.forEach { observer -> observer.onResume() } }
    fun onStart() { observers.forEach { observer -> observer.onStart() } }
}

class SaveStateEvents {
    private val observers = mutableListOf<SaveObserver>()

    fun addObserver(observer: SaveObserver) {
        this.observers.add(observer)
    }

    fun removeObserver(observer: SaveObserver) {
        this.observers.remove(observer)
    }

    fun onSaveInstanceState(outState: Bundle) {
        this.observers.forEach {
            it.onSaveInstanceState(outState)
        }
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        this.observers.forEach {
            it.onRestoreInstanceState(savedInstanceState)
        }
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appPrefs = (application as App).localPrefsDataSource

        val navigator = Navigator.Base(
            ScreenVitals.Base(binding.navigatorBox, this),
            ScreenStack.Base(appPrefs, TestStack.Base(appPrefs)),
            TestStack.Base(appPrefs),
            (application as App).localPrefsDataSource
        )

        viewModel = ViewModelProvider(this, MainViewModelFactory(navigator)).get(MainViewModel::class.java)

    }

    override fun onPause() {
        super.onPause()
        (application as App).lifecycleEvents.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (application as App).saveStateEvents.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        (application as App).saveStateEvents.onRestoreInstanceState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (viewModel.isBackPressed()) {
            super.onBackPressed()
        }
    }

}