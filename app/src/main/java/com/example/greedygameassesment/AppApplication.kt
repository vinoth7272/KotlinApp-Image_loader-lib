package com.example.greedygameassesment

import android.app.Application
import com.example.greedygameassesment.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppApplication)
            modules(listOf(retrofitModule))
        }
    }
}