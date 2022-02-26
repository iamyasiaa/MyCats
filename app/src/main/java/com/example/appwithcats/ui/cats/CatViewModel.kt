package com.example.appwithcats.ui.cats



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.model.CatModel
import com.example.appwithcats.model.VoteCatsModel
import com.example.appwithcats.repository.CatRepository
import javax.inject.Inject

class CatViewModel(private val onNavigate: (CatModel) -> Unit, private val cat: CatModel) {

    init {
        App.getInstance().appComponent.inject(this)
    }


    @Inject
    lateinit var catRepository: CatRepository


    private val _vote = MutableLiveData<Boolean?>()
    val vote: LiveData<Boolean?>
        get() = _vote




    private fun postRequest(vote: Boolean) {
        catRepository.postFavorites(VoteCatsModel(cat.id, vote))
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


}