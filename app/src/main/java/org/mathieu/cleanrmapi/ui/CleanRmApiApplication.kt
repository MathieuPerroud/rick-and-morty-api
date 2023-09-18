package org.mathieu.cleanrmapi.ui

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.mathieu.cleanrmapi.di.dataModule
import org.mathieu.cleanrmapi.di.domainModule

class CleanRmApiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CleanRmApiApplication)
            modules(dataModule)
            modules(domainModule)
        }
    }
}
