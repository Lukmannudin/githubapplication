package com.lukmannudin.githubapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lukmannudin.githubapp.BuildConfig
import com.lukmannudin.githubapp.common.Keys
import com.lukmannudin.githubapp.data.user.remote.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext appContext: Context): Retrofit {
        val mLoggingInterceptor = HttpLoggingInterceptor()

        val chuckerInterceptor = ChuckerInterceptor.Builder(appContext)
            .collector(ChuckerCollector(appContext))
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "User-Agent", Keys.getUserAgent(),
                    )
                    .addHeader(
                        "Authorization", Keys.getToken())
                    .build()
                chain.proceed(request)
            }
            .build()

        mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory((CoroutineCallAdapterFactory()))
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): UserApiService = retrofit.create(
        UserApiService::class.java
    )
}