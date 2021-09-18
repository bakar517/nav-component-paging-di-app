package com.bakar.baselib

class AppConfig(
    val isDebug: Boolean,
    val env: Environment,
    val buildInfo: BuildInfo,
)

data class BuildInfo(
    val version: String,
    val versionCode: Int
)

enum class Environment {
    STAGING, PROD
}
