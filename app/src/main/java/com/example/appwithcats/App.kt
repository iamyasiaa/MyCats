package com.example.appwithcats

import android.app.Application
import com.example.appwithcats.module.Module
import com.example.appwithcats.dagger.AppComponent
import com.example.appwithcats.dagger.DaggerAppComponent
import com.example.appwithcats.repository.SharedPreferenceRepository


class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        app = this
        val sharedPreferenceRepository = SharedPreferenceRepository(applicationContext)


        appComponent = DaggerAppComponent.builder()
            .module(Module(getString(R.string.BaseUrl), sharedPreferenceRepository.apikey, this))
            .build()
    }

    companion object {
        private lateinit var app: App

        @Synchronized
        fun getInstance(): App {
            return app
        }
    }
}
