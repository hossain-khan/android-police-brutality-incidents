package com.github.policebrutality.di.module

import com.github.policebrutality.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {
    companion object {
        private const val API_BASE_URL = "" // TODO - define base URL for the data
    }

    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()

    init {
        if (BuildConfig.DEBUG)
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_BASE_URL)
            .client(client)
            .build()
    }
}