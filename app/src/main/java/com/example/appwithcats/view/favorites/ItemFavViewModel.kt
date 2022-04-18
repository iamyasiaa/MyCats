package com.example.appwithcats.view.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appwithcats.App
import com.example.appwithcats.data.CatRepository
import com.example.appwithcats.data.SharedPreferenceRepository
import com.example.appwithcats.domain.DeleteFavorites
import com.example.appwithcats.domain.FavoritesModel
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import retrofit2.HttpException
import javax.inject.Inject

class ItemFavViewModel(private val favorite: FavoritesModel) : ViewModel() {
    init {
        App.getInstance().appComponent.inject(this)

    }

    @Inject
    lateinit var catRepository: CatRepository
    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository



    private var _deleteFavoriteLiveData = MutableLiveData<DeleteFavorites>()
    val deleteFavoriteLiveData: LiveData<DeleteFavorites>
        get() = _deleteFavoriteLiveData

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

    fun onDeleteClick(){
        favorite.favorites = true
        deleteFavorite()
    }
}