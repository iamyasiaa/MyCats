package com.example.appwithcats.view.cats.viemodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.domain.CatModel
import com.example.appwithcats.data.CatRepository
import com.example.appwithcats.domain.PostFavorites
import com.example.appwithcats.domain.VoteCatsModel
import com.example.appwithcats.view.interfaces.ICatRepo
import com.example.appwithcats.view.interfaces.ISharPref
import javax.inject.Inject

class CatViewModel(private val onNavigate: (CatModel) -> Unit, private val cat: CatModel) {

    init {
        App.getInstance().appComponent.inject(this)
    }


    @Inject
    lateinit var catRepository: ICatRepo


    private val _vote = MutableLiveData<Boolean?>()
    val vote: LiveData<Boolean?>
        get() = _vote

    private val _fav = MutableLiveData<Boolean?>()
    val fav: LiveData<Boolean?>
        get() = _fav

    private fun postRequest(vote: Boolean) {
        catRepository!!.postFavorites(VoteCatsModel(cat.id, vote))
            .subscribe({
                if (it.message.lowercase() == "success") {
                    _vote.value = vote

                } else {
                    _vote.value = null
                }
            }, {
                _vote.value = null
            })
    }

    private fun postFavorites(fav:Boolean) {
        catRepository!!.postFavoritesCats(PostFavorites(cat.id))
            .subscribe({
                if (it.message.lowercase() == "success") {
                    _fav.value = fav

                } else {
                    _fav.value = null
                }
            }, {
                _fav.value = null
            })
    }


    fun onLikeClicked() {
        cat.like = true
        postRequest(true)
    }

    fun onDislikeClicked() {
        cat.like = false
        postRequest(false)
    }

    fun onImageClicked() {
        onNavigate(cat)
    }

    fun onFavoritesClick(){
        cat.favorites = true
        postFavorites(true)
    }


}