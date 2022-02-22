package com.freeit.empathyquotient.core

import android.app.Application
import com.freeit.empathyquotient.LifecycleEvents
import com.freeit.empathyquotient.SaveStateEvents
import com.freeit.empathyquotient.data.QuestionDataSource

class App: Application() {

    val localPrefsDataSource by lazy {
        LocalPrefsDataSource(this)
    }

    val questionDataSource by lazy {
        QuestionDataSource.Base(this)
    }

    var saveStateEvents = SaveStateEvents()
    var lifecycleEvents = LifecycleEvents()

}