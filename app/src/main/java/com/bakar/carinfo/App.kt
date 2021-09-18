package com.bakar.carinfo

import android.app.Application
import com.bakar.baselib.AppConfig
import com.bakar.baselib.BuildInfo
import com.bakar.baselib.Environment
import com.bakar.carinfo.di.AppInjector
import timber.log.Timber

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppInjector.init(createAppConfigs())
    }

    fun createAppConfigs() = AppConfig(
        isDebug = BuildConfig.DEBUG,
        env = Environment.PROD,
        buildInfo = BuildInfo(
            version = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE
        )
    )
}