package com.ticketswapassessment.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyQueryInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("key", "tHMCJ69E")
            .build()

        val request = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
