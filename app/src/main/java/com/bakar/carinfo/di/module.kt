package com.bakar.carinfo.di

import com.bakar.baselib.ErrorLog
import com.bakar.baselib.Navigator
import com.bakar.carinfo.navigation.RealNavigation
import com.bakar.carinfo.service.ApiService
import com.bakar.carinfo.util.ErrorLogImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create


@Module
class InternalModule {
    @Provides
    fun provideErrorLog(logImp: ErrorLogImp): ErrorLog = logImp

    @Provides
    fun providesIoDispatchers(dispatchers: RealDispatchers): Dispatchers = dispatchers

}

@Module
class ServiceModule {
    @Provides
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create()
}

@Module
interface RetainedModule {
    @Binds
    fun bindRealNavigator(realNavigation: RealNavigation): Navigator
}