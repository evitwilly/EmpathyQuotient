package com.freeit.empathyquotient

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.freeit.empathyquotient.core.App
import com.freeit.empathyquotient.core.navigator.Navigator
import com.freeit.empathyquotient.core.navigator.ScreenStack
import com.freeit.empathyquotient.core.navigator.TestStack
import com.freeit.empathyquotient.core.navigator.ScreenVitals
import ru.freeit.noxml.extensions.frameLayout
import ru.freeit.noxml.extensions.layoutParams
import ru.freeit.noxml.extensions.viewGroupLayoutParams

class MainActivity : AppCompatActivity() {

    private var navigator: Navigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigatorBox = frameLayout {
            layoutParams(viewGroupLayoutParams().match().build())
        }
        setContentView(navigatorBox)

        val appPrefs = (application as App).localPrefsDataSource

        navigator = Navigator.Base(
            ScreenVitals.Base(navigatorBox, this),
            ScreenStack.Base(appPrefs, TestStack.Base(appPrefs)),
            TestStack.Base(appPrefs),
            (application as App).localPrefsDataSource
        )

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
        if (navigator?.back() != false) {
            super.onBackPressed()
        }
    }

}