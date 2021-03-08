package com.test.kecipirtest.app

import android.app.Application
import com.test.kecipirtest.di.KoinContext
import com.test.kecipirtest.di.module.productModule
import com.test.kecipirtest.di.module.serviceModule
import com.test.kecipirtest.di.module.utilityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application :Application(){
    override fun onCreate() {
        super.onCreate()

        KoinContext.initialize(applicationContext)

        startKoin {
            androidContext(this@Application)
            modules(
                listOf(
                    serviceModule,
                    utilityModule,
                    productModule
                )
            )
        }

    }
}