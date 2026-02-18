package dev.xnative.cleanrmapi

import android.app.Application
import dev.xnative.cleanrmapi.app.di.initKoin
import org.koin.android.ext.koin.androidContext

class CleanRmApiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CleanRmApiApplication)
        }
    }
}
