package com.bakar.carinfo.di

import com.bakar.carinfo.BuildConfig
import javax.inject.Inject

class WebApiKey @Inject constructor() : () -> String {
    override fun invoke() = BuildConfig.WEB_KEY
}