package com.bakar.carinfo

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.bakar.carinfo.di.AppInjector
import com.bakar.carinfo.di.DaggerTestComponent

class EspressoTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(cl, TestApp::class.java.name, context)
}

class TestApp : App() {

    override fun onCreate() {
        super.onCreate()
        AppInjector.initWith(
            DaggerTestComponent
                .builder()
                .appConfig(createAppConfigs())
                .build()
        )
    }
}