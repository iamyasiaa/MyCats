package com.example.appwithcats.dagger.modules

import com.example.appwithcats.view.interfaces.ISharPref
import com.example.appwithcats.data.CatRepository
import com.example.appwithcats.module.Module
import com.example.appwithcats.view.apikey.viewmodel.ApiKeyViewModel
import com.example.appwithcats.view.authrization.viewmodel.AuthorizationViewModel
import com.example.appwithcats.view.cats.viemodel.CatViewModel
import com.example.appwithcats.view.cats.viemodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(Module::class)])
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
    fun inject(catRepository: CatRepository)
    fun inject(AuthorizationViewModel: AuthorizationViewModel)
    fun inject(sharedPreference: ISharPref)
    fun inject(ApiKeyViewModel: ApiKeyViewModel)
    fun inject(catViewModel: CatViewModel)

}