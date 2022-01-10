package com.example.appwithcats.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appwithcats.app.interseptor.App
import javax.inject.Inject

class MainViewModel (application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
    }
    @Inject
    lateinit var catRepository: MyRepository

    val randomImage: LiveData<MutableList<CatModel>>
        get() = catRepository.getCatLiveData
}