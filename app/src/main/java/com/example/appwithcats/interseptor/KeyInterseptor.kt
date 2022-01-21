package com.example.appwithcats.interseptor


import okhttp3.Interceptor
import okhttp3.Response

class KeyInterseptor(private val keyApi: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", keyApi)
            .method(original.method(), original.body())
            .build()
        return chain.proceed(requestBuilder)
    }
}