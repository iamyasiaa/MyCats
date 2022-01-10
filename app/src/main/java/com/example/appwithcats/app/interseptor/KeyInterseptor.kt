package com.example.appwithcats.app.interseptor

import okhttp3.Interceptor
import okhttp3.Response

class KeyInterseptor (private val ApiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", ApiKey)
            .method(original.method(), original.body())
            .build()
        return chain.proceed(requestBuilder)
    }
}