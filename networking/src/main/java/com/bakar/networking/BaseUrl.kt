package com.bakar.networking

import com.bakar.baselib.AppConfig
import com.bakar.baselib.Environment
import javax.inject.Inject

class BaseUrl @Inject constructor(
    private val appConfig: AppConfig
) : () -> String {
    override fun invoke() = when (appConfig.env) {
        Environment.PROD -> "https://raw.githubusercontent.com/bakar517/nav-component-paging-di-app/main/response/"
        Environment.STAGING -> error("No available at the moment!!")
    }
}