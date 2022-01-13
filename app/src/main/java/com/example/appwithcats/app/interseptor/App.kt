package com.example.appwithcats.app.interseptor

import android.app.Application
import android.content.Context
import com.example.appwithcats.R
import com.example.appwithcats.SharedPreferenceRepository
import com.example.appwithcats.app.interseptor.modules.Module
import android.content.SharedPreferences
import android.util.Log


class App : Application(){
    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        app = this

        appComponent = DaggerAppComponent.builder()
            .module(Module(getString(R.string.BaseUrl), "451b3588-400a-4f2f-b2da-aeec3a44841d", this ))
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
