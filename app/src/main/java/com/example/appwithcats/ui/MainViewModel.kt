package com.example.appwithcats.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appwithcats.App
import com.example.appwithcats.repository.MyRepository
import com.example.appwithcats.model.CatModel
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