package com.example.appwithcats.ui.cats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.model.CatModel
import com.example.appwithcats.model.VoteCatsModel
import com.example.appwithcats.model.VoteModel
import com.example.appwithcats.repository.CatRepository
import com.example.appwithcats.repository.SharedPreferenceRepository
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
    }

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository


    @Inject
    lateinit var catRepository: CatRepository
    val randomImage: LiveData<MutableList<CatModel>>
        get() = catRepository.getCatLiveData

    private var _voteInLiveData = MutableLiveData<VoteModel>()
        get() = _voteInLiveData


    fun postRequest() {
        val vote = VoteCatsModel(com.example.appwithcats.Util.id, sharedPreferenceRepository.vote)
        catRepository.postFavorites(vote)
            .subscribe({
                _voteInLiveData.value = it
            }, {
                if (it is HttpException) {
                    val body = it.response()?.errorBody()
                    val gson = Gson()
                    val adapter: TypeAdapter<VoteModel> = gson.getAdapter(VoteModel::class.java)
                    try {
                        val error: VoteModel = adapter.fromJson(body?.string())
                        _voteInLiveData.value = error
                    } catch (e: IOException) {
                        Timber.d(e.toString())
                    }
                }

            })
    }

    fun updateLike() {
        sharedPreferenceRepository.vote = true
    }

    fun updateDislike() {
        sharedPreferenceRepository.vote = false
    }

}