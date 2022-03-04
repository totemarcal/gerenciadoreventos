package com.example.gerenciadordeeventos

import android.app.Application
import com.example.gerenciadordeeventos.helper.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class EventApplication : Application() {

    init {
        appInstance = this
    }

    companion object {
        private var appInstance: EventApplication? = null
        fun getInstance(): EventApplication {
            return appInstance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@EventApplication)
            modules(mainModule)
        }
    }

}