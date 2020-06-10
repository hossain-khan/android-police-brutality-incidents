package com.blacklivesmatter.policebrutality.di.module

import com.blacklivesmatter.policebrutality.BuildConfig
import com.blacklivesmatter.policebrutality.api.IncidentApi
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
        /**
         * Base URL for 8:46 police brutality API.
         *
         * See
         * - https://github.com/949mac/846-backend#api
         * - https://github.com/949mac/846-backend/issues/6
         */
        private const val API_BASE_URL = "https://api.846policebrutality.com/api/"
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
        // https://square.github.io/retrofit/
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(API_BASE_URL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideIncidentApi(retrofit: Retrofit): IncidentApi {
        return retrofit.create(IncidentApi::class.java)
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
