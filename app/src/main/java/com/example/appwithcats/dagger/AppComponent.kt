package com.example.appwithcats.dagger

import com.example.appwithcats.module.Module
import com.example.appwithcats.repository.CatRepository
import com.example.appwithcats.repository.SharedPreferenceRepository
import com.example.appwithcats.ui.apiKey.ApiKeyViewModel
import com.example.appwithcats.ui.authorization.AuthorizationViewModel
import com.example.appwithcats.ui.cats.MainViewModel
import com.example.appwithcats.ui.cats.VoteViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(Module::class)])
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
    fun inject(catRepository: CatRepository)
    fun inject(AuthorizationViewModel: AuthorizationViewModel)
    fun inject(SharedPreferenceRepository: SharedPreferenceRepository)
    fun inject(ApiKeyViewModel: ApiKeyViewModel)
    fun inject(voteViewModel: VoteViewModel)

}