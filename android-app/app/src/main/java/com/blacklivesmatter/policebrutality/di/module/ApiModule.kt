package com.blacklivesmatter.policebrutality.di.module

import com.blacklivesmatter.policebrutality.BuildConfig
import com.blacklivesmatter.policebrutality.data.OffsetDateTimeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.OffsetDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(API_BASE_URL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        val gson = GsonBuilder()
            .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeConverter())
            .create()
        return gson
    }
}
