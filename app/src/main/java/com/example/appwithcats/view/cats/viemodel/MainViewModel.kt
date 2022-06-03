package com.example.appwithcats.view.cats.viemodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.example.appwithcats.App
import com.example.appwithcats.data.CatRepository
import com.example.appwithcats.domain.cats.CatModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    init {
        App.getInstance().appComponent.inject(this)
        postRequest()
    }

    @Inject
    lateinit var catRepository: CatRepository

    private var _catsLiveData = MutableLiveData<PagingData<CatModel>>()
    val catsLiveData: LiveData<PagingData<CatModel>>
        get() = _catsLiveData


    fun postRequest() {
        catRepository.getCatList()
            .observeOn(AndroidSchedulers.mainThread())
            .map { it }
            .cachedIn(viewModelScope)
            .subscribe {
                _catsLiveData.value = it
            }
    }


}