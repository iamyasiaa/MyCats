package com.example.appwithcats.app.interseptor

import com.example.appwithcats.domain.MainViewModel
import com.example.appwithcats.domain.MyRepository
import com.example.appwithcats.app.interseptor.modules.Module
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(Module::class)])
    interface AppComponent {
        fun inject(mainViewModel: MainViewModel)
        fun inject(myRepository: MyRepository)

}