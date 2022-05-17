package com.example.appwithcats.interseptor


import com.example.appwithcats.data.SharedPreferenceRepository
import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor(private val sharedPreference: SharedPreferenceRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val builder = request.newBuilder()

        val apikey = sharedPreference.apikey

        if(apikey.isNotBlank()) {
            builder.addHeader("x-api-key", apikey)
        }

        return chain.proceed(builder.build())
    }
}