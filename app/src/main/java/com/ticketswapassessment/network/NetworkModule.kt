package com.ticketswapassessment.network

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.ticketswapassessment.BuildConfig
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoshiSerializer(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthHttpClient(
        apiKeyQueryInterceptor: ApiKeyQueryInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val cacheSize = 10L * 1024L * 1024L // 10 MB
        val cache = Cache(File(context.cacheDir, "http_cache"), cacheSize)

        val builder = OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(apiKeyQueryInterceptor)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideApiService(
        okHttpClient: Lazy<OkHttpClient>,
        moshi: Moshi
    ): MuseumApi {
        return Retrofit.Builder()
            .client(okHttpClient.get())
            .baseUrl("https://www.rijksmuseum.nl/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MuseumApi::class.java)
    }
}
