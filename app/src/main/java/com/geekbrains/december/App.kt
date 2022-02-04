package com.geekbrains.december

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        appInstance = this //запускаем

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    /**
     * Для базы данных
     */

    companion object{
        @SuppressLint( "StaticFieldLeak")
        lateinit var appInstance: Context
    }
}