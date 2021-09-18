package com.bakar.carinfo.di

import com.bakar.baselib.AppConfig
import com.bakar.carinfo.service.ApiService
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InternalModule::class,
        MockServiceModule::class,
    ],
    dependencies = [AppConfig::class]

)
interface TestComponent : AppComponent {

}

@Module
class MockServiceModule() {
    @Provides
    fun apiService(): ApiService = MockApiService()

}

