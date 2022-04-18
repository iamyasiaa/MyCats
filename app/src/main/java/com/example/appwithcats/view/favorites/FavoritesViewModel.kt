package com.example.appwithcats.view.favorites

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.example.appwithcats.App
import com.example.appwithcats.data.CatRepository
import com.example.appwithcats.data.SharedPreferenceRepository
import com.example.appwithcats.domain.*
import com.google.gson.Gson
import com.google.gson.TypeAdapter

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import retrofit2.HttpException
import javax.inject.Inject

class FavoritesViewModel (application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
        postRequest()
    }


    @Inject
    lateinit var catRepository: CatRepository

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    private var _favLiveData = MutableLiveData<MutableList<FavoritesModel>>()
    val favLiveData: LiveData<MutableList<FavoritesModel>>
        get() = _favLiveData


fun postRequest() {
    val count = 30
    catRepository.getFavoritesCats(count)
        .subscribe({
            _favLiveData.value = it
        }, {
            Log.e("Ошибка", "Error")
        })
}



}