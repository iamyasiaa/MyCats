package com.example.appwithcats

import com.example.appwithcats.app.interseptor.modules.Module
import com.example.appwithcats.repository.MyRepository
import com.example.appwithcats.repository.SharedPreferenceRepository
import com.example.appwithcats.ui.ApiKeyViewModel
import com.example.appwithcats.ui.AuthorizationViewModel
import com.example.appwithcats.ui.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(Module::class)])
    interface AppComponent {
        fun inject(mainViewModel: MainViewModel)
        fun inject(myRepository: MyRepository)
        fun inject(AuthorizationViewModel: AuthorizationViewModel)
        fun inject(SharedPreferenceRepository: SharedPreferenceRepository)
        fun inject(ApiKeyViewModel: ApiKeyViewModel)

}