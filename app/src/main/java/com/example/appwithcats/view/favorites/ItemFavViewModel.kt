package com.example.appwithcats.view.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appwithcats.App
import com.example.appwithcats.data.CatRepository
import com.example.appwithcats.data.SharedPreferenceRepository
import com.example.appwithcats.domain.*
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import retrofit2.HttpException
import javax.inject.Inject

class ItemFavViewModel(
    private val onNavigate: (FavoritesModel) -> Unit,
    private val favorite: FavoritesModel,

) : ViewModel() {
    init {
        App.getInstance().appComponent.inject(this)

    }

    @Inject
    lateinit var catRepository: CatRepository

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    private val _vote = MutableLiveData<Boolean?>()
    val vote: LiveData<Boolean?>
        get() = _vote

    private var _favLiveData = MutableLiveData<MutableList<FavoritesModel>>()
    val favLiveData: LiveData<MutableList<FavoritesModel>>
        get() = _favLiveData


    private var _deleteFavoriteLiveData = MutableLiveData<DeleteFavorites>()
    val deleteFavoriteLiveData: LiveData<DeleteFavorites>
        get() = _deleteFavoriteLiveData

    private val _fav = MutableLiveData<Boolean?>()
    val fav: LiveData<Boolean?>
        get() = _fav


    fun deleteFavorite() {
        catRepository.deleteFavorites(favorite.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _deleteFavoriteLiveData.value = it
            }, {
                if (it is HttpException) {
                    val body = it.response()?.errorBody()
                    val gson = Gson()
                    val adapter: TypeAdapter<DeleteFavorites> =
                        gson.getAdapter(DeleteFavorites::class.java)
                    val error: DeleteFavorites = adapter.fromJson(body?.string())
                    _deleteFavoriteLiveData.value = error
                }
            })
    }

    private fun postRequest(vote: Boolean) {
        catRepository.postFavorites(VoteCatsModel(favorite.image.id, vote))
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

   fun postFavorites(fav:Boolean) {
        catRepository.postFavoritesCats(PostFavorites(favorite.image.id))
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


    fun onDeleteClick() {
        favorite.favorites = true
        deleteFavorite()
    }

    fun onClickLike() {
        favorite.image.like = true
        postRequest(true)
    }

    fun onClickDislike() {
        favorite.image.like = false
        postRequest(false)
    }

    fun onImageClicked() {
        onNavigate(favorite)
    }

}