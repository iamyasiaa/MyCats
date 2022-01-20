package com.example.appwithcats

import android.app.Application
import android.util.Log
import com.example.appwithcats.app.interseptor.DaggerAppComponent
import com.example.appwithcats.app.interseptor.modules.Module
import com.example.appwithcats.dagger.AppComponent
import com.example.appwithcats.repository.SharedPreferenceRepository

class App : Application(){
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        app = this
        val sharedPreferenceRepository = SharedPreferenceRepository(applicationContext)

        appComponent = DaggerAppComponent.builder()
            .module(Module(getString(R.string.BaseUrl), sharedPreferenceRepository.apikey ,this))
            .build()

        Log.e("app", "" + sharedPreferenceRepository.apikey)
    }

    companion object {
        private lateinit var app: App
        @Synchronized
        fun getInstance(): App {
            return app
        }
    }
}
