package com.example.appwithcats.ui.authorization

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.inresfaces.ISharPref
import com.example.appwithcats.model.PersonalData
import com.example.appwithcats.model.UserModel
import com.example.appwithcats.repository.CatRepository
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
    }

    @Inject
    lateinit var myRepository: CatRepository
    var sharedPreference: ISharPref? = null
        @Inject set

    private var _loginInLiveData = MutableLiveData<UserModel>()
    val loginInLiveData: LiveData<UserModel>
        get() = _loginInLiveData

    fun postRequest() {
        val user =
            PersonalData(sharedPreference!!.email, sharedPreference!!.description)
        myRepository.postLoginIn(user)
            .subscribe({
                _loginInLiveData.value = it
            }, {
                if (it is HttpException) {
                    val body = it.response()?.errorBody()
                    val gson = Gson()
                    val adapter: TypeAdapter<UserModel> =
                        gson.getAdapter(UserModel::class.java)
                    try {
                        val error: UserModel =
                            adapter.fromJson(body?.string())
                        _loginInLiveData.value = error
                    } catch (e: IOException) {
                        Timber.d(e.toString())
                    }
                }
            })
    }

    fun updateEmail(email: String) {
        sharedPreference!!.email = email
    }

    fun updateDescription(description: String) {
        sharedPreference!!.description = description
    }

    fun checkOnStatus(): Boolean {
        if (loginInLiveData.value!!.status == 400) {
            Timber.e("400")
            return true
        } else {
            return false
        }
    }

    fun checkOnEmpty() {
        updateEmail("")
        updateDescription("")
    }
}
