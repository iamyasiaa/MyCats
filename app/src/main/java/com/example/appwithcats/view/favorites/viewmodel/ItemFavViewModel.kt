package com.example.appwithcats.view.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appwithcats.App
import com.example.appwithcats.data.SharedPreferenceRepository
import com.example.appwithcats.domain.cats.votes.VoteCatsModel
import com.example.appwithcats.domain.favorites.DeleteFavorites
import com.example.appwithcats.domain.favorites.FavoritesModel
import com.example.appwithcats.view.interfaces.ICatRepo
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
    lateinit var catRepository: ICatRepo


    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    private val _vote = MutableLiveData<Boolean?>()
    val vote: LiveData<Boolean?>
        get() = _vote


    private var _deleteFavoriteLiveData = MutableLiveData<DeleteFavorites>()
    val deleteFavoriteLiveData: LiveData<DeleteFavorites>
        get() = _deleteFavoriteLiveData



    fun deleteFavorite() {
        catRepository!!.deleteFavorites(favorite.id)
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
        catRepository!!.postFavorites(VoteCatsModel(favorite.image.id, vote))
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