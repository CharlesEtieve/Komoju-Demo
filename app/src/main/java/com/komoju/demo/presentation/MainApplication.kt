package com.komoju.demo.presentation

import android.app.Application
import com.komoju.demo.data.di.dataModule
import com.komoju.demo.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(dataModule, presentationModule)
        }
    }
}