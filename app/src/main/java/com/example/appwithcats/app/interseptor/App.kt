package com.example.appwithcats.app.interseptor

import android.app.Application
import com.example.appwithcats.R
import com.example.appwithcats.app.interseptor.modules.Module

class App : Application(){
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        app = this

        appComponent = DaggerAppComponent.builder()
            .module(Module(getString(R.string.BaseUrl), getString(R.string.ApiKey),this))
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
