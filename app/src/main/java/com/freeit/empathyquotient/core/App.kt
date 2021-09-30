package com.freeit.empathyquotient.core

import android.app.Application
import com.freeit.empathyquotient.LifecycleEvents
import com.freeit.empathyquotient.SaveStateEvents
import com.freeit.empathyquotient.data.QuestionDataSource

// Android App class
class App: Application() {

    val fontManager by lazy {
        FontManager(this)
    }

    val localPrefsDataSource by lazy {
        LocalPrefsDataSource(this)
    }

    val questionDataSource by lazy {
        QuestionDataSource.Base(this)
    }

    var saveStateEvents = SaveStateEvents()
    var lifecycleEvents = LifecycleEvents()

}