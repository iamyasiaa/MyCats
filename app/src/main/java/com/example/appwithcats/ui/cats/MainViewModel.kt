package com.example.appwithcats.ui.cats

import android.app.Application
import android.util.Log
import android.util.MutableDouble
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.inresfaces.ISharPref
import com.example.appwithcats.model.CatModel
import com.example.appwithcats.repository.CatRepository
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
        postRequest()
    }

    var sharedPreference: ISharPref? = null
        @Inject set


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