package com.skymeter.skymeterapp

import android.app.Application
import com.skymeter.skymeterapp.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidLogger(Level.NONE)//Level.NONE
            androidContext(this@BaseApp)
            modules(
                listOf(
                    Modules.modules
                )
            )

        }


    }
}