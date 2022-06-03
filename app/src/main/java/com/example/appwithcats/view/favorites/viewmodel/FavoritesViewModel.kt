package com.example.appwithcats.view.favorites.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.data.SharedPreferenceRepository
import com.example.appwithcats.domain.cats.votes.GetVotes
import com.example.appwithcats.domain.favorites.FavoritesModel
import com.example.appwithcats.view.interfaces.ICatRepo

import javax.inject.Inject

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
        postRequest()
    }


    @Inject
    lateinit var catRepository: ICatRepo


    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    private var _favLiveData = MutableLiveData<MutableList<FavoritesModel>>()
    val favLiveData: LiveData<MutableList<FavoritesModel>>
        get() = _favLiveData
    private val _votes = MutableLiveData<GetVotes>()
    val votes: LiveData<GetVotes>
        get() = _votes


    fun postRequest() {
        val count = 100
        catRepository!!.getFavoritesCats(count)
            .subscribe({
                _favLiveData.value = it
            }, {
                Log.e("Ошибка", "Error")
            })
    }

    fun postRequestVotes() {
        catRepository!!.getVotesCats()
            .subscribe({
                _votes.value = it
            }, {
                Log.e("Ошибка", "Error")
            })
    }


}