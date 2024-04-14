package org.mathieu.cleanrmapi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.mathieu.cleanrmapi.data.dataModule
import org.mathieu.cleanrmapi.ui.uiModule

class CleanRmApiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CleanRmApiApplication)
            modules(dataModule)
            modules(uiModule)
        }
    }
}
