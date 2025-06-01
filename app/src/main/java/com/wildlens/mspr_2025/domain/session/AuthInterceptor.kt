package com.wildlens.mspr_2025.domain.session

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        println("AuthInterceptor: Adding Authorization header with API key")
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .build()
        return chain.proceed(request)
    }
}
