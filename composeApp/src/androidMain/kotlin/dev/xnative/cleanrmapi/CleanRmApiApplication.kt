package dev.xnative.cleanrmapi

import android.app.Application
import org.koin.android.ext.koin.androidContext

class CleanRmApiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CleanRmApiApplication)
        }
    }
}
