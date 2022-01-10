package com.example.appwithcats.app.interseptor.modules

import com.example.appwithcats.app.interseptor.Api
import com.example.appwithcats.app.interseptor.KeyInterseptor
import com.example.appwithcats.domain.MyRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class  Module (private val BaseUrl: String, private val ApiKey: String) {
    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesRepository(api: Api): MyRepository {
        return MyRepository(api)
    }

    @Provides
    @Singleton
    fun providesAPI(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(interceptor)
        okHttpClient.addInterceptor(KeyInterseptor(ApiKey))
        return okHttpClient.build()
    }

}