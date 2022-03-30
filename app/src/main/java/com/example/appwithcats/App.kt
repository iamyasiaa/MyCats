package com.example.appwithcats

import android.app.Application
import com.example.appwithcats.dagger.modules.AppComponent
import com.example.appwithcats.dagger.modules.DaggerAppComponent
import com.example.appwithcats.data.SharedPreferenceRepository
import com.example.appwithcats.module.Module
import timber.log.Timber


class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        app = this
        val sharedPreferenceRepository = SharedPreferenceRepository(applicationContext)


        appComponent = DaggerAppComponent.builder()
            .module(Module(getString(R.string.BaseUrl), sharedPreferenceRepository.apikey, this))
            .build()
        Timber.plant(Timber.DebugTree())
    }


    companion object {
        private lateinit var app: App

        @Synchronized
        fun getInstance(): App {
            return app
        }
    }
}
