package com.example.appwithcats.view.cats.viemodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.domain.CatModel
import com.example.appwithcats.data.CatRepository
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    init {
        App.getInstance().appComponent.inject(this)
        postRequest()
    }

    @Inject
    lateinit var catRepository: CatRepository

    private var _catsLiveData = MutableLiveData<MutableList<CatModel>>()
    val catsLiveData: LiveData<MutableList<CatModel>>
        get() = _catsLiveData


    fun postRequest() {
        val count = 10
        catRepository.getCatList(count)
            .subscribe({
                _catsLiveData.value = it
            }, {
                Log.e("Ошибка", "Error")
            })
    }


}