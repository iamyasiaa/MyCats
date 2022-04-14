package com.example.appwithcats.view.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.example.appwithcats.App
import com.example.appwithcats.data.CatRepository

import com.example.appwithcats.domain.FavoritesModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavoritesViewModel (application: Application) : AndroidViewModel(application){
    init {
        App.getInstance().appComponent.inject(this)
        postRequest()
    }

    @Inject
    lateinit var catRepository: CatRepository

    private var _favLiveData = MutableLiveData<PagingData<FavoritesModel>>()
    val favLiveData: LiveData<PagingData<FavoritesModel>>
        get() = _favLiveData


    fun postRequest() {
        catRepository.getFavoritesList()
            .observeOn(AndroidSchedulers.mainThread())
            .map { it }
            .cachedIn(viewModelScope)
            .subscribe {
                _favLiveData.value = it
            }
    }
}