package com.freeit.empathyquotient

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.freeit.empathyquotient.core.App
import com.freeit.empathyquotient.databinding.ActivityMainBinding
import com.freeit.empathyquotient.presentation.navigator.Navigator
import com.freeit.empathyquotient.presentation.navigator.ScreenStack
import com.freeit.empathyquotient.presentation.navigator.TestStack
import com.freeit.empathyquotient.presentation.screens.intro.ScreenVitals

private fun Int.dp(ctx: Context) = (ctx.resources.displayMetrics.density * this)

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