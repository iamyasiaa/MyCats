package com.example.appwithcats.interseptor

import com.example.appwithcats.ui.apiKey.KeyApi
import okhttp3.Interceptor
import okhttp3.Response

class KeyInterseptor(private val keyApi: KeyApi) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", keyApi.toString())
            .method(original.method(), original.body())
            .build()
        return chain.proceed(requestBuilder)
    }
}