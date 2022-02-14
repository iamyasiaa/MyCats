package com.example.appwithcats.ui.cats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.Util
import com.example.appwithcats.inresfaces.ISharPref
import com.example.appwithcats.model.VoteCatsModel
import com.example.appwithcats.model.VoteModel
import com.example.appwithcats.repository.CatRepository
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class VoteViewModel {

    init {
        App.getInstance().appComponent.inject(this)
    }
    var sharedPreference: ISharPref? = null
        @Inject set

    @Inject
    lateinit var catRepository: CatRepository

    private var _voteInLiveData = MutableLiveData<VoteModel>()
    val voteInLiveData: LiveData<VoteModel>
        get() = _voteInLiveData


    fun postRequest() {
        val vote = VoteCatsModel(Util.id, sharedPreference!!.vote)
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
        sharedPreference!!.vote = true
    }

    fun updateDislike() {
        sharedPreference!!.vote = false
    }
}