package com.example.appwithcats.dagger.modules

import android.app.Application
import android.content.Context
import com.example.appwithcats.data.SharedPreferenceRepository
import com.example.appwithcats.dagger.api.Api
import com.example.appwithcats.view.interfaces.ISharPref

import com.example.appwithcats.data.CatRepository
import com.example.appwithcats.interseptor.KeyInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class Module(
    private val baseUrl: String,
    private val apiKey: String,
    private val application: Application,
    private val sharedPreference: SharedPreferenceRepository
) {
    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }


    @Provides
    @Singleton
    fun providesRepository(api: Api): CatRepository {
        return CatRepository(api)
    }

    @Provides
    @Singleton
    fun providesContext(): Context = application

    @Provides
    @Singleton
    fun provideSPRepository(context: Context): SharedPreferenceRepository {
        return SharedPreferenceRepository(context)
    }
    @Provides
    @Singleton
    fun provideSPI(context: Context): ISharPref {
        return SharedPreferenceRepository(context)
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
        okHttpClient.run {
            readTimeout((60*2).toLong(), TimeUnit.SECONDS)
            connectTimeout((60*2).toLong(), TimeUnit.SECONDS)
            writeTimeout((60*2).toLong(), TimeUnit.SECONDS)
            addInterceptor(KeyInterceptor(sharedPreference))
            addInterceptor(interceptor)
        }
        return okHttpClient.build()
    }

}