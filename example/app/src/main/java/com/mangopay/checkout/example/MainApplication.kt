package com.mangopay.checkout.example

import android.app.Application
import com.mangopay.android.core.MangopaySdk
import com.mangopay.android.core.model.objectclass.TenantId
import com.mangopay.android.core.util.Environment
import com.mangopay.android.core.util.LogLevel
import com.mangopay.checkout.example.di.demoAppModule
import com.mangopay.checkout.example.model.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        MangopaySdk.initialize(
            context = this,
            tenantId = TenantId.EU,
            profilingMerchantId = Constants.FF_PROFILLING_MERCHANT_ID,
            clientId = Constants.CLIENT_ID,
            environment = Environment.SANDBOX,
            logLevel = LogLevel.Basic
        )

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(demoAppModule)
        }
    }
}
