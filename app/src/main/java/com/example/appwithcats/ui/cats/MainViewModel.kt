package com.example.appwithcats.ui.cats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appwithcats.App
import com.example.appwithcats.model.CatModel
import com.example.appwithcats.repository.CatRepository
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
    }

    @Inject
    lateinit var catRepository: CatRepository

    val randomImage: LiveData<MutableList<CatModel>>
        get() = catRepository.getCatLiveData
}