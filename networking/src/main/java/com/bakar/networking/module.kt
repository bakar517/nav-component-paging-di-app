package com.bakar.networking

import com.bakar.baselib.AppConfig
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun getOkHttpClient(appConfig: AppConfig): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (appConfig.isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(Wrapped.ADAPTER_FACTORY)
        .add(KotlinJsonAdapterFactory())
        .build()


    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        baseUrl: BaseUrl
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl())
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory
                    .create(moshi)
            )
            .build()
}
